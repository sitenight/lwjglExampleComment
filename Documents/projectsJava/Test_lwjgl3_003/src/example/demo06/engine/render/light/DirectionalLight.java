package example.demo06.engine.render.light;

import example.demo04.math.*;

/**
 * Направленный источник света, как солнце.
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class DirectionalLight {

    /** Цвет */
    private Vector3f color;
    
    /** Вектор направления */
    private Vector3f direction;
    
    /** Интенсивность */
    private float intensity;
    
    /** Ортографические координаты */
    private OrthoCoords orthoCoords;
    
    /** Фактор */
    private float shadowPosMult;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
        this.orthoCoords = new OrthoCoords();
        this.shadowPosMult = 1;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColor()), new Vector3f(light.getDirection()), light.getIntensity());
    }
    
    // ============================================

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    public float getShadowPosMult() {
        return shadowPosMult;
    }

    public void setShadowPosMult(float shadowPosMult) {
        this.shadowPosMult = shadowPosMult;
    }

    public OrthoCoords getOrthoCoords() {
        return orthoCoords;
    }

    public void setOrthoCoords(float left, float right, float bottom, float top, float near, float far) {
        orthoCoords.left = left;
        orthoCoords.right = right;
        orthoCoords.bottom = bottom;
        orthoCoords.top = top;
        orthoCoords.near = near;
        orthoCoords.far = far;
    }
    
    
}
