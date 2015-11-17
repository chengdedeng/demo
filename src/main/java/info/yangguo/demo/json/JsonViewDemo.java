package info.yangguo.demo.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/9/14
 * Time:上午9:44
 * <p/>
 * Description:
 */
public class JsonViewDemo {
    private static class User {
        private long id;
        @JsonView({FilterView.Output.class})
        private String name;
        @JsonView({FilterView.Output.class})
        private String avator240;
        private String avator160;
        private String address;
        public long getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getAvator240() {
            return avator240;
        }
        public String getAvator160() {
            return avator160;
        }
        public String getAddress() {
            return address;
        }
    }

    private static class FilterView {
        static class Output {}
    }

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.id = 1000L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
        System.out.println(mapper.writerWithView(FilterView.Output.class).writeValueAsString(user));
    }
}
