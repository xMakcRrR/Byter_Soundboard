package com.bytersoundboard;

import android.app.Activity;
import android.media.MediaPlayer;

public class SoundButton {
    private String name;
    private MediaPlayer mediaPlayer;
    private int soundId;
    private Activity activity;

    SoundButton (String _name, Activity activity, int _id) {
        this.name = _name;
        this.activity = activity;
        this.mediaPlayer = MediaPlayer.create(activity, _id);
        this.soundId = _id;
    }

    public void playSound () {
        mediaPlayer.start();
    }

    public MediaPlayer getSound () {
        return mediaPlayer;
    }

    public String getName () {
        return name;
    }

    public int getSoundId () {
        return soundId;
    }
}
