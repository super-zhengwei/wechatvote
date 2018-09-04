<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>图文消息</title>
</head>
<body>
<div class="page-container">
	<div class="text-l mt-20"> 分类名称：
		<input type="text" class="input-text query-text" style="width:250px" placeholder="输入分类名称" id="keyword" >
		<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add('添加分类')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c">
				<th width="60">分类名称</th>
				<th width="60">分类图片</th>
				<th width="40">操作</th>
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
});
/*查询*/
function query(curr){
	var keyword = $.trim($("#keyword").val());
	ajaxPost("${base}/wxMsgType/query",{keyword:keyword,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
			html +='<td>'+nullToSpace(item.type_name)+'</td>';
			html +='<td><img src="${imageUrl}'+nullToSpace(item.type_img)+'" style="width:50px;height:50px;"></td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改用户\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
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

/*图文消息增加*/
function add(title,id){
	var url="wxMsgTypeAdd.jsp";
	if(id){
		url +="?id="+id;
	}
	layer_show(title,url,'500','350');
}
/*图文消息删除*/
function del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/wxMsgType/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}
</script>
</body>
</html>