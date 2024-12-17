package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView koreanTextView = findViewById(R.id.korean_text_view);
        TextView englishTextView = findViewById(R.id.english_text_view);

        koreanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 한국어 선택 시 실행될 코드
                Intent intent = new Intent(MainActivity.this, com.google.androidgamesdk.GameActivity.class);
                intent.putExtra("language", "korean");
                startActivity(intent);
            }
        });

        englishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 영어 선택 시 실행될 코드
                Intent intent = new Intent(MainActivity.this, com.google.androidgamesdk.GameActivity.class);
                intent.putExtra("language", "english");
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}