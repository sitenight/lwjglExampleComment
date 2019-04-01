package example.demo06.engine.loaders.obj;

import example.demo04.math.*;
import example.demo06.engine.Utils;
import example.demo06.engine.render.InstancedMesh;
import example.demo06.engine.render.Mesh;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Загружает и читает основной файл <tt> .obj </tt>
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

    
    public static Mesh loadMesh(String path) throws Exception {
        return loadMesh(path, 1);
    }
    
    /**
     * Создания экземпляра модели
     * @param path путь к файлу модели
     * @param instances экземпляр
     * @return экземпляр модели
     * @throws Exception 
     */
    public static Mesh loadMesh(String path, int instances) throws Exception {
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
        return reorderLists(vertices, textures, normals, faces, instances);
    }
    
    public static Mesh loadMeshFromWorld(String path, int instances) throws Exception {
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        
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
        return reorderLists(vertices, textures, normals, faces, instances);
    }

    private static Mesh reorderLists(List<Vector3f> vertices, List<Vector2f> textures, 
            List<Vector3f> normals, List<Face> faces, int instances) {
        List<Integer> indices = new ArrayList<>();
        // Создание позиции массива в том порядке, который был объявлен
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
            for (IdxGroup indicesValue : faceVertexIndices) {
                processFaceVertex(indicesValue, textures, normals, 
                        indices, textCoordArr, normalArr);
            }
        }
        int[] indicesArr = Utils.listIntToArray(indices);
        Mesh mesh;
        if(instances > 1) {
            mesh = new InstancedMesh(posArr, textCoordArr, normalArr, indicesArr, instances);
        } else {
            mesh = new Mesh(posArr, textCoordArr, normalArr, indicesArr);
        }
        return mesh;
    }
    
    private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoord,
            List<Vector3f> normalsList, List<Integer> indicesList,
            float[] textCoordArr, float[] normalArr) {
        
        // Установить индекс для координат вершины
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);
        
        // Изменяем порядок текстурных координат
        if(indices.idxTextCoord >= 0) {
            Vector2f texture = textCoord.get(indices.idxTextCoord);
            textCoordArr[posIndex * 2] = texture.x;
            textCoordArr[posIndex * 2 + 1] = 1 - texture.y;
        }
        
        // Изменяем порядок векторных нормалей
        if(indices.idxVecNormal >= 0) {
            Vector3f normal = normalsList.get(indices.idxVecNormal);
            normalArr[posIndex * 3] = normal.x;
            normalArr[posIndex * 3 + 1] = normal.y;
            normalArr[posIndex * 3 + 1] = normal.z;
        }
    }
}

