<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<title>会员编辑</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="username" name="username" placeholder="请填写姓名">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>手机号码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="mobile" name="mobile" placeholder="请填写手机号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>用户类型：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="select-box">
					<dict:dm dictType="APPLY_SOURCE" dictKey="" id="user_type" name="user_type" render="select" styleClass="select"/>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>所属城市：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="select-box">
					<dict:dm dictType="REGION" dictKey="" id="city" name="city" render="select" styleClass="select"/>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>微信公众号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="openid" name="openid" placeholder="请填写微信公众号openid">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">昵称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="nickname" name="nickname" placeholder="选填">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red"></span>分销码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="dis_num" name="dis_num" placeholder="选填">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>接收订单消息：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input type="radio" id="need_send1" name="need_send" value="1" checked>
					<label for="need_send1">是</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="need_send0" name="need_send" value="0">
					<label for="need_send0">否</label>
				</div>
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
var id=$.getUrlVars("id");
$(document).ready(function(){
	if(id){
		getUser();
	}
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			username:{
				required:true
			},
			mobile:{
				required:true,
				isMobile:true
			},
			openid:{
				required:true
			},
			dis_num:{
				digits:true
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
	ajaxPost("${base}/users/getUser",{id:id},function(data){
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
	var param = $('#form-member-add').serialize();
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	ajaxPost("${base}/users/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.query();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>