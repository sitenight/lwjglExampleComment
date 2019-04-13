package example.demo07.engine.scene;

import example.demo07.engine.gldata.vbo.VertexBufferObject;
import example.demo07.engine.system.Shader;

public class RenderModule extends Module {

    /*
        Module with purpose of connecting OpenGL gldata calls to
        scenegraph nodes. Any node with this module will be able to
        attach a shader and VBO model.
     */

    private Shader shader;
    private VertexBufferObject mesh;

    public RenderModule(Shader shader, VertexBufferObject mesh) {
        this.shader = shader;
        this.mesh = mesh;
    }

    
    
    @Override
    public void render() {

        shader.bind();
        shader.updateUniforms(getParent());
        mesh.render();
        shader.unbind();

    }

    @Override
    public void cleanup() {

        mesh.cleanup();
        shader.cleanup();

    }

}

