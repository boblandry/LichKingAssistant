package com.lichking.lka.pu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.lichking.lka.util.JSONTransUtil;
import com.lichking.lka.vo.LKAConstants;
import com.lichking.lka.vo.tuling.NewsResInfo;
import com.lichking.lka.vo.tuling.NewsResItem;
import com.lichking.lka.vo.tuling.RecipeItem;
import com.lichking.lka.vo.tuling.RecipeResInfo;
import com.lichking.lka.vo.tuling.ReqInfo;
import com.lichking.wcb.cmd.MessageTransCmd;
import com.lichking.wcb.cmd.RequestCmd;
import com.lichking.wcb.constants.MessageConstants;
import com.lichking.wcb.vo.message.NewsItem;
import com.lichking.wcb.vo.message.NewsMessage;

public class MessagePU {

	private static Logger logger = Logger.getLogger(MessagePU.class);
	
	public static String doMessageProcessing(Map<String,String> map){
		
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		//String msgType = map.get("MsgType");
		String content = map.get("Content");
		
		ReqInfo reqInfo = new ReqInfo(fromUserName,content);
		
		JSONObject jsonObject = JSONObject.fromObject(reqInfo);
		JSONObject resObject = RequestCmd.doPostStr(LKAConstants.REQ_URL, jsonObject.toString());
		
		logger.info(resObject.toString());
		String code = resObject.getString("code");
		logger.info("code:"+code);
		
		String returnMsg = content;
		
		if(code.equals(LKAConstants.TEXT_CODE)){
		
			String text = resObject.getString("text");
			returnMsg = MessageTransCmd.initText(toUserName, fromUserName, text);
		
		}else if(code.equals(LKAConstants.LINK_CODE)){
			
			String text = resObject.getString("text");
			text += "\r\n" + resObject.getString("url");
			returnMsg = MessageTransCmd.initText(toUserName, fromUserName, text);
			
		}else if(code.equals(LKAConstants.NEWS_CODE)){
			
			NewsResInfo newsResInfo = JSONTransUtil.jsonToNewsResInfo(resObject);
			NewsMessage newsMessage = NewsResToNewsMessage(newsResInfo,map);
			returnMsg = MessageTransCmd.newsMessageToXml(newsMessage);
			
		}else if(code.equals(LKAConstants.RECIPE_CODE)){
			
			RecipeResInfo recipeResInfo = JSONTransUtil.jsonToRecipeResInfo(resObject);
			NewsMessage newsMessage = RecipeResToNewsMessage(recipeResInfo,map);
			returnMsg = MessageTransCmd.newsMessageToXml(newsMessage);
			
		}else{
			logger.error(code);
			returnMsg = LKAConstants.ERROR_MSG;
		}
		
		return returnMsg;
		
	}
	
	private static NewsMessage NewsResToNewsMessage(NewsResInfo newsResInfo,Map<String,String> map){
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFromUserName(map.get("ToUserName"));
		newsMessage.setToUserName(map.get("FromUserName"));
		newsMessage.setMsgType(MessageConstants.MESSAGE_NEWS);
		
		List<NewsItem> news_list = new ArrayList<NewsItem>();
		List<NewsResItem> list = newsResInfo.getList();
		int count = 0;
		for(NewsResItem newsResItem : list){
			if(count >= 8){
				break;
			}
			NewsItem newsItem = new NewsItem();
			newsItem.setTitle(newsResItem.getArticle());
			newsItem.setDescription(newsResItem.getArticle() + "--" + newsResItem.getSource());
			newsItem.setPicUrl(newsResItem.getIcon());
			newsItem.setUrl(newsResItem.getDetailurl());
			news_list.add(newsItem);
			count++;
		}
		
		newsMessage.setArticleCount(news_list.size());
		newsMessage.setArticles(news_list);
		
		return newsMessage;
	}
	
	private static NewsMessage RecipeResToNewsMessage(RecipeResInfo recipeResInfo,Map<String,String> map){
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFromUserName(map.get("ToUserName"));
		newsMessage.setToUserName(map.get("FromUserName"));
		newsMessage.setMsgType(MessageConstants.MESSAGE_NEWS);
		
		List<NewsItem> news_list = new ArrayList<NewsItem>();
		List<RecipeItem> list = recipeResInfo.getList();
		int count = 0;
		for(RecipeItem recipeItem : list){
			if(count >= 8){
				break;
			}
			NewsItem newsItem = new NewsItem();
			newsItem.setTitle(recipeItem.getName());
			newsItem.setDescription(recipeItem.getInfo());
			newsItem.setPicUrl(recipeItem.getIcon());
			newsItem.setUrl(recipeItem.getDetailurl());
			news_list.add(newsItem);
			count++;
		}
		
		newsMessage.setArticleCount(news_list.size());
		newsMessage.setArticles(news_list);
		
		return newsMessage;
		
	}
	
}
