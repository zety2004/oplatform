import com.hklk.oplatform.comm.cache.RedisCache;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.StudentChoice;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SchoolVo;
import com.hklk.oplatform.service.*;
import com.hklk.oplatform.util.JsonUtil;
import com.hklk.oplatform.util.PayUtil;
import comm.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;
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
    @Autowired
    SchoolService schoolService;
    @Autowired
    StudentChoiceService studentChoiceService;

    @Autowired
    TeacherMessageService teacherMessageService;

    @Autowired
    SchoolChannelService schoolChannelService;


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
        List<Map<String, Object>> result = studentChoiceService.queryMyCurriculum(344, 3);
        System.out.println(JsonUtil.toJson(result));
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
        PageTableForm<CurriculumApplyVo> pageTableForm = scApplyService.queryCurriculumApplyForPage(1, null, 1, 10);
    }

    @Test
    public void queryStudent() {
        sStudentService.queryStudentByClassId("", 24);
    }

    @Test
    public void selectFeedBack() {

        PageTableForm<Map<String, Object>> queryFeedBackList = feedBackService.queryFeedBackList(0, 1, 10);
    }

    @Test
    public void testMapToXml() throws Exception {
        System.out.println(1111);
        Map<String,Object> temp  = new HashMap<>();
        temp.put("temp",1);
        System.out.println(PayUtil.mapToXml(temp));
    }

    @Test
    public void insertChannelCurriculumBySchoolId() {
        System.out.println(11111111);
        schoolChannelService.insertChannelCurriculumBySchoolId(32, 29);
    }

    @Test
    public void queryCurriculumOrder() {

        scApplyService.queryStudentBySCAId(17);
    }

    @Test
    public void updateStudentChoice() {
        StudentChoice studentChoice = new StudentChoice();
        studentChoice.setRefundTime(new Date());
        studentChoice.setId(43);
        studentChoiceService.updateByPrimaryKeySelective(studentChoice);
    }

    @Test
    public void queryAllCurriculumForParent() {

        List<Map<String, Object>> curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(28, 1, null);
    }

    @Test
    public void queryCurriculumByID() {

        Map<String, List<Map<String, Object>>> curriculumPageTableForm = scApplyService.queryHotCurriculumForParent(1, null);
        System.out.println(curriculumPageTableForm);
    }

    @Test
    public void queryHotCurriculumForParent() {
        System.out.println(JsonUtil.toJson(scApplyService.queryHotCurriculumForParent(1, 3)));
    }

    @Test
    public void getRedisCache() {
        System.out.println(RedisCache.get("parentToken:0deaba2ce9b647d6b016cd862dc9dbc4"));
    }

    @Test
    public void getSchools() {
        PageTableForm<SchoolVo> pageTableForm = schoolService.querySchools(null, 1, 10);
        System.out.println(JsonUtil.toJson(pageTableForm));
    }
}
