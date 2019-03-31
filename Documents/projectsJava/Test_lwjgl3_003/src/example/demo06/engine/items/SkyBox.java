package example.demo06.engine.items;

import example.demo04.math.Vector3f;
import example.demo06.engine.render.*;

/**
 * Объект, представляющий Скай бокс (небесную коробку)
 * <p> Расширяет {@link Item}, что упрощает доступ к служебным методам </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class SkyBox extends Item {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Texture skyBoxTexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(skyBoxTexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }

    public SkyBox(String objModel, Vector3f colour) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Material material = new Material(colour, 0);
        skyBoxMesh.setMaterial(material);
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
    
}
