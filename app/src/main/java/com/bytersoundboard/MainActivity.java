package com.bytersoundboard;


import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ByterButtonsPage page;
    public static final String PREF_NAME = "BSB_PREF";
    public static final String ARRAY_LIST = "ARRAY_LISTt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteButtonsPage page1 = new FavoriteButtonsPage();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framel, page1);
                ft.commit();
            }
        });

        //show first page on start
        page = new ByterButtonsPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.framel, page);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.byter_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.barInfo) {
            Toast.makeText(this, getString(R.string.information), Toast.LENGTH_LONG).show();
        }

        return true;
    }

}