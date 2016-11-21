package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/11/16.
 */

public class UpdateAppBack {

    /**
     * name : 爱简单
     * version : 1
     * changelog : 初次上传版本
     * updated_at : 1475991910
     * versionShort : 1.6.3
     * build : 1
     * installUrl : http://download.fir.im/v2/app/install/57f9d66a959d690397000963?download_token=376b5dec227a01b6593c8ecc8e28fb3f
     * install_url : http://download.fir.im/v2/app/install/57f9d66a959d690397000963?download_token=376b5dec227a01b6593c8ecc8e28fb3f
     * direct_install_url : http://download.fir.im/v2/app/install/57f9d66a959d690397000963?download_token=376b5dec227a01b6593c8ecc8e28fb3f
     * update_url : http://fir.im/slove
     * binary : {"fsize":5600075}
     */

    private String name;
    private String version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 5600075
     */

    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
