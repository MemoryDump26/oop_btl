package resources;

import static resources.Resources.soundDataMap;


public class SoundFX{

    public static void playSound(String name, int loop, boolean multiple) {
        if (!soundDataMap.get(name).isPlaying() || (multiple)) {
            soundDataMap.get(name).play();
            soundDataMap.get(name).setCycleCount(loop);
        }
    }
    public static void stopSound(String name){
            soundDataMap.get(name).stop();
    }
}

