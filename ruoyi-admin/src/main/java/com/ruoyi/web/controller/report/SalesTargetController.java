package com.ruoyi.web.controller.report;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.report.domain.ClientModel;
import com.ruoyi.report.domain.OrdersModel;
import com.ruoyi.report.domain.TargetModel;
import com.ruoyi.report.mapper.ClientFollowMapper;
import com.ruoyi.report.mapper.ClientMapper;
import com.ruoyi.report.mapper.OrdersMapper;
import com.ruoyi.report.mapper.TargetMapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.mapper.ProOrdertMapper;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static com.ruoyi.common.core.domain.R.fail;

/**
 * sales target controller
 * 金蝶云星空 API
 */
@RequiresRoles(value = {"admin", "report"}, logical = Logical.OR)
@Controller
@RequestMapping("/report/target")
public class SalesTargetController extends BaseController {
    @Autowired
    private ClientMapper cMapper;

    @Autowired
    private ClientFollowMapper fMapper;

    @Autowired
    private OrdersMapper oMapper;

    @Autowired
    private ProOrdertMapper poMapper;

    @Autowired
    private TargetMapper tMapper;
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
    public TableDataInfo rerurnlist(TargetModel tar)
    {
        startPage();
        List<TargetModel> targetModels = tMapper.selectTargetModel(tar);
        return getDataTable(targetModels);
    }
    /**
     * 修改保存
     */
    @Log(title = "目标管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TargetModel tModel)
    {
        return AjaxResult.success(tMapper.updateTarget(tModel));
    }
    /**
     * 调用金蝶系接口数据案例
     */

    public static void main(String[] args) throws IOException {
        String par = "";
        par+="fId";
        par+=",FSALEERID";//销售员
        par+=",FSALEORGID";//销售组织
        par+=",FSALEORGID";//销售组织


        String table ="";
        table+="AR_receivable";
        K3CloudApi client = new K3CloudApi();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<List<Integer>>>(){}.getType();
        //请求参数，要求为json字符串
        String jsonData = "{\"FormId\":\""+table+"\",\"FieldKeys\":\""+par+"\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10,\"SubSystemId\":\"\"}";
        //System.out.println("接口返回结果: " + jsonData);
        try {
            //调用接口
            String resultJson = String.valueOf(client.executeBillQuery(jsonData));
            List<List<Integer>> listOfLists = gson.fromJson(resultJson, listType);
            listOfLists.forEach(re->{
                System.out.println("接口返回结果: " + re);
                /*re.forEach(ss->{

                });*/
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }
    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        String [] idss = Convert.toStrArray(ids);
        for (String id : idss)
        {

            tMapper.deleteTargetById(id);
        }
        return AjaxResult.success();
    }


    /**
     * 历史数据跳转
     */
    @GetMapping("/targeTcount/{id}")
    public String targeTcount(@PathVariable("id") String id, ModelMap mmap)
    {
        return prefix + "/order";
    }
    /**
     * 导出
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult exportSelected(TargetModel tModel, String targetIds)
    {
        List<TargetModel> targets = tMapper.selectTargetModel(tModel);
        List<TargetModel> tList = new ArrayList<TargetModel>(Arrays.asList(new TargetModel[targets.size()]));
        Collections.copy(tList, targets);

        // 条件过滤
        if (StringUtils.isNotEmpty(targetIds))
        {
            tList.clear();
            for (String tId : Convert.toStrArray(targetIds))
            {
                for (TargetModel target : targets)
                {
                    if (target.getId().equals(tId))
                    {
                        tList.add(target);
                    }
                }
            }
        }
        ExcelUtil<TargetModel> util = new ExcelUtil<TargetModel>(TargetModel.class);
        return util.exportExcel(tList, "目标数据");
    }

}
