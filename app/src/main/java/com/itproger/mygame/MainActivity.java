package com.itproger.mygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton ;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "НЕ_НАЙДЕНО" ;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.buttonLogin);

        RegisterButton = (Button)findViewById(R.id.buttonRegister);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        sqLiteHelper = new DatabaseHelper(this);

        //Добавить прослушиватель кликов для входа в систему.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Вызов EditText пуст или метода нет.
                CheckEditTextStatus();

                // Вызов метода входа в систему.
                LoginFunction();


            }
        });

        // Добавить прослушиватель кликов к кнопке регистрации.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Открытие действия регистрации нового пользователя с использованием намерения при нажатии кнопки.
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });

    }

    // Функция входа в систему запускается отсюда.
    @SuppressLint("Range")
    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            // Открыть разрешение на запись в базу данных SQLite.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Добавить поисковый запрос электронной почты к курсору.
            cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Сохранение пароля, связанного с введенным адресом электронной почты.
                    TempPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Table_Column_3_Password));

                    // Закрыть курсор.
                    cursor.close();
                }
            }

            // Вызов метода для проверки конечного результата ..
            CheckFinalResult();

        }
        else {

            //Если какой-либо из login EditText пуст, то этот блок будет выполнен.
            Toast.makeText(MainActivity.this,"Пожалуйста, введите имя пользователя или пароль",Toast.LENGTH_LONG).show();

        }

    }

    // Проверяем, пуст EditText или нет.
    public void CheckEditTextStatus(){

        // Получение значения из всего EditText и сохранение в строковых переменных.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Проверяем, пуст ли EditText или нет, используя TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Проверка введенного пароля из базы данных SQLite, связанного с паролем электронной почты.
    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(MainActivity.this,"Успешно",Toast.LENGTH_LONG).show();

            // Переход к активности панели мониторинга после сообщения об успешном входе в систему.
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

            // Отправка электронной почты на панель мониторинга Activity с использованием intent.
            intent.putExtra(UserEmail, EmailHolder);

            startActivity(intent);


        }
        else {

            Toast.makeText(MainActivity.this,"Неверно, попробуйте снова",Toast.LENGTH_LONG).show();

        }
        TempPassword = "НЕ_НАЙДЕНО" ;

    }

}