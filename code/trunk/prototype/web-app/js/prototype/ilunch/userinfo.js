$(document).ready(function($) {

    var undefined;

    var ilunch = $.ilunch_namespace("cn.ilunch");

    var userId = 3;

    ilunch.getCurrentUserInfo(function(data) {
        $("#userInfoTemplate").tmpl(data).appendTo(".mlc_l");
    });


});
var orgName;

function fillNickname() {
    orgName = $('#nickname').text();
    $('#nickname').html('<input type="text" id="nicknameInput" onblur="changeNickname(this,orgName)" value="' + orgName + '"/>');

        $('#nicknameInput').focus();
    }

    function changeNickname(event, orgName) {
        if (event.value != orgName) {
            if (window.confirm("确定要修改昵称吗？")) {
                $.ajax({
                    type:"post",
                    url:"/prototype/person/changeName",
                    data: {"nickName": event.value},
                    beforeSend: function(XMLHttpRequest) {

                    },
                    success: function(data, textStatus) {
                        event.parentNode.innerHTML  = event.value ;
                    },
                    complete: function(data, textStatus) {

                    },
                    error: function() {
                        event.parentNode.innerHTML = orgName; //出现错误时界面显示修改前的昵称
                    }
                });
            }
        } else {
            event.parentNode.innerHTML = orgName;
        }
    }

