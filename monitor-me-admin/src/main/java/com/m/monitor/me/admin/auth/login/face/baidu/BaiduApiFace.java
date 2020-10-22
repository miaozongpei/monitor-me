package com.m.monitor.me.admin.auth.login.face.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.face.AipFace;
import com.m.monitro.me.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;
@Slf4j
public class BaiduApiFace extends AipFace{
    public BaiduApiFace(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }
    public Boolean identify(String image,String groupId) {
        this.getAccessToken(this.config);
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            String param = "group_id=" + groupId + "&image=" +  URLEncoder.encode(image, "UTF-8"); ;
            String result = HttpUtil.post(url, this.accessToken, param);
            Map<String, Object> map = JSON.parseObject(result);
            //result:{"log_id":3039494049,"result_num":1,"result":[{"uid":"miaozp","user_info":"","scores":[73.035263061523],"group_id":"user"}]}
            JSONArray r= (JSONArray) map.get("result");
            com.alibaba.fastjson.JSONObject x=r.getJSONObject(0);
            BigDecimal scores=(BigDecimal)((JSONArray)x.get("scores")).get(0);
            return scores.doubleValue()>=80;
        } catch (Exception e) {
            log.error("人脸识别登录异常：",e);
            return false;
        }
    }
}
