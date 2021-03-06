package example.demo07.engine.scene.light;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DirectionalLight extends Light {

    private Vector3f ambientLight;

    private Matrix4f lightProjection;
    private Matrix4f lightView;

    private float size = 40f;

    public DirectionalLight(){
        super();
        lightProjection = new Matrix4f().ortho(
                -size, size, -size, size, -size, size
        );
        lightView = new Matrix4f().lookAt(
                0f, 5f, -5f,
                0,0,0,
                0,1,0
        );
        this.ambientLight = new Vector3f(0.03f);
    }

    @Override
    public void update(){
        super.update();
        Vector3f rot = new Vector3f(getWorldRotation()).normalize().mul(1.0f);
        lightView = new Matrix4f().lookAt(
              rot, new Vector3f(0,0,0),  new Vector3f(0,1,0)
        );
    }

    public Matrix4f getLightSpaceMatrix(){
        return new Matrix4f(lightProjection).mul(lightView);
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }

    public Matrix4f getLightProjection() {
        return lightProjection;
    }

    public Matrix4f getLightView() {
        return lightView;
    }

    
}

