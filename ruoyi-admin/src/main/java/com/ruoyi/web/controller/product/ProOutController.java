package com.ruoyi.web.controller.product;

import com.ruoyi.archives.domain.OrdersModel;
import com.ruoyi.archives.mapper.ClientMapper;
import com.ruoyi.archives.mapper.OrdersMapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.product.mapper.ProOrdertMapper;
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

import java.util.List;

/**
 * about product out
 * liuqingchen
 */
@RequiresRoles(value = {"admin", "product"}, logical = Logical.OR)
@RequestMapping("/product/out")
@Controller
public class ProOutController   extends BaseController {
    @Autowired
    private OrdersMapper oMapper;

    @Autowired
    private ClientMapper cMapper;

    @Autowired
    private ProOrdertMapper poMapper;

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private String prefix = "product/out";

    /**
     * table search
     */
    @RequiresPermissions("product:out:view")
    @GetMapping("")
    public String tz()
    {
        return prefix + "/outlist";
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrdersModel oMordel)
    {
        oMordel.setStatus("已审核");
        startPage();
        //只查询审核通过的订单
        List<OrdersModel> orderList = oMapper.selectAllByStatus(oMordel);
        return getDataTable(orderList);
    }

    /**
     * 审核下推订单
     */
    @PostMapping("/conform")
    @ResponseBody
    public AjaxResult conform(String ids)
    {
        String [] idss = Convert.toStrArray(ids);
        for (String id : idss)
        {
            OrdersModel ordersModel = oMapper.selectOrderById(id);
            if(ordersModel.getOutstatus().equals("已审核")){
                return  AjaxResult.error("该出库单已审核，请勿重复审核");
            }
            OrdersModel om = oMapper.selectOrderById(id);
            om.setOutstatus("已审核");
            oMapper.updateOrder(om);
        }
        return AjaxResult.success();
    }

    /**
     * 反审核
     */
    @PostMapping("/fconform")
    @ResponseBody
    public AjaxResult fconform(String ids)
    {
        String [] idss = Convert.toStrArray(ids);
        for (String id : idss)
        {
            OrdersModel om = oMapper.selectOrderById(id);
            om.setOutstatus("未审核");
            oMapper.updateOrder(om);
        }
        return AjaxResult.success();
    }
}
