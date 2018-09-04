<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<style>
	.mui-table-view .mui-media-object {
	    line-height: 57px;
	    max-width: 76px;
	    height: 57px;
	}
</style>
</head>

<body>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper bgcolor1">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view">
			</ul>
		</div>
	</div>
</body>
<script src="./js/msgListSub.js?v=${version}"></script>
</html>