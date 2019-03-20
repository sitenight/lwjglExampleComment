package sbtm.engine.maths;

public class Vector3f {

    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    /**
     * Вычисление длины вектора
     * @return длина вектора
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }
    
    
}

