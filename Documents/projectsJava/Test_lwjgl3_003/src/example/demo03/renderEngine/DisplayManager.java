package example.demo03.renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    
    private static long display;

    public DisplayManager() {
    }
    
    
    public static void createDisplay() {
        
        if(!GLFW.glfwInit()) { // Проверяем инициализацию GLFW
            System.err.println("Error: Couldn't initialize GLFW");
            System.exit(-1);
        }
        // настройки окна
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // невидимое окно
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // неизменяемые размеры
        // создание окна
        display = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Тест", 0, 0);
        
        if(display == 0) { // Проверяем создание окна
            System.err.println("Error: Window couldn't be created");
            System.exit(-1);
        }
        
        // Прежде чем мы сможем использовать команды для рендеринга в 
        // созданное окно его нужно сделать его текущим контекстом
        GLFW.glfwMakeContextCurrent(display);
        GL.createCapabilities();
        
        // Режим видео из главного экрана
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // по центру экрана окно размещаем
        GLFW.glfwSetWindowPos(display, (videoMode.width() - WIDTH)/2, (videoMode.height() - HEIGHT)/2);
        
        // показываем окно
        GLFW.glfwShowWindow(display);
    }
    
    public static void updateDisplay() {
        // задаем цвет очистки
        GL11.glClearColor(0.5f, 0.5f, 0.0f, 1.0f);
        // задаем что нам очищать
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GLFW.glfwPollEvents(); // берет сообщения из очереди и обрабатывает их
        // Если нет необходимости в постоянной перерисовке содержимого окна, 
        // то для обработки сообщений можно использовать функцию glfwWaitEvents. 
        // Ее отличием от glfwPollEvents является то, что если сообщений 
        // в очереди нет, то нить "засыпает" до их появления.
    }
    
    public static void closeDisplay() {
        GLFW.glfwTerminate();
    }
    
    public boolean isClosedDisplay() {
        return GLFW.glfwWindowShouldClose(display);
    }    
    
    /**
     * Метад смены буферов
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(display);
    }
}

