package example.demo06.engine.loaders.obj;

/**
 * Список групп индексов одной вершины, имеет следующую информацию:
 *  - индекс позиции
 *  - индекс текстурных координат
 *  - индекс вектора нормали
 * 
 * @author user
 */
public class IdxGroup {

    /** Значение по умолчанию */
    public static final int NO_VALUE = -1;
    
    /** Индекс позиции */
    public int idxPos;
    
    /** Индекс текстурных координат */
    public int idxTextCoord;
    
    /** Индекс вектора нормали */
    public int idxVecNormal;

    /**
     * В конструкторе задаем всем данным значение по умолчанию равное -1
     */
    public IdxGroup() {
        idxPos = NO_VALUE;
        idxTextCoord = NO_VALUE;
        idxVecNormal = NO_VALUE;
    }
}

