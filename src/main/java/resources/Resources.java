package resources;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sprite.SpriteData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Resources {
    private static Map<String, SpriteData> spriteDataMap = new HashMap<>();
    public static Map<String, AudioClip> soundDataMap = new HashMap<>();
    private static ArrayList<File> levelList = new ArrayList<>();

    public static void loadAllSprites() {
        try {
            ArrayList<String> spriteDirArray = new ArrayList<>();

            String tmp = ClassLoader.getSystemClassLoader().getResource("sprites").getPath();
            if (File.separator.equals("\\")) tmp = tmp.substring(1);
            String spriteDirPath = tmp;

            Files.walk(Path.of(spriteDirPath)).forEach((path) -> {
                String fileName = Path.of(spriteDirPath).relativize(path).toString();
                if (fileName.contains(".")) spriteDirArray.add(fileName);
            });

            spriteDirArray.sort(Comparator.naturalOrder());

            for (String r:spriteDirArray) {
                loadSprite(r);
            }
        } catch (Exception e) {
            System.out.printf("Can't load all sprites\n");
        }
    }

    public static void loadCustomSprite(String fileName) {
        try {
            String[] parsed = fileName.split("/|\\\\|(?<=\\D)(?=\\d)|\\.");
            Image i = new Image("sprites" + File.separator + fileName);
            if (!spriteDataMap.containsKey(parsed[0])) {
                spriteDataMap.put(parsed[0], new SpriteData(i.getWidth(), i.getHeight()));
            }
            spriteDataMap.get(parsed[0]).addFrame(parsed[1], i);
        }
        catch (Exception e) {
            System.out.printf("Can't load sprite: %s\n", fileName);
        }
    }


    public static void loadSprite(String fileName) {
        try {
            String[] parsed = fileName.split("/|\\\\|(?<=\\D)(?=\\d)|\\.");
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

    public static void loadAllLevels() {
        try {
            String levelDirPath = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
            if (File.separator.equals("\\")) levelDirPath = levelDirPath.substring(1);

            Files.walk(Path.of(levelDirPath)).forEach((path) -> {
                String fileName = path.toString();
                if (fileName.contains(".")) levelList.add(new File(fileName));
            });

            levelList.sort(Comparator.naturalOrder());
        } catch (Exception e) {
            System.out.printf("Can't load levels\n");
        }
    }

    public static void loadAllSounds() {
        try {
            ArrayList<String> soundDirArray = new ArrayList<>();

            String tmp = ClassLoader.getSystemClassLoader().getResource("sounds").getPath();
            if (File.separator.equals("\\")) tmp = tmp.substring(1);
            String soundDirPath = tmp;

            Files.walk(Path.of(soundDirPath)).forEach((path) -> {
                String fileName = path.toString();
                if (fileName.contains(".")) soundDirArray.add(fileName);
            });

            soundDirArray.sort(Comparator.naturalOrder());

            for (String r:soundDirArray) {
                loadSound(r);
            }
        } catch (Exception e) {
            System.out.printf("Can't load all sounds\n");
        }
    }

    public static void loadSound(String fileName) {
        try {
            fileName = fileName.replace("\\", "/");
            String[] parsed = fileName.split("[/\\\\.]");
            if (!soundDataMap.containsKey(parsed[parsed.length-2])) {
                soundDataMap.put(parsed[parsed.length - 2], new AudioClip("File:" + fileName));
            }
        }
        catch (Exception e) {
            System.out.printf("Can't load sounds: %s\n", fileName);
        }
    }

    public static SpriteData getSprite(String name) {
        try {
            if (!spriteDataMap.containsKey(name)) throw new Exception();
        }
        catch (Exception e){
            System.out.printf("Can't get sprite: %s\n", name);
        }
        return spriteDataMap.get(name);
    }

    public static AudioClip getSound(String name) {
        try {
            if (!soundDataMap.containsKey(name)) throw new Exception();
        }
        catch (Exception e){
            System.out.printf("Can't get sound: %s\n", name);
        }
        return soundDataMap.get(name);
    }

    public static File getLevel(int levelIndex) {
        try {
            if (levelIndex < 0 || levelIndex > levelList.size()) throw new Exception();
        }
        catch (Exception e){
            System.out.printf("Can't get level: %s\n", levelIndex);
        }
        return levelList.get(levelIndex);
    }
}
