package com.example.tt;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Game extends AppCompatActivity {

    private SQLiteDatabase db;
    static RequestQueue requestQueue;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String audioFilePath;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    private String language;
    private Button voiceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // 데이터베이스 열기
        db = openOrCreateDatabase("tongue_twisters.db", MODE_PRIVATE, null);

        voiceButton = findViewById(R.id.voice_button);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                    sendAudioFile();
                } else {
                    startRecording();
                }
            }
        });

        // Intent에서 언어 정보 가져오기
        Intent intent = getIntent();
        language = intent.getStringExtra("language");

        // 언어에 따라 SQL 쿼리 생성
        String query = "SELECT phrase FROM tongue_twisters WHERE language = ?";
        String[] selectionArgs = {language};

        // 쿼리 실행 및 결과 처리
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            int phraseIndex = cursor.getColumnIndex("phrase");
            int phraseCount = cursor.getCount();
            Random random = new Random();
            int randomIndex = random.nextInt(phraseCount);

            // 랜덤한 문자열 가져오기
            cursor.moveToPosition(randomIndex);
            String randomPhrase = cursor.getString(phraseIndex);

            // TextView에 문자열 표시
            TextView phraseTextView = findViewById(R.id.target_text_view);
            phraseTextView.setText(randomPhrase);
        }

        cursor.close();


    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 데이터베이스 닫기
        db.close();
    }



    // 오디오 녹음 시작
    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            return;
        }

        audioFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/" + UUID.randomUUID().toString() + ".3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            voiceButton.setText("Stop Recording");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 오디오 녹음 중지
    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        isRecording = false;
        voiceButton.setText("Start Recording");
    }

    // 오디오 파일 전송
    private void sendAudioFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
                    String accessKey = "APIKey"; // 발급받은 API Key, github에 연동 X
                    String languageCode = language; // 언어 코드 (필요에 따라 변경)

                    Gson gson = new Gson();

                    Map<String, Object> request = new HashMap<>();
                    Map<String, String> argument = new HashMap<>();

                    // 오디오 파일 Base64 인코딩
                    File audioFile = new File(audioFilePath);
                    byte[] audioBytes = new byte[(int) audioFile.length()];
                    FileInputStream fis = new FileInputStream(audioFile);
                    fis.read(audioBytes);
                    fis.close();
                    String audioContents = Base64.getEncoder().encodeToString(audioBytes);

                    argument.put("language_code", languageCode);
                    argument.put("audio", audioContents);

                    request.put("argument", argument);

                    URL url = new URL(openApiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", accessKey);

                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.write(gson.toJson(request).getBytes("UTF-8"));
                    wr.flush();
                    wr.close();

                    int responseCode = con.getResponseCode();
                    InputStream is = con.getInputStream();
                    byte[] buffer = new byte[is.available()];
                    int byteRead = is.read(buffer);
                    String responBody = new String(buffer);

                    // JSON 파싱 및 결과 처리
                    JSONObject jsonResponse = new JSONObject(responBody);
                    int result = jsonResponse.getInt("result");
                    if (result == 0) {
                        JSONObject returnObject = jsonResponse.getJSONObject("return_object");
                        String recognizedText = returnObject.getString("recognized"); // 음성 인식 결과

                        // UI 업데이트 (runOnUiThread 사용)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView resultTextView = findViewById(R.id.result_text_view); // 결과 표시할 TextView
                                resultTextView.setText(recognizedText);
                            }
                        });
                    } else {
                        // 오류 처리
                    }

                } catch (MalformedURLException | FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




}

