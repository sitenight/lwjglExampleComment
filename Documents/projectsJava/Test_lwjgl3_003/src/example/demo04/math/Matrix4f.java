package example.demo04.math;

import example.demo04.engine.Window;
import java.nio.FloatBuffer;

public class Matrix4f {

    private float[][] matrix;

    public Matrix4f() {
        matrix = new float[4][4];
    }

    public Matrix4f(Matrix4f matrix) {
        this.matrix = new float[4][4];
        for(int x = 0; x < 4; x++) 
            for(int y = 0; y < 4; y++) 
                this.matrix[x][y] = matrix.get(x, y);
    }
    
    /**
     * Генерирование единичной матрицы
     * @return 
     */
    public Matrix4f identity() {
        matrix[0][0] = 1;   matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][3] = 0;
        matrix[1][0] = 0;   matrix[1][1] = 1;   matrix[1][2] = 0;   matrix[1][3] = 0;
        matrix[2][0] = 0;   matrix[2][1] = 0;   matrix[2][2] = 1;   matrix[2][3] = 0;
        matrix[3][0] = 0;   matrix[3][1] = 0;   matrix[3][2] = 0;   matrix[3][3] = 1;
        
        return this;
    }
    
    /**
     * Генерирование матрицы переноса
     * @param vector координаты переноса
     * @return матрицу переноса
     */
    public Matrix4f translated(Vector3f vector) {
        matrix[0][0] = 1;   matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][3] = vector.x;
        matrix[1][0] = 0;   matrix[1][1] = 1;   matrix[1][2] = 0;   matrix[1][3] = vector.y;
        matrix[2][0] = 0;   matrix[2][1] = 0;   matrix[2][2] = 1;   matrix[2][3] = vector.z;
        matrix[3][0] = 0;   matrix[3][1] = 0;   matrix[3][2] = 0;   matrix[3][3] = 1;
        
        return this;
    }
    
    /**
     * Генерирование матрицы поворота 
     * @param angleX угол поворота по оси Х
     * @param angleY угол поворота по оси Y
     * @param angleZ угол поворота по оси Z
     * @return матрицу поворота
     */
    public Matrix4f rotated(float angleX, float angleY, float angleZ) {
        return rotated(new Vector3f(angleX, angleY, angleZ));
    }
    
    /**
     * Генерирование матрицы поворота 
     * @param vector
     * @return 
     */
    public Matrix4f rotated(Vector3f vector) {	        
        Matrix4f rx = new Matrix4f().rotated(vector.x, new Vector3f(1, 0, 0));
	Matrix4f ry = new Matrix4f().rotated(vector.y, new Vector3f(0, 1, 0));
	Matrix4f rz = new Matrix4f().rotated(vector.z, new Vector3f(0, 0, 1));
		
       /*  Matrix4f rx = new Matrix4f();
	Matrix4f ry = new Matrix4f();
	Matrix4f rz = new Matrix4f();
		
	float x = (float)Math.toRadians(vector.x);
	float y = (float)Math.toRadians(vector.y);
	float z = (float)Math.toRadians(vector.z);
        
	rz.matrix[0][0] = (float)Math.cos(z);   rz.matrix[0][1] = -(float)Math.sin(z);  rz.matrix[0][2] = 0;			rz.matrix[0][3] = 0;
	rz.matrix[1][0] = (float)Math.sin(z);   rz.matrix[1][1] = (float)Math.cos(z);   rz.matrix[1][2] = 0;			rz.matrix[1][3] = 0;
	rz.matrix[2][0] = 0;			rz.matrix[2][1] = 0;			rz.matrix[2][2] = 1;			rz.matrix[2][3] = 0;
	rz.matrix[3][0] = 0;			rz.matrix[3][1] = 0;			rz.matrix[3][2] = 0;			rz.matrix[3][3] = 1;
		
	rx.matrix[0][0] = 1;			rx.matrix[0][1] = 0;			rx.matrix[0][2] = 0;			rx.matrix[0][3] = 0;
	rx.matrix[1][0] = 0;			rx.matrix[1][1] = (float)Math.cos(x);   rx.matrix[1][2] = -(float)Math.sin(x);  rx.matrix[1][3] = 0;
	rx.matrix[2][0] = 0;			rx.matrix[2][1] = (float)Math.sin(x);   rx.matrix[2][2] = (float)Math.cos(x);   rx.matrix[2][3] = 0;
	rx.matrix[3][0] = 0;			rx.matrix[3][1] = 0;			rx.matrix[3][2] = 0;			rx.matrix[3][3] = 1;
		
	ry.matrix[0][0] = (float)Math.cos(y);   ry.matrix[0][1] = 0;			ry.matrix[0][2] = (float)Math.sin(y);   ry.matrix[0][3] = 0;
	ry.matrix[1][0] = 0;			ry.matrix[1][1] = 1;			ry.matrix[1][2] = 0;			ry.matrix[1][3] = 0;
	ry.matrix[2][0] = -(float)Math.sin(y);  ry.matrix[2][1] = 0;			ry.matrix[2][2] = (float)Math.cos(y);   ry.matrix[2][3] = 0;
	ry.matrix[3][0] = 0;			ry.matrix[3][1] = 0;			ry.matrix[3][2] = 0;			ry.matrix[3][3] = 1;
		
        Matrix4f rzz = new Matrix4f().rotated(vector.z, new Vector3f(0, 0, 1));
	Matrix4f ryy = new Matrix4f().rotated(vector.y, new Vector3f(0, 1, 0));
	Matrix4f rxx = new Matrix4f().rotated(vector.x, new Vector3f(1, 0, 0));
        System.out.println("// rz");
        System.out.println(rz.toString());
        System.out.println("// rzz");
        System.out.println(rz.toString());
        */
	//matrix = rz.mul(ry.mul(rx)).getMatrix();
                
        return rz.mul(ry.mul(rx.mul(this)));
    }
    
    /**
     * Поворот матрицы по одной из оси
     * @param angle угол поворота
     * @param axis ось поворота. Например (1, 0, 0) по оси x
     * т.к. ось должна быть равна 1
     * @return матрицу поворота
     */
    public Matrix4f rotated(float angle, Vector3f axis) {
	float a = (float)Math.toRadians(angle);
	
        if(axis.x > 0) {
            matrix[0][0] = 1;			matrix[0][1] = 0;                   matrix[0][2] = 0;                   matrix[0][3] = 0;
            matrix[1][0] = 0;			matrix[1][1] = (float)Math.cos(a);  matrix[1][2] = -(float)Math.sin(a); matrix[1][3] = 0;
            matrix[2][0] = 0;			matrix[2][1] = (float)Math.sin(a);  matrix[2][2] = (float)Math.cos(a);  matrix[2][3] = 0;
            matrix[3][0] = 0;			matrix[3][1] = 0;                   matrix[3][2] = 0;                   matrix[3][3] = 1;
	} else if(axis.y > 0) {	
            matrix[0][0] = (float)Math.cos(a);  matrix[0][1] = 0;                   matrix[0][2] = (float)Math.sin(a);  matrix[0][3] = 0;
            matrix[1][0] = 0;                   matrix[1][1] = 1;                   matrix[1][2] = 0;                   matrix[1][3] = 0;
            matrix[2][0] = -(float)Math.sin(a); matrix[2][1] = 0;                   matrix[2][2] = (float)Math.cos(a);  matrix[2][3] = 0;
            matrix[3][0] = 0;                   matrix[3][1] = 0;                   matrix[3][2] = 0;                   matrix[3][3] = 1;
        } else if(axis.z > 0) {
            matrix[0][0] = (float)Math.cos(a);  matrix[0][1] = -(float)Math.sin(a); matrix[0][2] = 0;                   matrix[0][3] = 0;
            matrix[1][0] = (float)Math.sin(a);  matrix[1][1] = (float)Math.cos(a);  matrix[1][2] = 0;                   matrix[1][3] = 0;
            matrix[2][0] = 0;			matrix[2][1] = 0;                   matrix[2][2] = 1;                   matrix[2][3] = 0;
            matrix[3][0] = 0;			matrix[3][1] = 0;                   matrix[3][2] = 0;                   matrix[3][3] = 1;
        } 
        return this;
    }
    
    /**
     * Генерирование матрицы масштабирования 
     * @param vector масштаб по каждой координатной оси
     * @return отмасштабированую матрицу
     */
    public Matrix4f scaled(Vector3f vector) {        
        matrix[0][0] = vector.x;   matrix[0][1] = 0;          matrix[0][2] = 0;         matrix[0][3] = 0;
        matrix[1][0] = 0;          matrix[1][1] = vector.y;   matrix[1][2] = 0;         matrix[1][3] = 0;
        matrix[2][0] = 0;          matrix[2][1] = 0;          matrix[2][2] = vector.z;  matrix[2][3] = 0;
        matrix[3][0] = 0;          matrix[3][1] = 0;          matrix[3][2] = 0;         matrix[3][3] = 1;
        
        return this;
    }
    
    /**
     * Равномерное масштабирование матрицы
     * @param scale масштаб
     * @return равномерно отмасштабированую матрицу
     */
    public Matrix4f scaled(float scale) {        
        return this.scaled(new Vector3f(scale, scale, scale));
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
     * @param width ширина экрана
     * @param height высота экрана
     * @param zNear расстояние от точки зрения до ближней отсекающей рамки
     * @param zFar расстояние от точки зрения до дальней отсекающей рамки
     * @return матрицу проекции
     */
    public Matrix4f projection(float fov, int width, int height, float zNear, float zFar) {
        float aspect = (float)width / (float)height; // коэффициент отношения сторон окна OpenGL width/height
        float f = 1.0f / (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;
        float a = (zFar + zNear) / zRange;
        float b = (2 * zFar * zNear) / zRange;
        
        
        matrix[0][0] = f / aspect; matrix[0][1] = 0;   matrix[0][2] = 0;   matrix[0][3] = 0;
        matrix[1][0] = 0;          matrix[1][1] = f;   matrix[1][2] = 0;   matrix[1][3] = 0;
        matrix[2][0] = 0;          matrix[2][1] = 0;   matrix[2][2] = a;   matrix[2][3] = b;
        matrix[3][0] = 0;          matrix[3][1] = 0;   matrix[3][2] = -1;  matrix[3][3] = 0;
        
        return this;
    }
    
    /**
     * Ортографическая матрица проекции
     * @param l левая координата
     * @param r правая координата
     * @param b нижняя координата
     * @param t верхняя координата
     * @param n Ближняя координата (zNear)
     * @param f Дальняя координата (zFar)
     * @return матрицу проекции
     */
    public Matrix4f OrthographicProjection(float l, float r, float b, float t, float n, float f) {
	matrix[0][0] = 2.0f/(r-l);  matrix[0][1] = 0; 		matrix[0][2] = 0;           matrix[0][3] = -(r+l)/(r-l);
	matrix[1][0] = 0;           matrix[1][1] = 2.0f/(t-b); 	matrix[1][2] = 0;           matrix[1][3] = -(t+b)/(t-b);
	matrix[2][0] = 0;           matrix[2][1] = 0; 		matrix[2][2] = 2.0f/(f-n);  matrix[2][3] = -(f+n)/(f-n);
	matrix[3][0] = 0;           matrix[3][1] = 0; 		matrix[3][2] = 0;           matrix[3][3] = 1;
	
	return this;
    }
    
	
    public Matrix4f Orthographic2D(double width, double height) {
	//Z-значение 1: глубина орфографической между 0 и -1
	matrix[0][0] = 2f/(float)width; matrix[0][1] = 0;                matrix[0][2] = 0; matrix[0][3] = -1;
	matrix[1][0] = 0;               matrix[1][1] = 2f/(float)height; matrix[1][2] = 0; matrix[1][3] = -1;
	matrix[2][0] = 0;               matrix[2][1] = 0;                matrix[2][2] = 1; matrix[2][3] =  0;
	matrix[3][0] = 0;               matrix[3][1] = 0;                matrix[3][2] = 0; matrix[3][3] =  1;
		
	return this;
    }
    
    
    public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
        return Orthographic2D((double)(right - left),(double)(bottom - top));
    }
    
    /**
     * Матрица вида
     * @param forward
     * @param up
     * @return 
     */
    public Matrix4f View(Vector3f forward, Vector3f up) {
	Vector3f f = forward.normalized();
	Vector3f r = up.normalized();
	r = r.cross(f);
		
	Vector3f u = f.cross(r);
		
	matrix[0][0] = r.x;	matrix[0][1] = r.y;	matrix[0][2] = r.z;	matrix[0][3] = 0;
	matrix[1][0] = u.x;	matrix[1][1] = u.y;	matrix[1][2] = u.z;	matrix[1][3] = 0;
	matrix[2][0] = f.x;	matrix[2][1] = f.y;	matrix[2][2] = f.z;	matrix[2][3] = 0;
	matrix[3][0] = 0;	matrix[3][1] = 0;	matrix[3][2] = 0;	matrix[3][3] = 1;
		
	return this;
    }
    
    /**
     * Создает новую матрицу модели перемещения, поворота и масштаба
     * @param posX позиция Х
     * @param posY позиция Y
     * @param posZ позиция Z
     * @param rotX поворот по оси Х
     * @param rotY поворот по оси Y
     * @param rotZ поворот по оси Z
     * @param rotW поворот по оси W
     * @param scaleX масштаб по Х
     * @param scaleY масштаб по Y
     * @param scaleZ масштаб по Z
     * @return 
     */
    public Matrix4f translationRotateScale(
            float posX, float posY, float posZ, 
            float rotX, float rotY, float rotZ, float rotW, 
            float scaleX, float scaleY, float scaleZ) {
        
        Matrix4f translationMatrix = new Matrix4f().translated(new Vector3f(posX, posY, posZ));
	Matrix4f rotationMatrix = new Matrix4f().rotated(new Vector3f(-rotX, -rotY, -rotZ));
	Matrix4f scaleMatrix = new Matrix4f().scaled(new Vector3f(scaleX, scaleY, scaleZ));
                
	return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
        
    }
    
    // =========================================
    
    /**
     * Произведение матрицы на матрицу
     * @param m матрица на которую будем умножать
     * @return произведение матриц
     */
    public Matrix4f mul(Matrix4f m) {
	Matrix4f res = new Matrix4f();
		
	for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
		res.set(i, j, matrix[i][0] * m.get(0, j) +
                        matrix[i][1] * m.get(1, j) +
			matrix[i][2] * m.get(2, j) +
			matrix[i][3] * m.get(3, j));
            }
	}
		
	return res;
    }

    
    /**
     * Перевод матрицы из двумерного в одномерный массив
     * @return одномерный массив матрицы
     */
    public float[] toSingle() {
        return new float[] {
            matrix[0][0], matrix[0][1], matrix[0][2], matrix[0][3],
            matrix[1][0], matrix[1][1], matrix[1][2], matrix[1][3],
            matrix[2][0], matrix[2][1], matrix[2][2], matrix[2][3],
            matrix[3][0], matrix[3][1], matrix[3][2], matrix[3][3]
        };
    }
    /*
    public FloatBuffer gets(FloatBuffer buffer) {
        return gets(buffer.position(), buffer);
    }

    
    public FloatBuffer gets(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }
    */
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
    
    public void set(Matrix4f matrix) {
        this.matrix = new float[4][4];
        for(int x = 0; x < 4; x++) 
            for(int y = 0; y < 4; y++) 
                this.matrix[x][y] = matrix.get(x, y);
    }

    @Override
    public String toString() {
        String matrix = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix += (Float.toString(get(i,j)) + ", ");
            }
            matrix += "\n";
        }
        return matrix;
    }

}

