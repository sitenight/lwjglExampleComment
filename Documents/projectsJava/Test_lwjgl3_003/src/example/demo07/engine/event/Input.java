package example.demo07.engine.event;

import example.demo07.engine.system.*;
import java.util.HashSet;
import org.joml.Vector2d;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Responsible for handling window input callbacks for convienent retrieval
 */
public class Input {

    private int mods = 0;

    HashSet<Integer> pressedButtons;
    HashSet<Integer> heldButtons;
    HashSet<Integer> releasedButtons;

    HashSet<Integer> pressedKeys;
    HashSet<Integer> heldKeys;
    HashSet<Integer> releasedKeys;

    private float scrollAmount;

    private Vector2d prevPos;
    private Vector2d cursorPos;
    private Vector2d displacement;

    private boolean lockCursor;
    Vector2d lockCursorPos;

    private static Input instance = null;
    public static Input instance(){
        if(instance == null){
            instance = new Input();
        }
        return instance;
    }

    private Input(){
        pressedButtons = new HashSet<>();
        heldButtons = new HashSet<>();
        releasedButtons = new HashSet<>();
        pressedKeys = new HashSet<>();
        heldKeys = new HashSet<>();
        releasedKeys = new HashSet<>();
    }

    /**
     * Initializes callbacks
     */
    public void init(){

        Window window = Window.instance();

        this.prevPos = new Vector2d();
        this.cursorPos = new Vector2d();
        this.displacement = new Vector2d();

        /** Mouse position Callback */
        glfwSetCursorPosCallback(window.getHandle(), (windowHandle, x, y) -> {
            cursorPos.x = x;
            cursorPos.y = y;
        });

        /** Keyboard Callback */
        glfwSetKeyCallback (
                window.getHandle(), (windowHandle, key, scancode, action, mods) -> {
            if(action == GLFW_PRESS){
                this.mods = mods;
                pressedKeys.add(key);
                heldKeys.add(key);
            } else if (action == GLFW_RELEASE){
                releasedKeys.add(key);
                heldKeys.remove(key);
                this.mods = 0;
            }



            });

        /** Mouse button Callback */
        glfwSetMouseButtonCallback (
                window.getHandle(), (windowHandle, button, action, mods) -> {


            if(button == 1 && action == GLFW_PRESS) {
                lockCursor = true;
                lockCursorPos = new Vector2d(cursorPos);
                glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            }
            else if(button == 1 && action == GLFW_RELEASE) {
                lockCursor = false;
                glfwSetInputMode(windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                glfwSetCursorPos(windowHandle, lockCursorPos.x, lockCursorPos.y);
            }

            if(action == GLFW_PRESS){
                this.mods = mods;
                pressedButtons.add(button);
                heldButtons.add(button);
            } else if (action == GLFW_RELEASE){
                this.mods = 0;
                releasedButtons.add(button);
                heldButtons.remove(button);
            }

            Core.events().fire(new MouseEvent(

            ));
        });

        /** Mouse scroll Callback */
        glfwSetScrollCallback(window.getHandle(), (windowHandle, dx, dy) -> {
            scrollAmount = (float) dy;
        });
    }

    public boolean isMod(int keycode){ return (keycode & mods) == keycode;}

    /**
     * Returns true for GLFW keycode of any key hold this update
     * @param keycode GLFW keycode
     * @return boolean held or not
     */
    public boolean isKeyHeld(int keycode){
        return heldKeys.contains(keycode);
    }

    /**
     * Returns true for GLFW keycode of any key pressed this update
     * @param keycode GLFW keycode (key)
     * @return boolean pressed or not
     */
    public boolean isKeyPressed(int keycode){
        return pressedKeys.contains(keycode);
    }

    /**
     * Returns true for GLFW keycode of any key released this update
     * @param keycode GLFW keycode (key)
     * @return boolean released or not
     */
    public boolean isKeyReleased(int keycode){
        return releasedKeys.contains(keycode);
    }

    /**
     * Returns true for GLFW keycode of any button hold this update
     * @param keycode GLFW keycode (buttons)
     * @return boolean held or not
     */
    public boolean isButtonHeld(int keycode){
        return heldButtons.contains(keycode);
    }

    /**
     * Returns true for GLFW keycode of any button pressed this update
     * @param keycode GLFW keycode (buttons)
     * @return boolean pressed or not
     */
    public boolean isButtonPressed(int keycode){
        return pressedButtons.contains(keycode);
    }

    /**
     * Returns true for GLFW keycode of any button released this update
     * @param keycode GLFW keycode (buttons)
     * @return boolean released or not
     */
    public boolean isButtonReleased(int keycode){
        return releasedButtons.contains(keycode);
    }

    /**
     * Clears the key and button maps and updates displacement vectors
     */
    public void update(){

        /** update displacement vector */

        displacement.x = cursorPos.x - prevPos.x;
        displacement.y = cursorPos.y - prevPos.y;

        prevPos.x = cursorPos.x;
        prevPos.y = cursorPos.y;


        /** update key maps */

        pressedButtons.clear();
        releasedButtons.clear();

        pressedKeys.clear();
        releasedKeys.clear();

        /** reset scroll displacement */
        scrollAmount = 0;


    }

    public Vector2d getDisplacement() {
        return displacement;
    }

    public Vector2d getCursorPos() {
        return cursorPos;
    }

    
    
}

