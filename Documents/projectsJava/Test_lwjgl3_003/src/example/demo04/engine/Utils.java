package example.demo04.engine;

import example.demo04.engine.model.Vertex;
import example.demo04.math.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;

public class Utils {

    public static FloatBuffer createFloatBuffer(int size) {
	return BufferUtils.createFloatBuffer(size);
    }
	
    public static IntBuffer createIntBuffer(int size) {
	return BufferUtils.createIntBuffer(size);
    }

    public static ByteBuffer createByteBuffer(int size) {
	return BufferUtils.createByteBuffer(size);
    }

    public static IntBuffer createFlippedBuffer(int... values) {
	IntBuffer buffer = createIntBuffer(values.length);
	buffer.put(values);
	buffer.flip();
		
	return buffer;
    }
	
    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
	FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		
	for(int i = 0; i < vertices.length; i++) {
		buffer.put(vertices[i].getPos().x);
		buffer.put(vertices[i].getPos().y);
		buffer.put(vertices[i].getPos().z);
		buffer.put(vertices[i].getTexCoord().x);
		buffer.put(vertices[i].getTexCoord().y);
		buffer.put(vertices[i].getNormal().x);
		buffer.put(vertices[i].getNormal().y);
		buffer.put(vertices[i].getNormal().z);
	}
		
	buffer.flip();
		
	return buffer;
    }
	
    public static FloatBuffer createFlippedBuffer(Matrix4f value) {
	FloatBuffer buffer = createFloatBuffer(4 * 4);
		
	for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
		buffer.put(value.get(j, i));
		
	buffer.flip();
		
	return buffer;
    }
    
    public static String[] removeEmptyStrings(String[] data) {
	       ArrayList<String> result = new ArrayList<String>();
		
	for(int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
		result.add(data[i]);
		
	String[] res = new String[result.size()];
	result.toArray(res);
		
	return res;
    }
	
    public static int[] toIntArray(Integer[] data) {
	int[] result = new int[data.length];
		
	for(int i = 0; i < data.length; i++)
            result[i] = data[i].intValue();
		
        return result;
    }
    
    public static List<String> readAllLines(String path) throws Exception {
        List<String> list = new ArrayList<>();
        System.out.println(path);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(path)))) {
            String line;
            while((line = br.readLine()) != null)  {
                list.add(line);
            
            System.out.println(line);
            }
        }
        return list;
    }
}

