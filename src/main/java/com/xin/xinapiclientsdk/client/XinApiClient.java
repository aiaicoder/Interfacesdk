package com.xin.xinapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xin.xinapiclientsdk.model.User;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import static com.xin.xinapiclientsdk.utils.SignUtil.getSign;


/**
 * 调用第三方接口的客户端
 * @author xin
 */
public class XinApiClient {
    private String accessKey;
    private String secretKey;

    public XinApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getName(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String getName = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(getName);
        return getName;
    }


    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String getName = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(getName);
        return getName;
    }


    public Map<String,String> getHeaderMap(String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey",accessKey);
        headers.put("secretKey",secretKey);
        //优先对body进行加密
        headers.put("sign",getSign(body,secretKey));
        headers.put("nonce", RandomUtil.randomNumbers(4));
        headers.put("timestamp", String.valueOf(System.currentTimeMillis()/ (1000 * 60)));
        //对body参数进行编码
        try {
            body = URLEncoder.encode(body,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        headers.put("body", body);
        return headers;
    }



    public String getUserName(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .body(json)
                .addHeaders(getHeaderMap(json))
                .execute();
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println(body);
        return body;

    }
}
