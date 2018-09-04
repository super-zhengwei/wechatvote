<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>${siteName}数据字典信息</title>
<meta name="keywords" content="美往向导">
<meta name="description" content="美往向导">
</head>
<body>

<!-- content start -->
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>字典索引：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="字典索引" id="dict_type" name="dict_type">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>字典键：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="字典键" id="dict_key" name="dict_key">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>字典值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="字典值" id="dict_value" name="dict_value">
			</div>
		</div>
		<div class="row foot cl">
			<div class="col-xs-12 col-sm-9 col-sm-offset-2">
				<input class="btn btn-primary radius" type="submit" value="提交">
				<input class="btn btn-danger radius" id="closeBtn" type="button" value="关闭">
			</div>
		</div>
	</form>
</article>
<script type="text/javascript">
	var id=$.getUrlVars("id");
	$(document).ready(function(){
		if(id){
			getDict();
		}
		$("#closeBtn").on("click",function(){
			layer_close();
		});
		$("#form-member-add").validate({
			rules:{
				dict_type:{
					required:true
				},
				dict_key:{
					required:true
				},
				dict_value:{
					required:true
				}
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				submit()
			}
		});
	});
	
	function getDict(){
		ajaxPost("${base}/dict/getDict",{id:id},function(data){
			if(data){
				$.each(data,function(key,value){
					if($('#'+key)){
						$('#'+key).val(nullToSpace(value));
					}
				});
			}
		});
	}
	function submit(){
		var param;
		if(id){
			param=$.param({'id':id}) + '&' + $('#form-member-add').serialize();
		}else{
			param=$('#form-member-add').serialize();
		}
		ajaxPost("${base}/dict/submit",param,function(data){
			if(data.success){
				var index = parent.layer.getFrameIndex(window.name);
				parent.$('#queryBtn').click();
				parent.layer.close(index);
			}else{
				layer.msg(data.msg);
			}
		});
	}
</script>
</script>
</body>
</html>