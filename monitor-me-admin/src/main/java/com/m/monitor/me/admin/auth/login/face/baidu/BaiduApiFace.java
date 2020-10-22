package com.m.monitor.me.admin.auth.login.face.baidu;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import org.json.JSONObject;

public class BaiduApiFace extends AipFace{
    public BaiduApiFace(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }
    public JSONObject identify(String image, String groupId) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        request.addBody("image", image);
        request.addBody("group_id", groupId);
        request.setUri("https://aip.baidubce.com/rest/2.0/face/v2/identify?access_token="+this.accessToken);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        return this.requestServer(request);
    }
}
