package example.demo06.engine.render.weather;

import example.demo04.math.Vector3f;

/**
 * Класс, представляющий туман
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Fog {

    /** Активен ли эффект тумана или нет */
    private boolean active;
    
    /** Цвет тумана */
    private Vector3f colour;
    
    /** Плотность тумана */
    private float density;
    
    public static Fog NOFOG = new Fog();
    
    /**
     * Конструктор нового тумана
     */
    public Fog() {
        active = false;
        this.colour = new Vector3f();
        this.density = 0.0f;
    }

    /**
     * Конструктор нового тумана
     * @param active Является ли туман активным
     * @param colour Цвет тумана
     * @param density Плотность тумана
     */
    public Fog(boolean active, Vector3f colour, float density) {
        this.active = active;
        this.colour = colour;
        this.density = density;
    }
    
    // ===============================================

    /**
     * Активен ли туман
     * @return активность тумана
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Установить активен ли туман или нет
     * @param active TRUE если туман активен
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Получение цвета тумана
     * @return цвет тумана
     */
    public Vector3f getColour() {
        return colour;
    }

    /**
     * Установка нового цвета тумана
     * @param colour цвет тумана
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    /**
     * Получение плотности тумана
     * @return плотность тумана
     */
    public float getDensity() {
        return density;
    }

    /**
     * Установка новой плотности тумана
     * @param density плотность тумана
     */
    public void setDensity(float density) {
        this.density = density;
    }
    
    
}
