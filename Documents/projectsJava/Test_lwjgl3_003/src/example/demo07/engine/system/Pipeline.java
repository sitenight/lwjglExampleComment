package example.demo07.engine.system;

import example.demo07.engine.gldata.fbo.OverlayFrameBufferObject;
import example.demo07.engine.gldata.tex.TextureObject;
import example.demo07.engine.gui.element.DynamicPanel;
import example.demo07.engine.scene.RenderType;
import example.demo07.modules.generic.OverlayBlendingShader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;

public abstract class Pipeline {

    // Status of render engine
    private Boolean running;

    private OverlayFrameBufferObject overlayFrameBufferObject;
    private TextureObject overlayBlendedImage;

    // final texture to be displayed
    protected TextureObject sceneTexture;

    /** TODO: refactor with camera controller **/

    protected Pipeline(){
        Window window = Window.instance();
        sceneTexture = new TextureObject(GL_TEXTURE_2D, window.getWidth(), window.getHeight())
                .allocateImage2D(GL_RGBA16F, GL_RGBA)
                .trilinearFilter();
        overlayBlendedImage = new TextureObject(GL_TEXTURE_2D, window.getWidth(), window.getHeight())
                .allocateImage2D(GL_RGBA16F, GL_RGBA)
                .bilinearFilter();
        overlayFrameBufferObject = new OverlayFrameBufferObject();
        running = true;
    }

    public void onResize(){
        overlayFrameBufferObject.resize(Window.instance().getWidth(), Window.instance().getHeight());
        overlayBlendedImage.resize(Window.instance().getWidth(), Window.instance().getHeight());
    }

    protected abstract void renderScene(Context context);
    protected abstract TextureObject getDepth();

    void draw(DynamicPanel viewport, Context context){

        // adjust dynamicpanel size
        if(Window.instance().isResized()){
            glViewport(0,0, Window.instance().getWidth(),
                    Window.instance().getHeight());
            onResize();
            Window.instance().setResized(false);
        }

        // call sub-render method
        // that will populate the scene texture
        renderScene(context);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if(Config.instance().isDebugLayer()) {

            overlayFrameBufferObject.bind();
            {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glClear(GL_DEPTH_BUFFER_BIT);
                context.getScene().render(RenderType.TYPE_OVERLAY);

                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glLineWidth(1f);
                context.getSelectionManager().renderSelected(RenderType.TYPE_WIREFRAME);
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

//            context.getScene()
//                    .stream()
//                    .filter(e -> e instanceof ModuleNode && !(e.getParent() instanceof ModuleNode))
//                    .map(e -> (ModuleNode)e)
//                    .filter(e -> e.getModules().containsKey(RenderType.TYPE_SCENE))

            }
            overlayFrameBufferObject.unbind();

            OverlayBlendingShader.instance().compute(
                    sceneTexture, overlayFrameBufferObject.getOverlay(),
                    overlayBlendedImage, getDepth(), overlayFrameBufferObject.getDepth()
            );

            // render scene texture to the gui
            viewport.setScreenTexture(overlayBlendedImage);
        }
        else
            viewport.setScreenTexture(sceneTexture);

        viewport.setChanged(true);
    }

    public void cleanup(){
        sceneTexture.cleanup();
    }

    public Boolean isRunning(){
        return running;
    }

    public void activate(){
        running = true;
    }

    public void deactivate(){
        running = false;
    }

    public TextureObject getSceneTexture() {
        return sceneTexture;
    }
    
    
    
}

