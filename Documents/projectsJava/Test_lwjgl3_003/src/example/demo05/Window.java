package example.demo05;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
    
    private int width, height;
    private String title;
    private long window;
    
    public Input input;
    
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
	if(!GLFW.glfwInit()) { // Проверяем инициализацию GLFW
            System.err.println("Error: Couldn't initialize GLFW");
            System.exit(-1);
        }
        // настройки окна
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // невидимое окно
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // неизменяемые размеры
        // создание окна
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        
        if(window == 0) { // Проверяем создание окна
            System.err.println("Error: Window couldn't be created");
            System.exit(-1);
        }
        
        // Прежде чем мы сможем использовать команды для рендеринга в 
        // созданное окно его нужно сделать его текущим контекстом
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        // Режим видео из главного экрана
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // по центру экрана окно размещаем
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);
        
        // показываем окно
        GLFW.glfwShowWindow(window);
        
        // подключаем мышь и клавиатуру
        input = new Input(window);
        input.lockMouse();
    }
	
    public void update() {
        // обрабатывае клавиатуру и мышь
        input.update();
        
        // обработка сообщений из очереди
	GLFW.glfwPollEvents();
    }
    
    /**
     * Метад смены буферов
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }
	
    public void stop() {
	GLFW.glfwTerminate();
    }
	
    public boolean isCloseRequested() {
	return GLFW.glfwWindowShouldClose(window);
    }
    
    // ============= getter & setter ===========

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
    
    
}

