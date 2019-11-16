package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TetrisActivity extends AppCompatActivity {

    public static final String TAG = "***  Tetris Activity ***";
    private TetrisView tetrisView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        this.tetrisView = (TetrisView) findViewById(R.id.tetrisView);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_tetris, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_pause) {
            tetrisView.getTetrisThread().setPaused(true);
            return true;
        } else if(id == R.id.menu_unpause){
            tetrisView.getTetrisThread().setPaused(false);
            tetrisView.setFocusable(true); // make sure we get key events
        } else if(id == R.id.menu_exit){
            tetrisView.getTetrisThread().setRunning(false);
            finish();
        } else if(id == R.id.menu_to_main_menu){
            tetrisView.getTetrisThread().setRunning(false);
            Intent intent = new Intent("com.example.tetris.MainScreenActivity");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        tetrisView.getTetrisThread().setPaused(true);
    }

}
