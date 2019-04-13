package example.demo07.engine.gui.element;


import example.demo07.engine.event.*;
import example.demo07.engine.gui.*;
import example.demo07.engine.gui.layout.*;
import example.demo07.engine.system.Window;
import java.util.ArrayList;
import java.util.Collections;
import org.joml.Vector2i;

public abstract class Element {

    protected Box relativeBox, absoluteBox;

    protected Layout layout;

    private Event.MouseEventHandler mouseEventHandler;

    boolean activated = true;
    boolean changed = true;

    private ArrayList<Element> children;
    private Element parent;

    protected Element(){
        children = new ArrayList<>();
        layout = new AbsoluteLayout(this);
        relativeBox = new Box(0,0,0,0);
        absoluteBox = new Box(0,0,0,0);

        applyDefaultHandlers();
    }

    private void applyDefaultHandlers(){
        setMouseEventHandler( e -> {

        });
    }

    public void render(){
        children.stream()
                .filter(e -> e.isActivated())
                .forEach(e -> e.render());
    }

    public void addChild(Element e){
        children.add(e);
        e.setParent(this);
    }

    public void addChildren(Element... elements){
        for(Element e: elements){
            children.add(e);
            e.setParent(this);
        }
    }

    protected void layoutChildren(){
        int i = 0;
        for(Element child: children){
            child.relativeBox = layout.findRelativeTransform(child, i++);
            child.absoluteBox = child.relativeBox.within(absoluteBox);
            child.layoutChildren();
        }
    }

    public Vector2i getAbsolutePixelSize(){
        Vector2i ret = new Vector2i();
        Box box = getAbsoluteBox();
        ret.x = (int) (Window.instance().getWidth() * box.getWidth());
        ret.y = (int) (Window.instance().getHeight() * box.getHeight());
        return ret;
    }

    public void handle(){
        children.stream().filter(e -> e.isActivated()).forEach(e -> e.handle());
    }

    public void cleanup(){
        children.stream().forEach(e -> e.cleanup());
    }

    public void setMouseEventHandler(Event.MouseEventHandler mouseEventHandler) {
        this.mouseEventHandler = mouseEventHandler;
    }

    public boolean isActivated() {
        return activated;
    }

    public Box getAbsoluteBox() {
        return absoluteBox;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public ArrayList<Element> getChildren() {
        return children;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    
    
}

