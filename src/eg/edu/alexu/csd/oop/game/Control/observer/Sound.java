package eg.edu.alexu.csd.oop.game.Control.observer;

import eg.edu.alexu.csd.oop.game.Control.World.Circus;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound extends Observer {

    public Sound(Circus game) {
        game.attach(this);
    }

    @Override
    public void update(int num) {
        try {
            URL url = eg.edu.alexu.csd.oop.game.sound.Sound.class.getResource("/ThreePlates.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip three = AudioSystem.getClip();
            three.open(audioIn);
            three.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}