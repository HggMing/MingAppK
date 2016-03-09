package com.study.mingappk.userlogin.request;

public class ResultPacket {

    private boolean _IsError;
    private String _ResultCode;
    private String _Description;

    // 构造函数
    public ResultPacket() {
        _IsError = false;
        _ResultCode = "00";
        _Description = "操作成功";
    }
    public ResultPacket(boolean isError,String resultCode, String pDescription)
    {
        _IsError = isError;
        _ResultCode = resultCode;
        _Description = pDescription;
    }


    public boolean getIsError() {
        return this._IsError;
    }

    public void setIsError(boolean IsError) {
        this._IsError = IsError;
    }

    public String getResultCode() {
        return this._ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this._ResultCode = ResultCode;
    }

    public String getDescription() {
        return this._Description;
    }

    public void setDescription(String _Description) {
        this._Description = _Description;
    }

}
