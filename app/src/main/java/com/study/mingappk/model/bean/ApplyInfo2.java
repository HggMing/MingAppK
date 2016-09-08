package com.study.mingappk.model.bean;

import java.io.File;

/**
 * 店长申请人填写信息，用于本地化
 * Created by Ming on 2016/6/23.
 */
public class ApplyInfo2 {
    private String headUrl;//用户头像地址
    private String showName;//昵称
    private String showName2;//账号

    private String name;//姓名
    private String sex;//性别
    private String brithday;//生日
    private String phone;//手机号
    private String education;//学历
    private String villageName;//申请店长的村名

    public String getVillageId() {
        return villageId;
    }

    private String villageId;//申请店长的村id
    private File file1;//身份证正面照
    private File file2;//身份证反面照
    private String otherImagePath;//其他照片地址

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowName2() {
        return showName2;
    }

    public void setShowName2(String showName2) {
        this.showName2 = showName2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getOtherImagePath() {
        return otherImagePath;
    }

    public void setOtherImagePath(String otherImagePath) {
        this.otherImagePath = otherImagePath;
    }


    public ApplyInfo2(String headUrl, String showName, String showName2,
                      String name, String sex, String brithday, String phone,
                      String education, String villageName,String villageId, File file1,
                      File file2, String otherImagePath) {
        this.headUrl = headUrl;
        this.showName = showName;
        this.showName2 = showName2;
        this.name = name;
        this.sex = sex;
        this.brithday = brithday;
        this.phone = phone;
        this.education = education;
        this.villageName = villageName;
        this.villageId = villageId;
        this.file1 = file1;
        this.file2 = file2;
        this.otherImagePath = otherImagePath;
    }
}
