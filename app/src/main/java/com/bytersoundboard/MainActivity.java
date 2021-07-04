package com.bytersoundboard;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Fragment page;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        //show first page on start
        page = new ByterButtonsPage();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.framel, page);
        ft.commit();
    }

    private void fragmReplace (Fragment p) {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.framel, p);
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