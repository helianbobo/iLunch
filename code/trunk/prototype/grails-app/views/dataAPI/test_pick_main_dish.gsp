<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>无标题文档</title>
<link href="${resource(dir:'css', file:'main.css')}" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'jquery-1.5.2.min.js')}" ></script>
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'JSONUtil.js')}" ></script>
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'dataapi.js')}" ></script>
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'cart.js')}" ></script>
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'pick_main_dish.js')}" ></script>
</head>
<body>
<div class="container" style="background:#def5ff">
  <!--header begin-->
  <div class="header_body">
    <div class="header_wide">
      <div class="header">
        <div class="logo"></div>
        <div class="slogan"></div>
        <div class="place">
          <p>上海凌空科技园</p>
          <p><a href="#">[切换其他地区]</a></p>
          <input id="area_id" type="hidden" value="1" />
        </div>
        <div class="clear"></div>
        <div class="h_menu">
          <ul>
            <li class="on"><a href="#" class="h_m_1"></a></li>
            <li><a href="#" class="h_m_2"></a></li>
            <li><a href="#" class="h_m_3"></a></li>
            <li><a href="#" class="h_m_4"></a></li>
            <li><a href="#" class="h_m_5"></a></li>
          </ul>
        </div>
        <div class="login"><a href="#">登录</a> | <a href="#">注册</a></div>
        <div class="clear"></div>
      </div>
    </div>
  </div>
  <!--header end-->
  <div class="content">
    <div class="c_c">
      <div class="dd_1"></div>
      <div class="rili">
        <input id="last_month_btn" class="month_1" onmouseover="this.className='month_1_1'" onmouseout="this.className='month_1'" name="" type="button" value="" />
        <span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
        <input id="next_month_btn" class="month_2" onmouseover="this.className='month_2_1'" onmouseout="this.className='month_2'" name="" type="button" value="" />   
      </div>
      <div id="md_list" class="dd_list">
      </div>
      <div class="clear"></div>
        <div class="clear"></div>
      <div class="dd_list_bottom">共计：<span id="in_total"></span>元</div>
      <div class="dd_next_1">
      <form id="confirm_form" method="post">
      <input id="btn_confirm" class="next_1" onmouseover="this.className='next_1_1'" onmouseout="this.className='next_1'" name="" type="button" value="" />
      </form>
      </div>
    </div>
  </div>
  <!--footer begin-->
  <div class="footer">
    <p>© 2011 ilunch.com 沪ICP证070791号 沪公海网安备310108000700号</p>
  </div>
  <!--footer end-->
</div>
</body>
</html>
