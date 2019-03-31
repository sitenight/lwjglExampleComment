package example.demo06.engine.render.shaders;

import example.demo04.math.*;
import example.demo06.engine.render.Material;
import example.demo06.engine.render.light.*;
import example.demo06.engine.render.weather.Fog;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.opengl.GL32;

/**
 * Объект, представляющий <tt> шейдерную программу </tt>.
  * <p> Предоставляет полезные методы для создания, установки и загрузки шейдеров. </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class ShaderProgram {
    
    /** Идентификатор программы */
    private final int programId;
    
    /** Идентификатор вершинного шейдера */
    private int vertexShaderId;
    
    /** Идентификатор фрагментного шейдера */
    private int fragmentShaderId;
    
    /** Идентификатор геометрического шейдера */
    private int geometryShaderId;
    
    /** Глобальные переменные(uniform) для шейдеров */
    private final Map<String, UniformData> uniforms;

    /**
     * Cоздаем новую программу в OpenGL 
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
        uniforms.put(uniformName, new UniformData(uniformLocation));
    }
    
    /**
     * Устанавливает значение данной униформы
     * @param uniformName название униформы
     * @param matrix 4 мерная матрица
     */
    public void setUniform(String uniformName, Matrix4f matrix){
        UniformData uniformData = uniforms.get(uniformName);
        if(uniformData == null)
            throw  new RuntimeException("Юниформа [" + uniformName + "] не создана");
        glUniformMatrix4fv(uniformData.getUniformLocation(), true, matrix.toSingle());
    }
    
    public void setUniform(String uniformName, Matrix4f[] matrices){
        int length = matrices != null ? matrices.length : 0;
        UniformData uniformData = uniforms.get(uniformName);
        if(uniformData == null)
            throw  new RuntimeException("Юниформа [" + uniformName + "] не создана");
        
        float[] m = new float[length * 16];
        for(int i = 0; i < length; i++){
            for (int j = 0; j < 16; j++) {
                m[i * 16 + j] = matrices[i].get((int)(j / 4), j % 0);
            }
        }
        glUniformMatrix4fv(uniformData.getUniformLocation(), true, m);
    }
    
    /**
     * Устанавливаем целочисленное значение для униформы
     * @param uniformName название униформы
     * @param value целочисленое значение
     */
    public void setUniform(String uniformName, int value) {
        UniformData uniformData = uniforms.get(uniformName);
        if(uniformData == null)
            throw  new RuntimeException("Юниформа [" + uniformName + "] не создана");
	glUniform1i(uniformData.getUniformLocation(), value);
    }
	
    public void setUniform(String uniformName, float value) {
        UniformData uniformData = uniforms.get(uniformName);
        if(uniformData == null)
            throw  new RuntimeException("Юниформа [" + uniformName + "] не создана");
	glUniform1f(uniformData.getUniformLocation(), value);
    }
	
    /**
     * Устанавливает значение данной униформы
     * @param uniformName название униформы
     * @param value 3 мерный вектор
     */
    public void setUniform(String uniformName, Vector3f value) {
        UniformData uniformData = uniforms.get(uniformName);
        if(uniformData == null)
            throw  new RuntimeException("Юниформа [" + uniformName + "] не создана");
	glUniform3f(uniformData.getUniformLocation(), value.x, value.y, value.z);
    }
    
    // ===================== Униформа Источников света ==================
    
    /**
     * Устанавливает значение униформы Направленного источника света
     * @param uniformName название униформы
     * @param dirLight Направленный источник света
     */
    public void setUniform(String uniformName, DirectionalLight dirLight) {
        setUniform(uniformName + ".colour", dirLight.getColor());
        setUniform(uniformName + ".direction", dirLight.getDirection());
        setUniform(uniformName + ".intensity", dirLight.getIntensity());
    }
    
    /**
     * Создание униформы Направленного источника света
     * @param uniformName название униформы
     * @throws Exception Если что-то пошло не так
     */
    public void createDirectionalLightUniform(String uniformName) throws Exception{
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".direction");
        createUniform(uniformName + ".intensity");
    }
    
    /**
     * Создает массив униформ света
     * @param uniformName название униформы
     * @param size колличество униформ
     * @throws Exception Если что-то пошло не так
     */
    public void createPointLightListUniform(String uniformName, int size) throws Exception {
        for (int i = 0; i < size; i++) {
            createPointLightUniform(uniformName + "[" + i + "]");
        }
    }
    
    /**
     * Создает униформу для точечного источника света.
     * @param uniformName название униформы
     * @throws Exception Если что-то пошло не так
     */
    public void createPointLightUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".position");
        createUniform(uniformName + ".intensity");
        createUniform(uniformName + ".att.constant");
        createUniform(uniformName + ".att.linear");
        createUniform(uniformName + ".att.exponent");
    }
    
    /**
     * Устанавливаем массив Униформ
     * @param uniformName название униформы
     * @param pointLights источник света
     */
    public void setUniform(String uniformName, PointLight[] pointLights) {
        int numLights = pointLights != null ? pointLights.length : 0;
        for (int i = 0; i < numLights; i++) {
            setUniform(uniformName, pointLights[i], i);
        }
    }
    
    /**
     * Установка униформы точечного источника света
     * @param uniformName название униформы
     * @param pointLight источник света
     * @param pos позиция в массиве
     */
    public void setUniform(String uniformName, PointLight pointLight, int pos) {
        setUniform(uniformName + "[" + pos + "]", pointLight);
    }
    
    /**
     * Установка униформы для точечного источника света
     * @param uniformName название униформы
     * @param pointLight источник света
     */
    public void setUniform(String uniformName, PointLight pointLight){
        setUniform(uniformName + ".colour", pointLight.getColor());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniform(uniformName + ".intensity", pointLight.getIntensity());
        Attenuation att = pointLight.getAttenuation();
        setUniform(uniformName + ".att.constant", att.getConstant());
        setUniform(uniformName + ".att.linear", att.getLinear());
        setUniform(uniformName + ".att.exponent", att.getExponent());
    }
    
    /**
     * Создание массива униформ для прожектора света
     * @param uniformName название униформы
     * @param size размер массива
     * @throws Exception Если что-то пошло не так
     */
    public void createSpotLightListUniform(String uniformName, int size) throws Exception{
        for(int i = 0; i < size; i++){
            createSpotLightUniform(uniformName + "[" + i + "]");
        }
    }
    
    /**
     * Создание униформы для прожектора света
     * @param uniformName название униформы
     * @throws Exception Если что-то пошло не так
     */
    public void createSpotLightUniform(String uniformName) throws Exception{
        createPointLightUniform(uniformName + ".pl");
        createUniform(uniformName + ".conedir");
        createUniform(uniformName + ".cutoff");
    }

    /**
     * Установка униформы для прожектора света
     * @param uniformName название униформы
     * @param spotLight прожектора света
     */
    public void setUniform(String uniformName, SpotLight spotLight){
        setUniform(uniformName + ".pl", spotLight.getPointLight());
        setUniform(uniformName + ".conedir", spotLight.getConeDirection());
        setUniform(uniformName + ".cutoff", spotLight.getCutOff());
    }
    
    /**
     * Установка униформы для прожектора света
     * @param uniformName название униформы
     * @param spotLight прожектора света
     * @param pos номер в массиве
     */
    public void setUniform(String uniformName, SpotLight spotLight, int pos){
        setUniform(uniformName + "[" + pos + "]", spotLight);
    }

    /**
     * Установка массива униформ для прожектора света
     * @param uniformName название униформы
     * @param spotLights массив прожекторов света
     */
    public void setUniform(String uniformName, SpotLight[] spotLights){
        int numLights = spotLights != null ? spotLights.length : 0;
        for(int i = 0; i < numLights; i++){
            setUniform(uniformName, spotLights[i], i);
        }
    }

    // ===================== Униформа Материала ==================
    
    /**
     * Установка униформы для материала
     * @param uniformName название униформы
     * @param material материал
     */
    public void setUniform(String uniformName, Material material){
        setUniform(uniformName + ".colour", material.getColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".hasNormalMap", material.isNormalMap() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }
    
    /**
     * Создание униформы Материала
     * @param uniformName название униформы
     * @throws Exception Если что-то пошло не так
     */
    public void createMaterialUniform(String uniformName) throws Exception{
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".hasNormalMap");
        createUniform(uniformName + ".reflectance");

    }
    
    // ===================== Униформа Тумана ==================
    
    /**
     * Установка униформы Тумана
     * @param uniformName название униформы
     * @param fog Туман
     */
    public void setUniform(String uniformName, Fog fog){
        setUniform(uniformName + ".activeFog", fog.isActive() ? 1 : 0);
        setUniform(uniformName + ".colour", fog.getColour());
        setUniform(uniformName + ".density", fog.getDensity());
    }
    
    /**
     * Создание униформы тумана
     * @param uniformName название униформы
     * @throws Exception Если что-то пошло не так
     */
    public void createFogUniform(String uniformName) throws Exception{
        createUniform(uniformName + ".activeFog");
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".density");
    }
    
    // ===================== Шейдеры ==================
    
    /**
     * Создание вершинного шейдера
     * @param schaderCode шейдерный код
     * @throws Exception Если идентификатор вершинного шейдера = 0
     */
    public void createVertexShader(String schaderCode) throws Exception {
        vertexShaderId = createShader(schaderCode, GL_VERTEX_SHADER);
    }
    /**
     * Создание вершинного шейдера
     * @param schaderCode шейдерный код
     * @throws Exception Если идентификатор вершинного шейдера = 0
     */
    public void createGeometryShader(String schaderCode) throws Exception {
        geometryShaderId = createShader(schaderCode, GL32.GL_GEOMETRY_SHADER);
    }
    
    /**
     * Создание фрагментного/пиксельного шейдера
     * @param schaderCode шейдерный код
     * @throws Exception Если идентификатор фрагментного шейдера = 0
     */
    public void createFragmentShader(String schaderCode) throws Exception {
        fragmentShaderId = createShader(schaderCode, GL_FRAGMENT_SHADER);
    }
    
    /**
     * Создание шейдера
     * @param schaderCode шейдерный код
     * @param shaderType тип шейдера
     * @return идентификатор шейдера
     * @throws Exception Если идентификатор шейдера = 0
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
     * @throws Exception Если что-то пошло не так
     */
    public void link() throws Exception {
        glLinkProgram(programId);
        if(glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw new Exception("Ошибка присоединения шейдера, код: " + 
                    glGetProgramInfoLog(programId, 1024));
        
        // освобождаем шейдеры
        if(vertexShaderId != 0) 
            glDetachShader(programId, vertexShaderId);
        if(geometryShaderId != 0)
                glDetachShader(programId, geometryShaderId);
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
        if(programId != 0)  {
            if(vertexShaderId != 0){
                glDetachShader(programId, vertexShaderId);
            }
            if(fragmentShaderId != 0){
                glDetachShader(programId, fragmentShaderId);
            }
            if(geometryShaderId != 0){
                glDetachShader(programId, geometryShaderId);
            }
            glDeleteProgram(programId);
        }
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
