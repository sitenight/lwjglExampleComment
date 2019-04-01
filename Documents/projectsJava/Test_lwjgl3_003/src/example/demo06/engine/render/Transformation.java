package example.demo06.engine.render;

import example.demo04.math.*;
import example.demo06.engine.items.Item;

/**
 * Служебный класс, который предоставляет методы для создания матриц, 
 * используемых в процессе рендеринга
 * @author user
 */
public class Transformation {

    /** Матрица проекции/перспектива */
    private final Matrix4f projectionMatrix;
    
    /** Матрица модели */
    private Matrix4f modelMatrix;
    
    /** Матрица модели вида */
    private Matrix4f modelViewMatrix;
    
    /** Матрица модели света */
    private Matrix4f modelLightViewMatrix;
    
    /** Матрица вида */
    private Matrix4f viewMatrix;
    
    /** Матрица вида света */
    private Matrix4f lightViewMatrix;
    
    /** Ортографическая матрица проекции */
    private final Matrix4f orthoProjMatrix;
    
    /** Орто матрица */
    private final Matrix4f ortho2DMatrix;
    
    /** Ортомодельная матрица */
    private final Matrix4f orthoModelMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        modelLightViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        orthoProjMatrix = new Matrix4f();
        ortho2DMatrix = new Matrix4f();
        orthoModelMatrix = new Matrix4f();
        lightViewMatrix = new Matrix4f();
    }
    
    /**
     * Получение матрицы проекции
     * @return матрица проекции
     */
    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
    
    /**
     * Обновление {@link #projectionMatrix}
     * @param fov Поле зрения в радианах
     * @param width Ширина усеченного конуса
     * @param height Высота усеченного конуса
     * @param zNear Ближняя координата усеченного конуса
     * @param zFar Дальняя координата усеченного конуса
     * @return 
     */
    public Matrix4f updateProjectionMatrix(float fov, int width, int height, float zNear, float zFar) {
        return projectionMatrix.projection(fov, width, height, zNear, zFar);
    }

    // ============ ORTHO ============
    
    /**
     * Возвращает ортографическую матрицу
     * @param left левая координата
     * @param right правая координата
     * @param bottom нижняя координата
     * @param top верхняя координата
     * @return Ортогональная матрица
     */
    public final Matrix4f getOrtho2DProjectionMatrix(float left, float right, float bottom, float top){
        return ortho2DMatrix.setOrtho2D(left, right, bottom, top);
    }
    
    /**
     * Получение ортогональной матрицы 
     * @return Ортогональная матрица
     */
    public final Matrix4f getOrthoProjectionMatrix(){
        return orthoProjMatrix;
    }

    /**
     * Обновление матрицы ортографической проекции
     * @param left левая координата
     * @param right правая координата
     * @param bottom нижняя координата
     * @param top верхняя координата
     * @param zNear Ближняя координата
     * @param zFar Дальняя координата
     * @return Ортогональная матрица
     */
    public Matrix4f updateOrthoProjectionMatrix(float left, float right, float bottom, float top, float zNear, float zFar){
        return orthoProjMatrix.OrthographicProjection(left, right, bottom, top, zNear, zFar);
    }
    
    // ============ VIEW MATRIX ============
    
    /**
     * Получение матрицы вида
     * @return Матрица вида
     */
    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }
    
    /**
     * Обновляем матрицу вида
     * @param camera камера
     * @return Матрица вида
     */
    public Matrix4f updateViewMatrix(Camera camera) {
        return updateGenericViewMatrix(camera.getPosition(), camera.getRotation(), viewMatrix);
    }
    
    /**
     * Обновляем матрицу вида
     * @param position позиция
     * @param rotation поворот
     * @param matrix матрица
     * @return Матрица вида
     */
    private Matrix4f updateGenericViewMatrix(Vector3f position, Vector3f rotation, Matrix4f matrix) {        
        // Сначала делаем поворот, чтобы камера вращалась над своей позицией        
        Matrix4f rotationMatrix = new Matrix4f(matrix.rotated(rotation.x, rotation.y, 0));
        
        // Потом перемещение, позиции с отрицательными значениями     
        Matrix4f translationMatrix = new Matrix4f(matrix.translated(position.mul(-1.0f)));
                
        matrix = rotationMatrix.mul(translationMatrix);
        return matrix;
    }
    
    // ============ LIGHT MATRIX ============
    
    /**
     * Получение Световой матрицы вида
     * @return Световая матрица вида
     */
    public Matrix4f getLightViewMatrix(){
        return lightViewMatrix;
    }

    /**
     * Установка Световой матрицы вида
     * @param lightViewMatrix Световая матрица вида
     */
    public void setLightViewMatrix(Matrix4f lightViewMatrix){
        this.lightViewMatrix.set(lightViewMatrix);
    }

    /**
     * Обновляем Световую матрицу вида
     * @param position вектор позиции
     * @param rotation вектор поворота
     * @return Световая матрица вида
     */
    public Matrix4f updateLightViewMatrix(Vector3f position, Vector3f rotation){
        return updateGenericViewMatrix(position, rotation, lightViewMatrix);
    }

    /**
     * Создает новую матрицу модели, умноженную на матрицу вида.
     * @param item Объект
     * @return Новая матрица.
     */
    public Matrix4f buildModelMatrix(Item item){
        Quaternion rotation = item.getRotation();
        return modelMatrix.translationRotateScale(
                item.getPosition().x, item.getPosition().y, item.getPosition().z,
                rotation.x, rotation.y, rotation.z, rotation.w,
                item.getScale(), item.getScale(), item.getScale());
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

