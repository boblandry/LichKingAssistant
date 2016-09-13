package com.lichking.lka.vo.tuling;

import com.lichking.lka.vo.LKAConstants;

/**
 * 请求tuling api 封装对象
 * @author LichKing
 *
 */
public class ReqInfo {

	private String key;
	
	private String info;
	
	private String userid;
	
	private String loc;

	public ReqInfo(String userid, String info){
		
		this.info = info;
		this.userid = userid;
		this.key = LKAConstants.API_KEY;
		
	}
	
	public ReqInfo(){}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	
	
}
