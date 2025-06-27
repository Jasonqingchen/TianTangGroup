package com.ruoyi.web.controller.report;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.archives.domain.ClientFollowModel;
import com.ruoyi.archives.domain.ClientModel;
import com.ruoyi.archives.domain.OrdersModel;
import com.ruoyi.archives.mapper.ClientMapper;

import com.ruoyi.archives.mapper.ClientFollowMapper;
import com.ruoyi.archives.mapper.OrdersMapper;
import com.ruoyi.product.domain.ProOrderModel;
import com.ruoyi.product.domain.ProductModel;
import com.ruoyi.product.mapper.ProOrdertMapper;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * employ follow customer list
 * liuqingchen
 */
@RequiresRoles(value = {"admin", "report"}, logical = Logical.OR)
@Controller
@RequestMapping("/report/listdata")
public class RptDataController extends BaseController {



    @Autowired
    private ClientMapper cMapper;

    @Autowired
    private ClientFollowMapper fMapper;

    @Autowired
    private OrdersMapper oMapper;

    @Autowired
    private ProOrdertMapper poMapper;



    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private String prefix = "report/listdata";
    private final static Map<String, ClientModel> clients = new LinkedHashMap<String, ClientModel>();

    // 下载模板
    @GetMapping("/importTemplate")
    @ResponseBody
    public void importTemplate(Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        ExcelUtil<ClientModel> util = new ExcelUtil<ClientModel>(ClientModel.class);
        util.importTemplateExcel("客户模版数据");
    }
    /**
     * 表格
     */
    @RequiresPermissions("report:listdata:view")
    @GetMapping("")
    public String user()
    {
        return prefix + "/cuslist";
    }

    /**
     * 查询数据
     */
    @RequiresPermissions("report:listdata:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClientModel clientModel)
    {
        startPage();
        List<ClientModel> userList = cMapper.selectByTj(clientModel);
        return getDataTable(userList);
    }

    /**
     * 新增客户
     */
    @RequiresPermissions("report:listdata:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    @RequiresPermissions("report:listdata:add")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated ClientModel cModel)
    {
        Integer integer=0;
        ClientModel u = cMapper.selectClientByName(cModel.getName());
        if(StringUtils.isNull(u)){
            cModel.setId(String.valueOf(Math.random()*999999999));
            cModel.setFlag("0");
            integer = cMapper.insertClientone(cModel);
        }
        
        return toAjax(integer);
    }

    /**
     * 新增订单-跳转
     */
    @RequiresPermissions("report:listdata:order")
    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") String id, ModelMap mmap)
    {
        mmap.put("cModel", cMapper.selectClientById(id));
        return prefix + "/order";
    }
    /**
     * 订单保存
     */
    @RequiresPermissions("report:listdata:order")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/order")
    @ResponseBody
    public AjaxResult addorder(OrdersModel oModel)
    {
        oModel.setCid(oModel.getId());
        //根据 id 去客户表里面修改客户状态为已成交
        cMapper.updateStatusById(oModel.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat years = new SimpleDateFormat("yyyy");
        oModel.setId(String.valueOf(Math.random()*999999999));
        oModel.setDate(formatter.format(new Date()));
        oModel.setMonth(month.format(new Date()));
        oModel.setYears(years.format(new Date()));
        if(oModel.getGoods()==null){
            return AjaxResult.error("未填写商品 无法下单");
        } else {
        //save to new Orderpro table and use order table the id connect
            ProOrderModel pom = new ProOrderModel();
            List<ProductModel> goods = oModel.getGoods();
            goods.forEach(go->{
                pom.setCou(go.getCou());
                pom.setId(String.valueOf(Math.random()*999999999));
                pom.setDate(formatter.format(new Date()));
                pom.setPrice(go.getPrice());
                pom.setProductname(go.getProductname());
                pom.setPsize(go.getPsize());
                pom.setSsxs(oModel.getSsxs());
                pom.setOid(oModel.getId());
                poMapper.insertProOrder(pom);
            });
        }

        return AjaxResult.success(oMapper.insertOrder(oModel));
    }
    /**
     * 条件查询
     * @param cModel
     * @return
     */
    @RequiresPermissions("report:listdata:search")
    @Log(title = "客户查询", businessType = BusinessType.INSERT)
    @PostMapping("/search")
    @ResponseBody
    public TableDataInfo search(@Validated ClientModel cModel)
    {
        startPage();
        List<ClientModel> list = cMapper.selectByTj(cModel);
        return getDataTable(list);
    }


    /**
     * 修改用户
     */
    @RequiresPermissions("report:listdata:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        mmap.put("cModel", cMapper.selectClientById(id));
        return prefix + "/edit";
    }


    /**
     * 修改保存用户
     */
    @RequiresPermissions("report:listdata:edit")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ClientModel cModel)
    {
        return AjaxResult.success(cMapper.updateClient(cModel));
    }

    /**
     * 跟进页面
     */

    @RequiresPermissions("report:listdata:detail")
    @GetMapping("/detail/{id}")
    public String follow(@PathVariable("id") String id, ModelMap mmap)
    {
        ClientModel clientModel = cMapper.selectClientById(id);
        ClientFollowModel clientFollowModel = new ClientFollowModel();
        clientFollowModel.setCid(id);
        clientFollowModel.setSsxs(clientModel.getSsxs());
        clientFollowModel.setStatus(clientModel.getStatus());
        mmap.put("fModel", clientFollowModel);
        return prefix + "/detail";
    }

    /**
     * 跟进详情保存
     */
    @RequiresPermissions("report:listdata:follow")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/follow")
    @ResponseBody
    public AjaxResult followSave(ClientFollowModel fModel)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        fModel.setId(String.valueOf(Math.random()*999999999));
        fModel.setGjdate(formatter.format(new Date()));
        fModel.setFlag("0");
        fModel.setMonth(month.format(new Date()));
        return AjaxResult.success(fMapper.insertClientFollow(fModel));
    }

    /**
     * 跟进列表查询
     */
    @RequiresPermissions("report:listdata:searchfollow")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/searchfollow")
    @ResponseBody
    public TableDataInfo searchfollow(String id)
    {

       return getDataTable(fMapper.selectClientFollowById(id));

    }

    /**
     * 删除
     */
    @RequiresPermissions("report:listdata:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {

        //验证该用户是否存在订单
        Integer om = oMapper.selectOrderByCid(ids);
        if (om == 0){
            String [] idss = Convert.toStrArray(ids);
            for (String id : idss)
            {
                cMapper.deleteReportById(id);
            }
            return AjaxResult.success();
        } else {
            return AjaxResult.error("该用户名下存在订单，不可删除");
        }

    }

    // 导入方法

    @RequiresPermissions("report:listdata:importData")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ClientModel> util = new ExcelUtil<ClientModel>(ClientModel.class);
        List<ClientModel> clientList = util.importExcel(file.getInputStream());
        String message = this.importClient(clientList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 导入用户数据
     *
     * @param clientList  用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importClient(List<ClientModel> clientList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(clientList) || clientList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (ClientModel client : clientList)
        {
            try
            {
                // 验证是否存在这个用户
                ClientModel u = cMapper.selectClientByName(client.getName());
                if (StringUtils.isNull(u))
                {
                    client.setId(String.valueOf(Math.random()*999999999));
                    client.setFlag("0");
                    client.setBalance("0");
                    LocalDate date = LocalDate.parse(client.getDate(), inputFormatter);
                    LocalDate gjdate = LocalDate.parse(client.getGjdate(), inputFormatter);
                    client.setDate(date.format(outputFormatter));
                    client.setGjdate(gjdate.format(outputFormatter));
                    cMapper.insertClient(client);
                    successNum++;
                    successMsg.append("<br/>" + failureNum +"导入成功");
                }
                else if (isUpdateSupport)
                {
                    //更新用户
                    cMapper.updateClient(client);
                    successNum++;
                    successMsg.append("<br/> 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;

                String msg = "<br/>" + failureNum + "、账号  导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


}
