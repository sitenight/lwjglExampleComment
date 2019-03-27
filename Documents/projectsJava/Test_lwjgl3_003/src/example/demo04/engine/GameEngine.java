package example.demo04.engine;

import org.lwjgl.glfw.GLFW;

/**
 * Содержит код игрового цикла. 
 * Этот класс будет реализовывать интерфейс Runnable, так
 * как игровой цикл будет запущен внутри отдельного потока
 * @author user
 */
public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 75;
    
    public static final int TARGET_UPS = 30;
    
    private final Window window;
    
    private final Thread gameLoopThread;
    
    private final Timer timer;
    
    private final IGameLogic gameLogic;
    
     private final MouseInput mouseInput;
    
    public GameEngine(String windowTitle, int width, int height, 
            boolean vSync, IGameLogic gameLogic) throws Exception {
        // создаем отдельный поток
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        // создаем окно
        window = new Window(windowTitle, width, height, vSync);
        
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }    

    /**
     * Метод запуска
     */
    public void start() {
        String osName = System.getProperty("os.name");
        // игнорируем поток игрового цикла, если операционная система OSX 
        // выполняем код цикла игры непосредственно в главном потоке
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }
    
    /**
     * Выполняет задачи инициализации и будет запускать цикл игры
     */
    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // очищаем освобождая всех ресурсы после завершения цикла
            cleanup();
        }
    }
    
    /**
     * Метод инициализации
     */
    protected void init() throws Exception {
        // инициализируются экземпляры Window и Renderer.
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
    }

    protected void gameLoop() {
        float interval = 1.0f / TARGET_UPS; // устанавливаем время обновления состояния игры по частоте кадров(FPS) = 30
        float steps = 0f;
        float elapsedTime; // время за одну итерацию игрового цикла
        
        // Игровой цикл
        while(!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            steps += elapsedTime; // подсчитываем время итераций цикла
            
            input();  // обработку пользовательского ввода
            
            // позволяеv обновлять состояние игры через фиксированные промежутки времени
            while(steps >= interval) {
                update(interval);  // обновление состояния игры
                steps -= interval; 
            }
            render();           // отрисовки(рендеринг) на экране
            
            if(!window.isvSync())
                sync();
        }
    }
    
    protected void input() {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }
    
    protected void update(float interval)  {
        gameLogic.update(interval, mouseInput);
    }
    
    protected void render() {
        gameLogic.render(window);
        window.update();
    }
    
    protected void cleanup() {
        gameLogic.cleanup();
    }
    
    /**
     *  Метод синхронизации, чтобы непрерывный рендеринг не израсходовал все ресурсы компьютера
     */
    private void sync() {        
        float loopSlot = 1f / TARGET_FPS; // время итерации цикла игры
        double endTime = timer.getLastLoopTime() + loopSlot; // сумма полученого времени и время ожидания
        // Вычисляем когда мы должны выйти из цикла ожидания и запустить еще одну итерацию игрового цикла
        while(timer.getTime() < endTime) {
            try {
                Thread.sleep(1);// время ожидания равно 1 миллисекунду, много маленьких ожиданий
            } catch (InterruptedException e) {
            }
        }
    }
}

