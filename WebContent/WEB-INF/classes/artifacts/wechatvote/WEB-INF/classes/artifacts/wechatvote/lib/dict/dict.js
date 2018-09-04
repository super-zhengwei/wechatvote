var olstatus ={
	'00':'新建',
	'10':'接受',
	'15':'通知专线',
	'20':'完成',
	'99':'取消'
};
var paystatus = {
	'00':'未支付',
	'10':'已预付',
	'20':'已支付',
	'99':'退款'
}
var paymode = {
	'00':'支付宝',
	'10':'微信支付',
	'15':'转账支付',
	'20':'专线代收',
	'25':'导游代收'
}

var yesno = {
	'0':'否',
	'1':'是'
}

function renderOsStatus(renderType,val){
	if(renderType){
		if(renderType=='select'){
			
		}
		if(renderType=='label'){
			return olstatus[val]
		}
	}
	else{
		alert('请指定要render的类型!');
	}
}

function renderPayStatus(renderType,val){
	if(renderType){
		if(renderType=='select'){
			
		}
		if(renderType=='label'){
			return paystatus[val]
		}
	}
	else{
		alert('请指定要render的类型!');
	}
}

function renderPayMode(renderType,val){
	if(renderType){
		if(renderType=='select'){
			
		}
		if(renderType=='label'){
			return paymode[val]
		}
	}
	else{
		alert('请指定要render的类型!');
	}
}

function renderYesNo(renderType,val){
	if(renderType){
		if(renderType=='select'){
			
		}
		if(renderType=='label'){
			return yesno[val]
		}
	}
	else{
		alert('请指定要render的类型!');
	}
}