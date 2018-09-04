<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<!DOCTYPE html>
<title>角色编辑</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="role_name" name="role_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red"></span>备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="remark" name="remark">
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
		getRole();
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
			role_name:{
				required:true,
				minlength:2,
				maxlength:10
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

function getRole(){
	ajaxPost("${base}/sysRole/getRole",{id:id},function(data){
		if(data){
			$.each(data,function(key,value){
				if($('#'+key)){
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
	ajaxPost("${base}/sysRole/submit",param,function(data){
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