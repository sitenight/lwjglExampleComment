package example.demo06.engine.render.particles;

import example.demo04.math.*;
import example.demo06.engine.items.Item;
import example.demo06.engine.render.*;

/**
 * Частица Это объект, который всегда ориентирован на камеру.
 * <p> Имеет установленное время для жизни и скорости, может использовать текстурный атлас. </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Particle extends Item {

    /** Сколько миллисекунд от каждого обновления текстуры */
    private long updateTextureMillis;
    
    /** Сколько миллисекунд у этой частицы была текущая текстура */
    private long currentAnimTimeMillis;
    
    /** Скорость частиц */
    private Vector3f speed;
    
    /** Время жизни частицы в миллисекундах */
    private long ttl;
    
    /** Количество кадров в текстурном атласе */
    private int animFrames;

    public Particle(Mesh mesh, Vector3f speed, long ttl, long updateTextureMillis) {
        super(mesh);
        this.speed = new Vector3f(speed);
        this.ttl = ttl;
        this.updateTextureMillis = updateTextureMillis;
        this.currentAnimTimeMillis = 0;
        Texture texture = this.getMesh().getMaterial().getTexture();
        this.animFrames = texture.getNumCols() * texture.getNumRows();
    }

    public Particle(Particle baseParticle) {
        super(baseParticle.getMesh());
        //...
        
    }
    
    public long updateTtl(long elapsedTime) {
        //...
    }
    
    // ==============================================

    public int getAnimFrames() {
        return animFrames;
    }

    public Vector3f getSpeed() {
        return speed;
    }

    public void setSpeed(Vector3f speed) {
        this.speed = speed;
    }

    public long getUpdateTextureMillis() {
        return updateTextureMillis;
    }

    public void setUpdateTextureMillis(long updateTextureMillis) {
        this.updateTextureMillis = updateTextureMillis;
    }
    
    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
    
    
}
