package sbtm.engine.light;

import sbtm.engine.maths.Vector3f;
import sbtm.engine.maths.Vector4f;

/**
 * Точечный источник света
 * @author Sitenight 
 */
public class PointLight {

    /** Положение в пространстве  */
    private Vector4f position;
    
    /** Мощность фонового освещения  */
    private Vector4f ambient;
    
    /** Мощность рассеянного освещения  */
    private Vector4f diffuse;
    
    /** Мощность отраженного освещения  */
    private Vector4f specular;
    
    /** Коэффициенты затухания  */
    private Vector3f attenuation;
    
    /**
     * Передача параметров источника освещения в шейдерную программу
     */
    public void PointLightSetup() {
        // установка параметров
        
    }
}

