package com.example.programing.ToDoAndCounter.v1_ToDo_SQLite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.programing.ToDoAndCounter.R;

public class EditDataActivity extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete,btnViewData;
    private EditText editable_item,editable_note;

    SQLiteHelper dbHelper;

    private String selectedName;
    private String selectedNote;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_edit_layout);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        editable_note = (EditText) findViewById(R.id.editable_note);
        dbHelper = new SQLiteHelper(this);

        getSupportActionBar().setTitle("Change or delete your task");  // provide compatibility to all the versions

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();
        Intent receivedIntent2 = getIntent();

        //get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //get the name we passed as an extra
        selectedNote = receivedIntent2.getStringExtra("note");

        //set the text to show the current selected name
        editable_item.setText(selectedName);
        editable_note.setText(selectedNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String inote = editable_note.getText().toString();
                if(!item.equals("")){
                    dbHelper.updateName(item,selectedID,selectedName);
                    dbHelper.updateNote(inote,selectedID,selectedNote);
                    toastMessage("Task updated in the database");
                    Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                    startActivity(intent);
                }else{
                    toastMessage("You must write something");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                toastMessage("Task removed from database");
                Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
