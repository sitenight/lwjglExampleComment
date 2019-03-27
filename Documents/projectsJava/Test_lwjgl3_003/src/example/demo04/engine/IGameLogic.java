package example.demo04.engine;

/**
 * Инкапсулирует нашу логику игры
 * Делаем наш игровой движок универсальным для разных задач
 * @author user
 */
public interface IGameLogic {
    
    /**
     * Инициализвация данных
     * @throws Exception 
     */
    void init(Window window) throws Exception;
    
    /**
     * Ввод данных
     * @param window 
     * @param mouseInput 
     */
    void input(Window window, MouseInput mouseInput);
    
    /**
     * Обновление состояния игры
     * @param interval 
     * @param mouseInput 
     */
    void update(float interval, MouseInput mouseInput);
    
    /**
     * Отображения данных, относящихся к игре
     * @param window 
     */
    void render(Window window);
    
    /**
     * Метод очистки для освобождения всех ресурсов
     */
    void cleanup();
}
