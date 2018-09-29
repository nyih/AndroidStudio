package com.exercises.programing.simplegames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.exercises.programing.simplegames.hangingman.HangMainActivity;
import com.exercises.programing.simplegames.memory.MemoMainActivity;
import com.exercises.programing.simplegames.tictactoe.TicMainActivity;

/**
 * Created by Carla on 21-08-2018.
 */
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
        MenuInflater menuinf = getMenuInflater();
        menuinf.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_tictac:
                Intent gotodo=new Intent(this, TicMainActivity.class);
                startActivity(gotodo);
                return true;
            case R.id.nav_hang:
                Intent gohang=new Intent(this, HangMainActivity.class);
                startActivity(gohang);
                return true;
            case R.id.nav_memo:
                Intent gomemo=new Intent(this, MemoMainActivity.class);
                startActivity(gomemo);
                return true;
        }
        return false;
    }
}