<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${base}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${base}/lib/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${base}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${base}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${base}/admin/common/common.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>${siteName}后台登录</title>
<script type="text/javascript">
	$(document).ready(function(){
        if(top.location != self.location){
      		top.location=self.location;  
        }  
		if ($.cookie("rmbUser")=="true") { 
			if($.cookie("username")){
				$("#username").val($.cookie("username"));
			}
			if($.cookie("password")){
				$("#remember").prop("checked", true);
				$("#password").val($.cookie("password")); 
			}else{
				$('#password').focus();
			}
		}else{
			$('#username').focus();
		}
		document.onkeydown = function (e) {
			var theEvent = window.event || e;
			var code = theEvent.keyCode || theEvent.which;
			if (code == 13) {
			    $("input[type=button]").click();
			}
		};
		$("input[type=button]").on("click",function(){
			var username=$.trim($('#username').val());
			var password=$.trim($('#password').val());
			if(!username){
				layer.msg("请输入用户名!");
				$("#username").focus();
				return;
			}
			if(!password){
				layer.msg("请输入密码!");
				$("#password").val('');
				$("#password").focus();
				return;
			}
			ajaxPost("${base}/sysUser/login",{'username':username,'password':password},function(data){
				if(!data.success){
					layer.msg(data.msg);
					$("#username").focus();
					return;
				}else{
					if($('#remember').prop('checked')){//记住密码
						$.cookie("rmbUser", "true", { expires: 7 }); 
						$.cookie("username", username, { expires: 7 }); 
						$.cookie("password", password, { expires: 7 }); 
					}else{
						$.cookie("rmbUser", "true", { expire: 7 }); 
						$.cookie("username", username, { expires: 7 }); 
						$.cookie("password", "", { expires: -1 }); 
					}
					window.location.href="${base}/admin";
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="header"></div>
	<div class="loginWraper">
		<div class="cl">
			<div class="cloud1 col-xs-4"></div>
			<div class="cloud2 col-xs-4"></div>
			<div class="cloud3 col-xs-4"></div>
		</div>
		<div id="loginform" class="loginBox">
			<div class="login"></div>
			<form class="form form-horizontal" id="loginform">
				<div class="row cl">
					<i class="Hui-iconfont">&#xe60d;</i>
					<div class="formControls col-xs-12">
						<input id="username"  type="text"
							placeholder="请输入用户名" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-xs-12">
						<input id="password" type="password"
							placeholder="请输入密码" class="input-text size-L">
					</div>
				</div>
				<div class="row cl" style="margin-top:5px;">
					<div class="formControls col-xs-12">
						<label for="online"> <input type="checkbox" name="online"
							id="remember" value=""> 记住密码
						</label>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-12">
						<input name="" type="button" class="btn btn-success radius size-L"
							value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" style="width:100%">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">温馨提示：为了让您能更好的体验，请使用IE浏览器（8.0以上版本）、或其他支持HTML5的第三方浏览器</div>
</body>
</html>