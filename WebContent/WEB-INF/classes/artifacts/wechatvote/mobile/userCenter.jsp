<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<meta name="description" content="个人中心">
<title>个人中心</title>
<style>
	body {
		background-color: #efeff4;
	}
	.mui-content {
	   	background-color: #efeff4;
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
		<div class="mui-card-media" style="height:165px;background-image:url(../styles/images/user-center-bg.jpg);">
			<div style="padding:30px">
				<div class="avatar-bg user-avatar"></div>
				<div class="mui-text-center fs20 bold"></div>
			</div>
		</div>
		<ul class="mui-table-view mui-grid-view mui-grid-9 bgcolor1">
			<li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3" id="voteNum">
            	<div class="grid-box" value="../mobile/userVoteDetail.jsp">
                    <span class="grid-label">0</span>
                 	<div class="mui-media-body">票数</div>
                 </div>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3" id="giftNum">
            	<div class="grid-box">
                    <span class="grid-label">0</span>
                 	<div class="mui-media-body">礼物</div>
                 </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3" id="sumAmt">
            	<div class="grid-box">
                    <span class="grid-label">0</span>
                 	<div class="mui-media-body">消费</div>
                 </div>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3 mui-hidden" id="baoming">
            	<div class="grid-box" value="../mobile/baoming.jsp">
                    <span class="mui-icon iconfont icon-tianjia"></span>
                    <div class="mui-media-body">报名</div>
                </div>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3" id="detail">
            	<div class="grid-box" value="../mobile/itemDetail.jsp">
                    <span class="mui-icon mui-icon-person"></span>
                    <div class="mui-media-body">我的信息</div>
                </div>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
            	<a href="#" style="height:86px;">
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
           		<a href="#" style="height:86px;">
               </a>
           </li>
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
<script src="./js/userCenter.js?v=${version}"></script>
</html>