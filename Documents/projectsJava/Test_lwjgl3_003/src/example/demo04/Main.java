package example.demo04;

import example.demo04.engine.IGameLogic;
import example.demo04.engine.GameEngine;
import example.demo04.engine.Transformation;
import example.demo04.math.Matrix4f;
import example.demo04.math.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * По учебнику РУС https://github.com/Igorek536/lwjgl-rusbook
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

