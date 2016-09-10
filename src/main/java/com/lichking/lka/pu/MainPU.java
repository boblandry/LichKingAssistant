package com.lichking.lka.pu;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lichking.wcb.cmd.MessageTransCmd;
import com.lichking.wcb.constants.MessageConstants;

public class MainPU {

	public static void doProcessing(HttpServletRequest req,HttpServletResponse res){
		
		try {
			PrintWriter out = res.getWriter();
			
			Map<String, String> map = MessageTransCmd.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			
			String returnMsg = "";
			if(msgType.equals(MessageConstants.MESSAGE_TEXT)){
				returnMsg = MessageTransCmd.initText(toUserName, fromUserName, content);
			}else{
				returnMsg = MessageTransCmd.initText(toUserName, fromUserName, "sorry");
			}
			
			out.write(returnMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
