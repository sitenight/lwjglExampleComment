package example.demo04.engine;

import example.demo04.engine.model.GameItem;
import example.demo04.math.*;

public class Transformation {

    /** Матрица проекции/перспектива */
    private final Matrix4f projectionMatrix;
    
    /** Матрица вида */
    private final Matrix4f viewMatrix;
    
    private Matrix4f modelViewMatrix;

    public Transformation() {
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.modelViewMatrix = new Matrix4f();
    }
    
    public Matrix4f getProjectionMatrix(float fov, int width, int height, float zNear, float zFar) {
        projectionMatrix.identity();
        projectionMatrix.projection(fov, width, height, zNear, zFar);
        return projectionMatrix;
    }
    
    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        
        viewMatrix.identity();
        // Сначала делаем поворот, чтобы камера вращалась над своей позицией
        viewMatrix.rotated(rotation);
        // Потом перемещение, позиции с отрицательными значениями
        viewMatrix.translated(cameraPos.mul(-1.0f));
        return viewMatrix;
    }
    
    /**
     * Получение мировых координат
     * @param offset вектор смещения
     * @param rotation вектор вращения
     * @param scale масштаб
     * @return возвращает матрицу, которая будет использоваться 
     * для преобразования координат для каждого GameItem экземпляра
     */
    public Matrix4f getModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix) {
        Vector3f rotation = gameItem.getRotation();
        
	Matrix4f translationMatrix = modelViewMatrix.translated(gameItem.getPosition());
	Matrix4f rotationMatrix = new Matrix4f().rotated(rotation.mul(-1.0f));
	Matrix4f scaleMatrix = new Matrix4f().scaled(gameItem.getScale());
        
        modelViewMatrix = translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        
	return viewCurr.mul(modelViewMatrix);
    }
}

