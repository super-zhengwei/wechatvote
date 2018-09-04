<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<script src="${base}/static/mui/js/mui.zoom.js"></script>
<script src="${base}/static/mui/js/mui.previewimage.js"></script>
<meta name="description" content="欢迎大家来给我投票！">
<title></title>
<style>
	.mui-bar-tab ~ .mui-content {
	    padding-bottom: 90px;
	}
	.user-avatar {
	    width: 50px;
	    height: 50px;
	    border: 2px solid rgb(255,255,255);
	    margin: 0 10px 0 0;
	}
	.user-info{
	 	position: absolute;
		bottom: 0;
	    padding: 15px;
	}
	.user-group{
	    background: #f88d9f;
	    color: #fff;
	    padding: 0 5px;
	    border-radius: 5px;
	    font-size: 12px;
    	display: inline-block;
	}
	.mui-bar-tab .mui-tab-item .mui-tab-label{
		font-size: 14px;
	}
	#gift .mui-media-object.mui-pull-left {
	    line-height: 60px;
	    max-width: 60px;
	    height: 60px;
	}
	.vote-user .mui-media-object.mui-pull-left{
		width:42px;
		margin-bottom:11px;
	}
	.vote-user .mui-media{
		height:64px;
	}
</style>
</head>
<body>
	<nav class="mui-bar mui-bar-tab">
		<a class="mui-tab-item l mui-text-left">
			<span class="mui-tab-label">首页</span>
		</a>
		<a class="mui-tab-item" href="#">
			<div class="search-bar toupiao-bar">
				<i><img src="../styles/images/img-heart.png"></i><span>投票</span>
			</div>
		</a>
		<a class="mui-tab-item r mui-text-left" href="#">
			<span class="mui-tab-label">排行榜</span>
		</a>
	</nav>
	<div class="mui-content">
		<div class="mui-card-media index-head">
			<div class="user-info">
				<div class="avatar-bg user-avatar mui-pull-left"></div>
				<div class="mui-pull-left">
					<div class="fs14 color0 lh25 user-name">测试</div>
					<span class="user-group status"></span>
					<span class="user-group group-name" id="userGroup"></span>
					<span class="user-group type-name"></span>
				</div>
			</div>
		</div>
		<div class="items main-bgcolor border-bottom-color" style="padding:10px 0;">
			<div class="item mui-text-center">
				<span>编号</span>
				<span id="userCode">1</span>
			</div>
			<div class="item mui-text-center">
				<span>票数</span>
				<span id="total_num">0</span>
			</div>
			<div class="item mui-text-center">
				<span>人气</span>
				<span id="log_num">0</span>
			</div>
			<div class="item mui-text-center">
				<span>礼物</span>
				<span id="git_num">0</span>
			</div>
		</div>
		<div class="mui-text-center color0 pd10 fs14 main-bgcolor" id="itemInfo">
			当前排名：<span class="fs24">1</span><br>
			与前一名相差<span class="fs24">0</span>票 | 与后一名相差<span class="fs24">0</span>票
		</div>
		
		<ul class="mui-table-view mt10" id="gift">
			
		</ul>
		
		<ul class="mui-table-view mt10 fs14">
			<li class="mui-table-view-cell">
				<div class="items">
					<i class="mui-icon iconfont icon-x-jpeg" style="color:#f88d9f"></i>相册
				</div>
			</li>
			<div id="image-list" class="row image-list">
				<div id="thelist1" style="display: inline-block;">
	 			</div>
			</div>
		</ul>
		
		<ul class="mui-table-view fs14 mt10">
			<li class="mui-table-view-cell">
				<div class="items">
					<i class="mui-icon iconfont icon-message-zm" style="color:#f88d9f"></i>参赛宣言
				</div>
			</li>
			<li class="mui-table-view-cell">
				<span class="color1 fs14 remark"></span>
			</li>
		</ul>
		
		<ul class="vote-user mui-table-view mt10 fs14">
			<li class="mui-table-view-cell">
				<div class="mui-navigate-right items">
					<i class="mui-icon iconfont icon-xin1" style="color:#f88d9f"></i>支持Ta的人
				</div>
			</li>
			<li class="mui-table-view-cell mui-media">
			</li>
		</ul>
		<div id="giftDetail" class="mui-popup mui-popup-in" style="display: none;width:80%">
			<div class="mui-popup-inner">
				<img src="" style="height:60px;">
				<div class="mui-popup-text mt10">+<span id="giftNum">10</span><span class="giftName">智慧</span>值</div>
				<div class="mui-popup-text fs14 mt10">
				默认赠与宝宝10<span class="giftName">智慧</span>值，（每<span class="giftName">智慧</span>值<span id="giftPrice">0.3</span>元），需要修改数值请点击“其他”按钮。
				</div>
				<div class="mui-table mt10">
					<button type="button" class="mui-btn mui-active mui-col-xs-4">
						10
					</button>
					<input class="mui-btn mui-col-xs-4" placeholder="其他" onkeyup="num(this)"/>
			</div>
			</div>
			<div class="mui-popup-buttons">
				<span class="mui-popup-button mui-popup-button-bold">赠送</span>
			</div>
		</div>
		<div class="mui-popup-backdrop mui-active" id="giftBackdrop" style="display: none;"></div>
	</div>
</body>
<script src="./js/itemDetail.js?v=${version}"></script>
</html>