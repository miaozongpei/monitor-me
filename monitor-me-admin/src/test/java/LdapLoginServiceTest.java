import com.m.monitor.me.admin.MonitorMeAdminApplication;
import com.m.monitor.me.admin.auth.login.ldap.LdapLoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorMeAdminApplication.class)
@Lazy
public class LdapLoginServiceTest {
	@Resource
	private LdapLoginService ldapLoginService;
	@Test
	public void ldapAuthTest() {
		boolean isAuth= false;
		try {
			isAuth = ldapLoginService.loginAuth("miaozp","123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(isAuth);
	}

}
