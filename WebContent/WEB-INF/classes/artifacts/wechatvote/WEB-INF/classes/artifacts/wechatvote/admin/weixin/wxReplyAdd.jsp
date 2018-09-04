<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>关键字自动回复</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>关键字：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="reply_keyword" name="reply_keyword">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>回复类型：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="rm_type" id="rm_type">
					<option value="1">文本消息</option>
					<option value="2">图文消息</option>
				</select>
				</span> </div>
		</div>
		<div class="row cl" id="contentRow">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>回复内容：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea class="textarea" name="rm_content" id="rm_content"></textarea>
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
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		reply_keyword:{
			name:{
				required:true,
				maxlength:8
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			submit()
		}
	});
	$('#rm_type').change(function(){ 
		if($(this).val()=="2"){
			$("#msgRow").show();
			$("#contentRow").hide();
		}else{
			$("#msgRow").hide();
			$("#contentRow").show();
		}
	});
	if(id){
		getReply();
	}
});


function selectMsg(flag){
	layer_show('选择图文消息','wxMsg.jsp?flag='+flag,'600','400');
}

function delMsg(obj){
	$(obj).parents("tr").remove();
}
function getReply(){
	ajaxPost("${base}/wxReply/getReply",{id:id},function(data){
		if(data){
			$.each(data.reply,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
			if(data.reply.rm_type=="2"){
				$("#msgRow").show();
				$("#contentRow").hide();
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
				$("#contentRow").show();
			}
		}
	});
}
function submit(){
	var param = $('#form-member-add').serialize();
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	if($("#rm_type").val()=="2"){
		var msgIds=[];
		$("#table1 tbody tr").each(function(){
			var obj = new Object();
			obj.msgId=$(this).find('.hide').html();
			obj.sort=$(this).find('.input-text').val();
			msgIds.push(obj);
		});
		param +='&' +$.param({'msgIds':JSON.stringify(msgIds)});
	}
	ajaxPost("${base}/wxReply/keySave",param,function(data){
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