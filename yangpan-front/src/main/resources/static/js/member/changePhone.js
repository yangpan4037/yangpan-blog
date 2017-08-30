/**
 * time	2017-05-12
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */

 $(function(){
 	var flagArr=[];
 	function flagFun(f){
 		var index=f.indexOf(false);
 		return index==-1? true:false;
 	}
 	function rightHintHide(obj){
 		obj.siblings('.hint').hide();
 		obj.parent().removeClass('error right');
 		flagArr.push(true);
 	}
 	function hintNoIconInfo(obj,text){
 		obj.siblings('.hint').show().css('color','#ff7200').text(text);
 		obj.parent().removeClass('error right');
 		flagArr.push(false);
 	}
 	function hintErrorInfo(obj,text){
 		obj.siblings('.hint').css('color','#ff7200').text(text);
 		obj.parent().addClass('error');
 		flagArr.push(false);
 	}
 	function hintSuccessInfo(obj,text){
 		obj.siblings('.hint').css('color','#2bb8aa').text(text);
 		obj.parent().removeClass('error');
 		obj.parent().addClass('right');
 		flagArr.push(true);
 	}
 	var phone=$('#phone');
	function checkPhone(){
		if(phone.val()==''){
			hintErrorInfo(phone,'手机号码不能为空');
			return false;
		}else{
			var phonerReg=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
			if(phonerReg.test(phone.val())){
				hintSuccessInfo(phone,'手机号码正确');
				return true;
			}else{
				hintErrorInfo(phone,'请填写正确的手机号码');
				return false;
			}
		}
	}
	var safetyCode=$('#safetyCode');
	function checkCode(){
		if(safetyCode.val()==''){
			hintNoIconInfo(safetyCode,'验证码不能为空');
		}else{
			var codeReg=/^\d{6}$/;
			if(codeReg.test(safetyCode.val())){
				rightHintHide(safetyCode);
			}else{
				hintNoIconInfo(safetyCode,'验证码错误');
			}
		}
	}

	var sendCode=$('#sendCode');
	function sendCodeState(flag,text){
		sendCode.prop({
			'disabled':flag
		}).val(function(){
			return text;
		});
	}
	sendCodeState(false,'发送短信');
	sendCode.click(function(){
		var flag=checkPhone();
		if(flag){
			var timer=null;
			var wait=60;
			sendCodeState(true,'重发'+wait+'S');
			timer=setInterval(function(){
				if(wait==1){
					sendCodeState(false,'发送短信');
					clearInterval(timer);
				}
				else{
					wait--;
					sendCode.val(function(){
						return '重发'+wait+'S';
					});
				}
			},1000);
		}
	});
 	
 	var submitBtn=$('.submit-btn');
 	submitBtn.click(function(){
 		checkPhone();
 		checkCode();
 		if(flagFun(flagArr)){
 			$('.tab-body').hide();
 			$('.change-success').show();
 		}else flagArr=[];
 	});
 });