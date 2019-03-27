package example.demo04.engine.gui;

import example.demo04.engine.Window;
import example.demo04.math.Vector2d;

public abstract class HudObject {

    /** Координаты позиции на экране */
    protected Vector2d position;
    
    /** Выравнивание */
    protected EnumPosition align;

    public HudObject(double x, double y) {
        this.position.x = x;
        this.position.y = y;
        align = EnumPosition.LEFTTOP;
    }
    
    /** Обновление объекта */
    public abstract void update();
    
    /** Отрисовывание объекта */
    public abstract void draw();
    
    // ================ getter & setter =========

    public HudObject setAlign(EnumPosition align) {
        this.align = align;
        return this;
    }
    
    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
    }
    
    public Vector2d getPosition(Window window) {
        Vector2d shift = new Vector2d();
        
        double w = window.getWidth();
        double h = window.getHeight();
        
        switch(align) {
            case LEFTTOP:
                shift.set(0, 0);
                break;
            case TOP:
                shift.set(w/2, 0);
                break;
            case RIGHTTOP:
                shift.set(w, 0);
                break;
            case LEFT:
                shift.set(0, h/2);
                break;
            case CENTER:
                shift.set(w/2, h/2);
                break;
            case RIGHT:
                shift.set(w, h/2);
                break;
            case LEFTBOTTOM:
                shift.set(0, h);
                break;
            case BOTTOM:
                shift.set(w/2, h);
                break;
            case RIGHTBOTTOM:
                shift.set(w, h);
                break;
            default:
                shift.set(0, 0);
                break;                
        }
        
        return position.add(shift);
    }
}

