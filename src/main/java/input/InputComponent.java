package input;

import entity.Entity;

public abstract class InputComponent {
    public abstract void onAttach(Entity e);
    public abstract void handle(Entity e);

    public static InputComponent Null = new InputComponent() {
        @Override
        public void onAttach(Entity e) {
        }

        @Override
        public void handle(Entity e) {
        }
    };
}
