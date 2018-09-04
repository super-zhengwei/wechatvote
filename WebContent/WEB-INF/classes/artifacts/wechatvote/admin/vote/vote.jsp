<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>活动管理</title>
</head>
<body>
<div class="page-container">
	<div class="text-l"> 关键字：
		<input type="text" class="input-text query-text" style="width:250px" placeholder="输入活动名称" id="keyword" >
		<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add('添加活动')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加活动</a>
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="120">活动名称</th>
				<th width="90">开始日期</th>
				<th width="90">结束日期</th>
				<th width="60">状态</th>
				<th width="120">创建时间</th>
				<th width="90">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<div class="mt-10 text-r" id="page1"></div>
</div>
<script type="text/javascript">
var pageSize=10;
$(document).ready(function(){
	query();
	$("#queryBtn").on("click",function(){
		query();
	});
	$("#userRole").on("click",function(){
		userRole();
	});
});
/*查询*/
function query(curr){
	var keyword = $.trim($("#keyword").val());
	ajaxPost("${base}/vote/query",{keyword:keyword,pageNumber:curr || 1,pageSize:pageSize},function(data){
		laypage({
            cont: 'page1', //容器。
            pages: data.totalPage, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#5a98de',
            jump: function(obj, first){ //触发分页后的回s调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	query(obj.curr);
                }
            }
        });
		$("#totalRow").html(data.totalRow);
		var html="";
		$.each(data.list,function(id,item){
			html +='<tr class="text-c">';
			html +='<td><input type="checkbox" value="'+item.id+'" name=""></td>';
			html +='<td>'+nullToSpace(item.vote_name)+'</td>';
			html +='<td>'+nullToSpace(item.start_date)+'</td>';
			html +='<td>'+nullToSpace(item.end_date)+'</td>';
			if(item.vote_status=="0"){
				html +='<td class="td-status"><span class="label label-success radius">启用</span></td>';
			}else{
				html +='<td class="td-status"><span class="label label-default radius">停 用</span></td>';
			}
			html +='<td>'+nullToSpace(item.create_time)+'</td>';
			html +='<td class="td-manage f-14">';
			if(item.vote_status=="0"){
				html +=	'<a style="text-decoration:none" onClick="updateStatus(this,\''+item.vote_status+'\',\''+item.id+'\')" href="javascript:;" title="停用">';
				html +=		'<i class="Hui-iconfont">&#xe631;</i>';
				html +=	'</a>';
			}else{
				html +=	'<a style="text-decoration:none" onClick="updateStatus(this,\''+item.vote_status+'\',\''+item.id+'\')" href="javascript:;" title="起用">';
				html +=		'<i class="Hui-iconfont">&#xe615;</i>';
				html +=	'</a>';
			}
			html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改活动\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="del(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		$("#table1 tbody").html(html);
	});
}

/*用户增加*/
function add(title,id){
	var url="voteAdd.jsp";
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
/*用户删除*/
function del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/vote/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}
/*修改用户状态*/
function updateStatus(obj,flag,id){
	var statusName=flag=="1"?"启用":"停用";
	var status=flag=="1"?"0":"1";
	layer.confirm('确认要'+statusName+'吗？',function(index){
		ajaxPost("${base}/vote/updateStatus",{id:id,vote_status:status},function(data){
			if(data.success){
				if(flag=="0"){
					$(obj).parents("tr").find(".td-manage").prepend('<a onClick="updateStatus(this,\''+status+'\',\''+id+'\')" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">停用</span>');
				}else{
					$(obj).parents("tr").find(".td-manage").prepend('<a onClick="updateStatus(this,\''+status+'\',\''+id+'\')" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">启用</span>');
				}
				$(obj).remove();
				layer.msg('已'+statusName+'!');
			}else{
				layer.msg('操作失败');
			}
		});
	});
}

/*配置角色*/
function userRole(){
	var ids=[];
	$("#table1").find(":checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length!=1){
		layer.msg("请勾选一个用户");
		return;
	}
	layer_show("配置角色","userRole.jsp?id="+ids[0],'800','500',query);
}
</script>
</body>
</html>