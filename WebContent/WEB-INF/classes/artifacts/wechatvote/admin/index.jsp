<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../admin/meta.jsp"%> 
<title>${siteName}后台管理</title>
</head>
<body>
<%@ include file="../admin/header.jsp"%> 
<%@ include file="../admin/menu.jsp"%> 

<!-- content start -->
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="${base}/admin/welcome.jsp">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${base}/admin/welcome.jsp"></iframe>
		</div>
	</div>
</section>
<!-- content end -->

<%@ include file="../admin/footer.jsp"%> 
<script type="text/javascript">
$(document).ready(function(){
	getMenu();
});
/*加载菜单*/
function getMenu(){
	ajaxPost("${base}/sysRoleMenu/getMenu",{},function(data){
		var html="";
		$.each(data,function(id1,item1){
			html +='<dl>';
			html +='<dt><i class="Hui-iconfont">'+item1.menu_icon+'</i> '+item1.menu_name+'<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>';
			if(item1.children&&(item1.children).length>0){
				html +='<dd><ul>';
				$.each(item1.children,function(id2,item2){
					html +='<li><a _href="${base}'+nullToSpace(item2.menu_url)+'" data-title="'+item2.menu_name+'" href="javascript:void(0)">'+item2.menu_name+'</a></li>';
				});
				html +='</ul></dd>';
			}
			html +='</dl>';
		});
		$(".menu_dropdown").html(html);
		$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	});
}
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
</body>
</html>
