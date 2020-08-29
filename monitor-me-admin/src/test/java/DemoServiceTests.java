import com.alibaba.fastjson.JSON;
import com.m.monitor.me.admin.MonitorMeAdminApplication;
import com.m.monitor.me.service.mogodb.norm.MethodPointService;
import com.m.monitor.me.service.mogodb.norm.NormMinuteService;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DoubleUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorMeAdminApplication.class)
@Lazy
public class DemoServiceTests {
	@Resource
	private MethodPointService methodPointService;
	@Test
	public void queryRealTimeNorm() {
		List<double[]> monitorNorm=methodPointService.queryRealTimeNorm("10.249.243.155",
				null,20200821153453L, MonitorTimeUnitEnum.MINUTE,60);
		System.out.println(JSON.toJSON(monitorNorm));
	}

	@Resource
	private NormMinuteService normMinuteService;

	@Test
	public void saveTest() {
		for(int i=1;i<=48*60;i++) {
			IntegratorNormRecord record = new IntegratorNormRecord("monitor-me-example", "127.0.0.1");
			TimeNorm timeNorm = new TimeNorm(MonitorTimeUtil.subTime(20200829202700L, 1*i, MonitorTimeUnitEnum.MINUTE));
			timeNorm.setAvg(DoubleUtil.avg(Math.random() * 50, 1));
			timeNorm.setTotal(5000);
			record.getTs().add(timeNorm);
			normMinuteService.save(record);
		}
	}

}
