package example.demo06.engine.render.particles;

import example.demo06.engine.items.Item;
import java.util.List;

/**
 * Реализуйте этот интерфейс, чтобы создать свой собственную <tt> Систему частиц </tt>.
 * <p> Пример можно найти по адресу {@link FlowParticleEmitter} </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public interface IParticleEmitter {

    /**
     * Очистка ресурсов
     */
    void cleanup();
    
    /**
     * Получаем базовую частицу. Из этой частицы берут начало все остальные.
     * @return базовую частицу
     */
    Particle getBaseParticle();
    
    /**
     * Получает список частиц
     * @return список частиц
     */
    List<Item> getParticles();
}
