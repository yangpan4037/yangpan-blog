/**
 * time 2017-08-21
 * name 杨潘 yangpan
 * tel  13541292110
 * mail 916780135@qq.com
 */
$(function () {
    $("#enterContributeBtn").click(function () {
        var dialogLayer = layer.msg('保存中。。。', {
            icon: 16
            , shade: 0.01
        });
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/article/' + username + '/saveArticle',
            type: 'POST',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "id": $.trim($('#articleId').val()),
                "title": $.trim($('#title').val()),
                "summary": $.trim($('#summary').val()),
                "content": $.trim($('#content').val()),
                "catalog": {"id": $.trim($('#category').val())},
                "tags": $.trim($('#tags').val())
            }),
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                layer.close(dialogLayer);
                if (data.success) {
                    layer.msg(data.message, {icon: 6}, function () {
                        window.location = data.body;
                    });
                } else {
                    layer.msg(data.message, function () {
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