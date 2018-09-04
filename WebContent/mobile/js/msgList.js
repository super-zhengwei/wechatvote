var typeId=$.getUrlVars("typeId");
mui.init({
	subpages:[{
		url:'msgListSub.jsp?typeId='+typeId,
		id:'msgListSub.jsp',
		styles:{
			top: '45px',
			bottom: '0px',
		}
	}]
});
/**
 *初始化、添加监听 
 */
mui.ready(function() {
	getMsgType();
});

//查询消息详情
function getMsgType() {
	ajaxPost(base+"/wxMsgType/getMsgType", {id:typeId}, function(data) {
		if(data){
			$(".mui-title").html(data.type_name);
		}
	});
}