package example.demo06.engine;

/**
 * Интерфейс, представляющий игровую логику.
 * Реализуйте это, чтобы создать свою собственную игру.
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public interface ILogic {

    /**
     * Инициализируем игровую логику
     * @param window окно
     * @throws Exception если что-то пошло не так
     */
    void init(Window window) throws Exception;
    
    /**
     * Получение входных данных из окна
     * @param window текущее окно
     * @param mouseInput ввод мыши
     */
    void input(Window window, MouseInput mouseInput);
    
    /**
     * Обновление игровой логики. Использование расчетов
     * @param interval пройденые кадры
     * @param mouseInput ввод мыши
     */
    void update(float interval, MouseInput mouseInput);
    
    /**
     * Визуализация игровой логики
     * @param window окно для обновления
     */
    void render(Window window);
    
    /**
     * Очистка ресурсов
     */
    void cleanup();
}
