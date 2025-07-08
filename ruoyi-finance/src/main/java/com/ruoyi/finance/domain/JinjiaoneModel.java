package com.ruoyi.finance.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class JinjiaoneModel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name = "文件ID")
    private String Id;
    @Excel(name = "文件名称")
    private String Filename;
    @Excel(name = "文件路径")
    private String Fileurl;
    @Excel(name = "上传说明")
    private String Bz;
    @Excel(name = "上传时间")
    private String Date;
    @Excel(name = "上传人姓名")
    private String uploadname;
    @Excel(name = "当前登录用户 id")
    private String Userid;

    @Excel(name = "部门id")
    private String Did;

    @Excel(name = "部门名字")
    private String Dname;

    private String Cou;

    public String getCou() {
        return Cou;
    }

    public void setCou(String cou) {
        Cou = cou;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFileurl() {
        return Fileurl;
    }

    public void setFileurl(String fileurl) {
        Fileurl = fileurl;
    }

    public String getUploadname() {
        return uploadname;
    }

    public void setUploadname(String uploadname) {
        this.uploadname = uploadname;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }
}
