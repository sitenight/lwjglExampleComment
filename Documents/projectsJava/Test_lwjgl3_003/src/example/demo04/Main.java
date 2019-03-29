package example.demo04;

import example.demo04.engine.IGameLogic;
import example.demo04.engine.GameEngine;
import example.demo04.engine.Transformation;
import example.demo04.math.Matrix4f;
import example.demo04.math.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * По учебнику РУС https://github.com/Igorek536/lwjgl-rusbook
 * Англ вариант: https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter09/chapter9.html
 * исходник: https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter09/src/main/java/org/lwjglb/engine/graph/OBJLoader.java
 * @author user
 */
public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("GAME", 800, 600, vSync, gameLogic);     
            gameEng.start();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    
}

