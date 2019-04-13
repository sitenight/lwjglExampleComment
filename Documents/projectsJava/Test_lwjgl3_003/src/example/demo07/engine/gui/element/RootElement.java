package example.demo07.engine.gui.element;

import example.demo07.engine.gui.Box;

public class RootElement extends Element{

    public RootElement(){
        super();
        relativeBox = new Box(0,0,1,1);
        absoluteBox = new Box(0,0,1,1);
    }
}
