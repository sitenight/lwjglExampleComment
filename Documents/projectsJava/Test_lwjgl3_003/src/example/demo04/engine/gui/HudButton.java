package example.demo04.engine.gui;

import example.demo04.math.Vector2d;

public class HudButton extends HudObject {

    /** Текст на кнопке */
    private HudTextLabel label;
    
    /** Состояние кнопки */
    private EnumButtonState state;
    
    /** внутренние отступы от края кнопки до текста*/
    private Vector2d padding;
    
    public HudButton(double x, double y, String text) {
        this(new Vector2d(x, y), new Vector2d(), 
                EnumButtonState.NORMAL, text);
    }

    /**
     * Базовый конструктор кнопки
     * @param position позиция кнопки
     * @param padding отступы внутри кнопки до текста
     * @param state состояние кнопки
     * @param text текст на кнопке
     */
    public HudButton(Vector2d position, Vector2d padding, 
            EnumButtonState state, String text) {
        super(position);
        this.padding = padding;
        this.state = state;
        label = new HudTextLabel(position.x, position.y, text);
    /*    size = new Vector2d(
                label.getWidth() + (padding.x * 2), 
                label.getHeight() + (padding.y * 2));     
*/
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw() {
        
    }
    
    // ================== getter & setter ===========

    
    

}

