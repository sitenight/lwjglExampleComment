package example.demo04.math;

public class Vector2f {
        
    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2f(Vector2f vector) {
        this.x = vector.x;
        this.y = vector.y;
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
    
    public float dot(Vector2f vector) {
	return x * vector.x + y * vector.y;
    }
    
    public Vector2f normalized() {
        float length = length();
        return new Vector2f(x / length, y / length);
    }
    
    /**
     * Сложение векторов
     * @param vector вектор который будем добавлять
     * @return координаты суммы векторов
     */
    public Vector2f add(Vector2f vector) {
        return new Vector2f(x + vector.x, y + vector.y);
    }
    
    public Vector2f add(float r) {
        return new Vector2f(x + r, y + r);
    }
    
    /**
     * Вычитание векторов
     * @param vector вектор который будем отнимать
     * @return новые координаты вычититания
     */
    public Vector2f sub(Vector2f vector) {
        return new Vector2f(x - vector.x, y - vector.y);
    }
    
    public Vector2f sub(float r) {
        return new Vector2f(x - r, y - r);
    }
    
    /**
     * Умножение векторов
     * @param vector вектор на который будем умножать
     * @return координаты умноженных векторов
     */
    public Vector2f mul(Vector2f vector) {
        return new Vector2f(x * vector.x, y * vector.y);
    }
    
    public Vector2f mul(float r) {
        return new Vector2f(x * r, y * r);
    }
    
    /**
     * Деление векторов
     * @param vector вектор на который будем делить
     * @return координаты разделенных векторов
     */
    public Vector2f div(Vector2f vector) {
        return new Vector2f(x / vector.x, y / vector.y);
    }
    
    public Vector2f div(float r) {
        return new Vector2f(x / r, y / r);
    }
    
    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }
    
    /**
     * Сравнение векторов
     * @param vector вектор с которым сравниваем
     * @return Одинаковые ли вектора или нет
     */
    public boolean equals(Vector2f vector) {
        return x == vector.x && y == vector.y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

}

