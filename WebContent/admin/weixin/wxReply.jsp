<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%>
<title>自动回复</title>
</head>
<body>
	<div class="page-container">
		<div id="tab-system" class="HuiTab">
			<div class="tabBar cl">
				<span>被添加自动回复</span><span>关键词自动回复</span>
			</div>
			<div class="tabCon">
				<form class="form form-horizontal" id="form-article-add">
					<div class="row cl">
						<div class="formControls col-xs-12 col-sm-11">
							<textarea class="textarea" id="rm_content"></textarea>
						</div>
					</div>
					<div class="row cl">
						<div class="col-xs-12 col-sm-11s">
							<button class="btn btn-primary radius" 
								type="submit">&nbsp;&nbsp;保存&nbsp;&nbsp;</button>
						</div>
					</div>
				</form>
			</div>
			<div class="tabCon">
				<div class="text-l mt-20"> 关键字：
					<input type="text" class="input-text" style="width:250px" placeholder="输入关键字" id="keyword" >
					<button type="button" class="btn btn-success" id="queryBtn"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
				</div>
				<div class="cl pd-5 bg-1 bk-gray mt-20"> 
					<span class="l">
						<a href="javascript:;" onclick="add('添加关键词自动回复')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
					</span> 
					<span class="r">共有数据：<strong id="totalRow">0</strong>条</span> 
				</div>
				<table class="table table-border table-bordered table-bg" id="table1">
					<thead>
						<tr class="text-c">
							<th width="60">关键字</th>
							<th width="60">回复类型</th>
							<th width="90">回复内容</th>
							<th width="40">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div class="mt-10 text-r" id="page1"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var id="";
		var pageSize=10;
		$(document).ready(function(){
			$.Huitab("#tab-system .tabBar span", "#tab-system .tabCon",
					"current", "click", "0");
			$("#form-article-add").validate({
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(form){
					submit()
				}
			});
			query();
			$("#queryBtn").on("click",function(){
				query();
			});
		});
		/*查询*/
		function query(curr){
			ajaxPost("${base}/wxReply/getFollow",{},function(data){
				id=data.id;
				$("#rm_content").val(data.rm_content);
			});
			var keyword = $.trim($("#keyword").val());
			ajaxPost("${base}/wxReply/query",{keyword:keyword,pageNumber:curr || 1,pageSize:pageSize},function(data){
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
					html +='<td>'+nullToSpace(item.reply_keyword)+'</td>';
					html +='<td>'+nullToSpace(item.rm_type)+'</td>';
					html +='<td>'+nullToSpace(item.rm_content)+'</td>';
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
		function submit(){
			var rm_content = $.trim($("#rm_content").val());
			if(rm_content==""){
				layer.msg("请输入回复内容");
				return;
			}
			
			ajaxPost("${base}/wxReply/followSave",{id:id,rm_content:rm_content},function(data){
				layer.msg(data.msg);
				if(data.res){
					id=data.res.id;
				}
			});
		}
		
		/*关键字增加*/
		function add(title,id){
			var url="wxReplyAdd.jsp";
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
		/*关键字删除*/
		function del(obj,id){
			layer.confirm('确认要删除吗？',function(index){
				ajaxPost("${base}/wxReply/delete",{id:id},function(data){
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