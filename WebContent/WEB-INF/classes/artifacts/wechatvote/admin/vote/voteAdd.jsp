<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.vote.util.DictUtil"%>
<%@ taglib prefix="dict" uri="/data-dict" %>
<%@ include file="/admin/meta.jsp"%>
<script src="${base}/lib/laydate/laydate.js"></script>
<link href="${base}/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="${base}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/ueditor.all.js"> </script> 
<script type="text/javascript" src="${base}/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script> 

<title>活动管理</title>
</head>
<body>
<div class="page-container">
	<div id="tab-system" class="HuiTab">
		<div class="tabBar cl">
			<span>基本信息</span><span>选手管理</span><span>分组管理</span><span>礼物管理</span><span>礼物统计</span><span>排行榜</span>
		</div>
		<div class="tabCon">
			<form action="" method="post" class="form form-horizontal" id="form-member-add">
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>活动名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" id="vote_name" name="vote_name" placeholder="请填写活动名称">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>首页图片：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" id="vote_img" name="vote_img" style="display:none;">
						<div id="thelist1" class="uploader-list"></div>
					    <div class="btns">
					        <div id="picker1">选择文件</div>
					    </div>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>开始日期：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text laydate-icon" id="start_date" name="start_date" 
						style="height:31px;" placeholder="请选择开始日期">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>结束日期：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text laydate-icon" id="end_date" name="end_date" 
						style="height:31px;" placeholder="请选择结束日期">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>活动详情：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<script id="editor1" type="text/plain" style="width:100%;height:400px;"></script> 
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
		<div class="tabCon">
			<div class="text-l mt-20"> 关键字：
				<input type="text" class="input-text" style="width:250px" placeholder="输入选手姓名、编号" id="keyword" >
				<button type="button" class="btn btn-success" id="queryBtn1"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l">
					<a href="javascript:;" onclick="addItem('添加选手')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
					<a href="javascript:;" onclick="updateItemStatus()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe61d;</i>审核</a>
				</span> 
				<span class="r">共有数据：<strong id="totalRow1">0</strong>条</span> 
			</div>
			<table class="table table-border table-bordered table-bg" id="table1">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="60">编号</th>
						<th width="60">姓名</th>
						<th width="60">手机号码</th>
						<th width="60">分组</th>
						<th width="60">分类</th>
						<th width="60">头像</th>
						<th width="90">是否审核</th>
						<th width="40">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="mt-10 text-r" id="page1"></div>
		</div>
		<div class="tabCon">
			<div class="text-l mt-20"> 分组名称：
				<input type="text" class="input-text" style="width:250px" placeholder="输入分组名称" id="groupName" >
				<button type="button" class="btn btn-success" id="queryBtn2"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l">
					<a href="javascript:;" onclick="addGroup('添加分组')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
				</span> 
				<span class="r">共有数据：<strong id="totalRow2">0</strong>条</span> 
			</div>
			<table class="table table-border table-bordered table-bg" id="table2">
				<thead>
					<tr class="text-c">
						<th width="60">分组名称</th>
						<th width="60">备注</th>
						<th width="40">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="mt-10 text-r" id="page2"></div>
		</div>
		<div class="tabCon">
			<div class="text-l mt-20"> 礼物名称：
				<input type="text" class="input-text" style="width:250px" placeholder="输入礼物名称" id="giftName" >
				<button type="button" class="btn btn-success" id="queryBtn3"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l">
					<a href="javascript:;" onclick="addGift('添加礼物')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
				</span> 
				<span class="r">共有数据：<strong id="totalRow3">0</strong>条</span> 
			</div>
			<table class="table table-border table-bordered table-bg" id="table3">
				<thead>
					<tr class="text-c">
						<th width="60">礼物图片</th>
						<th width="60">礼物名称</th>
						<th width="60">礼物价格</th>
						<th width="60">增加数量</th>
						<th width="60">备注说明</th>
						<th width="40">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="mt-10 text-r" id="page3"></div>
		</div>
		<div class="tabCon">
			<div class="text-l mt-20"> 选择分组：
				<span class="select-box"  style="width:150px;">
					<select class="select" id="groupPay">
					</select>
				</span>
				<button type="button" class="btn btn-success" id="queryBtn5"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l" id="pay">
					<a href="javascript:;" class="btn btn-primary radius">选手统计</a>
					<a href="javascript:;" class="btn btn-default radius">分组统计</a>
				</span>
				<span class="r">合计：<strong id="totalRow5">0</strong>元</span>  
			</div>
			<table class="table table-border table-bordered table-bg" id="table5">
				<thead>
					<tr class="text-c">
						<th width="60" id="payName">选手</th>
						<th width="60">数量</th>
						<th width="60">金额</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="mt-10 text-r" id="page5"></div>
		</div>
		<div class="tabCon">
			<div class="text-l mt-20"> 选择分组：
				<span class="select-box"  style="width:150px;">
					<select class="select" id="group">
					</select>
				</span>
				<button type="button" class="btn btn-success" id="queryBtn4"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20"> 
				<span class="l" id="rank">
					<a href="javascript:;" class="btn btn-primary radius">选手排行</a>
					<a href="javascript:;" class="btn btn-default radius">分组排行</a>
				</span> 
			</div>
			<table class="table table-border table-bordered table-bg" id="table4">
				<thead>
					<tr class="text-c">
						<th width="60">排名</th>
						<th width="60" id="rankName">选手</th>
						<th width="60">票数</th>
						<th width="60">人气</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="mt-10 text-r" id="page4"></div>
		</div>
</div>

<script type="text/javascript">
var id=$.getUrlVars("id");
var ue1 ;
var pageSize=10;
$(document).ready(function(){
	$.Huitab("#tab-system .tabBar span", "#tab-system .tabCon",
			"current", "click", "0");
	ue1 = UE.getEditor('editor1');
	$("#queryBtn1").on("click",function(){
		getItem();
	});
	$("#keyword").keydown(function(e){
		if(e.keyCode==13){
		   if($.trim($(this).val())!=""){
			   getItem();
		   }
		}
	});
	$("#queryBtn2").on("click",function(){
		getGroup();
	});
	$("#groupName").keydown(function(e){
		if(e.keyCode==13){
		   if($.trim($(this).val())!=""){
			   getGroup();
		   }
		}
	});
	$("#queryBtn3").on("click",function(){
		getGift();
	});
	$("#giftName").keydown(function(e){
		if(e.keyCode==13){
		   if($.trim($(this).val())!=""){
			   getGift();
		   }
		}
	});
	$("#queryBtn4").on("click",function(){
		getRank();
	});
	$("#group").on('change',function(e) {
		getRank();
	});
	$("#queryBtn5").on("click",function(){
		getPay();
	});
	$("#groupPay").on('change',function(e) {
		getPay();
	});
	$("#rank").on("click","a",function(){
		if(!$(this).hasClass("btn-primary")){
			$(this).removeClass("btn-default");
			$(this).addClass("btn-primary");
			$(this).siblings().removeClass("btn-primary");
			$(this).siblings().addClass("btn-default");
			getRank();
		}
	});
	$("#pay").on("click","a",function(){
		if(!$(this).hasClass("btn-primary")){
			$(this).removeClass("btn-default");
			$(this).addClass("btn-primary");
			$(this).siblings().removeClass("btn-primary");
			$(this).siblings().addClass("btn-default");
			getPay();
		}
	});
	ue1.ready(function(){
		if(id){
			getVote();
			getItem();
			getGroup();
			getGift();
			getRank();
			getPay();
		}
	});
	laydate({
	    elem: '#start_date',
	    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
	    festival: true //显示节日
	});
	laydate({
	    elem: '#end_date',
	    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
	    festival: true //显示节日
	});
	initUpload1();
	$("#closeBtn").on("click",function(){
		layer_close();
	});
	$("#form-member-add").validate({
		rules:{
			vote_name:{
				required:true
			},
			start_date:{
				required:true
			},
			end_date:{
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
		$("#vote_img").val(response.path);
		uploader1.reset();
	});

	uploader1.on("uploadError", function(file) {
		layer.msg("上传失败");
		uploader1.reset();
	});
}
function getVote(){
	ajaxPost("${base}/vote/getVote",{id:id},function(data){
		console.info(data);
		if(data){
			$.each(data,function(key,value){
				if($('#'+key)){
					$('#'+key).val(nullToSpace(value));
				}
			});
			$("#thelist1").append('<div id="WU_FILE_0" class="file-item thumbnail">' +
               '<img src="${imageUrl}'+data.vote_img+'" style="width:100px;height:100px;">' +
           	   '</div>');
           	ue1.execCommand('insertHtml', data.vote_detail);
		}
	});
}

function submit(){
	if($("#vote_img").val()==""){
		layer.msg('请上传首页图片');
		return;
	}
	var text = UE.getEditor('editor1').getPlainTxt();
	if($.trim(text)==""){
		layer.msg("请输入活动详情");
		return;
	}
	var param = $('#form-member-add').serialize();
	param +='&' + $.param({'vote_detail':UE.getEditor('editor1').getContent()}) ;
	if(id){
		param +='&' + $.param({'id':id}) ;
	}
	ajaxPost("${base}/vote/submit",param,function(data){
		if(data.success){
			id=data.data;
			parent.query();
			layer.msg("保存成功");
		}else{
			layer.msg(data.msg);
		}
	});
}
/*查询选手*/
function getItem(curr){
	var keyword = $.trim($("#keyword").val());
	ajaxPost("${base}/voteItem/query",{keyword:keyword,voteId:id,pageNumber:curr || 1,pageSize:pageSize},function(data){
		laypage({
            cont: 'page1', //容器。
            pages: data.totalPage, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#5a98de',
            jump: function(obj, first){ //触发分页后的回s调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	getItem(obj.curr);
                }
            }
        });
		$("#totalRow1").html(data.totalRow);
		var html="";
		$.each(data.list,function(id,item){
			html +='<tr class="text-c">';
			html +='<td><input type="checkbox" value="'+item.id+'" name=""></td>';
			html +='<td>'+nullToSpace(item.user_code)+'</td>';
			html +='<td>'+nullToSpace(item.user_name)+'</td>';
			html +='<td>'+nullToSpace(item.mobile)+'</td>';
			html +='<td>'+nullToSpace(item.group_name)+'</td>';
			html +='<td>'+getTypeName(item.item_type)+'</td>';
			html +='<td><div style="height:50px;background:url(${imageUrl}'+nullToSpace(item.head_img)+') no-repeat center;background-size: contain;"></div></td>';
			if(item.status=="10"){
				html +='<td class="td-status"><span class="label label-success radius">已审核</span></td>';
			}else{
				html +='<td class="td-status"><span class="label label-default radius">未审核</span></td>';
			}
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="addItem(\'修改选手\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="delItem(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		$("#table1 tbody").html(html);
	});
}
/*查询分组*/
function getGroup(curr){
	var groupName = $.trim($("#groupName").val());
	ajaxPost("${base}/voteGroup/query",{groupName:groupName,voteId:id,pageNumber:curr || 1,pageSize:pageSize},function(data){
		laypage({
            cont: 'page2', //容器。
            pages: data.totalPage, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#5a98de',
            jump: function(obj, first){ //触发分页后的回s调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	getGroup(obj.curr);
                }
            }
        });
		$("#totalRow2").html(data.totalRow);
		var html="";
		$("#group").empty();
		$("#group").append('<option value="">选择分组</option>');
		$("#groupPay").empty();
		$("#groupPay").append('<option value="">选择分组</option>');
		$.each(data.list,function(id,item){
			html +='<tr class="text-c">';
			html +='<td>'+nullToSpace(item.group_name)+'</td>';
			html +='<td>'+nullToSpace(item.remark)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="addGroup(\'修改分组\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="delGroup(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
			$("#group").append('<option value="'+item.id+'">'+item.group_name+'</option>');
			$("#groupPay").append('<option value="'+item.id+'">'+item.group_name+'</option>');
		});
		$("#table2 tbody").html(html);
	});
}
/*查询礼物*/
function getGift(curr){
	var giftName = $.trim($("#giftName").val());
	ajaxPost("${base}/voteGift/query",{giftName:giftName,voteId:id,pageNumber:curr || 1,pageSize:pageSize},function(data){
		laypage({
            cont: 'page3', //容器。
            pages: data.totalPage, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#5a98de',
            jump: function(obj, first){ //触发分页后的回s调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	getGift(obj.curr);
                }
            }
        });
		$("#totalRow3").html(data.totalRow);
		var html="";
		$.each(data.list,function(id,item){
			html +='<tr class="text-c">';
			html +='<td><div style="height:50px;background:url(${imageUrl}'+nullToSpace(item.gift_img)+') no-repeat center;background-size: contain;"></div></td>';
			html +='<td>'+nullToSpace(item.gift_name)+'</td>';
			html +='<td>'+nullToSpace(item.gift_price)+'</td>';
			html +='<td>'+nullToSpace(item.add_vote_num)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="addGift(\'修改分组\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="delGift(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		$("#table3 tbody").html(html);
	});
}
/*查询礼物*/
function getGift(curr){
	var giftName = $.trim($("#giftName").val());
	ajaxPost("${base}/voteGift/query",{giftName:giftName,voteId:id,pageNumber:curr || 1,pageSize:pageSize},function(data){
		laypage({
            cont: 'page3', //容器。
            pages: data.totalPage, //通过后台拿到的总页数
            curr: curr || 1, //当前页
            skin: '#5a98de',
            jump: function(obj, first){ //触发分页后的回s调
                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                	getGift(obj.curr);
                }
            }
        });
		$("#totalRow3").html(data.totalRow);
		var html="";
		$.each(data.list,function(id,item){
			html +='<tr class="text-c">';
			html +='<td><div style="height:50px;background:url(${imageUrl}'+nullToSpace(item.gift_img)+') no-repeat center;background-size: contain;"></div></td>';
			html +='<td>'+nullToSpace(item.gift_name)+'</td>';
			html +='<td>'+nullToSpace(item.gift_price)+'</td>';
			html +='<td>'+nullToSpace(item.add_vote_num)+'</td>';
			html +='<td>'+nullToSpace(item.remark)+'</td>';
			html +='<td class="td-manage f-14">';
			html +=	'<a title="编辑" href="javascript:;" onclick="addGift(\'修改分组\',\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6df;</i>';
			html +=	'</a> ';
			html +=	'<a title="删除" href="javascript:;" onclick="delGift(this,\''+item.id+'\')" class="ml-5" style="text-decoration:none">';
			html +=		'<i class="Hui-iconfont">&#xe6e2;</i>';
			html +=	'</a>';
			html +='</td>';
			html +='</tr>';
		});
		$("#table3 tbody").html(html);
	});
}
function getRank(){
	var flag=$("#rank").find(".btn-primary").index();
	if(flag=="0"){
		$("#rankName").html("选手");
	}else{
		$("#rankName").html("分组");
	}
	var group = $("#group").val();
	ajaxPost("${base}/voteItem/getItemList",{voteId:id,group:group,flag:flag},function(json){
		console.info(json);
		var list=json.data;
		if(list&&list.length>0){
			var html="";
			jQuery.each(list,function(id,item){
				html +='<tr class="text-c">';
				html +='<td>'+(id+1)+'</td>';
				html +='<td>'+item.user_name+'</td>';
				html +='<td>'+item.log_num+'</td>';
				html +='<td>'+item.total_num+'</td>';
				html +='</tr>';
			});
			$("#table4 tbody").html(html);
		}else{
			$("#table4 tbody").empty();
		}
	});
}
/*礼物统计*/
function getPay(curr){
	var flag=$("#pay").find(".btn-primary").index();
	if(flag=="0"){
		$("#payName").html("选手");
	}else{
		$("#payName").html("分组");
	}
	var group = $("#groupPay").val();
	ajaxPost("${base}/votePay/query",{voteId:id,group:group,flag:flag},function(json){
		console.info(json);
		var list=json.data;
		if(list&&list.length>0){
			var html="";
			var total=0;
			jQuery.each(list,function(id,item){
				html +='<tr class="text-c">';
				html +='<td>'+item.user_name+'</td>';
				html +='<td>'+item.gift_num+'</td>';
				html +='<td>'+item.total_amt+'</td>';
				html +='</tr>';
				total+=parseFloat(item.total_amt);
			});
			$("#table5 tbody").html(html);
			$("#totalRow5").html(total);
		}else{
			$("#table5 tbody").empty();
			$("#totalRow5").html("0");
		}
	});
}
/*添加选手*/
function addItem(title,itemId){
	if(!id){
		layer.msg("请先保存基本信息");
		return;
	}
	var url="itemAdd.jsp?voteId="+id;
	if(itemId){
		url +="&itemId="+itemId;
	}
	var index = layer.open({
		type: 2,
		title:title,
		content:url
	});
	layer.full(index);
}
/*删除选手*/
function delItem(obj,itemId){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/voteItem/delete",{id:itemId},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg('删除失败');
			}
		});
	});
}
/*审核*/
function updateItemStatus(){
	var ids=[];
	$("#table1").find(":checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length!=1){
		layer.msg("请勾选一个选手");
		return;
	}
	ajaxPost("${base}/voteItem/updateStatus",{id:ids[0]},function(data){
		if(data.success){
			getItem();
			layer.msg('审核成功');
		}else{
			layer.msg(data.msg);
		}
	});
}
/*添加分组*/
function addGroup(title,groupId){
	if(!id){
		layer.msg("请先保存基本信息");
		return;
	}
	var url="groupAdd.jsp?voteId="+id;
	if(groupId){
		url +="&groupId="+groupId;
	}
	layer_show(title,url,'450','300');
}
/*删除分组*/
function delGroup(obj,groupId){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/voteGroup/delete",{id:groupId},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg(data.msg);
			}
		});
	});
}
/*添加礼物*/
function addGift(title,giftId){
// 	var url="giftAdd.jsp?voteId="+id;
// 	if(giftId){
// 		url +="&giftId="+giftId;
// 	}
// 	var index = layer.open({
// 		type: 2,
// 		title:title,
// 		content:url
// 	});
// 	layer.full(index);
	if(!id){
		layer.msg("请先保存基本信息");
		return;
	}
	var url="giftAdd.jsp?voteId="+id;
	if(giftId){
		url +="&giftId="+giftId;
	}
	layer_show(title,url,'600','540');
}
/*删除礼物*/
function delGift(obj,giftId){
	layer.confirm('确认要删除吗？',function(index){
		ajaxPost("${base}/voteGift/delete",{id:giftId},function(data){
			if(data.success){
				$(obj).parents("tr").remove();
				layer.msg('删除成功');
			}else{
				layer.msg(data.msg);
			}
		});
	});
}
</script> 
</body>
</html>