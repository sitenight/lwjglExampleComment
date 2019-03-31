package example.demo06.engine.items;

import example.demo04.math.*;
import example.demo06.engine.render.*;

/**
 * Объект, представляющий предмет в мире
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Item {

    /** Сетка этого объекта */
    private Mesh[] meshes;
    
    /** Позиция объекта в мире */
    private final Vector3f position;
    
    /** Масштаб объекта в мире */
    private float scale;
    
    /** Поворот объекта в мире */
    private final Quaternion rotation;
    
    /** Позиция текстуры объекта */
    private int textPosition;

    /**
     * Конструктор нового предмета
     */
    public Item() {
        this.position = new Vector3f();
        this.rotation = new Quaternion();
        this.scale = 1;
        this.textPosition = 0;
    }
    
    /**
     * Создает новый элемент с позицией и вращением, например <tt>new Vector3f(0,0,0)</tt>
     * @param mesh Сетка предмета
     */
    public Item(Mesh mesh) {
        this();
        this.meshes = new Mesh[] { mesh };
    }
    
    public Item(Mesh[] meshes) {
        this();
        this.meshes = meshes;
    }
    
    /**
     * Очистка ресурсов сеток
     */
    public void cleanup() {
        if(this.meshes != null)
            for (Mesh mesh : meshes) {
                mesh.cleanup();
            }
    }
    
    /**
     * Проверяет, сталкивается ли данный элемент с текущим
     * @param item Элемент для проверки
     * @return TRUE если есть столкновение
     */
    public boolean checkCollisionWith(Item item) {
        if(item.equals(this)) {
            return true;
        }
        boolean collidedX = false, collidedY = false, collidedZ = false;
        
        Vector3f thisPosition = new Vector3f(
                getPosition().x * getScale(), 
                getPosition().y * getScale(), 
                getPosition().z * getScale()
        );
        
        Vector3f itemPosition = new Vector3f(
                item.getPosition().x * item.getScale(), 
                item.getPosition().y * item.getScale(), 
                item.getPosition().z * item.getScale()
        );
        
        Vector2f thisXBounds = new Vector2f(thisPosition.x - getScale(), thisPosition.x + getScale());
        Vector2f thisYBounds = new Vector2f(thisPosition.y - getScale(), thisPosition.y + getScale());
        Vector2f thisZBounds = new Vector2f(thisPosition.z - getScale(), thisPosition.z + getScale());
        
        Vector2f itemXBounds = new Vector2f(itemPosition.x - item.getScale(), itemPosition.x + item.getScale());
        Vector2f itemYBounds = new Vector2f(itemPosition.y - item.getScale(), itemPosition.y + item.getScale());
        Vector2f itemZBounds = new Vector2f(itemPosition.z - item.getScale(), itemPosition.z + item.getScale());
        
        if(itemPosition.x < thisPosition.x) {
            if(itemXBounds.y >= thisXBounds.x)
                collidedX = true;
        } else {
            if(itemXBounds.x >= thisXBounds.y)
                collidedX = true;
        }
        
        if(itemPosition.y < thisPosition.y) {
            if(itemYBounds.y >= thisYBounds.x)
                collidedY = true;
        } else {
            if(itemYBounds.x >= thisYBounds.y)
                collidedY = true;
        }
        
        if(itemPosition.z < thisPosition.z) {
            if(itemZBounds.y >= thisZBounds.x)
                collidedZ = true;
        } else {
            if(itemZBounds.x >= thisZBounds.y)
                collidedZ = true;
        }
        
        return collidedX && collidedY && collidedZ;
    }
    
    // =====================================================

    /**
     * Возвращает Позицию объекта в мире
     * @return Позиция объекта в мире
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Устанавливает новую Позицию объекта в мире
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     */
    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     * Получение Масштаба предмета в мире
     * @return Масштаб предмета в мире
     */
    public float getScale() {
        return scale;
    }

    /**
     * Установка нового Масштаба предмета в мире
     * @param scale Масштаб предмета в мире
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Получение Поворота объекта в мире
     * @return Поворот объекта в мире
     */
    public Quaternion getRotation() {
        return rotation;
    }
    
    /**
     * Установка Поворота объекта в мире
     * @param q Поворот объекта в мире
     */
    public void setPosition(Quaternion q) {
        this.rotation.set(q);
    }

    /**
     * Получение сетку первого объекта
     * @return сетка первого объекта
     */
    public Mesh getMesh() {
        return meshes[0];
    }
    
    /**
     * Установка одной сетки объекта
     * @param mesh сетка объекта
     */
    public void setMesh(Mesh mesh) {
        this.meshes = new Mesh[] { mesh };
    }

    /**
     * Получение списка сеток объектов
     * @return списка сеток объектов
     */
    public Mesh[] getMeshes() {
        return meshes;
    }

    /**
     * Установка списка сеток объектов
     * @param meshes списка сеток объектов
     */
    public void setMeshes(Mesh[] meshes) {
        this.meshes = meshes;
    }

    /**
     * Получение позиции текстуры объекта
     * @return Позиция текстуры объекта
     */
    public int getTextPosition() {
        return textPosition;
    }

    /**
     * Установка Позиции текстуры объекта
     * @param textPosition Позиция текстуры объекта
     */
    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
    }
    
}
