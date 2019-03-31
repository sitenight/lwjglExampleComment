package example.demo06.engine.render;

import example.demo04.math.Vector3f;
import example.demo06.engine.render.Texture;

/**
 * Содержит информацию о материале
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Material {

    /** Цвет материала по умолчанию (Белый) */
    private static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    
    /** Цвет материала */
    private Vector3f colour;
    
    /** Коэффициент отражения материала */
    private float reflectance;
    
    /** Текстура */
    private Texture texture;
    
    /** Карта нормалей */
    private Texture normalMap;
    
    /**
     * Конструктор нового материала. 
     * По умолчанию белого цвета и без отражения
     */
    public Material() {
        colour = DEFAULT_COLOR;
        reflectance = 0;
    }

    public Material(Vector3f colour, float reflectance) {
        this();
        this.colour = colour;
        this.reflectance = reflectance;
    }

    public Material(Texture texture) {
        this();
        this.texture = texture;
    }

    public Material(Texture texture, float reflectance) {
        this();
        this.texture = texture;
        this.reflectance = reflectance;
    }
    
    
    
    // ========================================

    /**
     * Получение цвета материала
     * @return цвет материала
     */
    public Vector3f getColour() {
        return colour;
    }

    /**
     * Установка цвета материала
     * @param colour цвет материала
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    /**
     * Получение коэффициента отражения материала
     * @return Коэффициент отражения материала
     */
    public float getReflectance() {
        return reflectance;
    }

    /**
     * Установка нового коэффициента отражения материала
     * @param reflectance Коэффициент отражения материала
     */
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    /**
     * Загружена ли текстура
     * @return TRUE если тестура загружена
     */
    public boolean isTextured() {
        return this.texture != null;
    }
    
    /**
     * Получение текстуры
     * @return текстуру материала
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Установка новой текстуры
     * @param texture текстура материала
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Загружена ли карта нормалей
     * @return TRUE если карта нормалей загружена
     */
    public boolean isNormalMap() {
        return this.normalMap != null;
    }
    
    /**
     * Получение карты нормалей
     * @return карта нормалей материала
     */
    public Texture getNormalMap() {
        return normalMap;
    }

    /**
     * Установка новой карты нормалей
     * @param normalMap карта нормалей материала
     */
    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }
    
    
}
