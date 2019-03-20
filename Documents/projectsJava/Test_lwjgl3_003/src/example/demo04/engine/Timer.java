package example.demo04.engine;

/**
 * Предоставляет методы для вычисления прошедшего времени)
 * @author user
 */
public class Timer {

    private double lastLoopTime;
    
    public void init() {
        lastLoopTime = getTime();
    }
    
    /**
     * Получаем текущее время
     * @return текущее время
     */
    public double getTime() {
        return System.nanoTime() / 1000000000.0;
    }
    
    /**
     * Время прошедшее за предыдущюю итерацию игрового цикла
     * @return разница времени
     */
    public float getElapsedTime() {
        double time = getTime(); // текущее время
        float elapsedTime = (float) (time - lastLoopTime); // Время прошедшее за предыдущюю итерацию игрового цикла
        lastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}

