package com.ruoyi.archives.mapper;


import com.ruoyi.archives.domain.OrdersModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper
{
    /**
     * add order
     * @param oModel
     * @return
     */
    Integer insertOrder(OrdersModel oModel);

    /**
     * 列表查询和条件查询
     * @param oModer
     * @return
     */
    List<OrdersModel> selectAll(OrdersModel oModer);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteReportById(String id);

    /**
     * 根据姓名分组
     * @return
     */
    List<OrdersModel> selectAllByName(OrdersModel oModel);

    /**
     * 每个销售的订单数量
     */
    List<OrdersModel> selectAllGroupBySsxs();

    /**
     * 年度销售统计
     */
    List<OrdersModel> selectAllByNamendtj(String year);

    /**
     * 热力图
     */
    List<OrdersModel> selectByDate(String year);

    String selectSumPayY(String year);//今年总收入
    String  selectSumPayM(String month);//本月收
    String selectSumPayD(String day);//今日收入
    String selectCountOrder(String day);//今日订单

    /**
     * 根据订单号查询订单是否存在
     * @param receiptnumber
     * @return
     */
   OrdersModel selectClientByReceiptnumber(String receiptnumber);

    /**
     * 用户 Id 与 Cid 关联确认是否存在订单
     * @param id 用户 id
     * @return
     */
    Integer selectOrderByCid(String id);

}
