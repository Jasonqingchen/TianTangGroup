package com.ruoyi.product.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ProductModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name = "产品ID")
    private String Id;
    @Excel(name = "产品名称")
    private String Productname;
    @Excel(name = "产品备注")
    private String Bz;
    @Excel(name = "时间")
    private String Date;
    @Excel(name = "入库人姓名")
    private String Rkname;
    @Excel(name = "当前登录用户 id")
    private String Userid;

    @Excel(name = "部门id")
    private String Did;

    @Excel(name = "部门名字")
    private String Depname;

    @Excel(name = "所属仓库")
    private String Ssck;

    @Excel(name = "库存")
    private String Stock;

    @Excel(name = "产品编码")
    private String Pcode;

    @Excel(name = "产品尺寸")
    private String Psize;

    private String Cou;

    private String Price;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

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

    public String getRkname() {
        return Rkname;
    }

    public void setRkname(String rkname) {
        Rkname = rkname;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public String getDepname() {
        return Depname;
    }

    public void setDepname(String depname) {
        Depname = depname;
    }

    public String getSsck() {
        return Ssck;
    }

    public void setSsck(String ssck) {
        Ssck = ssck;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getPcode() {
        return Pcode;
    }

    public void setPcode(String pcode) {
        Pcode = pcode;
    }

    public String getPsize() {
        return Psize;
    }

    public void setPsize(String psize) {
        Psize = psize;
    }

    public String getCou() {
        return Cou;
    }

    public void setCou(String cou) {
        Cou = cou;
    }
}
