package info.yangguo.demo.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/9/14
 * Time:上午9:45
 * <p/>
 * Description:
 */
public class JsonApiViewDemo {
    private static class User {
        private long id;

        @JsonView({ApiView.Default.class})
        private String name;

        @JsonView({ApiView.Ios.class})
        private String avator240;

        @JsonView({ApiView.Android.class})
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
        public String getAvator240() {
            return avator240;
        }
        public String getAvator160() {
            return avator160;
        }

    }

    private static class ApiView {
        static class Default {}
        static class Ios extends Default {}
        static class Android extends Default {}
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);

        User user = new User();
        user.id = 10000L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";

        String apiViewJson = mapper.writerWithView(ApiView.Default.class).writeValueAsString(user);
        String iosViewJson = mapper.writerWithView(ApiView.Ios.class).writeValueAsString(user);
        String androidViewJson = mapper.writerWithView(ApiView.Android.class).writeValueAsString(user);

        System.out.println(apiViewJson);
        System.out.println(iosViewJson);
        System.out.println(androidViewJson);

    }
}
