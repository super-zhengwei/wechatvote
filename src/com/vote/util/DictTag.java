package com.vote.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;


public class DictTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String dictType;//字典类型，作为在内存中的keyCode;通过这个Type获取某个字典键值对；
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictKey() {
		return dictKey;
	}
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	public String getRender() {
		return render;
	}
	public void setRender(String render) {
		this.render = render;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String dictKey;//服务端响应的值，一定要输入，但可以为空字符串；不为空时，通过这个dictKey作为上面dictType获取到的字典键值对中key为dictKey的value; 
	private String render;//渲染成什么标签(Select或者是lable)
	private boolean  limitValue;//是否需要限制当前值(dictKey)以下的值不渲染出来;这个的值是"true"，如果为空则为false;i
	public boolean  getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(boolean  limitValue) {
		this.limitValue = limitValue;
	}
	private String name; //标签的name;
	private String id;//标签的Id
	private String styleClass;
	private String style;
	private boolean top;//是否需要有头条比如<option value="xx"></option>
	private String defaultVal;
	

    public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	@Override  
    public int doEndTag() throws JspException {  
    	//每个数据字典都内存中存在，key是DictUtil的常量
    	Map<String,String> dictMap = (Map<String,String>)pageContext.getServletContext().getAttribute(this.getDictType());
        try {  
            StringBuffer results = new StringBuffer(""); 
            
            if(!StringUtils.isBlank(this.getRender())){
            	if(StringUtils.equals(this.getRender(), "label")){  
                    results.append("<input type=\"");  
                    results.append("hidden\" name=\"");  
                    results.append(getName());  
                    results.append("\" id=\""); 
                    results.append(this.getId()); 
                    results.append("\" value=\"");  
                    results.append(this.getDictKey()==null?"":this.getDictKey()); 
                    results.append("\">");
                    results.append("<label>"); 
                    results.append(dictMap.containsKey(this.getDictKey())==false?"":dictMap.get(this.getDictKey())); 
                    results.append("</label>"); 
            	}
            	if(StringUtils.equals(this.getRender(), "select")){
            		results = generateSelectStr(results,dictMap);
            	}
            	if(StringUtils.equals(this.getRender(), "radio")){
            		results = generateRadioStr(results,dictMap);
            	}
            }
            else{
            	return -1;
            } 
            pageContext.getOut().print(results.toString());  
            pageContext.getOut().flush();  
        } catch (IOException ex) {  
            throw new JspTagException("错误");  
        }  
        return EVAL_PAGE;  
    }  
    
    private StringBuffer generateSelectStr(StringBuffer results,Map<String,String> dictMap){
    	results.append("<select name=\"");   
        results.append(getName());    
        results.append("\" id=\""); 
        results.append(this.getId()); 
        results.append("\"");
        if(!StringUtils.isBlank(this.getStyleClass())){
	        results.append(" class=\""); 
	        results.append(this.getStyleClass()); 
	        results.append("\"");
        }
        if(!StringUtils.isBlank(this.getStyle())){
	        results.append(" style=\""); 
	        results.append(this.getStyle()); 
	        results.append("\"");
        }
        results.append(">");
        //循环生成<option>
        //		<option value="未出行">未出行</option>; 
        if(this.top){
        	results.append("<option value=\"\">请选择</option>");
        }
        Set<String> keySet = dictMap.keySet();
		Iterator<String> iter = keySet.iterator();
		while(iter.hasNext()){
		   String key = iter.next();
		   if(this.limitValue){
			   //如果有限制的话
			   if(this.getDictKey().compareTo(key)>0)//实际的值大于内存中某个键值，则小于实际值的不需要渲染
				   continue;
			   else{
				   results.append("<br/><option value=\"");   
			       results.append(key);    
			       results.append("\""); 
			       if(this.getDictKey().equals(key))
				       results.append("selected>");
			       else
			    	   results.append(">");
			       results.append(dictMap.get(key)); 
			       results.append("</option>");
			   }
		   }else{
			   results.append("<br/><option value=\"");   
		       results.append(key);    
		       results.append("\""); 
		       if(this.getDictKey().equals(key))
			       results.append("selected>");
		       else
		    	   results.append(">");
		       results.append(dictMap.get(key)); 
		       results.append("</option>");
		   }
		} 
        results.append("<br/></select>");
    	return results;
    }
    
    private StringBuffer generateRadioStr(StringBuffer results,Map<String,String> dictMap){
        Set<String> keySet = dictMap.keySet();
		Iterator<String> iter = keySet.iterator();
		int i=1;
		while(iter.hasNext()){
			String key = iter.next();
			results.append("<div class=\"radio-box\"><input type=\"radio\" name=\"");
			results.append(getName());
			results.append("\" id=\"");
			results.append(this.getId());
			results.append(i);
			results.append("\" value=\"");
			results.append(key);
			results.append("\"");
			if (!StringUtils.isBlank(this.getStyleClass())) {
				results.append(" class=\"");
				results.append(this.getStyleClass());
				results.append("\"");
			}
			if (!StringUtils.isBlank(this.getStyle())) {
				results.append(" style=\"");
				results.append(this.getStyle());
				results.append("\"");
			}
			if(StringUtils.isBlank(this.getDictKey())){
				if(!StringUtils.isBlank(this.getDefaultVal())){
					if(key.equals(this.getDefaultVal())){
						results.append(" checked");
					}
				}
			}
			if(this.getDictKey().equals(key)){
				results.append(" checked");
			}
			results.append(">");
			results.append("<label for=\"");
			results.append(this.getId());
			results.append(i);
			results.append("\">");
			results.append((String)dictMap.get(key));
			results.append("</label></div>");
			i++;
		} 
    	return results;
    }
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
}
