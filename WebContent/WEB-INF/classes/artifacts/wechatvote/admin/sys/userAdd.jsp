<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%> 
<!DOCTYPE html>
<title>用户编辑</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>登录账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="user_name" name="user_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>用户姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="real_name" name="real_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>手机号码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="mobile" name="mobile">
			</div>
		</div>
		<div class="row foot cl">
			<div class="col-xs-12 col-sm-9 col-sm-offset-2">
				<input class="btn btn-primary radius" type="submit" value="提交">
				<input class="btn btn-danger radius" id="closeBtn" type="button" value="关闭">
			</div>
		</div>
	</form>
</article>

<script type="text/javascript">
var id=$.getUrlVars("id");
$(document).ready(function(){
	if(id){
		getUser();
	}
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-member-add").validate({
		rules:{
			user_name:{
				required:true,
				minlength:2,
				maxlength:10
			},
			real_name:{
				required:true,
				minlength:2,
				maxlength:10
			},
			mobile:{
				required:true,
				isMobile:true,
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

function getUser(){
	ajaxPost("${base}/sysUser/getUser",{id:id},function(data){
		if(data){
			$.each(data,function(key,value){
				if(key=="need_send"){
					$('#need_send'+value).iCheck('check');
				}else if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
		}
	});
}
function submit(){
	var param;
	if(id){
		param=$.param({'id':id}) + '&' + $('#form-member-add').serialize();
	}else{
		param=$('#form-member-add').serialize();
	}
	ajaxPost("${base}/sysUser/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.$('#queryBtn').click();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>