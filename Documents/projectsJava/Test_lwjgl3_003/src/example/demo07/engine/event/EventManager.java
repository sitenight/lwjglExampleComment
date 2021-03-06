package example.demo07.engine.event;

import example.demo07.engine.gui.element.*;
import example.demo07.engine.system.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;

public class EventManager {

    private ArrayList<Element> changedElements;
    private ArrayList<Event> events;

    private ArrayList<WeakReference<Element>> mouseListeners;
    private ArrayList<WeakReference<Element>> keyboardListeners;
    private ArrayList<WeakReference<Element>> systemListeners;

    private Element focused, root;

    public EventManager(){
        changedElements = new ArrayList<>();
        events = new ArrayList<>();
    }

    public void init(Context context){
        root = context.getRoot();
        focused = root;
    }


    // EVERY UPDATE:
    /**
     * 1. check for mouse input left
     */
    public void update(){


        ArrayList<Event> clone = (ArrayList<Event>) events.clone();
        events.clear();
        for(Event e: clone){

            if(e instanceof MouseEvent){
                if(((MouseEvent) e).action == GLFW.GLFW_MOUSE_BUTTON_1) {
                    
                }
            }
        }
    }

    public void fire(Event e){
        events.add(e);
    }

    public void findNewFocus(){
        Element ptr = root;
        while(true){
            for(Element element: ptr.getChildren()){

            }
        }
    }



}

