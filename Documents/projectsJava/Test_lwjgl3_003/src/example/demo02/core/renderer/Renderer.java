package example.demo02.core.renderer;

import example.demo02.core.buffers.VBO;
import example.demo02.core.scene.Component;

public class Renderer extends Component{
	
	private VBO vbo;
	private RenderInfo renderInfo;
	
	public Renderer(){}
	
	public void render(){
		renderInfo.getConfig().enable();
		renderInfo.getShader().bind();			
		renderInfo.getShader().updateUniforms(getParent());
		getVbo().draw();
		renderInfo.getConfig().disable();
	};

	public VBO getVbo() {
		return vbo;
	}

	public void setVbo(VBO vbo) {
		this.vbo = vbo;
	}

	public RenderInfo getRenderInfo() {
		return renderInfo;
	}

	public void setRenderInfo(RenderInfo renderinfo) {
		this.renderInfo = renderinfo;
	}
}
