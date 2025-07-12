package com.ruoyi.web.controller.report;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.report.domain.ClientModel;
import com.ruoyi.report.domain.OrdersModel;
import com.ruoyi.report.mapper.ClientMapper;
import com.ruoyi.report.mapper.OrdersMapper;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * About order
 */
@RequiresRoles(value = {"admin", "report"}, logical = Logical.OR)
@Controller
@RequestMapping("/report/orderlist")
public class OrderController  extends BaseController {
    @Autowired
    private OrdersMapper oMapper;

    @Autowired
    private ClientMapper cMapper;

    @Autowired
    private ProOrdertMapper poMapper;


    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private String prefix = "report/orderlist";
    private final static Map<String, OrdersModel> clients = new LinkedHashMap<String, OrdersModel>();
    /**
     * 表格
     */
    @RequiresPermissions("report:orderlist:view")
    @GetMapping("")
    public String order()
    {
        return prefix + "/orderslist";
    }

    /**
     * 查询数据
     */
    @RequiresPermissions("report:orderlist:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrdersModel oMordel)
    {
        startPage();
        List<OrdersModel> orderList = oMapper.selectAll(oMordel);
        return getDataTable(orderList);
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
            OrdersModel ordersModel = oMapper.selectOrderById(id);
            if(ordersModel.getStatus().equals("已审核")){
                return  AjaxResult.error("该订单已审核无法删除");
            }
            oMapper.deleteReportById(id);
            // Sam time del connect table for oid = id
            poMapper.delByOid(id);
        }
        return AjaxResult.success();
    }

    /**
     * update order information
     *
     */
    @Log(title = "修改订单", businessType = BusinessType.UPDATE)
    @PostMapping("/updateorder")
    @ResponseBody
    public AjaxResult updateorder(OrdersModel oModel)
    {
        //获得当前订单信息
        OrdersModel ordersModel = oMapper.selectOrderById(oModel.getOid());
        ordersModel.setPay(oModel.getPay());
        ordersModel.setBz(oModel.getBz());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //先删除原来订单
        oMapper.deleteReportById(ordersModel.getId());
        // Sam time del connect table for oid = id
        poMapper.delByOid(ordersModel.getId());
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
                pom.setSsxs(ordersModel.getSsxs());
                pom.setOid(ordersModel.getId());
                poMapper.insertProOrder(pom);
            });
        }

        return AjaxResult.success(oMapper.insertOrder(ordersModel));
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
            if(ordersModel.getStatus().equals("已审核")){
                return  AjaxResult.error("该订单已审核，请勿重复审核");
            }
           OrdersModel om = oMapper.selectOrderById(id);
            om.setStatus("已审核");
            oMapper.updateOrder(om);
        }
        return AjaxResult.success();
    }

    /**
     * 修改订单-跳转
     */
    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") String id, ModelMap mmap)
    {
        //入参为 订单 id
        OrdersModel om = oMapper.selectOrderById(id);
        ClientModel clientModel = cMapper.selectClientById(om.getCid());
        clientModel.setOid(id);
        clientModel.setPay(om.getPay());
        clientModel.setReceiptnumber(om.getReceiptnumber());
        clientModel.setGoods(poMapper.selectAllByOid(id));
        mmap.put("cModel", clientModel);
        return prefix + "/order";
    }

    /**
     * 获取指定订单商品数据
     */
    @PostMapping("/ordlist")
    @ResponseBody
    public AjaxResult goods(String id)
    {
        List<ProOrderModel> proOrderModels = poMapper.selectAllByOid(id);
        return AjaxResult.success(proOrderModels);
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
            if (om.getOutstatus().equals("已审核")) {
                return AjaxResult.error("已出库商品无法反审核订单");
            }
            om.setStatus("未审核");
            oMapper.updateOrder(om);
        }
        return AjaxResult.success();
    }

    /**
     * 打印回显数据
     */
    @PostMapping("/back")
    @ResponseBody
    public AjaxResult back(String id, ModelMap mmap)
    {
        mmap.put("Model", oMapper.selectCusAndProById(id));
        return AjaxResult.success(mmap);
    }

    /**
     * 导入订单数据
     *
     * @param ordersList  订单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importClient(List<OrdersModel> ordersList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(ordersList) || ordersList.size() == 0)
        {
            throw new ServiceException("导入订单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (OrdersModel order : ordersList)
        {
            try
            {
                // 收据编号验证收据是否存在
                OrdersModel ord = oMapper.selectClientByReceiptnumber(order.getReceiptnumber());

                if (StringUtils.isNull(ord))
                {
                    ord.setId(String.valueOf(Math.random()*999999999));
                    LocalDate date = LocalDate.parse(order.getDate(), outputFormatter);
                    order.setDate(date.format(outputFormatter));
                    ClientModel clientModel = cMapper.selectClientByName(ord.getName());
                    ord.setCid(clientModel.getId());
                    oMapper.insertOrder(order);
                    successNum++;
                    successMsg.append("<br/>" + failureNum +"导入成功");
                }
                else if (isUpdateSupport)
                {
                    //更新订单
                  //  oMapper.updatOrder(order);
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
