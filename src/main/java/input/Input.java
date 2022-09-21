package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Input {
    public static char lastKeyPress;
    public static char lastKeyReleased;
    public static ArrayList<KeyCode> inputList = new ArrayList<KeyCode>();
    public Map<String, Boolean> Pr = new HashMap<String, Boolean>();
    public void attachEventHandlers(Scene s){
        keyReleaseHanlder krh = new keyReleaseHanlder();
        keyPressedHandler kph = new keyPressedHandler();
        s.setOnKeyReleased(krh);
        s.setOnKeyPressed(kph);
    }

    public boolean isKeyPressed(KeyCode k, Map <String, Boolean> Pr) {
        Keycode k1=k;
        String a = toString(k);
        Pr.clear();
        if (inputList.contains(k)){
            return true;
        } else {
            return false;
        }
    }
    public static List getInputList(){
        return inputList;
    }
}

class keyReleaseHanlder implements javafx.event.EventHandler<KeyEvent>{
    public keyReleaseHanlder() {
    }
    @Override
    public void handle(KeyEvent evt) {
        //System.out.println("The key released is : "+evt.getText()+" with keycode "+evt.getCode().getName());

        KeyCode code = evt.getCode();

        if ( Input.inputList.contains(code) )
            Input.inputList.remove( code );
    }
}
class keyPressedHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent evt) {
        //System.out.println("The key pressed is : "+evt.getText()+" with keycode "+evt.getCode().getName());
        KeyCode code = evt.getCode();

        if ( !Input.inputList.contains(code) )
            Input.inputList.add( code );
    }
}
