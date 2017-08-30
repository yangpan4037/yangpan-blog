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
 	var submitBtn=$('.submit-btn');
 	submitBtn.click(function(){
 		var psw=$('#psw');
 		if(psw.val()==''){
 			hintErrorInfo(psw,'请输入原密码');
 		}else{
 			hintSuccessInfo(psw,'密码正确');
 		}
 		var email=$('#email');
 		emailReg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
 		if(email.val()==''){
 			hintErrorInfo(email,'请填写电子邮箱');
 		}else{
 			if(!emailReg.test(email.val())){
 				hintErrorInfo(email,'邮箱格式错误，请检查邮箱格式');
	 		}else{
	 			hintSuccessInfo(email,'邮箱填写正确');
	 		}
 		}
 		if(flagFun(flagArr)){
 			$('.tab-body').hide();
 			$('.change-success').show();
 		}else flagArr=[];
 	});
 });