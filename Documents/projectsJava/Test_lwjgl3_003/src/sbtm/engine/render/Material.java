package sbtm.engine.render;

import sbtm.engine.maths.Vector4f;

/**
 * Материал объекта
 * @author Sitenight
 */
public class Material {
    
    private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    
    /** Текстура объекта */
    private Texture texture;
    
    /** Восприятие фонового освещения */
    private Vector4f ambient;
    
    /** Восприятие рассеянного освещения  */
    private Vector4f diffuse;
    
    /** Восприятие отраженного освещения */
    private Vector4f specular;
    
    /** Самостоятельное свечение */
    private Vector4f emission;
    
    /** Коэффициент коэффициент отражения */
    private float reflectance;
    
    public Material() {
        this.texture = null;
        this.ambient = DEFAULT_COLOUR;
        this.diffuse = DEFAULT_COLOUR;
        this.specular = DEFAULT_COLOUR;
        this.emission = DEFAULT_COLOUR;
        this.reflectance = 0;
    }

    public Material(Texture texture, Vector4f ambient, Vector4f diffuse, Vector4f specular, Vector4f emission, float reflectance) {
        this.texture = texture;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.emission = emission;
        this.reflectance = reflectance;
    }
    
    public Material(Texture texture) {
        this(texture, DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, 0.0f);
    }
    
    public Material(Texture texture, float reflectance) {
        this(texture, DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, reflectance);
    }
    
    // ============== getter & setter =============

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    public Vector4f getEmission() {
        return emission;
    }

    public void setEmission(Vector4f emission) {
        this.emission = emission;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }
    
    
    
}

