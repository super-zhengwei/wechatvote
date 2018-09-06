<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<meta name="description" content="欢迎大家来报名参加！">
<title>报名</title>
<link href="${base}/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="${base}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<style>
   	p{
		padding: 10px 15px 0;
   	}
    textarea {
	    height: 100px;
	    margin-bottom: 0 !important;
	    padding-bottom: 0 !important;
	    border: none !important;
	}
	input[type='text']{
		margin-bottom: 0 !important;
	    border: none !important;
	}
	.row {
	    width: 100%;
	    background-color: #fff;
	}
	.webuploader-pick{
	    width: 65px;
	    height: 65px;
	    background: url(../styles/images/tianjia.png);
	    background-size: 100% 100%;
	    display: inline-block;
	    position: relative;
	    border-radius: 5px;
	    margin-right: 10px;
	    margin-bottom: 10px;
	}
	.webuploader-pick-hover{
		background:url(../styles/images/tianjia.png);
		background-size: 100% 100%;
	}
</style>
</head>

<body>
	<header id="header" class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 id="title" class="mui-title">报名</h1>
	</header>
	<div class="mui-content">
		<p class="main-color">姓名</p>
		<div class="mui-input-row">
			<input id="user_name" type="text" placeholder="请填写姓名"></span>
		</div>
		<p class="main-color">手机号码</p>
		<div class="mui-input-row">
			<input id="mobile" type="text" placeholder="请填写手机号码"></span>
		</div>
		<p class="main-color">选择分组</p>
		<div class="mui-input-row">
			<select id="vote_group" style="margin-bottom: 0;">
				
			</select>
		</div>
        <div id="itemTypeDiv">
            <p class="main-color">选择分类</p>
            <div class="mui-input-row" >
                <select id="item_type" style="margin-bottom: 0;">

                </select>
            </div>
        </div>
		<p class="main-color">头像</p>
		<div id="image-list" class="row image-list">
			<div id="thelist" style="display: inline-block;">
 			</div>
	        <div id="picker"></div>
		</div>
		<p class="main-color">照片</p>
		<div id="image-list" class="row image-list">
			<div id="thelist1" style="display: inline-block;">
 			</div>
	        <div id="picker1"></div>
		</div>
		<p class="main-color">我的宣言</p>
		<div class="row mui-input-row">
			<textarea id="remark" class="mui-input-clear question" placeholder="请填写我的宣言"></textarea>
		</div>
		<div class="pd15">
			<button type="button" class="mui-btn mui-btn-primary mui-btn-block pd10">提交</button>
		</div>
	</div>
</body>
<script src="./js/baoming.js?v=${version}"></script>
</html>