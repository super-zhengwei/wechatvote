var voteId=$.getUrlVars("voteId");
var itemId=$.getUrlVars("itemId");
var data;
var headImg;
var arr=new Array();
mui.init();
/**
 *初始化、添加监听 
 */
mui.ready(function(){
    $.each(itemType,function(id,item){
        $("#item_type").append('<option value="'+item.dict_key+'">'+item.dict_value+'</option>');
    });
	initUpload();
	initUpload1();
	getGroup();
	$('#thelist').on('click','.image-close',function(){
		event.stopPropagation();
		event.cancelBubble = true;
		headImg="";
		$(this).parents('.image-item').remove();
		if($('#thelist .image-item').length<1){
	    	   $("#picker").show();
		}
	});
	$('#thelist1').on('click','.image-close',function(){
		event.stopPropagation();
		event.cancelBubble = true;
		arr.removeAt(($(this).parents('.image-item').index()));
		$(this).parents('.image-item').remove();
//		if($('#thelist1 .image-item').length<3){
//	    	   $("#picker1").show();
//		}
	});
    $("#vote_group").change(function(){
        if($(this).val()=="2"){
            $("#item_type").val("");
            $("#itemTypeDiv").hide();
        }else{
            $("#itemTypeDiv").show();
        }
    })
	//提交
	$(".mui-btn-primary").on("tap",function(e){
		var userName=$.trim($("#user_name").val());
		var mobile=$.trim($("#mobile").val());
		var voteGroup=$("#vote_group").val();
		var itemType="";
		if (voteGroup=="1"){
            itemType = $("#item_type").val();
        }
		var remark=$.trim($("#remark").val());
		if(userName==""){
			return layer.open({content: "请输入姓名",time: 3});
		}
		if(mobile==""){
			return layer.open({content: "请输入手机号码",time: 3});
		}
		if(!checkMobile(mobile)){
			return layer.open({content: '请输入正确手机号码',time: 3});
		}
		if(voteGroup==""){
			return layer.open({content: "请选择分组",time: 3});
		}
		if(headImg==""){
			return layer.open({content: "请上传头像",time: 3});
		}
		if(arr.length==0){
			return layer.open({content: "请上传照片",time: 3});
		}
		if(remark==""){
			return layer.open({content: "请输入我的宣言",time: 3});
		}
		var param = $.param({'vote_id':voteId})+ '&' + $.param({'openid':openid})+ '&' +
			$.param({'user_name':userName})+ '&' + $.param({'mobile':mobile})+ '&' +
			$.param({'group_id':voteGroup})+ '&'+ $.param({'item_type':itemType})+ '&' + $.param({'head_img':headImg}) + '&'
			+ $.param({'list':JSON.stringify(arr)})+ '&' + $.param({'remark':remark});
		if(itemId){
			param = param + '&' + $.param({'id':itemId});
		}
//		for(var i=0;i<arr.length;i++){
//			param = param + '&' + 'pic'+(i+1)+'='+arr[i];
//		}
		ajaxPost(base+"/voteItem/save",param,function(json){
			if(json.success){
				if(itemId){
					layer.open({content: "提交成功",time: 3});
				}else{
					layer.open({content: "报名成功",time: 3});
				}
				setTimeout(function(){
					location.href="../mobile/itemDetail.jsp?voteId="+voteId+"&itemId="+json.data;
    			},2000);
			}else{
				layer.open({content: json.msg,time: 3});
			}
		});
	});
});

//查询订单
function getGroup(){
	ajaxPost(base+"/voteGroup/queryGroup",{voteId:voteId},function(json){
		$("#voteGroup").empty();
		$("#voteGroup").append('<option value="">请选择</option>');
		if(json.success){
			$.each(json.data,function(id,item){
				$("#vote_group").append('<option value="'+item.id+'">'+item.group_name+'</option>');
			});
			if(itemId){
				getItem();
			}
		}else{
			layer.open({content: json.msg,time: 3});
		}
	});
}

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
		server : base+'/common/upload',
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
		duplicate :true, 
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,png',
			mimeTypes : 'image/jpg,image/jpeg,image/png'
		}
	});
	uploader.on('fileQueued', function(file) {
		var $li = $(
	                   '<div class="image-item">'+
	                   '<div class="image-close">X</div>'+
	               	   '</div>'
	               );
	       $list.append( $li );
	       uploader.makeThumb( file, function( error, src ) {
	           if ( error ) {
	               $img.replaceWith('<span>不能预览</span>');
	               return;
	           }
	           $li.css("background-image","url("+src+")");
	       }, thumbnailWidth, thumbnailHeight );
	       if($('#thelist .image-item').length>=1){
	    	   $("#picker").hide();
	       }
	});

	//当文件上传成功时触发。
	uploader.on("uploadSuccess", function(file,response) {
		headImg = response.path;
	});

	uploader.on("uploadError", function(file) {
		layer.open({content: "上传失败",time: 3});
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
		server : base+'/common/upload',
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
		duplicate :true, 
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,png',
			mimeTypes : 'image/jpg,image/jpeg,image/png'
		}
	});
	uploader1.on('fileQueued', function(file) {
		var $li1 = $(
				'<div class="image-item">'+
				'<div class="image-close">X</div>'+
				'</div>'
		);
		$list1.append( $li1 );
		uploader1.makeThumb( file, function( error, src ) {
			if ( error ) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			$li1.css("background-image","url("+src+")");
		}, thumbnailWidth, thumbnailHeight );
//		if($('#thelist1 .image-item').length>=3){
//			$("#picker1").hide();
//		}
	});
	
	//当文件上传成功时触发。
	uploader1.on("uploadSuccess", function(file,response) {
		arr.push(response.path);
	});
	
	uploader1.on("uploadError", function(file) {
		layer.open({content: "上传失败",time: 3});
		uploader1.reset();
	});
}

function getItem(){
	ajaxPost(base+"/voteItem/getItemById",{id:itemId},function(json){
		if(json.data){
			$.each(json.data,function(key,value){
				if($("#"+key)){
					$("#"+key).val(value);
				}
			})
			$("#thelist").append('<div class="image-item" style="background-image:url('+imageUrl+json.data.head_img+')">'+
		                   '<div class="image-close">X</div>'+
		               	   '</div>');
			headImg=json.data.head_img;
			$("#picker").hide();
			$.each(json.data.list,function(id,item){
				arr.push(item.item_img);
				$("#thelist1").append('<div class="image-item" style="background-image:url('+imageUrl+item.item_img+')">'+
		                   '<div class="image-close">X</div>'+
		               	   '</div>');
			});
		}
	});
}