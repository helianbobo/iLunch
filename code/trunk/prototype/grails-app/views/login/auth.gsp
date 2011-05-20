<head>
    <meta name='layout' content='main'/>
    <title>登录</title>
    %{--<style  ttype='text/css' media='screen'>
    #login {
        margin: 15px 0px;
        padding: 0px;
        text-align: center;
    }

    #login .inner {
        width: 260px;
        margin: 0px auto;
        text-align: left;
        padding: 10px;
        border-top: 1px dashed #499ede;
        border-bottom: 1px dashed #499ede;
        background-color: #EEF;
    }

    #login .inner .fheader {
        padding: 4px;
        margin: 3px 0px 3px 0;
        color: #2e3741;
        font-size: 14px;
        font-weight: bold;
    }

    #login .inner .cssform p {
        clear: left;
        margin: 0;
        padding: 5px 0 8px 0;
        padding-left: 105px;
        border-top: 1px dashed gray;
        margin-bottom: 10px;
        height: 1%;
    }

    #login .inner .cssform input[type='text'] {
        width: 120px;
    }

    #login .inner .cssform label {
        font-weight: bold;
        float: left;
        margin-left: -105px;
        width: 100px;
    }

    #login .inner .login_message {
        color: red;
    }

    #login .inner .text_ {
        width: 120px;
    }

    #login .inner .chk {
        height: 12px;
    }
    </style>--}%
</head>

<body>

<g:render template="/shared/header"/>

<!-- 登录注册_登录-->
<div class="alert_div_3" >
    %{--<div class="login_l">
        <ul>
            <li class="log_on"><a href="#">登 录</a></li>
            <li class="reg_off"><a href="#">注 册</a></li>
        </ul>
    </div>--}%

    <g:if test='${flash.message}'>
        <div class='login_message'>${flash.message}</div>
    </g:if>

    <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
        <div class="login_2">
            <div class="title">
                %{--<a href="#"><img src="images/close.png"/></a>--}%
            </div>
            <ul>
                <li><strong>用户登录</strong></li>
                <li>手机号码：
                    <input type='text' class='log_srk' name='j_username' id='username'/>
                </li>
                <li>用户密码：
                    <input type='password' class='log_srk' name='j_password' id='password'/>

                    <div><a href="#">忘记密码了怎么办？</a></div>
                </li>
                <li>
                    <input class="button_11" onmouseover="this.className = 'button_11_1'" value=""
                           onmouseout="this.className = 'button_11'" name="" type="submit"/>
                    <input class="button_12" onmouseover="this.className = 'button_12_1'" value=""
                           onmouseout="this.className = 'button_12'" name="" type="reset"/>
                </li>
            </ul>
        </div>
    </form>
</div>
<!-- 登录注册 end-->


%{--<div id='login'>
    <div class='inner'>
        <g:if test='${flash.message}'>
            <div class='login_message'>${flash.message}</div>
        </g:if>
        <div class='fheader'>Please Login..</div>

        <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
            <p>
                <label for='username'>Login ID</label>
                <input type='text' class='text_' name='j_username' id='username'/>
            </p>

            <p>
                <label for='password'>Password</label>
                <input type='password' class='text_' name='j_password' id='password'/>
            </p>

            <p>
                <label for='remember_me'>Remember me</label>
                <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                       <g:if test='${hasCookie}'>checked='checked'</g:if>/>
            </p>

            <p>
                <input type='submit' value='Login'/>
            </p>
        </form>
    </div>
</div>--}%
<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
