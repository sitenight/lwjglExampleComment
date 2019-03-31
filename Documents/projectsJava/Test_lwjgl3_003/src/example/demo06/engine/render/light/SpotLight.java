package example.demo06.engine.render.light;

import example.demo04.math.Vector3f;

/**
 * Прожектор
 * <p> Очень похож на {@link PointLight}, но работает только под определенным углом. </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class SpotLight {

    /** Точечный свет */
    private PointLight pointLight;
    
    /** Направление конуса света */
    private Vector3f coneDirection;
    
    /** Отрезка */
    private float cutOff;

    /**
     * Конструктор нового прожектора
     * @param pointLight Точечный свет
     * @param coneDirection вектор направление конуса света
     * @param cutOffAngle угол отрезки
     */
    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutOffAngle) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        setCutOffAngle(cutOffAngle);
    }

    /**
     * Копирование прожектора
     * @param spotLight прожектор
     */
    public SpotLight(SpotLight spotLight) {
        this(new PointLight(spotLight.getPointLight()), 
                new Vector3f(spotLight.coneDirection), 
                spotLight.getCutOff());
    }
    
    // =================================================

    /**
     * Получение текущего Точечного света
     * @return Точечный свет 
     */
    public PointLight getPointLight() {
        return pointLight;
    }

    /**
     * Установка нового Точечного света 
     * @param pointLight Точечный свет 
     */
    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    /**
     * Получение текущего Направления конуса света
     * @return Направление конуса света
     */
    public Vector3f getConeDirection() {
        return coneDirection;
    }

    /**
     * Установка нового Направления конуса света
     * @param coneDirection Направление конуса света
     */
    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    /**
     * Получение текущей отрезки
     * @return Отрезка
     */
    public float getCutOff() {
        return cutOff;
    }

    /**
     * Установка новой Отрезки
     * @param cutOff Отрезка
     */
    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }

    /**
     * Установка угла отрезки
     * @param cutOffAngle угол отрезки
     */
    public void setCutOffAngle(float cutOffAngle) {
        this.setCutOff((float)Math.cos(Math.toRadians(cutOffAngle)));
    }
    
}
