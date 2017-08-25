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
});