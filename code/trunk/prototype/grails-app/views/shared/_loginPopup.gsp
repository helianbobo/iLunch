<div id="logon_dialog" class="alert_div_3" style="position:absolute;display:none;z-index:2000">
    <div class="login_l">
        <ul>
            <li class="log_on"><a href="#">登 录</a></li>
            <li class="reg_off"><a href="#" id="logon_dialog_reg_btn">注 册</a></li>
        </ul>
    </div>

    <div class="login_2">
        <div class="title">
            <a href="#" class="closeLogonImage"><img src="${resource(dir: 'images', file: 'close.png')}"></a>
        </div>
        <form action="" onsubmit="$('#logon_dialog_confirm').click();">
        <ul>
            <li><strong>用户登录</strong><span class="error" id="dialog_err"></span></li>
            <li>手机号码：
                <input id="logon_dialog_un" class="log_srk" name="" type="text"/>
            </li>
            <li>用户密码：
                <input id="logon_dialog_pwd" class="log_srk" name="" type="password"/>

                <div><a href="#">忘记密码了怎么办？</a></div>
            </li>
            <li>
                <input id="logon_dialog_confirm" class="button_11" onmouseover="this.className = 'button_11_1'"
                       onmouseout="this.className = 'button_11'" name="" type="button"/>
                <input id="logon_dialog_cancel" class="button_12" onmouseover="this.className = 'button_12_1'"
                       onmouseout="this.className = 'button_12'" name="" type="button"/>
                <input type="submit" sytle="display:none" />
            </li>
        </ul>
        </form>
    </div>
</div>

<div id="reg_dialog" class="alert_div_4" style="position:absolute;display:none;z-index:2000">
    <div class="login_l">
        <ul>
            <li class="log_off"><a id="reg_dialog_logon_btn" href="#">登 录</a></li>
            <li class="reg_on"><a href="#">注 册</a></li>
        </ul>
    </div>

    <div class="login_3">
        <div class="title">
            <a href="#" class="closeLogonImage"><img src="${resource(dir: 'images', file: 'close.png')}"></a>
        </div>
        <form onsubmit="$('#reg_dialog_confirm').click();">
        <ul>
            <li><strong>用户注册</strong><span class="error" id="dialog_err_reg"></span></li>
            <li>手机号码：
                <input id="reg_dialog_phone" class="reg_srk" name="" type="text"/>

                <div>手机号码是您登录网站和接受取餐识别码的唯一凭证</div>
            </li>
            <li>用户密码：
                <input id="reg_dialog_pwd" class="reg_srk" name="" type="password"/>

                <div>密码少于12个字符，由英文和数字组成，区分大小写</div>
            </li>
            <li>确认密码：
                <input id="reg_dialog_pwd2" class="reg_srk" name="" type="password"/>
            </li>
            <li>绑定帐号：
                <input name="" type="checkbox" value=""/>
                <span>直接使用<strong>新浪微博</strong>账号</span></li>
            <li>
                <div>
                    <input id="reg_dialog_agree" name="" type="checkbox" value=""/>
                    <span>我同意接受网站协议 <a href="#">查看网站协议</a></span></div>
            </li>
            <li>
                <input id="reg_dialog_confirm" class="button_13" onmouseover="this.className = 'button_13_1'"
                       onmouseout="this.className = 'button_13'" name="" type="button"/>
                <input id="reg_dialog_cancel" class="button_12" onmouseover="this.className = 'button_12_1'"
                       onmouseout="this.className = 'button_12'" name="" type="button"/>
                <input type="submit" sytle="display:none" />
            </li>
        </ul>
        </form>
    </div>
</div>

<script type="text/javascript">
    function showLoginDialog() {
        $('#logon_dialog').fadeIn('slow', function(){});
        $('#logon_dialog').find('input')[0].focus();
    }
    $(document).ready(function() {

        ///////////////////////////////////////////////////////
        ////////////////// define templates ///////////////////
        ///////////////////////////////////////////////////////

        var notLoggedTmplt = '';

        var loggedOnTmplt = '当前用户：##PHONE_NO## ' +
        '<g:link controller="logout" action="index">登出</g:link>';

        ///////////////////////////////////////////////////////
        ///// wait until data's ready then render page ////////
        ///////////////////////////////////////////////////////

        var ilunch = $.ilunch_namespace("cn.ilunch");

        function renderUserInfo(event, user) {

            //render log start...
            $('#userId').val(user.id);

            $('#authLink').empty();
            if (user.id) {
                var phone = user.phoneNumber;
                var contact = user.nickname;
                var points = user.points;
                $('#authLink').append(loggedOnTmplt.replace(/##PHONE_NO##/g, phone).replace(/##CONTACT##/g, contact ? contact : "未提供联系人姓名"));
        		$('#authLink').append('<input type="hidden" value="'+phone+'" name="phone" />');
        		$('#authLink').append('<input type="hidden" value="'+contact+'" name="contact" />');
        		$('#authLink').append('<input type="hidden" value="'+points+'" name="points" />');
                //TODO validate pointChange balance
                $('#change_point').html(points ? points : 0);
                $('#point_control').css({
                            "display": "block"
                        });
           	}
            else {
                $('#authLink').append(notLoggedTmplt);
            }
            //render logic end
        }

        $('#authLink').bind('onLoggedIn', renderUserInfo);

        $('#logon_lnk').click(function(e) {
            ilunch.lockScreen();
            var sh = $(window).height();
            var sw = $(window).width();
            var w = $('#logon_dialog').outerWidth();
            var h = $('#logon_dialog').outerHeight();
            $('#logon_dialog').css({"left":((sw - w) / 2) + "px","top":((sh - h) / 2) + "px"});
            showLoginDialog();
        });

        function closeLogonDialog() {
            $('#logon_dialog').fadeOut('slow', function(){$('#logon_dialog').hide();});
            $('#reg_dialog').fadeOut('slow', function(){$('#reg_dialog').hide();});
            $('#dialog_err').html('');
            $('#dialog_err_reg').html('');
            ilunch.unlockScreen();
        }

        $('#logon_dialog_cancel').click(closeLogonDialog);

        $('.closeLogonImage').click(closeLogonDialog);

        $('#logon_dialog_reg_btn').click(function() {
            var x = $('#logon_dialog').css("left");
            var y = $('#logon_dialog').css("top");
            $('#logon_dialog').fadeOut('slow', function(){$('#logon_dialog').hide();});
            $('#reg_dialog').css({"left":x,"top":y});
            $('#reg_dialog').fadeIn('slow', function(){});
            $('#reg_dialog').find('input')[0].focus();
        });

        $('#reg_dialog_logon_btn').click(function() {
            var x = $('#reg_dialog').css("left");
            var y = $('#reg_dialog').css("top");
            $('#reg_dialog').fadeOut('slow', function(){$('#reg_dialog').hide();});
            $('#logon_dialog').css({"left":x,"top":y});
            showLoginDialog();
        });

        $('#reg_dialog_cancel').click(function() {
            $('#reg_dialog').fadeOut('slow', function(){$('#reg_dialog').hide();});
            ilunch.unlockScreen();
        });

        $('#logon_dialog_confirm').click(function() {
            var un = $('#logon_dialog_un').val();
            if (!un || un == '') {
                $('#dialog_err').html('请填写手机号码');
                return;
            }
            // get pwd
            var pwd = $('#logon_dialog_pwd').val();
            if (!pwd || pwd == '') {
                $('#dialog_err').html('请填写密码');
                return;
            }
            // call logon API

            ilunch.lockScreen();
            var status = null;
            ilunch.login(un, pwd, true, function(data) {
                if (data.error)
                    status = data.error;
                else if (data.success) {
                    //assign userId and user
                    ilunch.getCurrentUserInfo(function(data) {
//                        user = data;
                        $('#authLink').trigger('onLoggedIn', [data]);
                        status = 'OK';
                    });
                }
            });
            function wait() {
                if (status != null) {
                    if (status == 'OK') {
                        // re-render userinfo
//                        renderUserInfo();
                        $('#dialog_err').html('');
                        $('#logon_dialog').fadeOut('slow', function(){$('#logon_dialog').hide();});
                        ilunch.unlockScreen();
                    }
                    else {
                        // show error msg
                        $('#dialog_err').html(status);
                    }
                    clearInterval(p);
                }
            }

            var p = setInterval(wait, 50);
        });

        $('#reg_dialog_confirm').click(function() {
            // get un
            var un = $('#reg_dialog_phone').val();
            if (!un || un == '') {
                $('#dialog_err_reg').html('请填写手机号码！');
                return;
            }
            if(!(/^\d{11}$/.test(un))){
                $('#dialog_err_reg').html('请正确填写手机号码（11位数字）！');
                return;
            }
            // get pwd
            var pwd = $('#reg_dialog_pwd').val();
            if (!pwd || pwd == '') {
                $('#dialog_err_reg').html('请填写密码！');
                return;
            }
            var pwd2 = $('#reg_dialog_pwd2').val();
            if (!pwd || pwd == '') {
                $('#dialog_err_reg').html('请确认密码！');
                return;
            }
            if (pwd != pwd2) {
                $('#dialog_err_reg').html('两次输入的密码不一致！');
                return;
            }
            if(!$('#reg_dialog_agree').attr("checked")) {
                $('#dialog_err_reg').html('在使用我们的服务之前请仔细阅读《网站使用协议》！');
                return;
            }
            // call reg API
            ilunch.lockScreen();
            var status = null;
            ilunch.register(un, pwd, function(data) {
                if (data.error) {
                    if(data.error.errorCode == '07')
                        status = '该手机号已被注册！';
                    else
                    	status = data.error.message;
                }
                else {
                    ilunch.login(un, pwd, true, function(data) {
                        if (data.error)
                            ilunch.fatalError("logon failed!");
                        else if (data.success) {
                            //assign userId and user
                            ilunch.getCurrentUserInfo(function(data) {
//                                user = data;
                                $('#authLink').trigger('onLoggedIn', [data]);
                                alert('注册成功，已登录！');
                                status = 'OK';
                            });
                        }
                    });
                }
            });
            function wait2() {
                if (status != null) {
                    if (status == 'OK') {
                        // re-render userinfo
//                        renderUserInfo();

                        $('#dialog_err_reg').html('');
                        $('#reg_dialog').fadeOut('slow', function(){$('#reg_dialog').hide();});
                        ilunch.unlockScreen();
                    }
                    else {
                        // show error msg
                        $('#dialog_err_reg').html(status);
                    }
                    clearInterval(p2);
                }
            }

            var p2 = setInterval(wait2, 50);
        });

    });



</script>