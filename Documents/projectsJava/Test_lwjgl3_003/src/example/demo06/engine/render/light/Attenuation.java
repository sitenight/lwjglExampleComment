package example.demo06.engine.render.light;

/**
 * Используется для определения интенсивности света в зависимости от расстояния.
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Attenuation {

    /** Константа */
    private float constant;
    
    /** Линейный компонент */
    private float linear;
    
    /** Экспонента */
    private float exponent;

    /**
     * Создание нового значения затухания
     * @param constant Константа
     * @param linear Линейный компонент
     * @param exponent Экспонента
     */
    public Attenuation(float constant, float linear, float exponent) {
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }
    
    // =================================================

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
    
    
}
