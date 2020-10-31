import com.m.monitor.me.admin.MonitorMeAdminApplication;
import com.m.monitor.me.service.mogodb.service.MonitorPointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorMeAdminApplication.class)
@Lazy
public class MonitorPointServiceTest {
	@Resource
	private MonitorPointService monitorPointService;
	@Test
	public void querySlowTest() {
		monitorPointService.querySlow(null,false,20);
	}
}
