import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.ConsumablesService;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.UserService;
import comm.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.HashMap;
import java.util.Map;

@TransactionConfiguration(defaultRollback = false)
public class serviceTest extends AbstractTestCase {
    @Autowired
    UserService userService;
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    ConsumablesService consumablesService;

    @Test
    public void loginTest() {
        Map<String, String> param = new HashMap<>();
        param.put("name", "developer");
        param.put("password", "123456");

        User temp = userService.loginUser("developer", "123456");
        System.out.println(temp.getUsername());
    }

    @Test
    public void redisTest() {
        Curriculum id = curriculumService.selectIdByUniqueNum("3423424");
        System.out.println(id);
        System.out.println(1111);
    }

    @Test
    public void selectByPrimaryKey() {
        Consumables consumables = new Consumables();
        consumables.setName("测试用品");
        consumables.setCurriculumId(4);
        consumables.setNum(5);
        consumables.setUnitPrice(3.12);
        consumables.setUnit("只");
        int temp = consumablesService.insertSelective(consumables);
        System.out.println("-------------------" + temp + "----------------------");
    }

}
