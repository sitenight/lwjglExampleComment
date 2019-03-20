package sbtm.engine.maths;

public class Matrix4f {

    private float[][] matrix;

    public Matrix4f() {
        matrix = new float[4][4];
    }
    
    /**
     * Генерирование единичной матрицы
     * @return 
     */
    public Matrix4f identity() {
        matrix[0][0] = 1;   matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][2] = 0;
        matrix[1][0] = 0;   matrix[1][1] = 1;   matrix[1][2] = 0;   matrix[1][2] = 0;
        matrix[2][0] = 0;   matrix[2][1] = 0;   matrix[2][2] = 1;   matrix[2][2] = 0;
        matrix[3][0] = 0;   matrix[3][1] = 0;   matrix[3][2] = 0;   matrix[3][2] = 1;
        
        return this;
    }
    
    /**
     * Генерирование матрицы переноса
     * @param vector координаты переноса
     * @return матрицу переноса
     */
    public Matrix4f translated(Vector3f vector) {
        matrix[0][0] = 1;   matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][2] = vector.x;
        matrix[1][0] = 0;   matrix[1][1] = 1;   matrix[1][2] = 0;   matrix[1][2] = vector.y;
        matrix[2][0] = 0;   matrix[2][1] = 0;   matrix[2][2] = 1;   matrix[2][2] = vector.z;
        matrix[3][0] = 0;   matrix[3][1] = 0;   matrix[3][2] = 0;   matrix[3][2] = 1;
        
        return this;
    }
    
    /**
     * Генерирование матрицы поворота 
     * @param vector
     * @return 
     */
    public Matrix4f rotated(Vector3f vector) {
        return this;
    }
    
    /**
     * Генерирование матрицы масштабирования 
     * @param vector
     * @return 
     */
    public Matrix4f scaled(Vector3f vector) {
        return this;
    }
    
    /**
     * Перспективная матрица проекции.
     * 
     * При перспективной проекции используется тот факт, что для человеческий 
     * глаз работает с предметом дальнего типа, размеры которого имеют угловые 
     * размеры. Чем дальше объект, тем меньше он нам кажется. Таким образом, 
     * объём пространства, который визуализируется представляет собой пирамиду.
     * 
     * В OpenGL версии 3.0 и выше отменили работу со стеком матриц OpenGL, 
     * теперь необходимо вручную создавать матрицы и передавать их в шейдер для 
     * дальнейшего использования.
     * 
     * @param fov (field of view) определяет поле зрения/Угол обзора
     * @param aspect коэффициент отношения сторон окна OpenGL width/height
     * @param zNear расстояние от точки зрения до ближней отсекающей рамки
     * @param zFar расстояние от точки зрения до дальней отсекающей рамки
     */
    public Matrix4f projection(float fov, float aspect, float zNear, float zFar) {
        float f = 1.0f / (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;
        float a = (zFar + zNear) / zRange;
        float b = 2 * zFar * zNear / zRange;
        
        
        matrix[0][0] = f / aspect;  matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][2] = 0;
        matrix[1][0] = 0;           matrix[1][1] = f;   matrix[1][2] = 0;   matrix[1][2] = 0;
        matrix[2][0] = 0;           matrix[2][1] = 0;   matrix[2][2] = a;   matrix[2][2] = b;
        matrix[3][0] = 0;           matrix[3][1] = 0;   matrix[3][2] = -1;  matrix[3][2] = 0;
        
        return this;
    }
    
    // ============== getter ============
    
    public float[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }
    
    /**
     * Получить значение элемента матрицы
     * @param x координаты в матрице
     * @param y координаты в матрице
     * @return значение в матрице
     */
    public float get(int x, int y) {
        return matrix[x][y];
    }
    
    /**
     * Ввод нового значения в матрице
     * @param x координаты в матрице
     * @param y координаты в матрице
     * @param value новое значение элемента
     */
    public void set(int x, int y, float value) {
        matrix[x][y] = value;
    }
}

