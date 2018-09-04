<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/meta.jsp"%>
<title>菜单编辑</title>
<style>

body {
    background-color: #f2f2f2;
}

@media ( max-width : 767px) {
	.form-horizontal .form-label {
		text-align: right;
	}
	.responsive .input-text, .btn, .responsive .input-text.size-M,
		.responsive .btn.size-M {
		font-size: 14px;
		height: 31px;
	}
}

.icon_lists li {
	float: left;
	width: 50px;
	height: 50px;
	margin: 5px;
	text-align: center;
	background-color: #fff;
    border: 1px solid transparent;
}
.active{
	border: 1px solid #0a6999 !important;
}
.icon_lists li:hover {
	border-color: #ddd;
}
.icon_lists .Hui-iconfont {
	font-size: 24px;
	line-height: 50px;
	color: #333;
}

@media ( max-width :450px) {
	.icon_lists li {
		width: 50%;
	}
}
</style>
</head>
<body>
	<div class="page-container">
		<ul class="icon_lists cl">
			<li><i class="icon Hui-iconfont">&#xe60d;</i></li>
			<li><i class="icon Hui-iconfont">&#xe62c;</i></li>
			<li><i class="icon Hui-iconfont">&#xe6cc;</i></li>
			<li><i class="icon Hui-iconfont">&#xe616;</i></li>
			<li><i class="icon Hui-iconfont">&#xe62e;</i></li>
			<li><i class="icon Hui-iconfont">&#xe613;</i></li>
			<li><i class="icon Hui-iconfont">&#xe636;</i></li>
			<li><i class="icon Hui-iconfont">&#xe687;</i></li>
			<li><i class="icon Hui-iconfont">&#xe637;</i></li>
			<li><i class="icon Hui-iconfont">&#xe691;</i></li>
			<li><i class="icon Hui-iconfont">&#xe692;</i></li>
			<li><i class="icon Hui-iconfont">&#xe620;</i></li>
			<li><i class="icon Hui-iconfont">&#xe6bb;</i></li>
			<li><i class="icon Hui-iconfont">&#xe623;</i></li>
			<li><i class="icon Hui-iconfont">&#xe667;</i></li>
			<li><i class="icon Hui-iconfont">&#xe665;</i></li>
			<li><i class="icon Hui-iconfont">&#xe604;</i></li>
			<li><i class="icon Hui-iconfont">&#xe60b;</i></li>
			<li><i class="icon Hui-iconfont">&#xe609;</i></li>
			<li><i class="icon Hui-iconfont">&#xe60c;</i></li>
			<li><i class="icon Hui-iconfont">&#xe61d;</i></li>
			<li><i class="icon Hui-iconfont">&#xe610;</i></li>
			<li><i class="icon Hui-iconfont">&#xe61e;</i></li>
			<li><i class="icon Hui-iconfont">&#xe61a;</i></li>
			<li><i class="icon Hui-iconfont">&#xe618;</i></li>
			<li><i class="icon Hui-iconfont">&#xe719;</i></li>
			<li><i class="icon Hui-iconfont">&#xe71c;</i></li>
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".icon_lists").on("click","li",function(){
				//$(this).toggleClass('active').siblings().removeClass("active");
				var index = parent.layer.getFrameIndex(window.name);
				parent.getIcon($(this).find("i").text());
				parent.layer.close(index);
			});
		});
	</script>
</body>
</html>