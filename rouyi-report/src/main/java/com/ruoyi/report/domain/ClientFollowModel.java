package com.ruoyi.report.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ClientFollowModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name = "Id")
    private String Id;
    @Excel(name = "客户备注")
    private String Bz;
    @Excel(name = "跟进时间")
    private String Gjdate;
    @Excel(name = "客户欠款")
    private String Balance;
    @Excel(name = "客户标识",readConverterExp = "0=正常,1=超时需跟进")
    private String Flag;
    @Excel(name = "客户状态")
    private String Status;
    @Excel(name = "所属销售")
    private String Ssxs;

    @Excel(name = "客户ID")
    private String Cid;

    @Excel(name = "跟进年月份")
    private String Conth;

    private String Cou;

    public String getConth() {
        return Conth;
    }

    public void setConth(String conth) {
        Conth = conth;
    }

    public String getCou() {
        return Cou;
    }

    public void setCou(String cou) {
        Cou = cou;
    }

    public String getMonth() {
        return Conth;
    }

    public void setMonth(String month) {
        this.Conth = month;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }



    public String getBz() {
        return Bz;
    }

    public void setBz(String bz) {
        Bz = bz;
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

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    @Override
    public String toString() {
        return "ClientFollowModel{" +
                "Id='" + Id + '\'' +
                ", Bz='" + Bz + '\'' +
                ", Gjdate='" + Gjdate + '\'' +
                ", Balance='" + Balance + '\'' +
                ", Flag='" + Flag + '\'' +
                ", Status='" + Status + '\'' +
                ", Ssxs='" + Ssxs + '\'' +
                ", Cid='" + Cid + '\'' +
                '}';
    }
}
