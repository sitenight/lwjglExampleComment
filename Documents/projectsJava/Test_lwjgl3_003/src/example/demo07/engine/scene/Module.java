package example.demo07.engine.scene;

public class Module {

    private ModuleNode parent;

    public void update(){}
    public void render(){}
    public void cleanup(){}

    public ModuleNode getParent() {
        return parent;
    }

    public void setParent(ModuleNode parent) {
        this.parent = parent;
    }
    
    

}

