package game.utility.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class AudioLibrary {
    ArrayList<Clip> clips;
    
    public AudioLibrary(){
        clips = new ArrayList<Clip>();
    }

    int lastAddIndex = 0;
    int load(String path)
    {
        File mediaFile = new File(path);
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(mediaFile);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clips.add(clip);
            return lastAddIndex++;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
