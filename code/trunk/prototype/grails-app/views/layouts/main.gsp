<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>ilunch - <g:layoutTitle default=""/></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'jquery-1.5.2.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'jquery-ext.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'JSONUtil.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'date-zh-CN.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'dataapi.js')}"></script>

    <script type="text/javascript">

        (function($) {

            var ilunch = $.ilunch_namespace("cn.ilunch");
            ilunch.ROOT = '${resource(dir:'/')}';
            ilunch.IMG_ROOT = ilunch.ROOT+'/images/products/';
            ilunch.ReserveDay =
            ${grailsApplication.config.cn.ilunch.order.reserve.day}
        })(jQuery);
    </script>

    <g:layoutHead/>
    <g:javascript library="application"/>

</head>

<body>

<g:render template="/shared/loginPopup"/>

<div class="container">

    <g:layoutBody/>

    <g:render template="/shared/footer"/>
</div>

</body>
</html>