$(document).ready(function($){

    var undefined;

    var ilunch = $.ilunch_namespace("cn.ilunch");

    var userId = 3;

    ilunch.getCurrentUserInfo(function(data){
        $("#userInfoTemplate").tmpl(data).appendTo(".mlc_l");
    });

});