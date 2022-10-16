package resources;

import javafx.scene.media.AudioClip;


public class SoundFX{

    public static void play() {
        String fileName = "/stage_theme.mp3";
        playSound(fileName);
    }


    private static void playSound(String fileName){
        try {
            String path = ClassLoader.getSystemClassLoader().getResource("SoundFX").getPath();
            AudioClip note = new AudioClip("File:" + path + fileName);
            System.out.printf("File:" + path + fileName + "\n");
            note.play();
        } catch (Exception e) {
            System.out.printf("cant get media");
        }
    }
    private static void setLoop(AudioClip file, int i){
        file.setCycleCount(i);
    }
    private static void stopSound(AudioClip fileName){
        fileName.stop();
    }

    private static void stopAll() {

    }

}

