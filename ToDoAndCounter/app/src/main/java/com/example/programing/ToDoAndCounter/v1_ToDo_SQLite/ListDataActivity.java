package com.example.programing.ToDoAndCounter.v1_ToDo_SQLite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programing.ToDoAndCounter.Countdown.CountdownActivity;
import com.example.programing.ToDoAndCounter.MainActivity;
import com.example.programing.ToDoAndCounter.R;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity{

    public static final String TAG="ListDataActivity";
    SQLiteHelper dbHelper;
    private ListView listView;
    private Button btnAddTask;
    private TextView mTextMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sql_list_layout);
        listView= findViewById(R.id.listView);
        btnAddTask=(Button) findViewById(R.id.btnAddTask);
        dbHelper=new SQLiteHelper(this);

        getSupportActionBar().setTitle("To do list");  // provide compatibility to all the versions

        populateListView();
        mTextMessage = (TextView) findViewById(R.id.message);


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDataActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
          Log.d(TAG,"populateListView: Displaying data in the ListView");

          //get data and append to a list
          Cursor data=dbHelper.getData();
        ArrayList<String> listData=new ArrayList<>();
        while (data.moveToNext()){
            //get the value from database in collumn
            //than adds it to the array
            listData.add(data.getString(1)); //in this case, COL1
            //listData.add(data.getString(2)); //in this case, COL2
        }
        //create list adapter and set adapter
        ListAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = dbHelper.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinf=getMenuInflater();
        menuinf.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent gohome=new Intent(this,MainActivity.class);
                startActivity(gohome);
                return true;
            case R.id.nav_todo:
                Intent gotodo=new Intent(this,ListDataActivity.class);
                startActivity(gotodo);
                return true;
            case R.id.nav_count:
                Intent gocount=new Intent(this,CountdownActivity.class);
                startActivity(gocount);
                return true;
        }
        return false;
    }
}
