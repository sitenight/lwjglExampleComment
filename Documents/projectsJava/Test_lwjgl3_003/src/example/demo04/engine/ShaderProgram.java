package example.demo04.engine;

import example.demo02.core.utils.Util;
import example.demo04.math.Matrix4f;
import example.demo04.math.Vector3f;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {
    
    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;
    
    /** глобальные переменные(uniform) для шейдеров */
    private final Map<String, Integer> uniforms;

    /**
     * Cоздаем новую программу в OpenGL и
     * предоставляет методы для добавления шейдеров вершин и фрагментов
     */
    public ShaderProgram() throws Exception {
        programId = glCreateProgram();
        if(programId == 0)
            throw  new Exception("Немогу создать шейдерную программу");
        uniforms = new HashMap<>();
    }
    
    /**
     * Создание новой глобальной переменной(uniform) для шейдера
     * метод для установки новой формы и сохранения полученного местоположения
     * @param uniformName наименование константы
     * @throws Exception 
     */
    public void createUniform(String uniformName) throws Exception {
        // получаем ссылку на место униформы
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if(uniformLocation < 0)
            throw new Exception("Не могу найти uniform: " + uniformName);
        // Добавляем в список констант
        uniforms.put(uniformName, uniformLocation);
    }
    
    
    public void setUniform(String uniformName, Matrix4f matrix){
        glUniformMatrix4fv(uniforms.get(uniformName), true, matrix.toSingle());
        //glUniformMatrix4fv(uniforms.get(uniformName), false, Utils.createFlippedBuffer(matrix));
    }
    
    public void setUniformi(String uniformName, int value) {
	glUniform1i(uniforms.get(uniformName), value);
    }
	
    public void setUniformf(String uniformName, float value) {
	glUniform1f(uniforms.get(uniformName), value);
    }
	
    public void setUniform(String uniformName, Vector3f value) {
	glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }
    
    /**
     * Создание вершинного шейдера
     * @param schaderCode шейдерный код
     * @throws Exception 
     */
    public void createVertexShader(String schaderCode) throws Exception {
        vertexShaderId = createShader(schaderCode, GL_VERTEX_SHADER);
    }
    
    /**
     * Создание фрагментного/пиксельного шейдера
     * @param schaderCode шейдерный код
     * @throws Exception 
     */
    public void createFragmentShader(String schaderCode) throws Exception {
        fragmentShaderId = createShader(schaderCode, GL_FRAGMENT_SHADER);
    }
    
    /**
     * Создание шейдера
     * @param schaderCode шейдерный код
     * @param shaderType тип шейдера
     * @return идентификатор шейдера
     * @throws Exception 
     */
    protected int createShader(String schaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if(shaderId == 0)
            throw  new Exception("Ошибка создания шейдера. Тип: " + shaderType);
        
        // связываем код шейдера с выделеным идентификатором
        glShaderSource(shaderId, schaderCode);
        
        // Компиляция шейдера
        glCompileShader(shaderId);
        if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
            throw  new Exception("Ошибка компиляции шейдера, код: " + 
                    glGetShaderInfoLog(shaderId, 1024));
        
        // присоединяем шейдер к программе
        glAttachShader(programId, shaderId);
        return shaderId;
    }
    
    /**
     * Когда все шейдеры присоеденены, следует
     * вызвать метод связывания(линковки), который связывает(линкует)
     * весь код и проверяет, что все сделано правильно.
     * @throws Exception 
     */
    public void link() throws Exception {
        glLinkProgram(programId);
        if(glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw new Exception("Ошибка присоединения шейдера, код: " + 
                    glGetProgramInfoLog(programId, 1024));
        
        // освобождаем шейдеры
        if(vertexShaderId != 0) 
            glDetachShader(programId, vertexShaderId);
        if(fragmentShaderId != 0) 
            glDetachShader(programId, fragmentShaderId);
        
        // Проверка шейдера на правильность с учетом текущего состояния OpenGL
        glValidateProgram(programId);
        if(glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
            System.err.println("Предупреждение, проверяющее код шейдера " + 
                    glGetProgramInfoLog(programId, 1024));
    }
    
    /**
     * Активация шейдерной программы для рендеринга
     */
    public void bind() {
        glUseProgram(programId);
    }
    
    /**
     * Прекращение использования шейдерной программы
     */
    public void unbind() {
        glUseProgram(0);
    }
    
    /**
     * Метод очистки для освобождения всех ресурсов
     */
    public void cleanup() {
        unbind();
        if(programId != 0) 
            glDeleteProgram(programId);
    }
    
    /**
     * Загрузка ресурсного/текстового файла шейдерной программы 
     * @param fileName путь и имя программы
     * @return Загруженную программу
     * @throws Exception 
     */
    public static String loadResource(String fileName)  throws Exception{
        String result;
        try(InputStream in = Class.forName(ShaderProgram.class.getName()).getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        
        return result;
    }
}

