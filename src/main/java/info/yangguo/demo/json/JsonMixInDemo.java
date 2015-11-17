package info.yangguo.demo.json;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/9/14
 * Time:上午9:47
 * <p/>
 * Description:
 */
public class JsonMixInDemo {
    static class User {
        private long id;
        private String name;
        private String avator240;
        private String avator160;
        private String address;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

    }

    abstract class MixIn {
        @JsonIgnore
        abstract int getAddress();

        @JsonIgnore
        long id;

        @JsonProperty("custom_name")
        abstract String getName();

        @JsonProperty("avator")
        String avator240;
    }


    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.id = 1234567L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";

        mapper.getSerializationConfig().addMixInAnnotations(User.class, MixIn.class);
        String json = mapper.writeValueAsString(user);
        System.out.println(json);

    }
}
