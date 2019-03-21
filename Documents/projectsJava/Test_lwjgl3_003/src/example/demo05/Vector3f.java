package example.demo05;

import example.demo04.math.*;

public class Vector3f {
        
    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3f(Vector3f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
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
    
    public float dot(Vector3f vector) {
	return x * vector.x + y * vector.y + z * vector.z;
    }
    
    public Vector3f cross(Vector3f vector) {
        float dx = y * vector.z - z * vector.y;
        float dy = z * vector.x - x * vector.z;
        float dz = x * vector.y - y * vector.x;
        
	return new Vector3f(dx, dy, dz);
    }
    
    public Vector3f normalized() {
        float length = length();
        return new Vector3f(x / length, y / length, z / length);
    }
    
    /**
     * Сложение векторов
     * @param vector вектор который будем добавлять
     * @return координаты суммы векторов
     */
    public Vector3f add(Vector3f vector) {
        return new Vector3f(x + vector.x, y + vector.y, z + vector.z);
    }
    
    public Vector3f add(float r) {
        return new Vector3f(x + r, y + r, z + r);
    }
    
    /**
     * Вычитание векторов
     * @param vector вектор который будем отнимать
     * @return новые координаты вычититания
     */
    public Vector3f sub(Vector3f vector) {
        return new Vector3f(x - vector.x, y - vector.y, z - vector.z);
    }
    
    public Vector3f sub(float r) {
        return new Vector3f(x - r, y - r, z - r);
    }
    
    /**
     * Умножение векторов
     * @param vector вектор на который будем умножать
     * @return координаты умноженных векторов
     */
    public Vector3f mul(Vector3f vector) {
        return new Vector3f(x * vector.x, y * vector.y, z * vector.z);
    }
    
    public Vector3f mul(float r) {
        return new Vector3f(x * r, y * r, z * r);
    }
    
    /**
     * Деление векторов
     * @param vector вектор на который будем делить
     * @return координаты разделенных векторов
     */
    public Vector3f div(Vector3f vector) {
        return new Vector3f(x / vector.x, y / vector.y, z / vector.z);
    }
    
    public Vector3f div(float r) {
        return new Vector3f(x / r, y / r, z / r);
    }
    
    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }
    
    public Vector3f rotate(float angle, Vector3f axis) {
	float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
	float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
	float rX = axis.x * sinHalfAngle;
	float rY = axis.y * sinHalfAngle;
	float rZ = axis.z * sinHalfAngle;
	float rW = cosHalfAngle;
		
	Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
	Quaternion conjugate = rotation.conjugate();
		
	Quaternion w = rotation.mul(this).mul(conjugate);
		
	return new Vector3f(w.x, w.y, w.z);
    }
    
    /**
     * Сравнение векторов
     * @param vector вектор с которым сравниваем
     * @return Одинаковые ли вектора или нет
     */
    public boolean equals(Vector3f vector) {
        return x == vector.x && y == vector.y && z == vector.z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }

}

