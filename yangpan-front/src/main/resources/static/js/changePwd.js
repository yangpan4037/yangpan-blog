/**
 * time	2017-05-12
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */

$(function() {
	var flagArr = [];

	function flagFun(f) {
		var index = f.indexOf(false);
		return index == -1 ? true : false;
	}

	function hintErrorInfo(obj, text) {
		obj.siblings('.hint').css('color', '#ff7200').text(text);
		obj.parent().addClass('error');
		flagArr.push(false);
	}

	function hintSuccessInfo(obj, text) {
		obj.siblings('.hint').css('color', '#2bb8aa').text(text);
		obj.parent().removeClass('error');
		obj.parent().addClass('right');
		flagArr.push(true);
	}
	var submitBtn = $('.submit-btn');
	var pswReg = /[0-9a-zA-Z]{6,18}/;
	submitBtn.click(function() {
		var psw = $('#psw');
		if (psw.val() == '') {
			hintErrorInfo(psw, '请输入原密码');
		} else {
			hintSuccessInfo(psw, '密码正确');
		}
		var newPsw = $('#newPsw');
		if (newPsw.val() == '') {
			hintErrorInfo(newPsw, '请输入新密码');
		} else {
			if (!pswReg.test(newPsw.val())) {
				hintErrorInfo(newPsw, '密码长度6~18之间，只能包含字母、数字和下划线');
			} else {
				hintSuccessInfo(newPsw, '密码可以使用');
			}
		}
		var affirmPsw = $('#affirmPsw');
		if (affirmPsw.val() == '') {
			hintErrorInfo(affirmPsw, '请确认新密码');
		} else {
			if (affirmPsw.val() != newPsw.val()) {
				hintErrorInfo(affirmPsw, '两次密码不一致，请重新输入');
			} else {
				if (!pswReg.test(affirmPsw.val())) {
					hintErrorInfo(affirmPsw, '两次密码一致，但不能用！');
				} else {
					hintSuccessInfo(affirmPsw, '两次密码一致！');
				}
			}
		}
		if (flagFun(flagArr)) {
			$('.tab-body').hide();
			$('.change-success').show();
		} else flagArr = [];
	});
});