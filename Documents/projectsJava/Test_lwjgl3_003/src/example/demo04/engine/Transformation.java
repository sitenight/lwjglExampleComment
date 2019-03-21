package example.demo04.engine;

import example.demo04.math.*;

public class Transformation {

    /** Матрица проекции/перспектива */
    private final Matrix4f projectionMatrix;
    
    /** Мировая матрица */
    private Matrix4f worldMatrix;

    public Transformation() {
        this.projectionMatrix = new Matrix4f();
        this.worldMatrix = new Matrix4f();
    }
    
    public Matrix4f getProjectionMatrix(float fov, int width, int height, float zNear, float zFar) {
        projectionMatrix.identity();
        projectionMatrix.projection(fov, width, height, zNear, zFar);
        return projectionMatrix;
    }
    
    /**
     * Получение мировых координат
     * @param offset вектор смещения
     * @param rotation вектор вращения
     * @param scale масштаб
     * @return возвращает матрицу, которая будет использоваться 
     * для преобразования координат для каждого GameItem экземпляра
     */
    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
	Matrix4f translationMatrix = new Matrix4f().translated(offset);
	Matrix4f rotationMatrix = new Matrix4f().rotated(rotation);
	Matrix4f scaleMatrix = new Matrix4f().scaled(scale);
        
        worldMatrix = translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
        //System.out.println(worldMatrix.toString());
        
	return worldMatrix;
    }
}

