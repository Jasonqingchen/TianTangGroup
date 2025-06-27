package com.ruoyi.product.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ProOrderModel  extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Excel(name = "下单商品ID")
    private String Id;
    @Excel(name = "下单商品名称")
    private String Productname;
    @Excel(name = "备注")
    private String Bz;
    @Excel(name = "时间")
    private String Date;
    @Excel(name = "所属销售")
    private String Ssxs;
    @Excel(name = "订单 id")
    private String Oid;

    @Excel(name = "商品数量")
    private String Cou;

    @Excel(name = "商品价格")
    private String Price;

    @Excel(name = "商品尺寸")
    private String psize;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
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

    public String getSsxs() {
        return Ssxs;
    }

    public void setSsxs(String ssxs) {
        Ssxs = ssxs;
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public String getCou() {
        return Cou;
    }

    public void setCou(String cou) {
        Cou = cou;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }
}
