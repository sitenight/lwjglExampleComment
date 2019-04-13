package example.demo07.engine.event;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class MouseEvent extends Event {

    Vector2d glfwScreenPos;
    Vector2i pixelScreenPos;
    Vector2f ndcScreenPos;

    int key;
    int action;
    int mods;

    public MouseEvent(int key, int action, int mods, Vector2d glfwScreenPos){
        this.key = key;
        this.action = action;
        this.mods = mods;
        this.glfwScreenPos = glfwScreenPos;
    }

    public MouseEvent(Vector2d glfwScreenPos, Vector2i pixelScreenPos, Vector2f ndcScreenPos, int key, int action, int mods) {
        this.glfwScreenPos = glfwScreenPos;
        this.pixelScreenPos = pixelScreenPos;
        this.ndcScreenPos = ndcScreenPos;
        this.key = key;
        this.action = action;
        this.mods = mods;
    }

    public MouseEvent() {
        
    }

    

}

