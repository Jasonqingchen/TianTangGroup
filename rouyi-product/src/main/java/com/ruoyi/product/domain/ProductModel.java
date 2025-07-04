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

    @Excel(name = "编码")
    private String Pcode;

    @Excel(name = "产品尺寸")
    private String Psize;
    @Excel(name = "货柜号码")
    private String Containercode;
    @Excel(name = "供应商")
    private String Supplier;
    @Excel(name = "批次号")
    private String Pccode;
    @Excel(name = "应收数量")
    private String Ysnumber;
    @Excel(name = "实收数量")
    private String Ssnumber;
    @Excel(name = "计价数量")
    private String Jjnumber;
    @Excel(name = "单价")
    private String Dj;
    @Excel(name = "总价")
    private String Totalpirce;
    @Excel(name = "含税价格")
    private String Hspirce;
    @Excel(name = "税率")
    private String Sl;
    @Excel(name = "税额")
    private String Se;
    @Excel(name = "价税合计")
    private String Jshj;
    @Excel(name = "净价")
    private String Jj;
    @Excel(name = "库存单位")
    private String Kcdw;

    private String Cou;

    private String Price;

    public String getContainercode() {
        return Containercode;
    }

    public void setContainercode(String containercode) {
        Containercode = containercode;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getPccode() {
        return Pccode;
    }

    public void setPccode(String pccode) {
        Pccode = pccode;
    }

    public String getYsnumber() {
        return Ysnumber;
    }

    public void setYsnumber(String ysnumber) {
        Ysnumber = ysnumber;
    }

    public String getSsnumber() {
        return Ssnumber;
    }

    public void setSsnumber(String ssnumber) {
        Ssnumber = ssnumber;
    }

    public String getJjnumber() {
        return Jjnumber;
    }

    public void setJjnumber(String jjnumber) {
        Jjnumber = jjnumber;
    }

    public String getDj() {
        return Dj;
    }

    public void setDj(String dj) {
        Dj = dj;
    }

    public String getTotalpirce() {
        return Totalpirce;
    }

    public void setTotalpirce(String totalpirce) {
        Totalpirce = totalpirce;
    }

    public String getHspirce() {
        return Hspirce;
    }

    public void setHspirce(String hspirce) {
        Hspirce = hspirce;
    }

    public String getSl() {
        return Sl;
    }

    public void setSl(String sl) {
        Sl = sl;
    }

    public String getSe() {
        return Se;
    }

    public void setSe(String se) {
        Se = se;
    }

    public String getJshj() {
        return Jshj;
    }

    public void setJshj(String jshj) {
        Jshj = jshj;
    }

    public String getJj() {
        return Jj;
    }

    public void setJj(String jj) {
        Jj = jj;
    }

    public String getKcdw() {
        return Kcdw;
    }

    public void setKcdw(String kcdw) {
        Kcdw = kcdw;
    }

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
