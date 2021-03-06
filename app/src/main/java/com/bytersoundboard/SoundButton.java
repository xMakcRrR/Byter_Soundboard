package com.bytersoundboard;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundButton {
    private String name;
    private static Context context;
    private int soundId;

    SoundButton (String _name, Context context, int _id) {
        this.name = _name;
        this.context = context;
        this.soundId = _id;
    }

    public void playSound () {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundId);

        if (MainActivity.speedUpSwitch) {
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(2.0f));
        }

        mediaPlayer.start();
    }

    public String getName () {
        return name;
    }

    public int getSoundId () {
        return soundId;
    }
}
