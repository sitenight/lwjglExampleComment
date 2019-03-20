package sbtm.engine;

import sbtm.engine.maths.Vector3f;
import sbtm.engine.maths.Matrix4f;

/**
 * Камера в графической сцене представляет собой положение наблюдателя и то, 
 * как он видит сцену вокруг себя. Положение и направление взгляда задаются 
 * матрицей вида (ViewMatrix), а формат отображения задается матрицей 
 * проекции (ProjectionMatrix).
 * @author user
 */
public class Camera {

    private Vector3f position;
    
    private Vector3f rotation;
    
    private Matrix4f projection;
    
}

