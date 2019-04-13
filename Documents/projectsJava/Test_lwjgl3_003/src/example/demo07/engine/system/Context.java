package example.demo07.engine.system;

import example.demo07.engine.event.*;
import example.demo07.engine.gui.element.*;
import example.demo07.engine.scene.*;

/**
 * Contains high level engine context for rendering scenes
 * and GUI layers, updating modules, and events
 */

public abstract class Context {

    // root UI element
    private Element root;

    private Picking picking;

    // scenegraph of 3D scene
    protected Scenegraph scene;

    // uses scene to create texture of 3D scene
    private Pipeline pipeline;

    // dynamicpanel UI element that displays 3D scene texture
    private DynamicPanel viewport;

    private SelectionManager selectionManager;

    protected Camera camera;

    protected Context() {
        this.root = new RootElement();
        this.viewport = new DynamicPanel();
        this.root.addChild(new DynamicWindow(viewport));
        this.scene = new Scenegraph();
        this.camera = new Camera();
        this.picking = new Picking(this);
        this.selectionManager = new SelectionManager();
    }

    void loadRenderer() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        this.pipeline = Context.class.getClassLoader().loadClass(
                Config.instance().getRenderEngine()).asSubclass(Pipeline.class).newInstance();
    }

    public abstract void init();
    protected abstract void update(double duration);
    public abstract void cleanup();

    void update(){
        // let UI updates happen first so the event can propogate to scene
        root.handle();

        // now update scene with proper inputs reaching the
        scene.update();

        camera.update();
    }

    void render(){
        pipeline.draw(viewport, this);
        root.render();
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public DynamicPanel getViewport() {
        return viewport;
    }

    public Scenegraph getScene() {
        return scene;
    }

    public Camera getCamera() {
        return camera;
    }

    public Picking getPicking() {
        return picking;
    }

    public void setSelectionManager(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public Element getRoot() {
        return root;
    }

    
    
}

