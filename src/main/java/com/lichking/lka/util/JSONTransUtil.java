package com.lichking.lka.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lichking.lka.vo.tuling.NewsResItem;
import com.lichking.lka.vo.tuling.NewsResInfo;
import com.lichking.lka.vo.tuling.RecipeItem;
import com.lichking.lka.vo.tuling.RecipeResInfo;

/**
 * json转换工具
 * @author LichKing
 *
 */
public class JSONTransUtil {

	public static NewsResInfo jsonToNewsResInfo(JSONObject json){
		
		Map<String, Class<NewsResItem>> classMap = new HashMap<String, Class<NewsResItem>>();
		classMap.put("list", NewsResItem.class);
		NewsResInfo newsResInfo = (NewsResInfo)JSONObject.toBean(json, NewsResInfo.class, classMap);
		return newsResInfo;
		
	}
	
public static RecipeResInfo jsonToRecipeResInfo(JSONObject json){
		
		Map<String, Class<RecipeItem>> classMap = new HashMap<String, Class<RecipeItem>>();
		classMap.put("list", RecipeItem.class);
		RecipeResInfo recipeResInfo = (RecipeResInfo)JSONObject.toBean(json, RecipeResInfo.class, classMap);
		return recipeResInfo;
		
	}
	
}
