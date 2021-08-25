package com.bytersoundboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class FavoriteButtonsPage extends Fragment {
    private static final int REQUEST_STORE_PERMISSION_CODE = 1;
    private ByterAdapter byterAdapter;
    private ListView byterList;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getApplicationContext().
                getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_list, null);

        // настраиваем список
        byterAdapter = new ByterAdapter(getActivity(), ByterButtonsPage.favoriteButtons);

        byterList = (ListView) v.findViewById(R.id.buttonsList);
        byterList.setAdapter(byterAdapter);

        registerForContextMenu(byterList);

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("Amogus", "on resume");
        refresh();
    }

    private void refresh() {
        byterAdapter = new ByterAdapter(getActivity(), ByterButtonsPage.favoriteButtons);
        byterList.setAdapter(byterAdapter);

        registerForContextMenu(byterList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_favorite, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if (itemId == R.id.share) {
            if (!CheckPermissions()) {
                requestPermissions();
            } else {
                //Toast.makeText(getActivity(), "" + getString(R.string.share_toast), Toast.LENGTH_SHORT).show();
                share(byterAdapter.getSoundId((int)info.id));
            }
        } else if (itemId == R.id.setOnCall) {
            if (!CheckPermissions()) {
                requestPermissions();
            } else {
                Toast.makeText(getActivity(), "efwef",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (itemId == R.id.unfavorite) {
            // TODO to unfavorite
            ByterButtonsPage.favoriteButtons.add(byterAdapter.getButton((int)info.id));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();

            String json = gson.toJson(ByterButtonsPage.favoriteButtons);

            editor.putString(MainActivity.ARRAY_LIST, json);
            editor.commit();
            refresh();

            /*
            FavoriteButtonsPage page1 = new FavoriteButtonsPage();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framel, page1);
            ft.commit();
             */
        }

        return true;
    }

    private void saving (int _id) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        try {
            inputStream = getResources().openRawResource(_id);

            File byterDir = new File(""+Environment.getExternalStorageDirectory()+"/byter_sounds/");

            if(!byterDir.exists()) {
                //Toast.makeText(getActivity(), "aboba", Toast.LENGTH_LONG).show();
                byterDir.mkdirs();
            }

            fileOutputStream = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory()+"/byter_sounds/",
                            "sound.mp3"));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void share(int _id) {
        saving(_id);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/byter_sounds/sound.mp3" ));
        intent.setType("application/*");
        startActivity(Intent.createChooser(intent, "Share sound"));
    }

    private void requestPermissions() {
        // this method is used to request the permission.
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORE_PERMISSION_CODE);
    }

    public boolean CheckPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void saveArrayList(ArrayList<SoundButton> list, String key){
        sharedPreferences = getActivity().getApplicationContext().
                getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<SoundButton> getArrayList(String key){
        sharedPreferences = getActivity().getApplicationContext().
                getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<SoundButton>>() {}.getType();
        return gson.fromJson(json, type);
    }
}