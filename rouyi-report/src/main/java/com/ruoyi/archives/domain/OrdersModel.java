package com.ruoyi.archives.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.product.domain.ProductModel;

import java.util.List;

public class OrdersModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name = "订单ID")
    private String Id;
    @Excel(name = "客户姓名")
    private String Name;

    @Excel(name = "客户备注")
    private String Bz;
    @Excel(name = "时间")
    private String Date;

    @Excel(name = "客户欠款")
    private String Balance;
    @Excel(name = "支付金额")
    private String Pay;
    @Excel(name = "当前月份")
    private String Month;
    @Excel(name = "所属销售")
    private String Ssxs;

    @Excel(name = "客户 ID")
    private String Cid;

    @Excel(name = "发票号")
    private String Receiptnumber;

    private String cou;

    private String Years;


    /**
     * 商品信息
     */
    private List<ProductModel> goods;

    public List<ProductModel> getGoods() {
        return goods;
    }

    public void setGoods(List<ProductModel> goods) {
        this.goods = goods;
    }

    public String getYears() {
        return Years;
    }

    public void setYears(String years) {
        Years = years;
    }

    public String getCou() {
        return cou;
    }

    public void setCou(String cou) {
        this.cou = cou;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBz() {
        return Bz;
    }

    public void setBz(String bz) {
        Bz = bz;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getPay() {
        return Pay;
    }

    public void setPay(String pay) {
        Pay = pay;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getSsxs() {
        return Ssxs;
    }

    public void setSsxs(String ssxs) {
        Ssxs = ssxs;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getReceiptnumber() {
        return Receiptnumber;
    }

    public void setReceiptnumber(String receiptnumber) {
        Receiptnumber = receiptnumber;
    }

    @Override
    public String toString() {
        return "OrdersModel{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Bz='" + Bz + '\'' +
                ", Date='" + Date + '\'' +
                ", Balance='" + Balance + '\'' +
                ", Pay='" + Pay + '\'' +
                ", Month='" + Month + '\'' +
                ", Ssxs='" + Ssxs + '\'' +
                ", Cid='" + Cid + '\'' +
                ", Receiptnumber='" + Receiptnumber + '\'' +
                ", cou='" + cou + '\'' +
                ", Years='" + Years + '\'' +
                ", goods=" + goods +
                '}';
    }
}
