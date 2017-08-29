/**
 * time 2017-08-21
 * name 杨潘 yangpan
 * tel  13541292110
 * mail 916780135@qq.com
 */
$(function () {

    //获取评论列表
    function getCommnet(articleId) {
        var dialogLayer = layer.msg('请稍后。。。', {
            icon: 16
            , shade: 0.01
        });
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/comments/article/' + articleId,
            type: 'GET',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (result) {
                layer.close(dialogLayer);
                $("#articleCommentsWrap").html(result);
            },
            error: function () {
                layer.close(dialogLayer);
                layer.msg('请求失败，请刷新后重试！', function () {
                });
            }
        });
    }

    getCommnet(articleId);

    //评论
    $("#commentBtn").click(function () {
        var dialogLayer = layer.msg('请稍后。。。', {
            icon: 16
            , shade: 0.01
        });
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/comments/article/' + articleId,
            type: 'POST',
            data: {"commentContent": $('#commentContent').val()},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (result) {
                layer.close(dialogLayer);
                if (result.success) {
                    layer.msg('评论成功！');
                    // 清空评论框
                    $('#commentContent').val('');
                    // 获取评论列表
                    getCommnet(articleId);
                } else {
                    layer.msg(result.message, function () {
                    });
                }
            },
            error: function () {
                layer.close(dialogLayer);
                layer.msg('请求失败，请刷新后重试！', function () {
                });
            }
        });
    });

    //删除评论
    $(document).on("click","#deleteCommentBtn",function(){
        var dialogLayer = layer.msg('请稍后。。。', {
            icon: 16
            , shade: 0.01
        });
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/comments/'+$(this).attr("commentId")+'?articleId='+articleId,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result){
                if (result.success) {
                    layer.msg(result.message);
                    setTimeout(function(){
                        getCommnet(articleId);
                    },1500);
                } else {
                    layer.msg(result.message, function () {
                    });
                }
            },
            error : function() {
                layer.close(dialogLayer);
                layer.msg('请求失败，请刷新后重试！', function () {
                });
            }
        });
    });

    //点赞
    $("#admireBtn").click(function(){
        var that = $(this);
        var dialogLayer = layer.msg('请稍后。。。', {
            icon: 16
            , shade: 0.01
        });
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        if(that.hasClass("active")){
            $.ajax({
                url: '/votes/cancel/'+$(this).attr('voteId')+'?articleId='+articleId,
                type: 'DELETE',
                beforeSend: function(request) {
                    request.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function(result){
                    layer.close(dialogLayer);
                    if (result.success) {
                        layer.msg(result.message);
                        that.removeClass("active");
                        setTimeout(function(){
                            location.reload();
                        },1500);
                    } else {
                        layer.msg(result.message, function () {
                        });
                    }
                },
                error : function() {
                    layer.close(dialogLayer);
                    layer.msg('请求失败，请刷新后重试！', function () {
                    });
                }
            });
        }else{
            $.ajax({
                url: '/votes/article/'+articleId,
                type: 'POST',
                beforeSend: function(request) {
                    request.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function(result){
                    layer.close(dialogLayer);
                    if (result.success) {
                        layer.msg(result.message);
                        setTimeout(function(){
                            location.reload();
                        },1500);
                    } else {
                        layer.msg(result.message, function () {
                        });
                    }
                },
                error : function() {
                    layer.close(dialogLayer);
                    layer.msg('请求失败，请刷新后重试！', function () {
                    });
                }
            });
        }
    });

    //文章目录
    $("#articleContent").find("h1,h2,h3,h4,h5,h6").each(function(index,el){
        $(el).attr("id","articleAnchor"+index);
        var tagName = $(el).get(0).localName;
        var html = "<a class='directory "+tagName+"' href='#articleAnchor"+index+"'>" + $(el).html() + "</a>"
        $("#articleDirectory").append(html);
    });

});