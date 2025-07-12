package com.ruoyi.web.controller.report;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.report.domain.ClientFollowModel;
import com.ruoyi.report.domain.ClientModel;
import com.ruoyi.report.domain.OrdersModel;
import com.ruoyi.report.mapper.ClientFollowMapper;
import com.ruoyi.report.mapper.ClientMapper;
import com.ruoyi.report.mapper.OrdersMapper;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据分析
 */
@RequiresRoles(value = {"admin", "report"}, logical = Logical.OR)
@Controller
@RequestMapping("/report/sjfx")
public class SjfxController  extends BaseController {
    @Autowired
    private ClientMapper cMapper;

    @Autowired
    private ClientFollowMapper fMapper;

    @Autowired
    private OrdersMapper oMapper;

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private String prefix = "report/sjfx";
    /**
     * 跳转 到 数据分析页面
     */
    @RequiresPermissions("report:sjfx:view")
    @GetMapping("")
    public String sjfx()
    {
        return prefix + "/sjfxpage";
    }

    /**
     * 订单分析
     */
    @RequiresPermissions("report:sjfx:ordertj")
    @Log(title = "订单分析", businessType = BusinessType.UPDATE)
    @PostMapping("/ordertj")
    @ResponseBody
    public Map<String,List<String>> ordertj(OrdersModel oModel)
    {
        Map<String,List<String>> map = new HashMap();
        List list1 = new ArrayList<>();
        List list2 = new ArrayList<>();
        for (OrdersModel model : oMapper.selectAllByName(oModel)) {
            list1.add(model.getSsxs());
            list2.add(model.getCou());
        }
        map.put("name",list1);
        map.put("cou",list2);
        return map;

    }

    /**
     * 年度销售额
     */
    @RequiresPermissions("report:sjfx:nxltj")
    @Log(title = "年度销售额", businessType = BusinessType.UPDATE)
    @PostMapping("/nxltj")
    @ResponseBody
    public Map<String,List<String>> nxltj(OrdersModel oModel)
    {
        Map<String,List<String>> map = new HashMap();
        List list1 = new ArrayList<>();
        List list2 = new ArrayList<>();
        SimpleDateFormat years = new SimpleDateFormat("yyyy");
        for (OrdersModel model : oMapper.selectAllByNamendtj(years.format(new Date()))) {
            list1.add(model.getSsxs());
            list2.add(model.getCou());
        }
        map.put("ndname",list1);
        map.put("ndcou",list2);
        return map;

    }

    /**
     * 客户地区分布
     */
    @RequiresPermissions("report:sjfx:dqfb")
    @Log(title = "客户地区分布", businessType = BusinessType.UPDATE)
    @PostMapping("/dqfb")
    @ResponseBody
    public List<Map<String,Object>> dqfb()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ClientModel clientModel : cMapper.selectAllByDqtj()) {
            Map m = new HashMap();
            m.put("value",clientModel.getCou());
            m.put("name",clientModel.getAddress());
            list.add(m);
        }
        return list;
    }

    /**
     * 客户类型分析
     */
    @RequiresPermissions("report:sjfx:khtype")
    @Log(title = "客户类型分析", businessType = BusinessType.UPDATE)
    @PostMapping("/khtype")
    @ResponseBody
    public List<Map<String,Object>> khtype()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ClientModel clientModel : cMapper.selectAllByType()) {
            Map m = new HashMap();
            m.put("value",clientModel.getCou());
            m.put("name",clientModel.getType());
            list.add(m);
        }
        return list;
    }


    /**
     * 头部数据
     */
    @RequiresPermissions("report:sjfx:tbdata")
    @Log(title = "头部数据", businessType = BusinessType.UPDATE)
    @PostMapping("/tbdata")
    @ResponseBody
    public List<Map<String,Object>> tbdata()
    {
        List<Map<String,Object>> list =  new ArrayList<>();

        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat years = new SimpleDateFormat("yyyy");

        String y = oMapper.selectSumPayY(years.format(new Date()));//今日收入
        String m = oMapper.selectSumPayM(month.format(new Date()));//本月收
        String d = oMapper.selectSumPayD(day.format(new Date()));//今年总收入
        String ocount = oMapper.selectCountOrder(day.format(new Date()));//今日订单数量
        Map map =  new HashMap();
        map.put("y",y);
        map.put("m",m);
        map.put("d",d);
        map.put("ocount",ocount);
        list.add(map);
        return list;
    }
    /**
     * 销售热力图
     */
    @RequiresPermissions("report:sjfx:rltdata")
    @Log(title = "销售热力图", businessType = BusinessType.UPDATE)
    @PostMapping("/rltdata")
    @ResponseBody
    public List rltdata()
    {

        SimpleDateFormat years = new SimpleDateFormat("yyyy");
        List list = new ArrayList<>();
        for (OrdersModel oModel : oMapper.selectByDate(years.format(new Date()))) {
            List list1= new ArrayList<>();
            list1.add(oModel.getDate());
            list1.add(oModel.getCou());
            list.add(list1);
        }
        return list;
    }

    /**
     * 转化率
     */
    @RequiresPermissions("report:sjfx:zhtj")
    @Log(title = "转化率", businessType = BusinessType.UPDATE)
    @PostMapping("/zhtj")
    @ResponseBody
    public Map<String,List<String>> zhtj(OrdersModel oModel)
    {
        Map<String,List<String>> map = new HashMap();
        List list1 = new ArrayList<>();
        List list2 = new ArrayList<>();
        List list3 = new ArrayList<>();
        //查询所有所属销售名下的客户数量
        List<ClientModel> cm = cMapper.selectAllGroupBySsxs();
        //跟进表格中的每个员工的跟进次数
        List<ClientFollowModel> cfm = fMapper.selectAllGroupBySsxs();
        //每个销售的订单数量
        List<OrdersModel> om = oMapper.selectAllGroupBySsxs();

        for (ClientModel cmodel : cMapper.selectAllGroupBySsxs()) {
            for (ClientFollowModel cfmodel : fMapper.selectAllGroupBySsxs()) {
                if(cmodel.getSsxs().equals(cfmodel.getSsxs())){
                    cmodel.setCou(String.valueOf(Integer.parseInt(cmodel.getCou())+Integer.parseInt(cfmodel.getCou())));
                    list2.add(cmodel.getCou());
                    list3.add(cmodel.getSsxs());
                }

            }
        }
        for (ClientModel cmodel : cMapper.selectAllGroupBySsxs()) {
        for (OrdersModel omodel : oMapper.selectAllGroupBySsxs()) {
            if (omodel.getSsxs().equals(cmodel.getSsxs())){
                list1.add(String.valueOf(Integer.parseInt(cmodel.getCou())/Integer.parseInt(omodel.getCou())));
            }
          }
        }

        map.put("zhcount",list1);
        map.put("bcount",list2);
        map.put("name",list3);
        return map;

    }

}
