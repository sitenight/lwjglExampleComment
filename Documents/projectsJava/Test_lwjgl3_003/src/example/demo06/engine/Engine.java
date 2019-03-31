package example.demo06.engine;

/**
 * Содержит код игрового цикла. 
 * Этот класс будет реализовывать интерфейс Runnable, так
 * как игровой цикл будет запущен внутри отдельного потока
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Engine implements Runnable {

    /** FPS, которые мы пытаемся получить */
    public static final int TARGET_FPS = 75;
    
    /** UPS(обновления в секунду), которые мы пытаемся получить */
    public static final int TARGET_UPS = 30;
    
    /** Объект окно */
    private final Window window;
    
    /** Новый поток, где происходит весь игровой цикл */
    private final Thread gameLoopThread;
    
    /** Таймер для регулирования потока */
    private final Timer timer;
    
    /** Интерфейс игровой логики */
    private final ILogic logic;
    
    /** Дескриптор ввода мыши */
    private final MouseInput mouseInput;
    
    public Engine(String windowTitle, int width, int height, 
            boolean vSync, WindowOptions options, ILogic logic) throws Exception {
        // создаем отдельный поток
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        // создаем окно
        window = new Window(windowTitle, width, height, vSync, options);
        
        mouseInput = new MouseInput();
        this.logic = logic;
        timer = new Timer();
    }    

    /**
     * Запуск игрового потока
     */
    public void start() {
        String osName = System.getProperty("os.name");
        // игнорируем поток игрового цикла, если операционная система OSX 
        // выполняем код цикла игры непосредственно в главном потоке
        if (osName.contains("Mac")) {
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
            logic.cleanup();
        }
    }
    
    /**
     * Инициализации  {@link #window}, {@link #timer}, {@link #mouseInput} и {@link #logic}
     * @throws Исключение, если любая из операций не удалась
     */
    protected void init() throws Exception {
        // инициализируются экземпляры Window и Renderer.
        window.init();
        timer.init();
        mouseInput.init(window);
        logic.init(window);
    }

    /**
     * Метод игрового цикла.
     * <p> Проверяет ввод, отображает экран и обновляет FPS </p>
     */
    protected void gameLoop() {
        float interval = 1.0f / TARGET_UPS; // устанавливаем время обновления состояния игры по частоте кадров(FPS) = 30
        float steps = 0f;
        float elapsedTime; // время за одну итерацию игрового цикла
        
        boolean running = true;
        // Игровой цикл
        while(!window.windowShouldClose() && running) {
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
    
    /**
     * Получает входные данные от {@link #logic}
     */
    protected void input() {
        mouseInput.input(window);
        logic.input(window, mouseInput);
    }
    
    /**
     * Обновляет {@link #logic}
     * @param interval пройденые кадры
     */
    protected void update(float interval)  {
        logic.update(interval, mouseInput);
    }
    
    /**
     * Рендерит/визуализирует и обновляет окно
     */
    protected void render() {
        logic.render(window);
        window.update();
    }
    
    /**
     * Метод синхронизации, чтобы непрерывный рендеринг не израсходовал все ресурсы компьютера
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
