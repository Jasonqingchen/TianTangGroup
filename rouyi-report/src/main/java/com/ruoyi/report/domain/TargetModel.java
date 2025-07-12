package com.ruoyi.report.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.product.domain.ProOrderModel;

import java.util.List;

public class TargetModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String Id;
    @Excel(name = "时间")
    private String Date;
    @Excel(name = "中方销售")
    private String Salesman;
    @Excel(name = "代理")
    private String Agentname;
    @Excel(name = "代理 ID")
    private String Salesmanid;
    @Excel(name = "代理编码")
    private String Agentcode;
    @Excel(name = "客户姓名")
    private String Customer;
    @Excel(name = "客户编码")
    private String Customercode;
    @Excel(name = "客户所属区域")
    private String Cusplace;
    @Excel(name = "销售目标")
    private String Target;

    @Excel(name = "本期应收")
    private String Bqys;

    @Excel(name = "本期收款")
    private String Bqsk;

    @Excel(name = "期初余额")
    private String Qcye;

    @Excel(name = "期末余额")
    private String Qmye;

    @Excel(name = "目标销售车数")
    private String Mbxscs;

    @Excel(name = "实际销售车数")
    private String Sjxscs;

    @Excel(name = "当前进度")
    private String Dqjd;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSalesman() {
        return Salesman;
    }

    public void setSalesman(String salesman) {
        Salesman = salesman;
    }

    public String getAgentname() {
        return Agentname;
    }

    public void setAgentname(String agentname) {
        Agentname = agentname;
    }

    public String getSalesmanid() {
        return Salesmanid;
    }

    public void setSalesmanid(String salesmanid) {
        Salesmanid = salesmanid;
    }

    public String getAgentcode() {
        return Agentcode;
    }

    public void setAgentcode(String agentcode) {
        Agentcode = agentcode;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getCustomercode() {
        return Customercode;
    }

    public void setCustomercode(String customercode) {
        Customercode = customercode;
    }

    public String getCusplace() {
        return Cusplace;
    }

    public void setCusplace(String cusplace) {
        Cusplace = cusplace;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getBqys() {
        return Bqys;
    }

    public void setBqys(String bqys) {
        Bqys = bqys;
    }

    public String getBqsk() {
        return Bqsk;
    }

    public void setBqsk(String bqsk) {
        Bqsk = bqsk;
    }

    public String getQcye() {
        return Qcye;
    }

    public void setQcye(String qcye) {
        Qcye = qcye;
    }

    public String getQmye() {
        return Qmye;
    }

    public void setQmye(String qmye) {
        Qmye = qmye;
    }

    public String getMbxscs() {
        return Mbxscs;
    }

    public void setMbxscs(String mbxscs) {
        Mbxscs = mbxscs;
    }

    public String getSjxscs() {
        return Sjxscs;
    }

    public void setSjxscs(String sjxscs) {
        Sjxscs = sjxscs;
    }

    public String getDqjd() {
        return Dqjd;
    }

    public void setDqjd(String dqjd) {
        Dqjd = dqjd;
    }
}
