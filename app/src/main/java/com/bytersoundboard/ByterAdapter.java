package com.bytersoundboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class ByterAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SoundButton> objects;

    ByterAdapter(Context context, ArrayList<SoundButton> buttons) {
        this.ctx = context;
        this.objects = buttons;
        this.lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.play_button, parent, false);
        }

        SoundButton sb = getButton(position);

        // заполняем View в пункте списка
        ((Button) view.findViewById(R.id.btnPlay)).setText(sb.getName() + "");

        Button btnPlay = (Button) view.findViewById(R.id.btnPlay);
        // присваиваем обработчик
        btnPlay.setOnClickListener(myListener);
        btnPlay.setTag(position);
        btnPlay.setLongClickable(true);
        return view;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getButton((Integer) v.getTag()).playSound();
        }
    };

    // кнопка по позиции
    SoundButton getButton(int position) {
        return ((SoundButton) getItem(position));
    }

    public int getSoundId (int objId) {
        return objects.get(objId).getSoundId();
    }
}
