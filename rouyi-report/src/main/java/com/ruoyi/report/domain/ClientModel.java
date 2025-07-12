package com.ruoyi.report.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.product.domain.ProOrderModel;
import com.ruoyi.product.domain.ProductModel;
import com.ruoyi.product.mapper.ProOrdertMapper;

import java.util.List;

public class ClientModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String Id;
    @Excel(name = "所属销售")
    private String Ssxs;
    @Excel(name = "客户名称")
    private String Name;
    @Excel(name = "客户地址")
    private String Address;
    @Excel(name = "客户电话")
    private String Phone;
    @Excel(name = "客户邮箱")
    private String Email;
    @Excel(name = "时间")
    private String Date;
    @Excel(name = "客户备注")
    private String Bz;
    @Excel(name = "客户状态")
    private String Status;
    @Excel(name = "客户等级")
    private String Xydj;
    @Excel(name = "客户属性")
    private String Type;
    //临时状态
    private String Sex;
    //临时状态
    private String Gjdate;
    //临时状态
    private String Balance;
    //临时状态
    private String Flag;
    //临时状态
    private String receiptnumber;

    private String pay;

    //临时状态
    private String Cou;
    //临时状态
    private String Oid;

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    /**
     * 商品信息
     */
    private List<ProOrderModel> goods;

    public List<ProOrderModel> getGoods() {
        return goods;
    }

    public void setGoods(List<ProOrderModel> goods) {
        this.goods = goods;
    }

    public String getReceiptnumber() {
        return receiptnumber;
    }

    public void setReceiptnumber(String receiptnumber) {
        this.receiptnumber = receiptnumber;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCou() {
        return Cou;
    }

    public void setCou(String cou) {
        Cou = cou;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getXydj() {
        return Xydj;
    }

    public void setXydj(String xydj) {
        Xydj = xydj;
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

    public String getGjdate() {
        return Gjdate;
    }

    public void setGjdate(String gjdate) {
        Gjdate = gjdate;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSsxs() {
        return Ssxs;
    }

    public void setSsxs(String ssxs) {
        Ssxs = ssxs;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Xydj='" + Xydj + '\'' +
                ", Bz='" + Bz + '\'' +
                ", Date='" + Date + '\'' +
                ", Gjdate='" + Gjdate + '\'' +
                ", Balance='" + Balance + '\'' +
                ", Flag='" + Flag + '\'' +
                ", Status='" + Status + '\'' +
                ", Ssxs='" + Ssxs + '\'' +
                '}';
    }
}
