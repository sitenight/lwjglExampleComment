package example.demo04.engine.model;

import example.demo04.engine.Utils;
import example.demo04.math.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс фнализирования и загрузки OBJ моделей
 * 
 * В файле .obj каждая строка начинается с токена с указанием типа элемента:
 * Комментарии - это строки, начинающиеся с #.  
 * Маркер «v» определяет геометрическую вершину с координатами (x, y, z, w).
 * Маркер «vn» определяет нормаль вершины с координатами (x, y, z). 
 * Маркер «vt» определяет координату текстуры. 
 * Маркер «f» определяет грани. С информацией, содержащейся в этих строках, мы 
 *   построим наш массив индексов. Мы будем обрабатывать только случай, когда
 *   грани экспортируются в виде треугольников. Может иметь несколько вариантов
 * @author user
 */
public class OBJLoader {

    /**
     * Создания экземпляра модели
     * @param path путь к файлу модели
     * @return экземпляр модели
     * @throws Exception 
     */
    public static Mesh loadMesh(String path) throws Exception {
        List<String> lines = Utils.readAllLines(path);
        
        List<Vector3f> vertices = new ArrayList<>(); // вершины
        List<Vector2f> textures = new ArrayList<>(); // координаты текстуры
        List<Vector3f> normals = new ArrayList<>(); // нормали 
        List<Face> faces = new ArrayList<>(); // грани
        
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch(tokens[0]) {
                case "v":
                    // вершины
                    Vector3f vector = new Vector3f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3]));
                    vertices.add(vector);
                    break;
                case "vt":
                    // координаты текстуры
                    Vector2f vectorTexture = new Vector2f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]));
                    textures.add(vectorTexture);
                    break;
                case "vn":
                    // нормали
                    Vector3f vectorNormal = new Vector3f(
                        Float.parseFloat(tokens[1]),
                        Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3]));
                    normals.add(vectorNormal);
                    break;
                case "f":
                    // грани
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    // Игнорируем другие линии/данные
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    private static Mesh reorderLists(List<Vector3f> vertices, List<Vector2f> textures, 
            List<Vector3f> normals, List<Face> faces) {
        List<Integer> indices = new ArrayList<>();
        // Create position array in the order it has been declared
        float[] posArr = new float[vertices.size() * 3];
        int i = 0;
        for (Vector3f position : vertices) {
            posArr[i * 3] = position.x;
            posArr[i * 3 + 1] = position.y;
            posArr[i * 3 + 2] = position.z;
            i++;
        }
        float[] textCoordArr = new float[vertices.size() * 2];
        float[] normalArr = new float[vertices.size() * 3];
        
        for (Face face : faces) {
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for (IdxGroup indisecValue : faceVertexIndices) {
                processFaceVertex(indisecValue, textures, normals, 
                        indices, textCoordArr, normalArr);
            }
        }
        int[] indicesArr = new int[indices.size()];
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        Mesh mesh = new Mesh(posArr, textCoordArr, normalArr, indicesArr);
        return mesh;
    }
    
    private static void processFaceVertex(IdxGroup indicesValue, List<Vector2f> textCoord,
            List<Vector3f> normals, List<Integer> indices,
            float[] textCoordArr, float[] normalArr) {
        
        // Set index for vertex coordinates
        int posIndex = indicesValue.idxPos;
        indices.add(posIndex);
        
        // Reorder texture coordinates
        if(indicesValue.idxTextCoord >= 0) {
            Vector2f texture = textCoord.get(indicesValue.idxTextCoord);
            textCoordArr[posIndex * 2] = texture.x;
            textCoordArr[posIndex * 2 + 1] = 1 - texture.y;
        }
        
        // Reorder vectornormals
        if(indicesValue.idxVecNormal >= 0) {
            Vector3f normal = normals.get(indicesValue.idxVecNormal);
            normalArr[posIndex * 3] = normal.x;
            normalArr[posIndex * 3 + 1] = normal.y;
            normalArr[posIndex * 3 + 1] = normal.z;
        }
    }
    
    
}

