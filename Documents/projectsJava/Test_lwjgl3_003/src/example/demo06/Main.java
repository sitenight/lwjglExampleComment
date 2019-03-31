package example.demo06;

import example.demo06.engine.*;

/**
 * https://github.com/gjkf/seriousEngine/blob/master/src/main/java/io/github/gjkf/seriousEngine/Window.java
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Main {

    public static void main(String[] args) {
        SharedLibraryLoader.load();
        String os = System.getProperty("os.name").toLowerCase();
        // Mac OS X нужен headless режим для AWT
        if(os.contains("mac"))
            System.setProperty("java.awt.headless", "true");
        
        try {
            boolean vSync = true;
            ILogic gameLogic = new DummyGame();
            WindowOptions options = new WindowOptions();
            options.cullFace = true;
            options.showFps = true;
            Engine gameEngine = new Engine("Game", 800, 600, vSync, options, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }   
}
