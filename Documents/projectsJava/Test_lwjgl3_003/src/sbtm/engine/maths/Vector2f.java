package sbtm.engine.maths;

public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Вычисление длины вектора
     * @return длина вектора
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
    
    
}

