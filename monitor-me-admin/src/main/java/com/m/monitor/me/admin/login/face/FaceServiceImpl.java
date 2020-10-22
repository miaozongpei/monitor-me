package com.m.monitor.me.admin.login.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucf.fs.monitor.service.FaceService;
import com.ucf.fs.monitor.utils.HttpUtil;
import com.ucf.fs.monitor.utils.UcfFundLoggerFactory;
import com.ucf.platform.framework.core.log.UcfLogger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;

@Service
public  class FaceServiceImpl implements FaceService {
    private static final UcfLogger logger = UcfFundLoggerFactory.getLogger(com.ucf.fs.monitor.service.impl.FaceServiceImpl.class);
    @Override
    public String regedit(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            String uid="m000001";
            String user_info="miao";
            String group_id="monitor";
            String param = "uid=" + uid + "&user_info=" + user_info + "&group_id=" + group_id + "&image=" +  URLEncoder.encode(image, "UTF-8"); ;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.160c9599c083b1111a3591ab9d4a5a27.2592000.1514531277.282335-10107741";
            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            logger.error("人脸识别注册异常：",e);
        }
        return null;
    }
    @Override
    public Boolean identify(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            String group_id="monitor";
            String param = "group_id=" + group_id + "&image=" +  URLEncoder.encode(image, "UTF-8"); ;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.160c9599c083b1111a3591ab9d4a5a27.2592000.1514531277.282335-10107741";
            String result = HttpUtil.post(url, accessToken, param);
            Map<String, Object> map = JSON.parseObject(result);
            JSONArray r= (JSONArray) map.get("result");
            JSONObject x=r.getJSONObject(0);
            BigDecimal scores=(BigDecimal)((JSONArray)x.get("scores")).get(0);
            return scores.doubleValue()>=80;
        } catch (Exception e) {
            logger.error("人脸识别登录异常：",e);
            return false;
        }
    }

}
