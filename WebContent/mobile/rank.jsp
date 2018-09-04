<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<meta name="description" content="排行榜">
<title>排行榜</title>
<style>
	.mui-table-view-cell:after {
	    left: 0;
    }
    .mui-table-cell{
	    height: 25px;
	    line-height: 25px;
    }
    .mui-table-cell img{
        width: 25px;
	    height: 25px;
	    border-radius: 50%;
        vertical-align:middle;
    }
    .badge{
    	font-size: 12px;
	    width:25px;
	    height:25px;
	    display: inline-block;
	    color: #fff;
	    border-radius: 50%;
	    background-color: rgba(0,0,0,.4);
    }
</style>
</head>
<body>
	<nav class="mui-bar mui-bar-tab">
		<a class="mui-tab-item" href="../mobile/index.jsp">
			<img src="../styles/images/home.png" class="tab-img">
			<span class="mui-tab-label">首页</span>
		</a>
		<a class="mui-tab-item" href="../mobile/baoming.jsp">
			<img src="../styles/images/bm.png" class="tab-img">
			<span class="mui-tab-label">报名</span>
		</a>
		<a class="mui-tab-item" href="#">
			<div class="search-bar"></div>
		</a>
		<a class="mui-tab-item" href="../mobile/rank.jsp">
			<img src="../styles/images/paih.png" class="tab-img">
			<span class="mui-tab-label">排行榜</span>
		</a>
		<a class="mui-tab-item" href="../mobile/userCenter.jsp">
			<img src="../styles/images/wode.png" class="tab-img">
			<span class="mui-tab-label">我的</span>
		</a>
	</nav>
	<div class="mui-content">
		<div class="mui-card-media index-head"></div>
		<div class="items main-bgcolor" style="padding:10px 0;">
			<div class="item mui-text-center b-right">
				<span>参赛人数</span>
				<span id="num1">0</span>
			</div>
			<div class="item mui-text-center b-right">
				<span>累计投票</span>
				<span id="num2">0</span>
			</div>
			<div class="item mui-text-center">
				<span>参与人数</span>
				<span id="num3">0</span>
			</div>
		</div>
		<div class="segmented-control mt10">
			<a class="control-item mui-active">
				品牌排行
			</a>
			<a class="control-item">
				厨神排行
			</a>
		</div>
		<div class="group" style="margin:0">
			<select id="itemType" style="border-radius: 0;">
			</select>
		</div>
		<ul class="mui-table-view fs14">
			 <li class="mui-table-view-cell color-yellow">
			 	<div class="mui-table">
	                <div class="mui-text-center mui-table-cell mui-col-xs-2">名次</div>
	                <div class="mui-text-center mui-table-cell mui-col-xs-4" id="groupName">选手</div>
	                <div class="mui-text-center mui-table-cell mui-col-xs-3">人气</div>
	                <div class="mui-text-center mui-table-cell mui-col-xs-3">票数</div>
	            </div>
			 </li>
		</ul>
		<ul class="mui-table-view fs14 main-color mt0" id="list">
		</ul>
	</div>
	<div class="search"> 
		<div class="s_up">
		    <form name="for1" method="post">
			    <input type="text" name="txt" placeholder="请输入您要查找的编号或姓名" id="search">
			    <input type="button" value="搜索" id="search-btn">
		    </form>
		</div>
		<p class="tip">搜索选手编号或姓名，为Ta投票吧！</p>
	</div>
	<div class="mui-popup-backdrop mui-active" style="display: none;"></div>
</body>
<script src="./js/rank.js?v=${version}"></script>
</html>