/**
 * time	2017-05-12
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */
$(function(){
	
	//底部位置
	var bodyHeight = $("body").height();
	function footBarSite(){
		console.log("window:"+ $(window).height())
		console.log("document:"+$(document).height())
		console.log("body:"+$("body").height())
		console.log("footer:"+$("#footer").height());

		var windowHeight = $(window).height();
		var footerHeight = $("#footer").height();

		if(bodyHeight < windowHeight){
			$("body").height(windowHeight - footerHeight);
			$("#footer").css({
				"position":"absolute",
				"bottom":"0",
				"left":"0"
			});
		}else{
			$("body").height(bodyHeight - footerHeight);
			$("#footer").removeAttr("style");
		}
	}
	footBarSite();
	$(window).resize(function(){
		footBarSite();
	});


	//checkbox
	var checkBox=$("input[type='checkbox']");
	function checkCheckbox(obj){
		if(obj.prop('checked')){
			obj.parent().addClass('yangpan-checkbox-on-style');
		}else{
			obj.parent().removeClass('yangpan-checkbox-on-style');
		}
	}
	checkBox.each(function() {
		checkCheckbox($(this));
	});
	checkBox.click(function(){
		checkCheckbox($(this));
	});


	//退出
    $("#headerLogoutBtn").click(function(){
        var dialogLayer = layer.msg('请稍后。。',{
            icon: 16,
            shade: 0.01
        });
        $.ajax({
            url: "/logout",
            dataType:"json",
            type: 'POST',
            data:{
                "_csrf":"[[${_csrf.token}]]"
            },
            beforeSend: function(request) {
                request.setRequestHeader('[[${_csrf.token}]]', '[[${_csrf.headerName}]]'); //添加CSRF Token
            },
            success: function(result){
                layer.close(dialogLayer);
                layer.msg('成功退出！');
                setTimeout(function(){
                    location.href="/index";
                },1000);
            },
            error : function() {
                layer.close(dialogLayer);
                layer.msg('请求失败！请刷新重试~');
            }
        });
    });
});