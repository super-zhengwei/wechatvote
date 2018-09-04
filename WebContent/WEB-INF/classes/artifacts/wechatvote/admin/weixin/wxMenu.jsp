<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>微信菜单管理</title>
<script type="text/javascript" src="${base}/lib/jquery-treegrid/jquery.treegrid.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/lib/jquery-treegrid/jquery.treegrid.css" />
</head>
<body>
<div class="page-container">
	<div class="Huialert Huialert-info"><i class="Hui-iconfont">&#xe6a6;</i>注意：1级菜单最多只能添加3个，2级子菜单最多添加5个!</div>
	<div class="cl pd-5 bg-1 bk-gray"> 
		<span class="l">
			<a href="javascript:;" onclick="add('添加菜单')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加菜单</a>
			<a href="javascript:;" onclick="createMenu()" class="btn btn-secondary radius"><i class="Hui-iconfont">&#xe6df;</i>生成微信菜单</a>
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="tree table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c">
				<th width="200">菜单名称</th>
				<th width="200">菜单类型</th>
				<th >菜单链接</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<script type="text/javascript">
var pageSize=10;
$(document).ready(function(){
	query();
	$("#queryBtn").on("click",function(){
		query();
	});
});
/*查询*/
function query(){
	ajaxPost("${base}/wxMenu/query",{},function(data){
		var i=0;
		var html="";
		$.each(data,function(id1,item1){
			i++;
			html +='<tr class="treegrid-'+(id1+1)+' text-c">';
			html +='<td>'+nullToSpace(item1.name)+'</td>';
			html +='<td>'+nullToSpace(item1.type)+'</td>';
			html +='<td>'+nullToSpace(item1.url)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改菜单\',\''+item1.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="del(this,\''+item1.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
			if(item1.children&&(item1.children).length>0){
				$.each(item1.children,function(id2,item2){
					i++;
					html +='<tr class="treegrid-'+(data.length+id2+1)+' treegrid-parent-'+(id1+1)+' text-c">';
					html +='<td>'+nullToSpace(item2.name)+'</td>';
					html +='<td>'+nullToSpace(item2.type)+'</td>';
					html +='<td>'+nullToSpace(item2.url)+'</td>';
					html +='<td class="td-manage f-14">';
					html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改菜单\',\''+item2.id+'\')" class="ml-5" style="text-decoration:none">';
					html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
					html +=	'</a> ';
					html +=	'<a title="删除" href="javascript:;" onclick="del(this,\''+item2.id+'\')" class="ml-5" style="text-decoration:none">';
					html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
					html +=	'</a>';
					html +='</td>';
					html +='</tr>';
				});
			}
		});
		$("#totalRow").html(i);
		$("#table1 tbody").html(html);
		$('.tree').treegrid();  
	});
}

/*菜单增加*/
function add(title,id){
	var url="wxMenuAdd.jsp";
	if(id){
		url +="?id="+id;
	}
	var index = layer.open({
		type: 2,
		title:title,
		content:url
	});
	layer.full(index);
}
/*菜单删除*/
function del(obj,id){
	var tip="";
	if($(obj).parents("tr").attr("class").indexOf("treegrid-parent")!=-1){
		tip="确认要删除吗？";
	}else{
		tip="确认要删除此菜单和其子菜单吗？";
	}
	layer.confirm(tip,function(index){
		ajaxPost("${base}/wxMenu/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}

function createMenu(){
	layer.confirm('生成成功后会覆盖原版本，且将在24小时内对所有用户生效，确认生成？',function(index){
		ajaxPost("${base}/wxMenu/createMenu",{},function(data){
			if(data.success){
				layer.msg('生成成功');
			}else{
				layer.msg(data.msg);
			}
		});
	});
}
</script>
</body>
</html>