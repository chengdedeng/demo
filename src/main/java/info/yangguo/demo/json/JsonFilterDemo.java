package info.yangguo.demo.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/9/14
 * Time:上午9:48
 * <p/>
 * Description:
 */
public class JsonFilterDemo {
    private static class User {
        private long id;
        private String name;
        private String avator240;
        private String avator160;
        private String address;

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
        public long getId() {
            return id;
        }
    }

    @JsonFilter("userFilter")
    private static interface UserFilterMixIn
    {

    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.id = 1000L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";


        FilterProvider idFilterProvider = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(new String[]{"name", "avator240"}));
        mapper.setFilters(idFilterProvider);
        mapper.getSerializationConfig().addMixInAnnotations(User.class, UserFilterMixIn.class);
        String userFilterJson = mapper.writeValueAsString(user);

        System.out.println(userFilterJson);
    }
}
