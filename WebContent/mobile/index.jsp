<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<meta name="description" content="欢迎大家来给我投票！">
<title></title>
</head>
<body>
	<nav class="mui-bar mui-bar-tab" style="display: none;">
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
	<div class="mui-content" style="display: none;">
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
		<div class="shijian">
			<img src="../styles/images/c.png">
			<div class="lh25" id="sjLbl">
				活动时间：
			</div>
			<div class="lh25" id="sjVal">
				2017-02-14至2017-02-28
			</div>
		</div>
		<ul class="mui-table-view fs14">
			<li id="detail" class="mui-table-view-cell mui-media main-color">
				<a href="javascript:;">
					<img class="mui-media-object mui-pull-left" style="height:20px;" src="../styles/images/i.png">
					<div class="mui-media-body">
						活动详情
					</div>
				</a>
			 </li>
			 <li id="baoming" class="mui-table-view-cell mui-media main-color">
				<a href="javascript:;">
					<img class="mui-media-object mui-pull-left" style="height:20px;" src="../styles/images/b.png">
					<div class="mui-media-body">
						我要报名
					</div>
				</a>
			 </li>
		</ul>
		<div class="segmented-control" style="padding: 10px 10px 0;">
			<a class="control-item mui-active">
				上饶品牌
			</a>
			<a class="control-item">
				上饶厨神
			</a>
		</div>
		<div class="group">
			<select id="itemType">
				
			</select>
		</div>
		<section class="falls">
	        <ul class="fl columns">
	        </ul>
	        <ul class="fr columns">
		    </ul>
	        <p id="loadMore" class="main-color"><span>点击查看更多</span></p>
	    </section>
	</div>
<!-- 	<div class="arrow"></div> -->
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
<script src="./js/index.js?v=${version}"></script>
</html>