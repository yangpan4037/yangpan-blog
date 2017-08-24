/**
 * time	2017-05-12
 * name	杨潘 yangpan
 * tel	13541292110
 * mail 916780135@qq.com
 */

$(function() {

	//关键词搜索
	$("#headerSearchKeywordBtn").click(function(){
        getArticlesBykeyword(0,1);
	});

    //最新、最热切换
    $(".header-article-state-btn").click(function(){
        var dialogLayer = layer.msg('加载中，请稍后。。',{
            icon: 16,
            shade: 0.01
        });
        var that= $(this);
        $.ajax({
            url: "/index",
            type: 'GET',
            data:{
                "order":$(this).attr("order"),
                "async":true
            },
            success: function(result){
                layer.close(dialogLayer);
                $("#articleCallbackWrap").html(result);
                $(".header-article-state-btn").removeClass("active");
                that.addClass("active");
                $("#headerSearchKeyword").val('');
            },
            error : function() {
                layer.close(dialogLayer);
                layer.msg('请求失败！请刷新重试~');
            }
        });
    });

    function getArticlesBykeyword(pageIndex, pageSize) {
        var dialogLayer = layer.msg('加载中，请稍后。。',{
            icon: 16,
            shade: 0.01
        });
        $.ajax({
            url: "/index",
            contentType : 'application/json',
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "keyword":$("#headerSearchKeyword").val()
            },
            success: function(result){
                layer.close(dialogLayer);
                $("#articleCallbackWrap").html(result);
                var keyword = $("#headerSearchKeyword").val();
                //如果是分类查询，则取消最新、最热选中样式
                if (keyword.length > 0) {
                    $(".header-article-state-btn").removeClass("active");
                }
            },
            error : function() {
                layer.close(dialogLayer);
                layer.msg('请求失败！请刷新重试~');
            }
        });
    }

	//点击切换动画速度，slow，first 也可以设置毫秒数
	var animateSpeed = 500;
	//自动切换的间隔毫秒
	var intervalTime = 3000; //只能用数字

	//获取最外层容器banner-wrap
	var bannerWrap = $(".banner-wrap");
	bannerWrap.each(function() {
		var banner = $(this).find(".banner");
		var bannerLen = banner.find("li").length;
		var bannerWidth = $(this).width();
		var prevBtn = $(this).find(".prev");
		var nextBtn = $(this).find(".next");
		var timer = null;
		var index = 0;

		//初始化banner的位置
		banner.css("marginLeft", -bannerWidth + "px");

		//创建小圆点
		var iconHtml = '<ul class="icon-wrap">';
		for (var i = 0; i < bannerLen; i++) {
			iconHtml += '<li></li>';
		}
		iconHtml += '</ul>';
		$(this).append(iconHtml);
		var icons = $(this).find(".icon-wrap li");
		icons.eq(0).addClass("active");

		//获取收尾的li，用于克隆
		var firstLi = banner.find("li:first").html();
		var lastLi = banner.find("li:last").html();

		//获取最后一个放到开始位置
		banner.prepend("<li>" + lastLi + "</li>");

		//获取最第一个放到最后位置
		banner.append("<li>" + firstLi + "</li>");

		//左边点击切换
		prevBtn.click(function() {
			if (parseInt(banner.css("marginLeft")) <= -bannerWidth * bannerLen) {
				banner.css("marginLeft", 0);
				index = -1;
			}
			cutAnimate("-", 1);
		});

		//右边点击切换
		nextBtn.click(function() {
			if (parseInt(banner.css("marginLeft")) >= -bannerWidth * 1) {
				banner.css("marginLeft", -bannerWidth * (bannerLen + 1) + "px");
				index = bannerLen;
			}
			cutAnimate("+", -1);
		});

		//点击小圆点
		icons.click(function() {
			var index = $(this).index() + 1;
			banner.animate({
				"marginLeft": -bannerWidth * index + "px"
			}, animateSpeed);
			$(this).addClass("active").siblings("li").removeClass("active");

		});

		//开启定时器
		startCutAnimate();

		//开关定时器
		$(this).mouseover(function() {
			stopCutAnimate();
		}).mouseout(function() {
			startCutAnimate();
		});

		//切换动画
		function cutAnimate(symbol, num) {
			if (!banner.is(":animated")) {
				banner.animate({
					"marginLeft": symbol + "=" + bannerWidth + "px"
				}, "first");
				iconChange(num);
			}
		}
		//小圆点高亮
		function iconChange(num) {
			index = index + num;
			icons.eq(index).addClass("active").siblings("li").removeClass("active");
		}

		//开启定时器
		function startCutAnimate() {
			//设表先关
			stopCutAnimate();
			timer = setInterval(function() {
				prevBtn.click();
			}, intervalTime);
		}

		//清除动画
		function stopCutAnimate() {
			clearInterval(timer);
		}

	});

	//随机颜色
	var getRandomColor = function() {
		return '#' +
		(function(color) {
			return (color += '0123456789abcdef' [Math.floor(Math.random() * 16)]) && (color.length == 6) ? color : arguments.callee(color);
		})('');
	}

	$("#hotTags .item").each(function(index,el){
		var randomColor = getRandomColor();
		$(this).css({
			"border":"2px solid "+randomColor
		}).hover(function(){
			$(this).css({"background":randomColor,"color":"#fff"});
		},function(){
			$(this).css({"background":"none","color":"#333"});
		});
	});
	console.log(getRandomColor())
});