var itemType=[];
$(document).ready(function(){
	$(".query-text").keydown(function(e){
		if(e.keyCode==13){
		   if($.trim($(this).val())!=""){
			   query();
		   }
		}
	});
	$(".query-select").change(function(e){
		query();
	});
});
$.extend({
	getUrlVars :function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r==null){
			r = window.parent.location.search.substr(1).match(reg);
		}
		if (r!=null) return unescape(r[2]); return null;
	}
});


//把null转为空
function nullToSpace(param){
	if(param == null || typeof param == 'undefined'){
		param = '';
	}
	return param;
}

function getTypeName(typeCode){
	var name = "";
	$.each(itemType,function(id,item){
		if(item.dict_key==typeCode){
			name = item.dict_value;
		}
	});
	return name;
}

function ajaxPost(url,params,callback){
	var index=null;
	$.ajax({
		type:"POST",
		url:url,
		data:params,
		dataType:"json",
		timeout:10000,
		beforeSend:function(){
			index=layer.load("加载中...");
		},
		success:function(data){
			if(index){
				layer.close(index);
			}
			if(typeof callback=='function'){
				callback(data);
			}
		},
		error:function(){
			if(index){
				layer.close(index);
			}
			layer.msg("请求失败，请联系管理人员!");
		},
		complete: function (XHR, TS) { XHR = null; } 
	});
}

function toDecimal(x) { 
    var f = parseFloat(x); 
    if (isNaN(f)) { 
       return; 
    } 
    f = Math.round(x*100)/100; 
    return f; 
}

Date.prototype.format = function(fmt){ 
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

Array.prototype.removeAt=function(Index){
   if(isNaN(Index)||Index>this.length){return false;}
   for(var i=0,n=0;i<this.length;i++)
   {
    if(this[i]!=this[Index])
    {
       this[n++]=this[i]
    }
   }
   this.length-=1
}