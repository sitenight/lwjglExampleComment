package example.demo05;

public class Game {
    
    private static Level level;
    private static boolean isRunning;
    private static int levelNum = 0;
    private static Window window;
	
    public Game(Window window) {
        this.window = window;
	loadNextLevel();
    }
	
    public void input() {
	level.input(window);
    }
	
    public void update() {
	if(isRunning)
            level.update();
    }
	
    public void render() {
	if(isRunning)
            level.render();
    }

    public static void loadNextLevel() {
	levelNum++;
	level = new Level("level" + levelNum + ".png", "WolfCollection.png");

	Transform.setProjection(70, window.getWidth(), window.getHeight(), 0.01f, 1000f);
	Transform.setCamera(level.getPlayer().getCamera());
	isRunning = true;
    }

    public static Level getLevel() {
	return level;
    }

    public static void setIsRunning(boolean value) {
	isRunning = value;
    }
}

