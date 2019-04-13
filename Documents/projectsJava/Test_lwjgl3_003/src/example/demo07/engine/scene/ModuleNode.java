package example.demo07.engine.scene;

import java.util.HashMap;
import java.util.Map;

public class ModuleNode extends Node {

    private Map<RenderType, Module> modules;

    public ModuleNode(){
        super();
        modules = new HashMap<>();

    }

    @Override
    public void update(){
        super.update();
        modules.values().forEach(e->e.update());
    }

    @Override
    public void render(RenderType type){
        if(modules.containsKey(type)){
            modules.get(type).render();
        }
        super.render(type);
    }

    @Override
    public void render(RenderType type, Condition condition) {
        if(modules.containsKey(type)){
            modules.get(type).render();
        }
        super.render(type, condition);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        modules.values().forEach(e->e.cleanup());
    }

    public void addModule(RenderType type, Module module){
        module.setParent(this);
        modules.put(type, module);
    }

    public Map<RenderType, Module> getModules() {
        return modules;
    }

    
}

