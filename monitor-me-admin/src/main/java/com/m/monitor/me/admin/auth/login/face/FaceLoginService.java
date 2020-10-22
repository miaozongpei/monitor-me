package com.m.monitor.me.admin.auth.login.face;
import com.m.monitor.me.admin.auth.login.AbstractLoginService;
import com.m.monitor.me.admin.auth.login.face.baidu.BaiduApi;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public  class FaceLoginService {
    public boolean loginAuth(String image) throws Exception {
        // 调用接口"取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
        // 人脸检测
        JSONObject res = BaiduApi.newFaceClient().identify(image, "user");
        return false;
    }
}
