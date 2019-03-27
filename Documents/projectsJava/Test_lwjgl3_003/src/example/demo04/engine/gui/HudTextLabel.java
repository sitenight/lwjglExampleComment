package example.demo04.engine.gui;

public class HudTextLabel extends HudObject {
    
    private Font font;
    
    private String text;

    public HudTextLabel(double x, double y, String text) {
        super(x, y);
        this.text = text;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw() {
        
    }
    
    // ======== getter & setter ===========

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    

}
