<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>菜单编辑</title>
<style>
	@media (max-width: 767px){
		.form-horizontal .form-label {
	    	text-align: right;
		}
		.responsive .input-text, .btn, .responsive .input-text.size-M, .responsive .btn.size-M {
		    font-size: 14px;
		    height: 31px;
		}
	}
</style>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>上级菜单：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="parent_id" id="parent_id">
				</select>
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>菜单名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="menu_name" name="menu_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>菜单排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="sort" name="sort">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">菜单地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="menu_url" name="menu_url" disabled>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">菜单图标：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="menu_icon" name="menu_icon" style="display:none;">
				<i id="icon" class="Hui-iconfont" style="font-size: 18px;"></i>
				<a href="javascript:void(0);" class="btn btn-primary radius" id="selectIcon">选择图标</a>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red"></span>备&#12288;&#12288;注：</label>
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
	getParentMenu();
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			menu_name:{
				required:true,
				minlength:2,
				maxlength:10
			},
			sort:{
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
	$('#parent_id').change(function(){ 
		if($(this).val()!=""){
			$("#menu_url").attr("disabled",false);
		}else{
			$("#menu_url").attr("disabled",true);
		}
	});
	
	$('#selectIcon').on('click',function(){
		var index = layer.open({
			type: 2,
			title:"点击选择图标",
			content:"selectIcon.jsp"
		});
		layer.full(index);
	});
});

function getParentMenu(){
	ajaxPost("${base}/sysMenu/getParentMenu",{},function(data){
		$("#parent_id").empty();
		$("#parent_id").append('<option value="">作为一级菜单</option>');
		$.each(data,function(id,item){
			$("#parent_id").append('<option value="'+item.id+'">'+item.menu_name+'</option>');
		});
		if(id){
			getMenu();
		}
	});
}

function getMenu(){
	ajaxPost("${base}/sysMenu/getMenu",{id:id},function(data){
		if(data){
			$.each(data,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
			if(data.parent_id){
				$("#menu_url").attr("disabled",false);
			}
			if(data.menu_icon){
				$("#icon").html(data.menu_icon);
			}
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
	ajaxPost("${base}/sysMenu/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.$('#queryBtn').click();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}

function getIcon(icon){
	$("#icon").html(icon);
	$("#menu_icon").val(icon);
}
</script> 
</body>
</html>