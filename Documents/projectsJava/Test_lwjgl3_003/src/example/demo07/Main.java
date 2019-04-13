package example.demo07;

import example.demo07.engine.system.Core;

/**
 * https://github.com/theZiggurat/LWJGL-Engine
 * @author user
 */
public class Main {

     public static void main(String... args){
        try {
            Core engine = new Core();
            engine.start();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

