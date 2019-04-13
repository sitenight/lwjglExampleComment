package example.demo07.engine.system;

import java.io.InputStream;
import java.util.Properties;
import org.joml.Vector3f;

public class Config {

    private int windowWidth;
    private int windowHeight;
    private String windowName;
    private boolean vsync;
    private int multisamples;
    private String renderEngine, engineInstance;
    private int numLights;
    private float zfar;

    private int shadowBufferWidth;
    private int shadowBufferHeight;

    private boolean ssao;
    private float ssaoRadius;
    private int ssaoSamples;

    private boolean debugLayer;

    private boolean isWireframe;
    private Vector3f wireframeColor;

    Properties properties;

    private static Config instance;
    public static Config instance(){
        if(instance == null){
            instance = new Config();
        }
        return instance;
    }



    public Config(){
        properties = new Properties();
        init();
    }

    public void init(){

        try{
            InputStream str = Config.class.getClassLoader().getResourceAsStream("demo07/config.properties");
            properties.load(str);
            str.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        // max lights
        numLights = Integer.valueOf(properties.getProperty("numLights"));

        // window settings
        windowWidth = Integer.valueOf(properties.getProperty("windowWidth"));
        windowHeight = Integer.valueOf(properties.getProperty("windowHeight"));
        windowName = properties.getProperty("windowName");
        vsync = properties.getProperty("isvsync").equalsIgnoreCase("true");
        multisamples = Integer.valueOf(properties.getProperty("multisamples"));

        // engine instance
        renderEngine = properties.getProperty("renderEngine");
        engineInstance = properties.getProperty("engineInstance");

        shadowBufferWidth = Integer.valueOf(properties.getProperty("shadow_buffer_x"));
        shadowBufferHeight = Integer.valueOf(properties.getProperty("shadow_buffer_y"));

        // SSAO settings
        ssao = Boolean.valueOf(properties.getProperty("ssao"));
        ssaoRadius = Float.valueOf(properties.getProperty("ssao_radius"));
        ssaoSamples = Integer.valueOf(properties.getProperty("ssao_samples"));

        // generic layer
        debugLayer = Boolean.valueOf(properties.getProperty("debug_layer"));

        isWireframe = Boolean.valueOf(properties.getProperty("isWireframe"));
        wireframeColor = new Vector3f(0.2f, 0.8f, 0.2f);

    }

    public String getWindowName() {
        return windowName;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public String getEngineInstance() {
        return engineInstance;
    }

    public static Config getInstance() {
        return instance;
    }

    public static void setInstance(Config instance) {
        Config.instance = instance;
    }

    public int getNumLights() {
        return numLights;
    }

    public String getRenderEngine() {
        return renderEngine;
    }

    public boolean isDebugLayer() {
        return debugLayer;
    }

    public int getShadowBufferHeight() {
        return shadowBufferHeight;
    }

    public int getShadowBufferWidth() {
        return shadowBufferWidth;
    }

    public boolean isWireframe() {
        return isWireframe;
    }

    public void setWireframeColor(Vector3f wireframeColor) {
        this.wireframeColor = wireframeColor;
    }

    public int getSsaoSamples() {
        return ssaoSamples;
    }

    public float getSsaoRadius() {
        return ssaoRadius;
    }

    public boolean isSsao() {
        return ssao;
    }
    
    
}

