import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.*;
import comm.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TransactionConfiguration(defaultRollback = false)
public class serviceTest extends AbstractTestCase {
    @Autowired
    UserService userService;
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    ConsumablesService consumablesService;
    @Autowired
    SSyllabusService sSyllabusService;
    @Autowired
    SCApplyService scApplyService;
    @Autowired
    SStudentService sStudentService;
    @Autowired
    FeedBackService feedBackService;

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

    @Test
    public void queryMapByWeekType() {
        List<Map<String, String>> result = sSyllabusService.queryMapByWeekType(1, 1);
    }

    @Test
    public void queryCurriculumApply() {
        PageTableForm<CurriculumApplyVo> pageTableForm = scApplyService.queryCurriculumApply(1, null, 1, 10);
    }

    @Test
    public void queryStudent() {
        sStudentService.queryStudentByClassId("", 24);
    }

    @Test
    public void selectFeedBack() {

        Map<String,String> temp = feedBackService.selectFeedBack(1, "hklk_school_admin");
    }

    @Test
    public void queryCurriculumOrder() {

        scApplyService.queryCurriculumOrder("",0, 1, 10);
    }

}
