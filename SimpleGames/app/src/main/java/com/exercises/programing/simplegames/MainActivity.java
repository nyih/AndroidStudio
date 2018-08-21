package com.exercises.programing.simplegames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.exercises.programing.simplegames.TicTacToe.TicMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*
     ********************************************************************************************
     * This is just a divider to separate the Activity code from the menu
     ********************************************************************************************
    */

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
                Intent gohome=new Intent(this, MainActivity.class);
                startActivity(gohome);
                return true;
            case R.id.nav_tictac:
                Intent gotodo=new Intent(this, TicMainActivity.class);
                startActivity(gotodo);
                return true;
        }
        return false;
    }
}