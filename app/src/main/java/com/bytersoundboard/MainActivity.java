package com.bytersoundboard;


import androidx.appcompat.app.AppCompatDelegate;
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
    private MenuItem bass;
    private MenuItem speedUp;
    private ByterButtonsPage page;
    private FavoriteButtonsPage favPage;
    public static final String PREF_NAME = "BSB_PREF";
    public static final String ARRAY_LIST = "ARRAY_LISTt";
    public static boolean bassSwitch;
    public static boolean speedUpSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //show first page on start
        showMain();
        turnFav = false;
        bassSwitch = false;
        speedUpSwitch = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.byter_bar, menu);
        star = menu.findItem(R.id.barFav);
        bass = menu.findItem(R.id.barBass);
        speedUp = menu.findItem(R.id.barSpeedUp);

        bass.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.barInfo) {
            Toast.makeText(this, getString(R.string.information)
                            + "\nVersion: " + BuildConfig.VERSION_NAME,
                    Toast.LENGTH_LONG).show();
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
        } else if (menuItem.getItemId() == R.id.barBass) {
            if (bassSwitch) {
                bass.setIcon(android.R.drawable.ic_lock_silent_mode);
                bassSwitch = false;
                Toast.makeText(this, getString(R.string.BassOff), Toast.LENGTH_SHORT).show();
            } else {
                bass.setIcon(android.R.drawable.ic_lock_silent_mode_off);
                bassSwitch = true;
                Toast.makeText(this, getString(R.string.BassOn), Toast.LENGTH_SHORT).show();
            }
        } else if (menuItem.getItemId() == R.id.barSpeedUp) {
            if (speedUpSwitch) {
                speedUp.setIcon(android.R.drawable.arrow_down_float);
                speedUpSwitch = false;
            } else {
                speedUp.setIcon(android.R.drawable.arrow_up_float);
                speedUpSwitch = true;
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