package com.lichking.lka.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lichking.lka.pu.MainPU;
import com.lichking.wcb.cmd.CheckTokenCmd;

@Controller
public class WCMainCtrl {

	Logger logger = Logger.getLogger(WCMainCtrl.class);
	
	@RequestMapping(value="/WeChatPortal",method=RequestMethod.GET)
	public void WeChatPortal_GET(HttpServletRequest req,HttpServletResponse res){
		
		logger.info("checking tokening with wechat server...");
		CheckTokenCmd.checkToken(req, res);
		
	}
	
	@RequestMapping(value="/WeChatPortal",method=RequestMethod.POST)
	public void WeChatPortal_POST(HttpServletRequest req,HttpServletResponse res){
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		res.setCharacterEncoding("UTF-8");
		MainPU.doProcessing(req, res);
		
	}
	
}
