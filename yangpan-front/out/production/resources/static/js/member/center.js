/**
 * time    2017-05-12
 * name    杨潘 yangpan
 * tel    13541292110
 * mail 916780135@qq.com
 */

$(function () {
    var now = new Date(), hour = now.getHours();
    if (hour < 6) {
        $("#sayHello").text("凌晨好！")
    }
    else if (hour < 9) {
        $("#sayHello").text("早上好！")
    }
    else if (hour < 12) {
        $("#sayHello").text("上午好！")
    }
    else if (hour < 14) {
        $("#sayHello").text("中午好！")
    }
    else if (hour < 17) {
        $("#sayHello").text("下午好！")
    }
    else if (hour < 19) {
        $("#sayHello").text("傍晚好！")
    }
    else if (hour < 22) {
        $("#sayHello").text("晚上好！")
    }
    else {
        $("#sayHello").text("夜里好！")
    }
});