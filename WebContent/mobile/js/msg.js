var id=$.getUrlVars("id");
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	$(".mui-content-padded").html(id);
	getMsgType();
});

//查询消息详情
function getMsgType() {
	ajaxPost(base+"/wxMsgType/query", {
		keyword:'',pageNumber:1,pageSize:100
	}, function(data) {
		$.each(data.list,function(id,item){
			$(".mui-table-view").append(' <li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-3">'+
					'<a href="../mobile/msgList.jsp?typeId='+item.id+'">'+
					'<img src="'+imageUrl+item.type_img+'">'+
                    '<div class="mui-media-body">'+item.type_name+'</div>'+
                    '</a></li>');
		});
	});
}