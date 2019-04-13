package example.demo07.engine.gui.layout;

import example.demo07.engine.gui.Box;
import example.demo07.engine.gui.element.Element;

public abstract class Layout {

    protected Element parent;

    public Layout(Element parent){
        this.parent = parent;
    }

    public abstract Box findRelativeTransform(Element e, int index);

    public Element getParent() {
        return parent;
    }
    
    
}

