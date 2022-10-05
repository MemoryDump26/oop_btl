package sprite;

import javafx.scene.image.Image;
import options.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpriteData {
    public Map<String, ArrayList<Image>> animations = new HashMap<String, ArrayList<Image>>();
    public String currentAnimation = new String();
    public double w;
    public double h;

    public void addFrame(String name, Image frame) {
        if (!animations.containsKey(name)) {
            animations.put(name, new ArrayList<Image>());
            currentAnimation = name;
        }
        animations.get(name).add(frame);
    }

    public SpriteData() {
        this.w = Globals.cellSize;
        this.h = Globals.cellSize;
    }

    public SpriteData(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
