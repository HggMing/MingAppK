package com.study.mingappk.userlogin.request;

public class ResultPacket
{
	// / <summary>
	// / 版本信息
	// / </summary>
	private final String VERSION	= "1.0";
	private boolean			_IsError;
	private String _ResultCode;
	private String _Description;

	// 构造函数
	public ResultPacket()
	{
		_IsError = false;
		_ResultCode = "00";
		_Description = "操作成功";
	}

	// / <summary>
	// / 构�?函数
	// / </summary>
	// / <param name="pIsError"></param>
	public ResultPacket(boolean pIsError)
	{
		_IsError = pIsError;
		_ResultCode = "99";
		_Description = "操作失败";
	}

	// / <summary>
	// / 构�?函数
	// / </summary>
	public ResultPacket(String pDescription)
	{
		_IsError = true;
		_ResultCode = "99";
		_Description = pDescription;
	}
	
	// / <summary>
	// / 构�?函数
	// / </summary>
	public ResultPacket(boolean isError, String resultCode, String pDescription)
	{
		_IsError = isError;
		_ResultCode = resultCode;
		_Description = pDescription;
	}

	public boolean getIsError()
	{
		return this._IsError;
	}

	public void setIsError(boolean IsError)
	{
		this._IsError = IsError;
	}

	public String getResultCode()
	{
		return this._ResultCode;
	}

	public void setResultCode(String ResultCode)
	{
		this._ResultCode = ResultCode;
	}

	public String getDescription()
	{
		return this._Description;
	}

	public void setDescription(String _Description)
	{
		this._Description = _Description;
	}

}
