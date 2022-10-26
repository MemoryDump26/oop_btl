package resources;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sprite.SpriteData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Resources {
    public static Map<String, SpriteData> spriteDataMap = new HashMap<String, SpriteData>();
    public static Map<String, AudioClip> soundDataMap = new TreeMap<>();
    public static ArrayList<File> levelList = new ArrayList<File>();

    public static void getSprites() {
        try {
            ArrayList<String> spriteDirArray = new ArrayList<String>();

            String tmp = ClassLoader.getSystemClassLoader().getResource("sprites").getPath();
            String spriteDirPath;
            if (File.separator.equals("\\")) spriteDirPath = tmp.substring(1);
            else spriteDirPath = tmp;
            Files.walk(Path.of(spriteDirPath)).forEach((path) -> {
                String fileName = Path.of(spriteDirPath).relativize(path).toString();
                if (fileName.contains(".")) spriteDirArray.add(fileName);
            });

            spriteDirArray.sort(Comparator.naturalOrder());
            for (String r:spriteDirArray) {
                loadSprite(r);
            }
        } catch (Exception e) {
            System.out.printf("Can't get sprites\n");
        }
    }

    public static void loadSprite(String fileName) {
        try {
            System.out.printf("%s\n",fileName);
            String[] parsed = fileName.split("/|\\\\|(?<=\\D)(?=\\d)|\\.");
            System.out.printf("%s %s %s\n", parsed[0], parsed[1], parsed[2]);
            Image i = new Image("sprites" + File.separator + fileName);
            if (!spriteDataMap.containsKey(parsed[0])) {
                spriteDataMap.put(parsed[0], new SpriteData());
            }
            spriteDataMap.get(parsed[0]).addFrame(parsed[1], i);
        }
        catch (Exception e) {
            System.out.printf("Can't load sprite: %s\n", fileName);
        }
    }

    public static void getLevels() {
        try {
            String levelDirPath = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
            if (File.separator.equals("\\")) levelDirPath = levelDirPath.substring(1);
            Files.walk(Path.of(levelDirPath)).forEach((path) -> {
                String fileName = path.toString();
                if (fileName.contains(".")) {
                    levelList.add(new File(fileName));
                }
            });
            levelList.sort(Comparator.naturalOrder());
        } catch (Exception e) {
            System.out.printf("Can't load levels\n");
        }
    }

    public static void getSoundFX() {
        try {
            ArrayList<String> SoundDirArray = new ArrayList<String>();

            String tmp = ClassLoader.getSystemClassLoader().getResource("SoundFX").getPath();
            String soundDirPath = tmp.substring(1);
            System.out.printf(soundDirPath);

            String finalSoundDirPath = soundDirPath;
            Files.walk(Path.of(soundDirPath)).forEach((path) -> {
                String fileName = Path.of(finalSoundDirPath).relativize(path).toString();
                System.out.printf(finalSoundDirPath + "\n ");
                if (fileName.contains(".")) SoundDirArray.add(finalSoundDirPath + "/" + fileName);
            });

            SoundDirArray.sort(Comparator.naturalOrder());

            for (String r : SoundDirArray) {
                loadSound(r);
            }
        } catch (Exception e) {
            System.out.printf("Can't get sounds\n");
        }
    }

    public static void loadSound(String fileName) {
        try {
            System.out.printf("%s\n",fileName);
            String[] parsed = fileName.split("/|\\\\|(?<=\\D)(?=\\d)|\\.");
            for (String i : parsed) {
                System.out.printf("%s\n", i);
            }
           if (!soundDataMap.containsKey(parsed[parsed.length-2])) {
                soundDataMap.put(parsed[parsed.length - 2], new AudioClip("File:" + fileName));
            }
        }
        catch (Exception e) {
            System.out.printf("Can't load sounds: %s\n", fileName);
        }
    }
}
