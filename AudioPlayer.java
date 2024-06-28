/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : D
 * Group    : 3
 * Members  :
 * 1. 5026231179 - Muhammad Hammam Aditama
 * 2. 5026231127 - Rafi Zahfran Putra Wibowo
 * 3. 5026231021 - Zaskia Muazatun Mahmud
 * ------------------------------------------------------
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    public void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
