<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/meta.jsp"%>
<title></title>
<style>
	.play{
		position:absolute;
		left:0;
		right:0;
		margin:0 auto;
		top:50px;
	}
</style>
</head>
<body>
	<video width="100%" id="video">
	     <source src="../styles/images/mov_bbb.mp4" type="video/mp4">
<!-- 	     <source src="http://27.152.180.13/vmind.qqvideo.tc.qq.com/h0200lkg92s.p202.1.mp4?vkey=2CCA5B5041166F6B87DA021B4C176B3C407B7351F4018A66A504123B0DF08EA0325ECB5C6334371E473C67914B805FD543B82560A36D6925EDF3303095559ADFE270EED0BDB5C45F1F7FC618338A3C33752046069AFC4861&platform=&sdtfrom=&fmt=hd&level=0" type="video/mp4"> -->
		  您的浏览器不支持 HTML5 video 标签。
	</video>
	<img alt="" src="../styles/images/play.png" class="play">
</body>
<script type="text/javascript">
	var i=0;
	mui.ready(function(){
		var myVideo=document.getElementById("video"); 
		$(".play").on("click",function(){
			if(i==0){
				alert("你还未购买该视频");
				i++;
				return;
			}
			myVideo.play();
			$(".play").hide();
		});
	});
</script>
</html>