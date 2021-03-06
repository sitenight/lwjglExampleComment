package example.demo07.engine.gui.layout;

import example.demo07.engine.gui.Box;
import example.demo07.engine.gui.element.Element;

public class AbsoluteLayout extends Layout {

    public AbsoluteLayout(Element e){
        super(e);
    }

    /**
     * Absolute layout that takes the entire screen no matter the input
     * and is default the parameter in the Element class
     * @param e any element object
     * @param index index of element in parent heirarchy
     * @return Box where element conforms to
     */
    @Override
    public Box findRelativeTransform(Element e, int index) {
        return e.getAbsoluteBox();
    }
}

