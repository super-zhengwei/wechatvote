package com.vote.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.vote.util.Utility;

public class DictDetail extends Model<DictDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6893855923286840781L;
	
	public static final DictDetail me = new DictDetail();
	
	public Page<DictDetail> paginate(String keyword, int pageNumber, int pageSize){
		StringBuffer sql = new StringBuffer();
    	sql.append(" from sys_dict_detail where 1=1 ");
    	if(!Utility.isEmpty(keyword)){
    		sql.append(" and (dict_type like '%"+keyword+"%' or dict_key like '%"+keyword+"%' "
    				+ " or dict_value like '%"+keyword+"%')");
    	}
    	sql.append(" order by dict_type");
        return paginate(pageNumber, pageSize, "select * ",
                sql.toString());
	}
	
	public List<DictDetail> query(String dictType){
		return DictDetail.me.find("select * from sys_dict_detail where dict_type = ?", dictType);
	}
	
	public Map<String,String> getDictMap(String index){
		List<DictDetail> lst = this.query(index);
		if(lst!=null&&0<lst.size()){
			Map<String,String> map = new HashMap<String,String>(lst.size());
			for(int i=0;i<lst.size();i++){
				map.put(lst.get(i).getStr("dict_key"), lst.get(i).getStr("dict_value"));
			}
			return map;
		}
		return null;
	}
}
