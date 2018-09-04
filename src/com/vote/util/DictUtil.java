package com.vote.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class DictUtil {
	
	public static StringBuffer generateSelectStr(StringBuffer results,Map<String,String> dictMap,String name,
			String id,String styleClass,String style,String dictKey,boolean limitValue){
    	results.append("<select name=\"");   
        results.append(name);    
        results.append("\" id=\""); 
        results.append(id); 
        results.append("\"");
        if(!StringUtils.isBlank(styleClass)){
	        results.append(" class=\""); 
	        results.append(styleClass); 
	        results.append("\"");
        }
        if(!StringUtils.isBlank(style)){
	        results.append(" style=\""); 
	        results.append(style); 
	        results.append("\"");
        }
        results.append(">");
        //循环生成<option>
        //		<option value="未出行">未出行</option>; 
        Set<String> keySet = dictMap.keySet();
		Iterator<String> iter = keySet.iterator();
		while(iter.hasNext()){
		   String key = iter.next();
		   if(limitValue){
			   //如果有限制的话
			   if(dictKey.compareTo(key)>0)//实际的值大于内存中某个键值，则小于实际值的不需要渲染
				   continue;
			   else{
				   results.append("<br/><option value=\"");   
			       results.append(key);    
			       results.append("\""); 
			       if(dictKey.equals(key))
				       results.append(" selected>");
			       else
			    	   results.append(">");
			       results.append(dictMap.get(key)); 
			       results.append("</option>");
			   }
		   }else{
			   results.append("<br/><option value=\"");   
		       results.append(key);    
		       results.append("\""); 
		       if(dictKey.equals(key))
			       results.append(" selected>");
		       else
		    	   results.append(">");
		       results.append(dictMap.get(key)); 
		       results.append("</option>");
		   }
		} 
        results.append("<br/></select>");
    	return results;
    }
	public static void main(String args[]){
		Map<String,String> orderStatusMap = new TreeMap<String,String>(); 
		orderStatusMap.put("20", "完成");
		orderStatusMap.put("99", "取消");
		orderStatusMap.put("00", "新建");
		orderStatusMap.put("10", "接受");
		
		Set<String> keySet = orderStatusMap.keySet();
		Iterator<String> iter = keySet.iterator();
		String c = "";
		while(iter.hasNext()){
		   String key = iter.next();
		   System.out.println("c:"+key+"="+c.compareTo(key));
		   System.out.println(key+":"+orderStatusMap.get(key));
		} 
//		StringBuffer sb = new StringBuffer();
//		sb = generateSelectStr(sb,orderStatusMap,"orderStatus","orderStatus",null,null,"",true);		
//		System.out.println("StringBuffer:"+sb.toString());
	}

}
