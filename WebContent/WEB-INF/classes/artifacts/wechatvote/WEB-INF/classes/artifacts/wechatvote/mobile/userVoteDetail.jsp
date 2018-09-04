<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<meta name="description" content="票数详情">
<title>票数详情</title>
</head>

<body>
	<header id="header" class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">票数详情</h1>
	</header>
	<div class="mui-content">
	</div>
</body>
<script type="text/javascript">
var voteId=$.getUrlVars("voteId");
var itemId=$.getUrlVars("itemId");
mui.init({
	subpages:[{
		url:'userVoteDetailSub.jsp?voteId='+voteId+'&itemId='+itemId,
		id:'userVoteDetailSub',
		styles:{
			top: '45px',
			bottom: '0px',
		}
	}]
});
</script>
</html>