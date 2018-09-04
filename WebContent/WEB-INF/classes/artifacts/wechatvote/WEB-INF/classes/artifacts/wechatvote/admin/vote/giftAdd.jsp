<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<script src="${base}/lib/laydate/laydate.js"></script>
<link href="${base}/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="${base}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<title>添加选手</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>礼物名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" class="input-text" id="vote_id" name="vote_id">
				<input type="text" class="input-text" id="gift_name" name="gift_name" placeholder="请输入礼物名称" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>礼物类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<select class="select" id="gift_type" name="gift_type">
						<option value="20">仁孝</option>
						<option value="30">智慧</option>
						<option value="40">勇毅</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>礼物价格：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="gift_price" name="gift_price" placeholder="请填写礼物价格">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>增加票数：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="add_vote_num" name="add_vote_num" placeholder="请填写增加票数">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>礼物图片：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="gift_img" name="gift_img" style="display:none;">
				<div id="thelist1" class="uploader-list"></div>
			    <div class="btns">
			        <div id="picker1">选择文件</div>
			    </div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>备注说明：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="remark" name="remark" placeholder="备注说明">
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
var id=$.getUrlVars("giftId");
var voteId=$.getUrlVars("voteId");
$(document).ready(function(){
	$("#vote_id").val(voteId);
	if(id){
		getGift();
	}
	initUpload1();
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			gift_name:{
				required:true,
				maxlength:4
			},
			gift_price:{
				required:true,
				number:true
			},
			add_vote_num:{
				required:true,
				digits:true
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
function initUpload1(){
	$list = $('#thelist1');
	ratio = window.devicePixelRatio || 1,
    // 缩略图大小
    thumbnailWidth = 100 * ratio,
    thumbnailHeight = 100 * ratio;
	var uploader1 = WebUploader.create({
		// 选完文件后，是否自动上传。
        auto: true,
		// swf文件路径
		swf : '${base}lib/webuploader/0.1.5/Uploader.swf',
		// 文件接收服务端。
		server : '${base}/common/upload',
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : "#picker1",
			multiple : false
		},
		formData: {
			folder: 'vote'
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
	uploader1.on('fileQueued', function(file) {
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
	       uploader1.makeThumb( file, function( error, src ) {
	           if ( error ) {
	               $img.replaceWith('<span>不能预览</span>');
	               return;
	           }
	           $img.attr( 'src', src );
	       }, thumbnailWidth, thumbnailHeight );
	});

	//当文件上传成功时触发。
	uploader1.on("uploadSuccess", function(file,response) {
		$("#gift_img").val(response.path);
		uploader1.reset();
	});

	uploader1.on("uploadError", function(file) {
		layer.msg("上传失败");
		uploader1.reset();
	});
}
function getGift(){
	ajaxPost("${base}/voteGift/getGift",{id:id},function(json){
		console.info(json);
		if(json.success){
			$.each(json.data,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
		}
		$("#thelist1").append('<div id="WU_FILE_0" class="file-item thumbnail">' +
	               '<img src="${imageUrl}'+json.data.gift_img+'" style="width:100px;height:100px;">' +
	           	   '</div>');
	});
}

function submit(){
	if($("#gift_img").val()==""){
		layer.msg('请上传礼物图片');
		return;
	}
	var param = $('#form-member-add').serialize();
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	ajaxPost("${base}/voteGift/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.getGift();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>