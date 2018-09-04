<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>角色管理</title>
<script type="text/javascript" src="${base}/lib/zTree/v3/js/jquery.ztree.core-3.5.js"></script> 
<script type="text/javascript" src="${base}/lib/zTree/v3/js/jquery.ztree.excheck-3.5.js"></script> 
<link rel="stylesheet" href="${base}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<style>
ul.ztree {border: 1px solid #ddd;overflow:auto;}
</style>
</head>
<body>
<div class="page-container">
	<div class="text-l">角色名称：
		<input type="text" class="input-text query-text" style="width:250px" placeholder="输入角色名称" id="roleName" >
		<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="col-xs-6 col-sm-6" style="padding-left:0;">
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			<span class="l">
				<a href="javascript:;" onclick="add('添加角色')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加角色</a>
			</span> 
			<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
		</div>
		<table class="table table-border table-bordered table-hover table-bg" id="table1">
			<thead>
				<tr class="text-c">
					<th width="200">角色名称</th>
					<th>备注</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div class="mt-10 text-r" id="page1"></div>
	</div>
	<div class="col-xs-6 col-sm-6" style="padding-right:0;">
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			<span class="l">
				<a href="javascript:;" onclick="save()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe632;</i> 保存</a>
			</span>
		</div>
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="cl"></div>
</div>
<script type="text/javascript">
var pageSize=10;
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

$(document).ready(function(){
	$("#treeDemo").css("height",$(window).height()-134);
	query();
	$("#table1").on("click","tr",function(){
		queryMenu($(this).find("input[type='checkbox']").val());
		$(this).toggleClass('danger').siblings().removeClass("danger")
	});
	$("#queryBtn").on("click",function(){
		query();
	});
});
/*查询*/
function query(curr){
	var roleName = $.trim($("#roleName").val());
	ajaxPost("${base}/sysRole/query",{roleName:roleName,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
			if(id==0){
				html +='<tr class="text-c danger">';
				queryMenu(item.id);
			}else{
				html +='<tr class="text-c">';
			}
			html +='<td style="display: none;"><input type="checkbox" value="'+item.id+'" ></td>';
			html +='<td>'+nullToSpace(item.role_name)+'</td>';
			html +='<td>'+nullToSpace(item.remark)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="add(\'修改角色\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
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

/*角色增加*/
function add(title,id){
	event.stopPropagation();
	var url="roleAdd.jsp";
	if(id){
		url +="?id="+id;
	}
	layer_show(title,url,'450','300');
}
/*角色删除*/
function del(obj,id){
	event.stopPropagation();
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/sysRole/delete",{id:id},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}

/*保存菜单*/
function save(){
	var menuIds=[];
	var roleId=$(".danger input[type='checkbox']").val();
	if(!roleId){
		layer.msg('请选择一个角色');
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var selectedNode = zTree.getCheckedNodes();
    for(var i=0;i<selectedNode.length;i++){
    	menuIds.push(selectedNode[i].id);
    }
    ajaxPost("${base}/sysRoleMenu/save",{roleId:roleId,menuIds:menuIds.join(",")},function(data){
		if(data.success){
			layer.msg('保存成功');
		}else{
			layer.msg('保存失败');
		}
	});
}

/*查询菜单*/
function queryMenu(roleId){
	ajaxPost("${base}/sysRoleMenu/queryByRoleId",{roleId:roleId},function(data){
		$.fn.zTree.init($("#treeDemo"), setting, data);
	});
}
</script>
</body>
</html>