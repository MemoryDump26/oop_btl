package resources;

import javafx.scene.media.AudioClip;

import java.util.ArrayList;
import java.util.List;


public class SoundFX{

    public static List<AudioClip> a = new ArrayList<>();

    public static void play(String t) {
        String fileName = t;
        playSound(fileName);
    }


    private static void playSound(String fileName){
        try {
            String path = ClassLoader.getSystemClassLoader().getResource("SoundFX").getPath();
            a.add(new AudioClip("File:" + path + fileName));
            a.get(a.size()-1).play();
            System.out.printf("File:" + path + fileName + "\n");
        } catch (Exception e) {
            System.out.printf("cant get media");
        }
    }
    public static void stopSound(){
        for (AudioClip i : a) {
            i.stop();
        }
    }
}

