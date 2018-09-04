<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%> 
<title>用户管理</title>
</head>
<body>
<div class="page-container">
	<div class="text-l"> 关键字：
		<input type="text" class="input-text query-text" style="width:180px" placeholder="输入昵称、姓名、手机号码" id="keyword" >
		会员类型：
		<span class="select-box" style="width:180px;">
			<dict:dm dictType="APPLY_SOURCE" dictKey="" id="userType" name="userType" render="select" styleClass="select query-select" top="true"/>
		</span>
		<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="add('添加会员')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c">
				<th width="120">openid</th>
				<th width="60">昵称</th>
				<th width="60">姓名</th>
				<th width="80">手机号码</th>
				<th width="50">类型</th>
				<th width="50">分销码</th>
				<th width="60">城市</th>
<!-- 				<th width="60">更新时间</th> -->
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
	var userType = $.trim($("#userType").val());
	ajaxPost("${base}/users/query",{keyword:keyword,userType:userType,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
			html +='<td>'+nullToSpace(item.openid)+'</td>';
			html +='<td>'+nullToSpace(item.nickname)+'</td>';
			html +='<td>'+nullToSpace(item.username)+'</td>';
			html +='<td>'+nullToSpace(item.mobile)+'</td>';
			html +='<td>'+getValue('APPLY_SOURCE',item.user_type)+'</td>';
			html +='<td>'+nullToSpace(item.dis_num)+'</td>';
			html +='<td>'+getValue('REGION',item.city)+'</td>';
			//html +='<td>'+nullToSpace(item.modify_time)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改会员\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a>';
			html +=	'<a title="删除" href="javascript:;" onclick="del(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		$("#table1 tbody").html(html);
	});
}

/*添加*/
function add(title,id){
	var url="usersAdd.jsp";
	if(id){
		url +="?id="+id;
	}
	layer_show(title,url,'600','500');
}

/*删除*/
function del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/users/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}

function getValue(code,key){
	if(!key){
		return '';
	}
	if(code=="REGION"){
		<c:forEach var="item" items="${REGION}">
		if(key=="${item.key}"){
			return '${item.value}';
		}
		</c:forEach>
	}else if(code=="APPLY_SOURCE"){
		<c:forEach var="item" items="${APPLY_SOURCE}">
			if(key=="${item.key}"){
				return '${item.value}';
			}
		</c:forEach>
	}
}
</script>
</body>
</html>