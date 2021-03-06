package example.demo07.engine.system;

import example.demo07.engine.event.*;
import example.demo07.engine.scene.*;
import example.demo07.engine.utils.Interpolation;
import java.util.function.Function;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends Transform<Camera> {

    private final Vector3f ABS_UP = new Vector3f(0,1,0);

    Function<Integer, Float> speedMod = (
                 e -> ( (float) Math.exp(-e) ) * 0.5f
            );

    private double FOV;
    private int speedLevel = 4;
    private final float ZNEAR = .01f;
    private final float ZFAR = 10000;

    private final float DEF_MOUSE_SENS = 0.3f;
    private float mouseSens = DEF_MOUSE_SENS;

    private Vector3f velocity;
    float speedLimit = 10;

    private boolean focused = false;


    public Camera(){
        super();
        setTranslation(new Vector3f(0,0,-10));
        setRotation(new Vector3f(0,0,180));
        FOV = 85;
        velocity = new Vector3f();
    }

    public Camera(Vector3f position){
        this();
        translateTo(position);
    }

    float t = 0;

    public void update(){

        Input input = Input.instance();

        if(input.isKeyHeld(GLFW_KEY_W)){
            velocity.add(getForward().mul(speedMod.apply(speedLevel)));
        }
        if(input.isKeyHeld(GLFW_KEY_S)){
            velocity.add(getForward().mul(-speedMod.apply(speedLevel)));
        }
        if(input.isKeyHeld(GLFW_KEY_A)){
            velocity.add(getRight().mul(speedMod.apply(speedLevel)));
        }
        if(input.isKeyHeld(GLFW_KEY_D)){
            velocity.add(getRight().mul(-speedMod.apply(speedLevel)));
        }

        if(velocity.length() > speedLimit){
            velocity.normalize().mul(speedLimit);
        }

        translate(velocity);

        // look
        if(input.isButtonHeld(1)){ // right click
            rotate(-(float)input.getDisplacement().y * mouseSens,
                    (float)input.getDisplacement().x * mouseSens,  0);
        }

        // view-space pan
        if(input.isButtonHeld(2)&&input.isMod(GLFW_MOD_CONTROL)){ // middle click
            velocity.add(getUp().mul((float)input.getDisplacement().y * -.05f * speedMod.apply(speedLevel-2)));
            velocity.add(getRight().mul((float)input.getDisplacement().x * -.05f * speedMod.apply(speedLevel-2)));
        }



        // zoom
        if(input.isButtonPressed(0)) { // left click
            Vector2d pos = Input.instance().getCursorPos();
            Node selected = Core.picking().pick((int)pos.x, (int)pos.y);
            if(selected != null) {
                if(selected.isSelected())
                    Core.selectionManager().remove(selected);
                else {
                    if(!input.isMod(GLFW_MOD_CONTROL))
                        Core.selectionManager().clear();
                    Core.selectionManager().addSelection(selected);
                }
            }
        }

        if(input.isKeyPressed(GLFW_KEY_MINUS)){
            speedLevel--;
        } else if (input.isKeyPressed(GLFW_KEY_EQUAL)){
            speedLevel++;
        }

        if(input.isKeyHeld(GLFW_KEY_Q))
            t += 0.01f;

        else
            t -= 0.01f;


        if(t < 0) t = 0;
        if(t > 1) t = 1;

        FOV = (double) Interpolation.smootherstep(85, 25, t);
        mouseSens = DEF_MOUSE_SENS/(1+t*2);

        velocity.mul(.95f);




    }

    public Matrix4f getViewProjectionMatrix(){
        return getProjectionMatrix().mul(getViewMatrix());
    }

    public Matrix4f getProjectionMatrix() {
        float apectRatio = (float) Window.instance().getWidth() /
                (float) Window.instance().getHeight();
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.perspective((float)Math.toRadians(FOV), apectRatio, ZNEAR, ZFAR);
        return ret;
    }

    public Matrix4f getOrthoProjectionMatrix() {
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.ortho(-20, 20, -20, 20, 0.0001f, 100f);
        return ret;
    }

    public Matrix4f getProjectionMatrixLH() {
        float apectRatio = (float) Window.instance().getWidth() /
                (float) Window.instance().getHeight();
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.perspectiveLH((float)Math.toRadians(FOV), apectRatio, ZNEAR, ZFAR);
        return ret;
    }

    public Matrix4f getViewMatrix(){
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1));

        ret.translate(getTranslation().mul(-1));
        return ret;
    }

    public Vector3f getForward(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m02();
        ret.y = -viewMatrix.m12();
        ret.z = -viewMatrix.m22();
        return ret;
    }

    public Vector3f getUp(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m01();
        ret.y = -viewMatrix.m11();
        ret.z = -viewMatrix.m21();
        return ret;
    }

    public Vector3f getRight(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m00();
        ret.y = -viewMatrix.m10();
        ret.z = -viewMatrix.m20();
        return ret;
    }

    public double getFOV() {
        return FOV;
    }

    
}

