package com.bytersoundboard;


import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean turnFav;
    private MenuItem star;
    private ByterButtonsPage page;
    private FavoriteButtonsPage favPage;
    public static final String PREF_NAME = "BSB_PREF";
    public static final String ARRAY_LIST = "ARRAY_LISTt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //show first page on start
        showMain();
        turnFav = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.byter_bar, menu);
        star = menu.findItem(R.id.barFav);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.barInfo) {
            Toast.makeText(this, getString(R.string.information), Toast.LENGTH_LONG).show();
        } else if  (menuItem.getItemId() == R.id.barFav) {
            if (turnFav) {
                showMain();
                star.setIcon(android.R.drawable.star_big_off);
                turnFav = false;
            } else {
                showFav();
                star.setIcon(android.R.drawable.star_big_on);
                turnFav = true;
            }
        }
        return true;
    }

    private void showMain () {
        page = new ByterButtonsPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framel, page);
        ft.commit();
    }

    private void showFav () {
        favPage = new FavoriteButtonsPage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framel, favPage);
        ft.commit();
    }
}