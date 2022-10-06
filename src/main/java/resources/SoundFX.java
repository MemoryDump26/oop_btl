package resources;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;


public class SoundFX{

    static MediaPlayer mediaPlayer;

    public static void play() {
        String fileName = "/stage_theme.mp3";
        playSound(fileName);
    }


    private static void playSound(String fileName){
        try {
            String path = ClassLoader.getSystemClassLoader().getResource("SoundFX").getPath();
            AudioClip note = new AudioClip("File:" + path+fileName);
            note.play();
        } catch (Exception e) {
            System.out.printf("cant get media");
        }
    }
    private static void stopSound(String fileName){

    }

}

