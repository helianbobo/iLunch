<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="ilunch 后台"/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'gmain.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <g:layoutHead/>
    <g:javascript library="application"/>
</head>
<body>
<div class="container">
    <!--header begin-->
    <div class="header_body">
        <div class="header_wide">
            <div class="header">
                <div class="logo"></div>
                <div class="slogan"></div>
                <div class="place"><p>上海凌空科技园</p><p><a href="#">[切换其他地区]</a></p></div>
                <div class="clear"></div>
                %{--<div class="h_menu">
                    <ul>
                        <li class="on"><a href="#" class="h_m_1"></a></li>
                        <li><a href="#" class="h_m_2"></a></li>
                        <li><a href="#" class="h_m_3"></a></li>
                        <li><a href="#" class="h_m_4"></a></li>
                        <li><a href="#" class="h_m_5"></a></li>
                    </ul>
                </div>--}%
                <div class="login"><a href="#">登录</a> | <a href="#">注册</a></div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!--header end-->
    <g:layoutBody/>
</div>
</body>
</html>