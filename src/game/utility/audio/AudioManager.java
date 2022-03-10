package game.utility.audio;

import javax.sound.sampled.Clip;

public class AudioManager {
    AudioLibrary library;

    public AudioManager(){
        library = new AudioLibrary();
    }

    public int addToLibrary(String path){
        return library.load(path);
    }

    public void play(int index){
        library.clips.get(index).start();
    }

    public void pause(int index){
        library.clips.get(index).stop();
    }

    public void loop(int index){
        library.clips.get(index).loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(int index){
        library.clips.get(index).stop();
        library.clips.get(index).setMicrosecondPosition(0);
    }
}
