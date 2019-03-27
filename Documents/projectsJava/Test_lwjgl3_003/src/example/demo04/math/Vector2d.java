package example.demo04.math;

public class Vector2d {
        
    public double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2d(Vector2d vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Вычисление длины вектора
     * @return длина вектора
     */
    public double length() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    public double dot(Vector2d vector) {
	return x * vector.x + y * vector.y;
    }
    
    public Vector2d normalized() {
        double length = length();
        return new Vector2d(x / length, y / length);
    }
    
    /**
     * Сложение векторов
     * @param vector вектор который будем добавлять
     * @return координаты суммы векторов
     */
    public Vector2d add(Vector2d vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vector2d add(float r) {
        x += r;
        y += r;
        return this;
    }
    
    /**
     * Вычитание векторов
     * @param vector вектор который будем отнимать
     * @return новые координаты вычититания
     */
    public Vector2d sub(Vector2d vector) {
        return new Vector2d(x - vector.x, y - vector.y);
    }
    
    public Vector2d sub(float r) {
        return new Vector2d(x - r, y - r);
    }
    
    /**
     * Умножение векторов
     * @param vector вектор на который будем умножать
     * @return координаты умноженных векторов
     */
    public Vector2d mul(Vector2d vector) {
        return new Vector2d(x * vector.x, y * vector.y);
    }
    
    public Vector2d mul(float r) {
        return new Vector2d(x * r, y * r);
    }
    
    /**
     * Деление векторов
     * @param vector вектор на который будем делить
     * @return координаты разделенных векторов
     */
    public Vector2d div(Vector2d vector) {
        return new Vector2d(x / vector.x, y / vector.y);
    }
    
    public Vector2d div(float r) {
        return new Vector2d(x / r, y / r);
    }
    
    public Vector2d abs() {
        return new Vector2d(Math.abs(x), Math.abs(y));
    }
    
    /**
     * Сравнение векторов
     * @param vector вектор с которым сравниваем
     * @return Одинаковые ли вектора или нет
     */
    public boolean equals(Vector2d vector) {
        return x == vector.x && y == vector.y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

}

