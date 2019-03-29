package example.demo04.engine.model;

/**
 * Класс граней
 * Имеет 3 группы индексов (IdxGroup), т.к. работаем с треугольниками
 * @author user
 */
public class Face {

    /** Список групп индексов в треугольнике/грани (грань из 3 точек) */
    private IdxGroup[] idxGroups;
    
    public Face(String v1, String v2, String v3) {
        idxGroups = new IdxGroup[3];
        // Обрабатываем 3 вершины
        idxGroups[0] = parseLine(v1);
        idxGroups[1] = parseLine(v2);
        idxGroups[2] = parseLine(v3);
    }
    
    /**
     * Обработка одной вершины
     * @param line линия в файле OBJ, выглеядит следующе 11/7/1 или 11//1(без текстуры)
     * @return 
     */
    private IdxGroup parseLine(String line) {
        IdxGroup idxGroup = new IdxGroup();
        
        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if(length > 1) {
            String textCoord = lineTokens[1];
            idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
            if(length > 2) {
                idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }
        return idxGroup;
    }
    
    public IdxGroup[] getFaceVertexIndices() {
        return idxGroups;
    }
}

