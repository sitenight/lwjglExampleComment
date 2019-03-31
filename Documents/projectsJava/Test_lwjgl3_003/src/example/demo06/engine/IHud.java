package example.demo06.engine;

import example.demo06.engine.items.Item;

/**
 * Интерфейс, который должен быть реализован при создании нового HUD
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public interface IHud {

    /**
     * Получение всех объектов
     * @return игровые объекты
     */
    Item[] getItems();
    
    /**
     * Очистка всех ресурсов объектов
     */
    default void cleanup() {
        Item[] items = getItems();
        for (Item item : items) {
            item.getMesh().cleanup();
        }
    }
}
