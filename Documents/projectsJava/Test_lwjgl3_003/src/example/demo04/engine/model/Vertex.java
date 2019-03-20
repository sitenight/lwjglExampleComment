package example.demo04.engine.model;

import example.demo04.math.*;

public class Vertex {

    public static final int SIZE = 8;
    
    public Vector3f pos;
    public Vector2f texCoord;
    public Vector3f normal;

    public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal) {
        this.pos = pos;
        this.texCoord = texCoord;
        this.normal = normal;
    }
    
    public Vertex(Vector3f pos, Vector2f texCoord) {
        this(pos, texCoord, new Vector3f(0, 0, 0));
    }
    
    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }
    
    // ================== getter & setter ===========

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
    
    
}

