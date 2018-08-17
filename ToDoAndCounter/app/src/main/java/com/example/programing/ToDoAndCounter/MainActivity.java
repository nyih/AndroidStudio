package com.example.programing.ToDoAndCounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.programing.ToDoAndCounter.Countdown.CountdownActivity;
import com.example.programing.ToDoAndCounter.v1_ToDo_SQLite.ListDataActivity;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnSQL,btnCount;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        configureActivitiesBtn();
        //the 3 lines bellow make possible to have a icon next to the ActionBar title
        ActionBar actbar = getSupportActionBar();
        actbar.setDisplayShowHomeEnabled(true);
        actbar.setIcon(R.mipmap.ic_randomicon_round);
    }

    private void configureActivitiesBtn(){
        btnCount=(Button) findViewById(R.id.btn_countdown);
        btnCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CountdownActivity.class));
            }
        });

        btnSQL=(Button) findViewById(R.id.btn_tasks_lite);
        btnSQL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListDataActivity.class));
            }
        });

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
