package site.gr8.mattis.creatingminecraft.core.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sounds {

    Clip clip;
    File[] fileURLS = new File[2];

    private static Sounds sounds = null;

    public static Sounds get() {
        if (Sounds.sounds == null) {
            Sounds.sounds = new Sounds();
        }
        return sounds;
    }

    public Sounds() {
        fileURLS[0] = new File("resources/bounce.wav");
        fileURLS[1] = new File("resources/filewav.wav");
    }

    public void playSound(int index, boolean shouldOverlap) {
        try {
            if (!shouldOverlap)
                if (clip != null)
                    stopSound();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileURLS[index]);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }


    public void stopSound() {
        clip.setMicrosecondPosition(0);
        clip.stop();
    }

}
