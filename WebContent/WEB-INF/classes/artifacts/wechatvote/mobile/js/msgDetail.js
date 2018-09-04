var id=$.getUrlVars("id");
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
	getMsg();
});

//查询消息详情
function getMsg(){
	ajaxPost(base+"/wxMsg/getMsg",{id:id},function(data){
		if(data){
			$(".mui-title").html(data.msg_title);
			$(".mui-content-padded").html(data.msg_content);
		}else{
			
		}
	});
}