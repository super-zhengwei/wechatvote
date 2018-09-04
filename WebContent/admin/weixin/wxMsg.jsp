<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>图文消息</title>
</head>
<body>
<div class="page-container">
	<div class="text-l" id="keyDiv"> 关键字：
		<input type="text" class="input-text query-text" style="width:250px" placeholder="输入标题" id="keyword" >
		<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20" id="btnDiv"> 
		<span class="l">
			<a id="btnAdd" href="javascript:;" onclick="add('添加')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
			<a id="btnSelect" href="javascript:;" onclick="select()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe6a7;</i>确定</a>
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c" id="tableTitle">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="90">标题</th>
				<th width="60">分类</th>
				<th width="90">链接</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<div class="mt-10 text-r" id="page1"></div>
</div>
<script type="text/javascript">
var pageSize=10;
var flag=$.getUrlVars("flag");//弹出框选择图文消息
$(document).ready(function(){
	if(!flag){
		$("#btnSelect").hide();
		$("#tableTitle").append('<th width="50">创建时间</th><th width="50">操作</th>');
	}else{
		$("#btnAdd").hide();
		$("#keyDiv").hide();
		$("#btnDiv").css("margin-top","0");
		$(".page-container").css("padding","0");
	}
	query();
	$("#queryBtn").on("click",function(){
		query();
	});
});
/*查询*/
function query(curr){
	var keyword = $.trim($("#keyword").val());
	ajaxPost("${base}/wxMsg/query",{keyword:keyword,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
			html +='<td>'+nullToSpace(item.msg_title)+'</td>';
			html +='<td>'+nullToSpace(item.type_name)+'</td>';
			html +='<td>'+nullToSpace(item.msg_url)+'</td>';
			if(!flag){
				//html +='<td><img src="${imageUrl}'+nullToSpace(item.msg_img)+'" style="width:50px;height:50px;"></td>';
				html +='<td>'+nullToSpace(item.create_time)+'</td>';
				html +='<td class="td-manage f-14">';
				html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
				html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
				html +=	'</a> ';
				html +=	'<a title="删除" href="javascript:;" onclick="del(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
				html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
				html +=	'</a>';
				html +='</td>';
			}
			html +='</tr>';
		});
		$("#table1 tbody").html(html);
	});
}

/*图文消息增加*/
function add(title,id){
	var url="wxMsgAdd.jsp";
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
/*图文消息删除*/
function del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/wxMsg/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}

/*图文消息选择*/
function select(){
	if(flag=="1"){
		var ids=[];
		var url="";
		$("#table1 td").find(":checkbox:checked").each(function(){
			ids.push($(this).val());
			url=$(this).parent().parent().find("td:last-child").html();
		});
		
		if(ids.length!=1){
			layer.msg("请选择一个图文消息");
			return;
		}
		var index = parent.layer.getFrameIndex(window.name);
		parent.$('#url').val(url);
		parent.layer.close(index);
	}else if(flag=="2"){
		var html="";
		var trLen=parent.$("#table1 tbody tr").length;
		$("#table1 td").find(":checkbox:checked").each(function(id){
			html +='<tr class="text-c">';
			html +='<td class="hide">'+$(this).val()+'</td>';
			html +='<td>'+$(this).parent().parent().find("td:nth-child(2)").html()+'</td>';
			html +='<td>'+$(this).parent().parent().find("td:last-child").html()+'</td>';
			html +='<td><input type="text" class="input-text text-c" value="'+(trLen+id+1)+'"></td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="删除" href="javascript:;" onclick="delMsg(this)" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		if(html==""){
			layer.msg("请选择图文消息");
			return;
		}
		var index = parent.layer.getFrameIndex(window.name);
		parent.$("#table1 tbody").append(html);
		parent.layer.close(index);
	}
}
</script>
</body>
</html>