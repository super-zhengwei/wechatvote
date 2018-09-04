<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<title>添加选手</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>分组名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="group_name" name="group_name" placeholder="请填写分组名称">
				<input type="hidden" class="input-text" id="vote_id" name="vote_id">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red"></span>备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="remark" name="remark" placeholder="请填写备注">
			</div>
		</div>
		<div class="row foot cl">
			<div class="col-xs-12 col-sm-9 col-sm-offset-2">
				<input class="btn btn-primary radius" type="submit" value="提交">
				<input class="btn btn-danger radius" id="closeBtn" type="button" value="关闭">
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
var id=$.getUrlVars("groupId");
var voteId=$.getUrlVars("voteId");
$(document).ready(function(){
	$("#vote_id").val(voteId);
	if(id){
		getGroup();
	}
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			group_name:{
				required:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			submit()
		}
	});
});
function getGroup(){
	ajaxPost("${base}/voteGroup/getGroup",{id:id},function(json){
		if(json.success){
			$.each(json.data,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
		}
	});
}

function submit(){
	var param = $('#form-member-add').serialize();
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	ajaxPost("${base}/voteGroup/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.getGroup();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>