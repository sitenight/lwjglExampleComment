package example.demo06.engine.render.light;

import example.demo04.math.Vector3f;

/**
 * Точечный источник света
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class PointLight {

    /** Цвет света */
    private Vector3f color;
    
    /** Положение в пространстве  */
    private Vector3f position;
    
    /** Интенсивность освещения  */
    private float intensity;
    
    /** Коэффициенты затухания  */
    private Attenuation attenuation;

    /**
     * Определяет новый точечный источник света
     * @param color цвет света
     * @param position Положение в пространстве
     * @param intensity Интенсивность освещения
     */
    public PointLight(Vector3f color, Vector3f position, float intensity) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.attenuation = new Attenuation(1, 0, 0);
    }

    /**
     * Определяет новый точечный источник света
     * @param color цвет света
     * @param position Положение в пространстве
     * @param intensity Интенсивность освещения
     * @param attenuation Коэффициенты затухания
     */
    public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
        this(color, position, intensity);
        this.attenuation = attenuation;
    }

    /**
     * Копируем точечный свет
     * @param pointLight точечный свет
     */
    public PointLight(PointLight pointLight) {
        this(new Vector3f(pointLight.getColor()), 
                new Vector3f(pointLight.getPosition()), 
                pointLight.getIntensity(), 
                pointLight.getAttenuation()
        );
    }
    
    // ===============================

    /**
     * Получение текущего Цвета света
     * @return Цвет света
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * Установка нового Цвета света
     * @param color Цвет света
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Получение текущего Положение в пространстве источника света
     * @return Положение в пространстве источника света
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Установка нового Положение в пространстве источника света
     * @param position Положение в пространстве источника света
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Получение текущей Интенсивности освещения
     * @return Интенсивность освещения
     */
    public float getIntensity() {
        return intensity;
    }
    
    /**
     * Установка новой Интенсивности освещения
     * @param intensity Интенсивность освещения
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Получение текущих Коэффициентов затухания
     * @return Коэффициенты затухания
     */
    public Attenuation getAttenuation() {
        return attenuation;
    }

    /**
     * Установка новых Коэффициентов затухания
     * @param attenuation Коэффициенты затухания
     */
    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }
    
    
}
