<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<title>送礼物</title>
<meta name="description" content="亲，送个礼物给我吧">
</head>

<body>
	<header id="header" class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 id="title" class="mui-title">送礼物</h1>
	</header>
	<div class="mui-content">
		<div class="mui-card-media" style="height:165px;background-image:url(../styles/images/user-center-bg.jpg);">
			<div style="padding:30px">
				<div class="avatar-bg user-avatar"></div>
				<div class="mui-text-center fs16 bold"></div>
			</div>
		</div>
		<div class="fs14 color0 main-bgcolor" style="padding:11px 15px;">
			选择礼物
		</div>
		<ul class="mui-table-view mui-grid-view">
	       
	    </ul>
	    <div class="bgcolor1" style="padding:10px 15px;">
	    	<div class="fs12 color2" style="padding-bottom:10px;">赠送数量</div>
	    	<div class="mui-table">
	    		<button type="button" class="mui-btn mui-active mui-col-xs-2">
					1
				</button>
				<button type="button" class="mui-btn mui-col-xs-2">
					5
				</button>
				<button type="button" class="mui-btn mui-col-xs-2">
					10
				</button>
				<button type="button" class="mui-btn mui-col-xs-2">
					20
				</button>
				<input class="mui-btn mui-col-xs-2" placeholder="其他"/>
			</div>
	    </div>
	    <div class="pd15">
			<button type="button" class="mui-btn mui-btn-primary mui-btn-block pd10">赠送</button>
		</div>
	</div>
</body>
<script src="./js/gift.js?v=${version}"></script>
</html>