package com.example.tetris;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@SuppressWarnings("deprecation")
public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguageAccordingToPref();
        setContentView(R.layout.activity_main_screen);
        Locale locale = getApplicationContext().getResources().getConfiguration().locale;

    }

    public void handlePlayButtonClick(View view){
        Intent intent = new Intent("com.example.tetris.TetrisActivity");
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

         if(id == R.id.menu_exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleTutorialButtonClick(View view){
        Intent intent = new Intent("com.example.tetris.TutorialActivity");
        startActivity(intent);
        finish();
    }

    public void handleSettingsButtonClick(View view){
        Intent intent = new Intent("com.example.tetris.SettingsActivity");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            this.setLanguageAccordingToPref();
            finish();
            startActivity(getIntent());
        }
    }

    private void setLanguageAccordingToPref(){
        Locale locale = getApplicationContext().getResources().getConfiguration().locale;

        String language = getDefaultSharedPreferences(this).getString("languagePreferance", "EN");
        if(language.equals("NO")){
            locale = new Locale("nb", "NO");
            this.configureLocale(locale);
        } else if(language.equals("EN")){
            locale = new Locale("en", "US");
            this.configureLocale(locale);
        }
        this.printLocaleInformation(locale);
    }

    private void configureLocale(Locale locale){
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        Resources resources = getBaseContext().getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void printLocaleInformation(Locale locale){
        System.out.println("*** " + locale);
        System.out.println("*** " + locale.getCountry());
        System.out.println("*** " + locale.getDisplayCountry());
        System.out.println("*** " + locale.getDisplayLanguage());
    }
}
