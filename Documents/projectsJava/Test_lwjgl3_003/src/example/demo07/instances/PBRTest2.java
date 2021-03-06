package example.demo07.instances;


import example.demo07.engine.event.Input;
import example.demo07.engine.gldata.vbo.Meshs;
import example.demo07.engine.scene.*;
import example.demo07.engine.scene.light.*;
import example.demo07.engine.system.*;
import example.demo07.modules.pbr.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class PBRTest2 extends Context {

    PointLight light1;
    Node object, lights;


    public void init() {


        object = new Node();
        lights = new Node();
        object.addChild(lights);

//        PBRMaterial mat = new PBRMaterial();
//        mat.setMetalMap(ImageLoader.loadTexture("res/images/plastic_squares/metal.png", false));
//        mat.setAlbedoMap(ImageLoader.loadTexture("res/images/plastic_squares/albedo.png", false));
//        mat.setRoughnessMap(ImageLoader.loadTexture("res/images/plastic_squares/rough.png", false));
//        mat.setNormalMap(ImageLoader.loadTexture("res/images/plastic_squares/normal.png", false));
//        mat.useMetalMap(true);
//        mat.useRoughnessMap(true);
//        mat.useAlbedoMap(true);
//        mat.useNormalMap(true);


        int matrixdim = 5;
        for(int i = 0; i < matrixdim; i++){
            for(int j = 0; j < matrixdim; j++){
                PBRModel model = new PBRModel(Meshs.sphere,
                        new PBRMaterial(0f, 0f, 0f,
                                (float)i/(float)matrixdim, (float)j/(float)matrixdim));
                model.translate(0, 2*(i-matrixdim/2), 2*(j-matrixdim/2));
                object.addChild(model);
            }
        }

        light1 = new PointLight();
        light1.setIntensity(5f);
        light1.translate(2,2,2);

        //light2 = new PointLight(light1).translate(2,2,-2);
        //light3 = new PointLight(light1).translate(2,-2,-2);
        //light4 = new PointLight(light1).translate(2,-2,2);

        LightManager.setSun(new DirectionalLight().setIntensity(1f).rotateTo(0,-1,0));

        lights.addChildren(light1);

        scene.addChild(object);
    }

    double time = 0;
    boolean rotate;

    public void update(double duration) {

        if(Input.instance().isKeyHeld(GLFW_KEY_SPACE))
            rotate = !rotate;

        if(rotate) {
            time += duration;
            lights.translateTo(0, (float) (2 * Math.cos(time)) - 2, (float) (2 * Math.sin(time)));
        }
    }

    public void cleanup() {

    }
}