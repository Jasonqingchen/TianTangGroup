package com.ruoyi.web.controller.report;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.archives.domain.ClientModel;
import com.ruoyi.archives.domain.OrdersModel;
import com.ruoyi.archives.mapper.ClientMapper;
import com.ruoyi.archives.mapper.OrdersMapper;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            oMapper.deleteReportById(id);
        }
        return AjaxResult.success();
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
