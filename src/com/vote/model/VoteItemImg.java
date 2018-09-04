
package com.vote.model;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;


@SuppressWarnings("serial")
public class VoteItemImg extends Model<VoteItemImg>
{
	static Log log = Log.getLog(VoteItemImg.class);
    public static final VoteItemImg dao = new VoteItemImg();

    public List<VoteItemImg> getList(int itemId){
    	return find("select * from vote_item_img where item_id=?",itemId);
    }
    
    public void deleteByItemId(int itemId) {
    	Db.update("delete from vote_item_img where item_id=?",itemId);
    }
}
