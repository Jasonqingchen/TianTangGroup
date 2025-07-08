package com.ruoyi.web.controller.report;

import cn.hutool.json.ObjectMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.archives.domain.OrdersModel;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import okhttp3.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

import static com.ruoyi.common.core.domain.R.fail;

/**
 * sales target controller
 * 金蝶云星空 API
 */
@RequiresRoles(value = {"admin", "report"}, logical = Logical.OR)
@Controller
@RequestMapping("/report/target")
public class SalesTargetController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private String prefix = "report/target";
    /**
     * 表格
     */
    @RequiresPermissions("report:target:view")
    @GetMapping("")
    public String target()
    {
        return prefix + "/targetlist";
    }

    /**
     * (return data form kingdee to app)
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo rerurnlist(String par)
    {

        return getDataTable(null);
    }

    /**
     * 查询数据
     */

    public static void main(String[] args) throws IOException {
        String par = "";
        par+="fId";
        String table ="";
        table+="AR_receivable";
        K3CloudApi client = new K3CloudApi();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<List<Integer>>>(){}.getType();
        //请求参数，要求为json字符串
        String jsonData = "{\"FormId\":\""+table+"\",\"FieldKeys\":\""+par+"\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            String resultJson = String.valueOf(client.executeBillQuery(jsonData));
            List<List<Integer>> listOfLists = gson.fromJson(resultJson, listType);
            listOfLists.forEach(re->{
                re.forEach(ss->{
                    System.out.println("接口返回结果: " + ss);
                });
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }



}
