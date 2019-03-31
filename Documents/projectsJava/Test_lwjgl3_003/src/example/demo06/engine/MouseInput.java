package example.demo06.engine;

import example.demo04.math.Vector2d;
import example.demo04.math.Vector2f;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Утилиты для работы с мышью
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class MouseInput {

    /** Последняя позиция мыши */
    private final Vector2d previousPosition;
    
    /** Текущая позиция мыши */
    private final Vector2d currentPosition;
    
    /** Разница между {@link #previousPos} и {@link #currentPos} */
    private final Vector2f displVec;
    
    /** Находится ли мышь в окне */
    private boolean inWindow = false;
    
    /** Нажата ли левая кнопка мыши */
    private boolean leftButtonPressed = false;
    
    /** Нажата ли правая кнопка мыши */
    private boolean rightButtonPressed = false;
    
    /** Обратный вызов позиции. Даже если никогда не используется, мы должны сохранить это */
    private GLFWCursorPosCallback cursorPosCallback;
    
    /** Обратный вызов ввода курсора. Даже если никогда не используется, мы должны сохранить это */
    private GLFWCursorEnterCallback cursorEnterCallback;
    
    /** Обратный вызов кнопки мыши. Даже если никогда не используется, мы должны сохранить это */
    private GLFWMouseButtonCallback mouseButtonCallback;

    public MouseInput() {
        this.previousPosition = new Vector2d(-1, -1);
        this.currentPosition = new Vector2d();
        this.displVec = new Vector2f();
    }
    
    /**
     * Инициализация обратного вызова
     * @param window ссылка на окно
     */
    public void init(Window window) {
        glfwSetCursorPosCallback(window.getWindowHandle(), cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                currentPosition.x = xpos;
                currentPosition.y = ypos;
            }
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), cursorEnterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                inWindow = entered;
            }
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods){
                leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
                rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
            }
        });
    }
    
    /**
     * Обновляем все вектора мыши
     * @param window окно
     */
    public void input(Window window) {
        displVec.x = 0;
        displVec.y = 0;
        if(previousPosition.x > 0 && previousPosition.y > 0 && inWindow) {
            double deltax = currentPosition.x - previousPosition.x;
            double deltay = currentPosition.y - previousPosition.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if(rotateX) 
                displVec.y = (float) deltax;
            if(rotateY) 
                displVec.x = (float) deltay;
        }
        previousPosition.x = currentPosition.x;
        previousPosition.y = currentPosition.y;
    }
    
    // ============================================

    /**
     * Возвращает вектор экрана
     * @return вектор
     */
    public Vector2f getDisplVec() {
        return displVec;
    }

    /**
     * Нажата или нет левая кнопка мыши
     * @return нажатие левой кнопки мыши
     */
    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    /**
     * Нажата или нет правая кнопка мыши
     * @return нажатие правой кнопки мыши
     */
    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
    
}
