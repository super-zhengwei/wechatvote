<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>用户编辑</title>
<style>
	.panel-body{
		padding:0;
		overflow:auto;
	}
	th:first-child, td:first-child {
	    border-right: 1px solid #ddd;
	}
</style>
</head>
<body>
<article class="page-container">
	<div class="row cl">
		<div class="col-xs-5 col-sm-5">
			<div class="panel panel-default">
				<div class="panel-header">未选角色</div>
				<div class="panel-body">
					<table class="table table-border table-hover table-bg" id="table1">
						<thead>
							<tr class="text-c">
								<th width="25"><input type="checkbox" name="" value=""></th>
								<th>角色名称</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-xs-2 col-sm-2 text-c" style="margin-top: 175px;">
			<input class="btn btn-primary radius" id="addBtn" type="button" value="添加">
			<input class="btn btn-danger radius" id="delBtn" type="button" value="移除" style="margin-top:25px;">
		</div>
		<div class="col-xs-5 col-sm-5">
			<div class="panel panel-default">
				<div class="panel-header">已选角色</div>
				<div class="panel-body">
					<table class="table table-border table-hover table-bg" id="table2">
						<thead>
							<tr class="text-c">
								<th width="25"><input type="checkbox" name="" value=""></th>
								<th>角色名称</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</article>

<script type="text/javascript">
var id=$.getUrlVars("id");
$(document).ready(function(){
	$(".panel-body").css("height",$(window).height() - 112);
	query();
	$("#addBtn").on("click",function(){
		addRole();
	});
	$("#delBtn").on("click",function(){
		delRole();
	});
});

function query(){
	ajaxPost("${base}/sysUserRole/query",{id:id},function(data){
		if(data.notselect){
			var html="";
			$.each(data.notselect,function(id,item){
				html +='<tr class="text-c">';
				html +='<td><input type="checkbox" value="'+item.id+'" name=""></td>';
				html +='<td>'+nullToSpace(item.role_name)+'</td>';
				html +='</tr>';
			});
			$("#table1 tbody").html(html);
		}
		if(data.select){
			var html="";
			$.each(data.select,function(id,item){
				html +='<tr class="text-c">';
				html +='<td><input type="checkbox" value="'+item.id+'" name=""></td>';
				html +='<td>'+nullToSpace(item.role_name)+'</td>';
				html +='</tr>';
			});
			$("#table2 tbody").html(html);
		}
	});
}
/*添加*/
function addRole(){
	var ids=[];
	$("#table1 td").find(":checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length<1){
		layer.msg("请勾选要添加的角色");
		return;
	}
	ajaxPost("${base}/sysUserRole/addRole",{userId:id,ids:ids.join(",")},function(data){
		if(data.success){
			layer.msg(data.msg);
			query();
		}else{
			layer.msg(data.msg);
		}
	});
}

/*移除*/
function delRole(){
	var ids=[];
	$("#table2 td").find(":checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length<1){
		layer.msg("请勾选要移除的角色");
		return;
	}
	ajaxPost("${base}/sysUserRole/delRole",{userId:id,ids:ids.join(",")},function(data){
		if(data.success){
			layer.msg(data.msg);
			query();
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>