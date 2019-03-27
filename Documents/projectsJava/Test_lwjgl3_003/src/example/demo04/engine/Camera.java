package example.demo04.engine;

import example.demo04.math.Vector3f;

public class Camera {

    private final Vector3f position;
    private final Vector3f rotation;

    public Camera() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
    }

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
     * @param offsetX
     * @param offsetY
     * @param offsetZ 
     */
    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    
    // ============== getter & setter ===============

    public Vector3f getPosition() {
        return position;
    }
    
    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vector3f getRotation() {
        return rotation;
    }    
    
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }
    
}

