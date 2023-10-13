package com.itproger.mygame;

import android.content.Context;
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

public class Login extends AppCompatActivity {

    EditText Email, Password, Name ;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "НЕ_НАЙДЕНО";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.buttonRegister);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Name = (EditText)findViewById(R.id.editName);

        sqLiteHelper = new DatabaseHelper(this);

        // Добавление прослушивателя кликов к кнопке регистрации.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Создание базы данных SQLite, если она не существует
                SQLiteDataBaseBuild();

                // Создание таблицы SQLite, если она не существует.
                SQLiteTableBuild();

                // Проверяем, пуст EditText или нет.
                CheckEditTextStatus();

                // Способ проверить, существует ли электронная почта уже или нет.
                CheckingEmailAlreadyExistsOrNot();

                // Очистить EditText после завершения процесса вставки.
                EmptyEditTextAfterDataInsert();


            }
        });

    }

    // Метод построения базы данных SQLite.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // Метод построения таблицы SQLite.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_NAME + "(" + DatabaseHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + DatabaseHelper.Table_Column_1_Name + " VARCHAR, " + DatabaseHelper.Table_Column_2_Email + " VARCHAR, " + DatabaseHelper.Table_Column_3_Password + " VARCHAR);");

    }

    // Вставить данные в метод базы данных SQLite.
    public void InsertDataIntoSQLiteDatabase(){

        // Если EditText не пуст, то этот блок будет выполнен.
        if(EditTextEmptyHolder == true)
        {

            // Запрос SQLite для вставки данных в таблицу.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+DatabaseHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";

            // Выполняющий запрос.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Закрытие объекта базы данных SQLite.
            sqLiteDatabaseObj.close();

            // Печать всплывающего сообщения после завершения вставки.
            Toast.makeText(Login.this,"Юзер успешно зарегистрировался", Toast.LENGTH_LONG).show();

        }
        // Этот блок будет выполнен, если какой-либо из регистрационных EditText пуст.
        else {

            // Печать всплывающего сообщения, если какой-либо из EditText пуст.
            Toast.makeText(Login.this,"Пожалуйста, заполните все поля", Toast.LENGTH_LONG).show();

        }

    }

    // Пустой edittext после завершения процесса вставки метода.
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Email.getText().clear();

        Password.getText().clear();

    }

    // Метод проверки того, является ли EditText пустым или нет.
    public void CheckEditTextStatus(){

        // Получение значения из всего EditText и сохранение в строковых переменных.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Проверка того, существует ли электронная почта уже или нет.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Открытие разрешения на запись в базу данных SQLite.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Добавление поискового запроса электронной почты к курсору.
        cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // Если адрес электронной почты уже существует, то значение результирующей переменной устанавливается как найденный адрес электронной почты.
                F_Result = "Найден код пользователя";

                // Закрывающий курсор.
                cursor.close();
            }
        }

        // Вызывающий метод для проверки конечного результата и вставки данных в базу данных SQLite.
        CheckFinalResult();

    }


    // Результат проверки
    public void CheckFinalResult(){

        // Проверка того, существует ли электронная почта уже или нет.
        if(F_Result.equalsIgnoreCase("Найден код пользователя"))
        {

            // Если электронная почта существует, то отобразится всплывающее сообщение.
            Toast.makeText(Login.this,"Код пользователя уже существует",Toast.LENGTH_LONG).show();

        }
        else {

            // Если адрес электронной почты еще не существует, то регистрационные данные пользователя будут введены в базу данных SQLite.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "НЕ_НАЙДЕНО" ;

    }

}