<%@ page import="com.jfinal.kit.PropKit"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${base}/static/mui/css/mui.css?v=${version}">
<link rel="stylesheet" href="${base}/static/mui/css/iconfont.css?v=${version}">
<link rel="stylesheet" href="${base}/styles/css/style.css?v=${version}">
<link rel="stylesheet" href="${base}/styles/css/blue.css?v=${version}">
<%
	String openid="";
	Cookie[] cookies = request.getCookies();
	if(cookies!=null){
	    for(Cookie cookie : cookies){
	        if(cookie.getName().equals("openid"+PropKit.get("version"))){
	        	openid = cookie.getValue();
	        }
	    }
	}
%>
<script type="text/javascript">
    var base = '${base}';
    var imageUrl = "${imageUrl}";
    var openid="<%=openid%>";
</script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script src="${base}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script src="${base}/lib/layer-mobile/layer.js"></script> 
<script src="${base}/static/mui/js/mui.min.js"></script>
<script src="./common/wechat.js?v=${version}"></script>
<script type="text/javascript" src="${base}/dict/getItemType"></script> 
