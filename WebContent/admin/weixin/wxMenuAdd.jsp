<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>菜单编辑</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>上级菜单：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="parent_id" id="parent_id">
				</select>
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>菜单类型：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="type" id="type">
					<option value="view">打开页面</option>
					<option value="click">发送信息</option>
				</select>
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>菜单名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>菜单排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="sort" name="sort">
			</div>
		</div>
		<div class="row cl" id="urlRow">
			<label class="form-label col-xs-3 col-sm-2">菜单地址：</label>
			<div class="formControls col-xs-5 col-sm-7">
				<input type="text" class="input-text" id="url" name="url">
			</div>
			<div class="formControls col-xs-3 col-sm-2">
				<a href="javascript:void(0);" onclick="selectMsg('1')" class="btn btn-primary radius" id="selectMsg" style="float:right;">选择图文消息</a>
			</div>
		</div>
		<div class="row cl" id="msgRow" style="display:none;">
			<div class="col-xs-11 col-sm-9 col-sm-offset-2">
				<div class="cl pd-5 bg-1 bk-gray formControls ">
					<span class="l"> <a href="javascript:;"
						onclick="selectMsg('2')" class="btn btn-primary radius"><i
							class="Hui-iconfont">&#xe600;</i>选择图文消息</a>
					</span>
				</div>
				<table class="table table-border table-bordered table-bg" id="table1">
					<thead>
						<tr class="text-c" id="tableTitle">
							<th width="90">标题</th>
							<th width="90">链接</th>
							<th width="30">排序</th>
							<th width="30">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
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
	getParentMenu();
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			name:{
				required:true,
				minlength:2,
				maxlength:8
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
	$('#type').change(function(){ 
		if($(this).val()=="click"){
			$("#msgRow").show();
			$("#urlRow").hide();
		}else{
			$("#msgRow").hide();
			$("#urlRow").show();
		}
	});
});


function selectMsg(flag){
	layer_show('选择图文消息','wxMsg.jsp?flag='+flag,'600','400');
}
function getParentMenu(){
	ajaxPost("${base}/wxMenu/getParentMenu",{},function(data){
		$("#parent_id").empty();
		$("#parent_id").append('<option value="">作为一级菜单</option>');
		$.each(data,function(id,item){
			$("#parent_id").append('<option value="'+item.id+'">'+item.name+'</option>');
		});
		if(id){
			getMenu();
		}
	});
}

function delMsg(obj){
	$(obj).parents("tr").remove();
}
function getMenu(){
	ajaxPost("${base}/wxMenu/getMenu",{id:id},function(data){
		if(data){
			$.each(data.menu,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
			if(data.menu.type=="click"){
				$("#msgRow").show();
				$("#urlRow").hide();
				if(data.list){
					var html="";
					$.each(data.list,function(id,item){
						html +='<tr class="text-c">';
						html +='<td class="hide">'+item.id+'</td>';
						html +='<td>'+item.msg_title+'</td>';
						html +='<td>'+item.msg_url+'</td>';
						html +='<td><input type="text" class="input-text text-c" value="'+item.sort+'"></td>';
						html +='<td class="td-manage f-14">';
						html +=	'<a title="删除" href="javascript:;" onclick="delMsg(this)" class="ml-5" style="text-decoration:none">';
						html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
						html +=	'</a>';
						html +='</td>';
						html +='</tr>';
					});
					$("#table1 tbody").append(html);
				}
			}else{
				$("#msgRow").hide();
				$("#urlRow").show();
			}
		}
	});
}
function submit(){
	var param = $('#form-member-add').serialize();
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	if($("#type").val()=="click"){
		var msgIds=[];
		$("#table1 tbody tr").each(function(){
			var obj = new Object();
			obj.msgId=$(this).find('.hide').html();
			obj.sort=$(this).find('.input-text').val();
			msgIds.push(obj);
		});
		param +='&' +$.param({'msgIds':JSON.stringify(msgIds)});
	}
	ajaxPost("${base}/wxMenu/save",param,function(data){
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