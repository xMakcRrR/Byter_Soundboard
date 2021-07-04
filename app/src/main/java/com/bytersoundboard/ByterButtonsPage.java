package com.bytersoundboard;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class ByterButtonsPage extends Fragment {
    ArrayList<SoundButton> soundButtons = new ArrayList<SoundButton>();
    ByterAdapter byterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] buttons = {getString(R.string.hi), getString(R.string.bye),
                getString(R.string.im_danila), getString(R.string.aboba),
                getString(R.string.ny_blin), getString(R.string.chto_ze_delat),
                getString(R.string.eli_pali), getString(R.string.chin_chopa)};
        int[] _ids = {R.raw.hi, R.raw.bye, R.raw.im_danila, R.raw.aboba, R.raw.ny_blin,
                R.raw.chto_ze_delat, R.raw.eli_pali, R.raw.chin_chopa};

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_list, null);

        // настраиваем список
        for (int ifor = 0; ifor < buttons.length; ifor++) {
            soundButtons.add(new SoundButton(buttons[ifor], getActivity(), _ids[ifor]));
        }
        byterAdapter = new ByterAdapter(getActivity(), soundButtons);

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
            Toast.makeText(getActivity(), "" + getString(R.string.share_toast), Toast.LENGTH_SHORT).show();
            share(byterAdapter.getSoundId((int) info.id));
        } else if (itemId == R.id.setOnCall) {
            Toast.makeText(getActivity(), "" + getString(R.string.go_to_medic_not),
                    Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "aboba", Toast.LENGTH_LONG).show();
                byterDir.mkdirs();
            }

            fileOutputStream = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory()+"/byter_sounds/", "sound.mp3"));

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
}