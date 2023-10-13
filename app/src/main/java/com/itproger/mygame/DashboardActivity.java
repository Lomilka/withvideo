package com.itproger.mygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    String EmailHolder;
    TextView Email;
    Button LogOUT ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toast.makeText(DashboardActivity.this,"Экран был запущен", Toast.LENGTH_LONG).show();

        Email = (TextView)findViewById(R.id.textView1);
        LogOUT = (Button)findViewById(R.id.button1);

        Intent intent = getIntent();

        // Получение электронного письма Пользователя, отправленного по основному действию.
        EmailHolder = intent.getStringExtra(MainActivity.UserEmail);

        /// Настройка полученного электронного письма в TextView.
        Email.setText(Email.getText().toString()+ EmailHolder);

        // Добавление прослушивателя щелчков для выхода из системы.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Переход на главное меню обучения
                Intent intent = new Intent(DashboardActivity.this, MenuActivity.class);
                startActivity(intent);

                Toast.makeText(DashboardActivity.this,"Приступим к обучению!", Toast.LENGTH_LONG).show();

            }
        });

    }
}