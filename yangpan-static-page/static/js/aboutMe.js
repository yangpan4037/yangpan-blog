/**
 * time	2017-05-12
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */

 $(function(){
 	var sexBtn=$('.sex a');
 	sexBtn.click(function(){
 		$(this).addClass('active')
 		.siblings().removeClass('active');
 	});
 	$('#birthday').keyup(function(){
 		$(this).val('');
 	});
 	function rightHintHide(obj){
 		obj.siblings('.hint').hide();
 		obj.parent().removeClass('error').addClass('right');
 	}
 	function hintErrorInfo(obj,text){
 		obj.siblings('.hint').css('color','#ff7200').text(text);
 		obj.parent().addClass('error');
 	}
 	function hintDefaultInfo(obj,text){
 		obj.siblings('.hint').show().css('color','#ccc').text(text);
 		obj.parent().removeClass('error right');
 	}
 	function hintSuccessInfo(obj,text){
 		obj.siblings('.hint').css('color','#ccc').text(text);
 		obj.parent().removeClass('error right');
 	}
 	var submitBtn=$('.submit-btn');
 	submitBtn.click(function(){
 		//昵称
 		var userName=$('#userName');
 		if(userName.val().length<1||userName.val().length>20){
 			hintErrorInfo(userName,'请填用户昵称，长度在1-20个字符');
 		}else{
 			rightHintHide(userName);
 		}
 		//真实姓名
 		var realName=$('#realName');
 		if(realName.val() ==""){
 			hintDefaultInfo(realName,'请输入真实姓名，20个英文或10个汉字');
 			realName.parent().removeClass('error right');
 		}else{
 			if(realName.val().length>20){
	 			hintErrorInfo(realName,'请填用真实，长度在1-20个字符');
	 		}else{
	 			rightHintHide(realName);
	 		}
 		}
 		//QQ
 		var qq=$('#QQ');
 		if(qq.val()!=''){
 			var regQQ=/^\d{5,10}$/;
	 		if(regQQ.test(qq.val())){
	 			rightHintHide(qq);
	 		}else{
				hintErrorInfo(qq,'请重新填写QQ，不要恶意乱填');
	 		}
 		}else{
 			hintSuccessInfo(qq,'QQ号码是您与卖家沟通最重要的工具之一，请务必填写');
 		}
 		//身份证号
 		var idCard=$('#idCard')
 		if(idCard.val()!=''){
 			var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	 		if(!regIdCard.test(idCard.val())){
	 			hintErrorInfo(idCard,'身份证号填写不正确，未能通过验证');
	 		}else{
	 			idCard.attr('disabled','disabled');
	 			hintSuccessInfo(idCard,'身份证号码是您在平台交易和使用的基本凭证，填写后将不可更改');
	 		}
 		}else{
 			hintSuccessInfo(idCard,'身份证号码是您在平台交易和使用的基本凭证，填写后将不可更改');
 		}
 	});
 	var selectDate=$('.birthday input');
	selectDate.datepicker({changeMonth:true,changeYear:true,yearRange:"1900:2050",dateFormat:'yy-mm-dd',inline:true,monthNamesShort:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],dayNamesMin:["日","一","二","三","四","五","六"],onSelect:function (dateText,inst){var theDate = new Date(Date.parse($(this).datepicker('getDate')));var dateFormatted = $.datepicker.formatDate('yy-mm-dd',theDate);}});
 });