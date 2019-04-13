package example.demo07.engine.gui;

import example.demo07.engine.system.Window;

public class Box {

    float x, y, width, height;

    public Box(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void translateX(int pixels){
        this.x += (float)pixels / (float)Window.instance().getWidth();
    }

    public void translateY(int pixels){
        this.y += (float)pixels / (float)Window.instance().getHeight();
    }

    public void translate(int xpixels, int ypixels){
        translateX(xpixels);
        translateY(ypixels);
    }

    public Box within(Box outer){
        return new Box(
            outer.x + outer.width * this.x,
            outer.y + outer.height * this.y,
            outer.width * this.width,
            outer.height * this.height
        );
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
    
    

}

