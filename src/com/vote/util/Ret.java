package com.vote.util;

public class Ret {

	private boolean success;
	private String msg;
	private Object data;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}
	
	// 添加错误，用于alertError
	public void addError(String message) {
		msg = message;
		success = false;
	}
	
	public void setData(Object data) {
		success = true;
		this.data = data;
	}

	public void success(String sucMsg){
		success = true;
		msg = sucMsg;
	}
	
	public void fail(String sucMsg){
		success = false;
		msg = sucMsg;
	}
}
