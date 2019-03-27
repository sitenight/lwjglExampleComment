package example.demo04.engine.gui;

import example.demo04.engine.Window;
import example.demo04.math.Vector2d;

public abstract class HudObject {
    
    protected static final double DEFAULT_SIZE = 10;

    /** Координаты позиции на экране */
    protected Vector2d position;
    
    /** Выравнивание */
    protected EnumPosition align;
    
    /** размеры элемента */
    protected Vector2d size;

    public HudObject(Vector2d position, Vector2d size) {
        this.position = position;
        this.size = size;
        this.align = EnumPosition.NONE;
    }

    public HudObject(double x, double y) {
        this(new Vector2d(x, y), new Vector2d(DEFAULT_SIZE, DEFAULT_SIZE));
    }

    public HudObject(Vector2d position) {
        this(position, new Vector2d(DEFAULT_SIZE, DEFAULT_SIZE));
    }

    public HudObject(Window window, EnumPosition align, Vector2d size) {
        this.size = size;
        this.setAlign(window, align);
    }
    
    /** Обновление объекта */
    public abstract void update();
    
    /** Отрисовывание объекта */
    public abstract void draw();
    
    // ================ getter & setter =========

    /**
     * Выравнивание относительно экрана
     * @param window ссылка на окно
     * @param align тип выравнивания
     * @return новые координаты элемента
     */
    public HudObject setAlign(Window window, EnumPosition align) {
        this.align = align;
        
        Vector2d shift = new Vector2d();
        
        double w = window.getWidth();
        double h = window.getHeight();
        
        switch(align) {
            case LEFTTOP:
                shift.set(0, 0);
                break;
            case TOP:
                shift.set(w/2 - size.x/2, 0);
                break;
            case RIGHTTOP:
                shift.set(w - size.x, 0);
                break;
            case LEFT:
                shift.set(0, h/2 - size.y/2);
                break;
            case CENTER:
                shift.set(w/2 - size.x/2, h/2 - size.y/2);
                break;
            case RIGHT:
                shift.set(w - size.x, h/2 - size.y/2);
                break;
            case LEFTBOTTOM:
                shift.set(0, h - size.y);
                break;
            case BOTTOM:
                shift.set(w/2 - size.x/2, h - size.y);
                break;
            case RIGHTBOTTOM:
                shift.set(w - size.x, h - size.y);
                break;
            default:
                shift.set(0, 0);
                break;                
        }
        
        this.position.add(shift);
        return this;
    }

    public EnumPosition getAlign() {
        return align;
    }
        
    public void setPosition(double x, double y) {
        this.position = new Vector2d(x, y);
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getSize() {
        return size;
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }
        
    public void setSize(double width, double height) {
        this.size = new Vector2d(width, height);
    }
    
    
    
}

