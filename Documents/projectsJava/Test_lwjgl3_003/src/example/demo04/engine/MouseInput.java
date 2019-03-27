package example.demo04.engine;

import example.demo04.math.*;
import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    /** Предыдущая позиция */
    private Vector2d previousPos;
    
    /** Текущая позиция */
    private final Vector2d currentPos;
    
    /** Смещение позиции, высиляется из предыдущей позиции */
    private final Vector2f displVec;
    
    private boolean inWindow = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public MouseInput() {
        this.previousPos = new Vector2d(-1, -1);
        this.currentPos = new Vector2d();
        this.displVec = new Vector2f();
    }
    
    /**
     * Должен быть вызван во время фазы инициализации и регистрирует 
     * набор обратных вызовов для обработки событий мыши
     * @param window 
     */
    public void init(Window window) {
        // Регистрирует обратный вызов, который будет вызываться при перемещении мыши
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        
        // Регистрирует обратный вызов, который будет вызываться, когда мышь 
        // входит в наше окно. Мы будем получать события мыши, даже если мышь 
        // не находится в нашем окне. Мы используем этот обратный вызов для 
        // отслеживания, когда мышь находится в нашем окне.
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        
        // Регистрирует обратный вызов, который будет вызываться при нажатии кнопки мыши.
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed  = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }
    
    public void input(Window window) {
        displVec.x = 0;
        displVec.y = 0;
        if(previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            Vector2d delta = currentPos.sub(previousPos);
            if(delta.x != 0)
                displVec.y = (float) delta.x;
            if(delta.y != 0)
                displVec.x = (float) delta.y;            
        }
        previousPos = new Vector2d(currentPos);
    }
            
    
    // ============== getter & setter ===============

    public Vector2f getDisplVec() {
        return displVec;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
    
    
    
    
}