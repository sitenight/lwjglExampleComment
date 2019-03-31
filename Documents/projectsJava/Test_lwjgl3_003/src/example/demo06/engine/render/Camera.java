package example.demo06.engine.render;

import example.demo04.math.Vector3f;

/**
 * Объект, представляющий камеру зрителя / игрока.
 * <p>Принимает во внимание положение и вращение</p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Camera {

    /** Вектор, представляющий положение камеры */
    private final Vector3f position;
    
    /** Вектор, представляющий поворот камеры */
    private final Vector3f rotation;

    /**
     * Конструктор камеры эквивалентен <tt>new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));</tt>
     */
    public Camera() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
    }

    /**
     * Конструктор камеры, который принимает положение и поворот
     * @param position положение камеры
     * @param rotation поворот камеры
     */
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }
    
    /**
     * Смещение камеры на заданное растояние
     * @param offsetX смещение по оси X
     * @param offsetY смещение по оси Y
     * @param offsetZ смещение по оси Z
     */
    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if(offsetZ != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if(offsetX != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }
    
    /**
     * Поворот камеры на заданный угол
     * @param offsetX поворот по оси X
     * @param offsetY поворот по оси Y
     * @param offsetZ поворот по оси Z
     */
    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    
    // ============== getter & setter ===============

    /**
     * Получение положения камеры
     * @return положение камеры
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Установка нового положения камеры
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     */
    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    /**
     * Получение вектора поворота камеры
     * @return вектор поворота камеры
     */
    public Vector3f getRotation() {
        return rotation;
    }    
    
    /**
     * Установка поворота камеры
     * @param x поворот по оси X
     * @param y поворот по оси Y
     * @param z поворот по оси Z
     */
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }
    
}

