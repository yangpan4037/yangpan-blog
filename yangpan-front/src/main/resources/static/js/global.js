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
});