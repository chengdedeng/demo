package info.yangguo.demo.utils;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Json序列化的工具。使用Jackson作为序列化的library
 * <p/>
 * author: 杨果
 * date: 11-3-22
 * time: 下午2:06
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static JsonFactory jsonFactory = new JsonFactory();

    /**
     * 将json发序列化为对象
     *
     * @param jsonAsString json字符串
     * @param pojoClass    需要反序列化的类型
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws java.io.IOException
     */
    public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass)
            throws JsonMappingException, JsonParseException, IOException {
        return objectMapper.readValue(jsonAsString, pojoClass);
    }

    /**
     * 从FileReader中的数据反序列化为对象
     *
     * @param fr        FileReader
     * @param pojoClass 需要反序列化的类型
     * @throws JsonParseException
     * @throws java.io.IOException
     */
    public static <T> Object fromJson(FileReader fr, Class<T> pojoClass)
            throws JsonParseException, IOException {
        return objectMapper.readValue(fr, pojoClass);
    }

    /**
     * 将对象转化为json
     *
     * @param pojo        需要序列化的对象
     * @param prettyPrint 是否打印优化，即换行
     * @return String 返回序列化的字符串
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws java.io.IOException
     */
    public static String toJson(Object pojo, boolean prettyPrint)
            throws JsonMappingException, JsonGenerationException, IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jsonFactory.createJsonGenerator(sw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        objectMapper.writeValue(jg, pojo);
        return sw.toString();
    }

    /**
     * 将对象序列化，并输出到FileWriter
     *
     * @param pojo        需要序列化的对象
     * @param fw          需要输出的FileWriter
     * @param prettyPrint 是否打印优化，即换行
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws java.io.IOException
     */
    public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
            throws JsonMappingException, JsonGenerationException, IOException {
        JsonGenerator jg = jsonFactory.createJsonGenerator(fw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        objectMapper.writeValue(jg, pojo);
    }
}
