<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<script src="${base}/lib/laydate/laydate.js"></script>  
<title>微信用户留言</title>
</head>
<body>
<div class="page-container">
	<div class="text-l"> 
		手机：
		<input type="text" class="input-text query-text" style="width:180px" placeholder="openid" id="openid">
		留言时间：
		<input id="startdate" class="laydate-icon" style="width:120px;height:31px;"/> 
		-
		<input id="enddate" class="laydate-icon" style="width:120px;height:31px"/> 
		<button type="button" class="btn btn-success radius" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
		</span> 
		<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
	</div>
	<table class="table table-border table-bordered table-bg" id="table1">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="100">openId</th>
				<th width="240">留言内容</th>
				<th width="80">留言时间</th>
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
	laydate({
	    elem: '#startdate',
	    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
	    festival: true //显示节日
	});
	laydate({
	    elem: '#enddate',
	    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
	    festival: true //显示节日
	});
	$("#queryBtn").on("click",function(){
		query();
	});
});
/*查询*/
function query(curr){
	var openid  = $("#openid").val();
	var startdate = $("#startdate").val();
	var enddate  = $("#enddate").val();
	ajaxPost("${base}/userMsg/getUserMsgs",{openid:openid,
		startdate:startdate,enddate:enddate,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
			html +='<td>'+nullToSpace(item.openId)+'</td>';
			html +='<td>'+nullToSpace(item.content)+'</td>';
			html +='<td>'+nullToSpace(item.createTime)+'</td>';
			html +='</tr>';
		});
		$("#table1 tbody").html(html);
	});
}
</script>
</body>
</html>