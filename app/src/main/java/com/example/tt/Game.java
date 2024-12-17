package com.example.tt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // 데이터베이스 열기
        db = openOrCreateDatabase("tongue_twisters.db", MODE_PRIVATE, null);

        // Intent에서 언어 정보 가져오기
        Intent intent = getIntent();
        String language = intent.getStringExtra("language");

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
}