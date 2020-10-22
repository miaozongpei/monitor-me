package com.m.monitor.me.admin.auth.login.face;
import com.m.monitor.me.admin.auth.login.face.baidu.BaiduApi;
import org.springframework.stereotype.Service;

@Service
public  class FaceLoginService {
    public boolean loginAuth(String image) throws Exception {
        return BaiduApi.newFaceClient().identify(image, "user");
    }
}
