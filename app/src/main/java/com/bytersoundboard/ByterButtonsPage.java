package com.bytersoundboard;

import android.Manifest;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ByterButtonsPage extends Fragment {
    private static final int REQUEST_STORE_PERMISSION_CODE = 1;
    ArrayList<SoundButton> soundButtons = new ArrayList<SoundButton>();
    static ArrayList<SoundButton> favoriteButtons = new ArrayList<SoundButton>();
    ByterAdapter byterAdapter;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] buttons = {getString(R.string.hi), getString(R.string.bye),
                getString(R.string.im_danila), getString(R.string.aboba),
                getString(R.string.ny_blin), getString(R.string.chto_ze_delat),
                getString(R.string.eli_pali), getString(R.string.chin_chopa),
                getString(R.string.byter_lager_gaming), getString(R.string.fu),
                getString(R.string.razrivnaya), getString(R.string.slabenko),
                getString(R.string.tyazeliyslychay), getString(R.string.otdelniye_izv),
                getString(R.string.prosti_poriv)};
        int[] _ids = {R.raw.hi, R.raw.bye, R.raw.im_danila, R.raw.aboba, R.raw.ny_blin,
                R.raw.chto_ze_delat, R.raw.eli_pali, R.raw.chin_chopa, R.raw.byterlagergaming,
                R.raw.fu, R.raw.razrivnaya, R.raw.slabenko, R.raw.tyazeliyslychay,
                R.raw.otdelniye_izv, R.raw.prosti_poriv};

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_list, null);

        // настраиваем список
        for (int ifor = 0; ifor < buttons.length; ifor++) {
            soundButtons.add(new SoundButton(buttons[ifor], getActivity(), _ids[ifor]));
        }
        byterAdapter = new ByterAdapter(getActivity(), soundButtons);

        // настраиваем избранное
        sharedPreferences = getActivity().getApplicationContext().
                getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MainActivity.ARRAY_LIST, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<SoundButton>>() {}.getType();
            favoriteButtons = gson.fromJson(json, type);
        }

        ListView byterList = (ListView) v.findViewById(R.id.buttonsList);
        byterList.setAdapter(byterAdapter);

        registerForContextMenu(byterList);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_buttons, menu);
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
                share(byterAdapter.getSoundId((int) info.id));
            }
        } else if (itemId == R.id.setOnCall) {
            if (!CheckPermissions()) {
                requestPermissions();
            } else {
                Toast.makeText(getActivity(), "" + getString(R.string.go_to_medic_not),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (itemId == R.id.favorite) {
            favoriteButtons.add(new SoundButton(byterAdapter.getButton((int) info.id).getName(),
                    getActivity(),
                    byterAdapter.getButton((int) info.id).getSoundId()));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();

            String json = gson.toJson(favoriteButtons);

            editor.putString(MainActivity.ARRAY_LIST, json);
            editor.commit();
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
}