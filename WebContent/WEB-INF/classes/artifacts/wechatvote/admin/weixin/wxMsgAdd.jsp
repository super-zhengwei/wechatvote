<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%> 
<title>菜单编辑</title>
<link href="${base}/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/ueditor.all.js"> </script> 
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="msg_title" name="msg_title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>类型：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="msg_type" id="msg_type">
				</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="msg_description" name="msg_description">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red"></span>图标：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="msg_img" name="msg_img" style="display:none;">
				<div id="thelist" class="uploader-list"></div>
			    <div class="btns">
			        <div id="picker">选择文件</div>
			    </div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>方式：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select class="select" size="1" name="msg_mode" id="msg_mode">
					<option value="1">链接</option>
					<option value="2">内容</option>
				</select>
				</span> 
			</div>
		</div>
		<div class="row cl" id="url">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>链接：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="msg_url" name="msg_url">
			</div>
		</div>
		<div class="row cl" id="content" style="display:none;">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="editor" type="text/plain" style="width:100%;height:400px;"></script> 
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
	var ue ;
	$(document).ready(function() {
		getMsgType();
		ue = UE.getEditor('editor');
		ue.ready(function(){
			if(id){
				getMsg();
			}
		})
		initUpload();
		$("#closeBtn").on("click", function() {
			layer_close();
		});
		$('#msg_mode').change(function(){ 
			if($(this).val()=="1"){
				$("#url").show();
				$("#content").hide();
			}else{
				$("#url").hide();
				$("#content").show();
			}
		});
		$("#form-member-add").validate({
			rules:{
				msg_title:{
					required:true
				}
			},
			onkeyup : false,
			focusCleanup : true,
			success : "valid",
			submitHandler : function(form) {
				submit();
			}
		});
	});

	function initUpload(){
		$list = $('#thelist'), $btn = $('#ctlBtn');
		ratio = window.devicePixelRatio || 1,
        // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio;
		var uploader = WebUploader.create({
			// 选完文件后，是否自动上传。
	        auto: true,
			// swf文件路径
			swf : '${base}lib/webuploader/0.1.5/Uploader.swf',
			// 文件接收服务端。
			server : '${base}/common/upload',
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : {
				id : "#picker",
				multiple : false
			},
			formData: {
				folder: 'weixin'
		    },
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize : false,
			multiple : false,
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,png',
				mimeTypes : 'image/jpg,image/jpeg,image/png'
			}
		});
		uploader.on('fileQueued', function(file) {
			var $li = $(
		               '<div id="' + file.id + '" class="file-item thumbnail">' +
		                   '<img>' +
		                   '<div class="info">' + file.name + '</div>' +
		               '</div>'
		               ),
		           $img = $li.find('img');
		       // $list为容器jQuery实例
		       $list.html("");
		       $list.append( $li );
		 
		       // 创建缩略图
		       // 如果为非图片文件，可以不用调用此方法。
		       // thumbnailWidth x thumbnailHeight 为 100 x 100
		       uploader.makeThumb( file, function( error, src ) {
		           if ( error ) {
		               $img.replaceWith('<span>不能预览</span>');
		               return;
		           }
		           $img.attr( 'src', src );
		       }, thumbnailWidth, thumbnailHeight );
		       
		       $("#filePicker").on('click',function(){
		    	   uploader.removeFile(file);
		       });
		});

		//当文件上传成功时触发。
		uploader.on("uploadSuccess", function(file,response) {
			$("#msg_img").val(response.path);
			uploader.reset();
		});

		uploader.on("uploadError", function(file) {
			layer.msg("上传失败");
			uploader.reset();
		});
	}
	function getMsg() {
		ajaxPost("${base}/wxMsg/getMsg", {
			id : id
		}, function(data) {
			if (data) {
				$.each(data, function(key, value) {
					if ($('#' + key)) {
						$('#' + key).val(nullToSpace(value));
					}
				});
				if(data.msg_mode=="1"){
					$("#url").show();
					$("#content").hide();
				}else{
					$("#url").hide();
					$("#content").show();
				}
				$("#thelist").append('<div id="WU_FILE_0" class="file-item thumbnail">' +
		                   '<img src="${imageUrl}'+data.msg_img+'" style="width:100px;height:100px;">' +
		               '</div>');
				ue.execCommand('insertHtml', data.msg_content);
			}
		});
	}
	
	function getMsgType() {
		ajaxPost("${base}/wxMsgType/query", {
			keyword:'',pageNumber:1,pageSize:100
		}, function(data) {
			$.each(data.list,function(id,item){
				$("#msg_type").append('<option value="'+item.id+'">'+item.type_name+'</option>');
			});
		});
	}
	function submit() {
// 		if($.trim($("#msg_img").val())==""){
// 			layer.msg("请上传图标");
// 			return;
// 		}
		var param = $('#form-member-add').serialize();
		if($("#msg_mode").val()=="1"){
			if($.trim($("#msg_url").val())==""){
				layer.msg("请输入链接");
				return;
			}
		}else{
			var content = UE.getEditor('editor').getContent();
			var text = UE.getEditor('editor').getPlainTxt();
			if($.trim(text)==""){
				layer.msg("请输入内容");
				return;
			}
			param=param+ '&' +$.param({'msg_content':content});
		}
		if(id){
			param=param+ '&' +$.param({'id':id});
		}
		ajaxPost("${base}/wxMsg/submit",param,function(data){
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
</body>
</html>