package com.bytersoundboard;

import android.app.Activity;
import android.media.MediaPlayer;

public class SoundButton {
    String name;
    MediaPlayer mediaPlayer;
    int soundId;

    SoundButton (String _name, Activity activity, int _id) {
        this.name = _name;
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
