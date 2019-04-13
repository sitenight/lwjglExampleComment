package example.demo07.engine.gui.layout;

import example.demo07.engine.gui.Box;
import example.demo07.engine.gui.element.Element;
import org.joml.Vector2i;


public class VerticalSplitLayout extends Layout{

    float cutoffY;

    public VerticalSplitLayout(Element parent){
        super(parent);
        cutoffY = 0.5f;
    }

    public void setCutoffY(int pixels){
        Vector2i pixSize = getParent().getAbsolutePixelSize();
        cutoffY = (float)pixels/(float)pixSize.y;
    }

    public Box findRelativeTransform(Element e, int index){
        if(index == 0){
            return new Box(0, 1-cutoffY, 1, cutoffY);
        } else if (index == 1){
            return new Box(0, 0, 1, 1-cutoffY);
        }
        return null;
    }

    public float getCutoffY() {
        return cutoffY;
    }

    public void setCutoffY(float cutoffY) {
        this.cutoffY = cutoffY;
    }
    
    
}

