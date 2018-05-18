import com.hklk.oplatform.comm.cache.RedisCache;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.UserService;
import comm.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class serviceTest extends AbstractTestCase {
    @Autowired
    UserService userService;


    @Test
    public void loginTest() {
        Map<String, String> param = new HashMap<>();
        param.put("name", "developer");
        param.put("password", "123456");

        User temp = userService.loginUser("developer", "123456");
        System.out.println(temp.getUsername());
    }
    @Test
    public void redisTest(){
        RedisCache.set("userToken",String.valueOf(System.currentTimeMillis()));
        System.out.println(1111);
    }


}
