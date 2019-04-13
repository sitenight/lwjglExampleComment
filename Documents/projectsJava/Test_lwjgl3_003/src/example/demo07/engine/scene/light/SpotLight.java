package example.demo07.engine.scene.light;

import org.joml.Vector3f;

public class SpotLight extends Light {

    private Vector3f direction;
    private float radius;

    public SpotLight(){
        super();
        this.direction = new Vector3f(0,-1,0);
        this.radius = 1f;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public float getRadius() {
        return radius;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    
}

