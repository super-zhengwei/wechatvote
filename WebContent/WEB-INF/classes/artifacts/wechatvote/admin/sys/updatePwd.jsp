<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>修改密码</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>旧密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" value="" placeholder="" id="oldPassword" name="oldPassword">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>新密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" value="" placeholder="" id="newPassword" name="newPassword">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" value="" placeholder="" id="entryPassword" name="entryPassword">
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
$(document).ready(function(){
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	
	$("#form-member-add").validate({
		rules:{
			oldPassword:{
				required:true
			},
			newPassword:{
				required:true
			},
			entryPassword:{
				required:true,
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

function submit(){
	if($.trim($("#newPassword").val())!=$.trim($("#entryPassword").val())){
		layer.msg('确认密码不一致');
		return;
	}
	ajaxPost("${base}/sysUser/updatePwd",{
		oldPassword:$.trim($("#oldPassword").val()),
		newPassword:$.trim($("#newPassword").val())
	},function(data){
		if(data.success){
			layer.msg(data.msg,{shift: -1},function(){
				layer_close();
			});
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>