<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<link href="${base}/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="${base}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<title>添加选手</title>
</head>
<body>
<div class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" class="input-text" id="vote_id" name="vote_id">
				<input type="text" class="input-text" id="user_code" name="user_code" placeholder="编号自动生成" disabled>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="user_name" name="user_name" placeholder="请填写姓名">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>手机号码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="mobile" name="mobile" placeholder="请填写手机号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>分组：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<select class="select" id="group_id" name="group_id">
					</select>
				</span>
			</div>
		</div>
		<div class="row cl" id="itemTypeDiv">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>分类：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<select class="select" id="item_type" name="item_type">
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>排序</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="sort" name="sort" placeholder="输入排序">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>头像：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div id="thelist" class="uploader-list"></div>
				<input type="hidden" class="input-text" id="head_img" name="head_img">
			    <div class="btns">
			        <div id="picker">选择图片</div>
			    </div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>照片：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div id="thelist1" class="uploader-list"></div>
			    <div class="btns">
			        <div id="picker1">选择图片</div>
			    </div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>宣言：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="remark" id="remark" class="textarea" placeholder="输入宣言"></textarea>
			</div>
		</div>
        <div class="row cl">
            <label class="form-label col-xs-3 col-sm-2">链接地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="url" name="url" placeholder="链接地址">
            </div>
        </div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>微信OPENID：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="openid" name="openid" disabled>
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
var id=$.getUrlVars("itemId");
var voteId=$.getUrlVars("voteId");
var arr=new Array() ;
$(document).ready(function(){
	$.each(itemType,function(id,item){
		$("#item_type").append('<option value="'+item.dict_key+'">'+item.dict_value+'</option>');
	});
	$("#vote_id").val(voteId);
	getGroup();
	if(id){
		getItem();
	}
	initUpload();
	initUpload1();
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			user_name:{
				required:true
			},
			mobile:{
				required:true,
				isMobile:true
			},
			remark:{
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
	$('#thelist1').on('mouseover','.file-item',function(){
		$(this).find('.file-panel').css('height','30px');
	});
	$('#thelist1').on('mouseout','.file-item',function(){
		$(this).find('.file-panel').css('height','0');
	});
	$('#thelist1').on('click','.cancel',function(){
		arr.removeAt(($(this).index()));
		$(this).parents('.file-item').remove();
	});
//	$("#group_id").change(function(){
//		if($(this).val()=="2"){
//			$("#item_type").val("");
//			$("#itemTypeDiv").hide();
//		}else{
//			$("#itemTypeDiv").show();
//		}
//	})
});
function initUpload(){
	$list = $('#thelist');
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
	uploader.on('fileQueued', function(file) {
		var $li = $('<div id="' + file.id + '" class="file-item thumbnail">' +
                   '<img>' +
                   '<div class="info">' + file.name + '</div>' +
               '</div>');
        // $list为容器jQuery实例
        $img = $li.find('img');
        $list.html( $li );
 		
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }
            $img.attr( 'src', src );
        },  100, 100 );
	});

	//当文件上传成功时触发。
	uploader.on("uploadSuccess", function(file,response) {
		$("#head_img").val(response.path);
	});

	uploader.on("uploadError", function(file) {
		layer.msg("上传失败");
		uploader.reset();
	});
}
function initUpload1(){
	$list1 = $('#thelist1');
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
			multiple : true
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
		var $li = $('<div id="' + file.id + '" class="file-item thumbnail">' +
                   '<img>' +
                   '<div class="info">' + file.name + '</div>' +
               '</div>'),
        $img1 = $li.find('img');
	    $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '</div>').appendTo( $li );
        // $list为容器jQuery实例
        $list1.append( $li );
 
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader1.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img1.replaceWith('<span>不能预览</span>');
                return;
            }
            $img1.attr( 'src', src );
        },  100, 100 );
	});

	//当文件上传成功时触发。
	uploader1.on("uploadSuccess", function(file,response) {
		arr.push(response.path);
	});

	uploader1.on("uploadError", function(file) {
		layer.msg("上传失败");
		uploader1.reset();
	});
}
function getGroup(){
	ajaxPost("${base}/voteGroup/query",{voteId:voteId,pageNumber:1,pageSize:100},function(data){
		$("#group_id").empty();
		$.each(data.list,function(id,item){
			$("#group_id").append('<option value="'+item.id+'">'+item.group_name+'</option>');
		});
	});
}
function getItem(){
	ajaxPost("${base}/voteItem/getItemById",{id:id},function(json){
		console.info(json);
		if(json.success){
			$.each(json.data,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
			if(json.data.group_id=="2"){
				$("#item_type").val("");
				$("#itemTypeDiv").hide();
			}else{
				$("#itemTypeDiv").show();
			}
			$("#thelist").append('<div id="WU_FILE_'+arr.length+'" class="file-item thumbnail">' +
                   '<img src="${imageUrl}'+json.data.head_img+'" style="width:100px;height:100px;">' +
               	   '</div>');
			$.each(json.data.list,function(id,item){
				$("#thelist1").append('<div id="WU_FILE_'+id+'" class="file-item thumbnail">' +
		                   '<img src="${imageUrl}'+item.item_img+'" style="width:100px;height:100px;">' +
		                   '<div class="file-panel">' +
		                   '<span class="cancel">删除</span>' +
		                   '</div>'+
		               	   '</div>');
		               	arr.push(item.item_img);
			});
		}
	});
}

function submit(){
	if($("#head_img")==""){
		layer.msg('请上传头像');
		return;
	}
	if(arr.length==0){
		layer.msg('请上传照片');
		return;
	}
	var param = $('#form-member-add').serialize();
	param = param + '&' +  $.param({'list':JSON.stringify(arr)}) ;
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	ajaxPost("${base}/voteItem/submit",param,function(data){
		if(data.success){
			var index = parent.layer.getFrameIndex(window.name);
			parent.getItem();
			parent.layer.close(index);
		}else{
			layer.msg(data.msg);
		}
	});
}
</script> 
</body>
</html>