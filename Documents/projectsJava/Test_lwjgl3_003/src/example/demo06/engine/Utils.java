package example.demo06.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;

public class Utils {

    /**
     * Загружает и возвращает ресурс по указанному пути
     * @param fileName Путь к ресурсу
     * @return Читаемый ресурс
     */
    public static String loadResource(String fileName){
        StringBuilder builder = new StringBuilder();
        try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
        }catch(IOException ex){
            throw new RuntimeException("Ошибка загрузки файла" + System.lineSeparator() + ex.getMessage());
        }
        return builder.toString();
    }

    /**
     * Читает все строки файла и возвращает их в виде списка
     * @param fileName Путь к файлу
     * @return Список, содержащий каждую строку
     * @throws Exception Если что-то пошло не так
     */
    public static List<String> readAllLines(String fileName) throws Exception{
        List<String> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClass().getResourceAsStream(fileName)))){
            String line;
            while((line = br.readLine()) != null){
                list.add(line);
            }
        }
        return list;
    }

    /**
     * Преобразует список в массив
     * @param list Оригинальный список
     * @return Новый массив
     */
    public static float[] listToArray(List<Float> list){
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for(int i = 0; i < size; i++){
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }

    /**
     * Получает список целых чисел и возвращает массив
     * @param list список
     * @return Массив
     */
    public static int[] listIntToArray(List<Integer> list){
        int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
        return result;
    }

    /**
     * Проверяет, существует ли ресурс
     * @param fileName Путь к файлу
     * @return Существует ли он
     */
    public static boolean existsResourceFile(String fileName){
        boolean result;
        try(InputStream is = Utils.class.getResourceAsStream(fileName)){
            result = is != null;
        }catch(Exception excp){
            result = false;
        }
        return result;
    }
}

