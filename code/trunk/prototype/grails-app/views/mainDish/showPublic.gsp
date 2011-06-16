<%@ page import="cn.ilunch.domain.MainDish" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>主菜 ${mainDishInstance.name}</title>
    <meta name="layout" content="main"/>

    %{--    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
<script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'pick_main_dish.js')}"></script>--}%
</head>

<body>

<g:render template="/shared/header" model="[current:2]"/>

<div class="xq_body">
    <div class="tejia">
        <div class="mbx">当前位置：<g:link controller="dataAPI" action="pickMainDish">午餐预定</g:link> > 主菜详情介绍</div>

        <div class="maindish_content">
            <div class="tc_title">${mainDishInstance.name}</div>

            <div class="tc_content">${mainDishInstance.description}</div>

            <g:render template="/shared/purchaseNotice"/>
        </div>

        <div class="tejia_pic">
            <div class="tp_on"><a href="#"><img src="${resource(dir:'/')}${mainDishInstance.largeImageUrl}"/></a></div>

            <div class="tp_on_title_1">主菜：${mainDishInstance.name}实拍</div>

        </div>
    </div>
</div>

<div class="clear"></div>


<div class="n_c">
    <div class="left">

        <div class="text">
            <div class="title">产品故事： ${mainDishInstance.name}</div>
            ${mainDishInstance.story}
        </div>
    </div>

    <div class="right" style="background:#fff;">
        <div class="xq_pic"><img src="${resource(dir:'/')}${mainDishInstance.detailImageUrl}"/></div>
    </div>

    <div class="clear"></div>
</div>

</body>
</html>
