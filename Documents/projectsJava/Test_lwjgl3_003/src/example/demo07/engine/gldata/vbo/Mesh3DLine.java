package example.demo07.engine.gldata.vbo;

import example.demo07.engine.utils.Buffer;
import java.util.ArrayList;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Mesh3DLine extends VertexBufferObject{

    private ArrayList<Vector3f> positions;
    private ArrayList<Integer> indices;

    public Mesh3DLine(){
        super();
        positions = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public void bind(){

        glBindVertexArray(vaoId);
        glBindVertexArray(vaoId);
        vbos.stream().forEach(i ->glDeleteBuffers(i));
        vbos.clear();

        // position vbo
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Buffer.buffer3f(positions), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // index vbo
        vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Buffer.indiciesBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    public void render(){

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0); // pos
        glDrawElements(GL_LINE, getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public int getVertexCount(){
        return indices.size();
    }

    public ArrayList<Vector3f> getPositions() {
        return positions;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }

    public void setPositions(ArrayList<Vector3f> positions) {
        this.positions = positions;
    }

    
}

