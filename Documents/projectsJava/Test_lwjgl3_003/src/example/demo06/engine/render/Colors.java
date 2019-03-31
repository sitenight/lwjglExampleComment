package example.demo06.engine.render;

import example.demo04.math.Vector3f;

/**
 * Наиболее используемые цвета
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public enum Colors {

    WHITE(new Color4f(1f, 1f, 1f, 1f)),
    BLACK(new Color4f(0f, 0f, 0f, 1f)),
    RED(new Color4f(1f, 0f, 0f, 1f)),
    GREEN(new Color4f(0f, 1f, 0f, 1f)),
    BLUE(new Color4f(0f, 0f, 1f, 1f)),
    PURPLE(new Color4f(1f, 0f, 1f, 1f)),
    NULL(null);
    
    public final Color4f color;
    
    Colors(Color4f color) {
        this.color = color;
    }

    public Vector3f toVector() {
        return new Vector3f(color.r, color.g, color.b);
    }
}
