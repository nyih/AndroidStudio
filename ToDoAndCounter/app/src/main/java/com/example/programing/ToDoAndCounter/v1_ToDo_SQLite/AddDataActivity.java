package com.example.programing.ToDoAndCounter.v1_ToDo_SQLite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.programing.ToDoAndCounter.R;

public class AddDataActivity extends AppCompatActivity {

    private static final String TAG="AddDataActivity";
    SQLiteHelper dbHelper;
    private Button btnAdd, btnView;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_add_layout);
        editText=(EditText) findViewById(R.id.editText);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnView=(Button) findViewById(R.id.btnView);
        dbHelper=new SQLiteHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry=editText.getText().toString();
                if(editText.length()!=0){
                    AddData(newEntry);
                    editText.setText("");
                }
                else{
                    toastMessage("You must write something in the text field");
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddDataActivity.this,ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry){
        boolean insertData=dbHelper.addData(newEntry);

        if(insertData){
            toastMessage("Data inserted successfully");
        }
        else{
            toastMessage("Did you see where that data go?");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
