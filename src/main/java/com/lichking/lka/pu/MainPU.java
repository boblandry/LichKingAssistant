package com.lichking.lka.pu;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.lichking.wcb.cmd.MessageTransCmd;
import com.lichking.wcb.constants.MessageConstants;

public class MainPU {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MainPU.class);
	
	public static void doProcessing(HttpServletRequest req,HttpServletResponse res){
		
		try {
			PrintWriter out = res.getWriter();
			
			Map<String, String> map = MessageTransCmd.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			
			String returnMsg = "";
			if(msgType.equals(MessageConstants.MESSAGE_TEXT)){
			
				returnMsg = MessagePU.doMessageProcessing(map);
				
			}else{
				
				returnMsg = MessageTransCmd.initText(toUserName, fromUserName, "sorry");
				
			}
			//logger.info(returnMsg);
			out.write(returnMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
