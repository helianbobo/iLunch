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
<script type="text/javascript" src="${resource(dir:'js/prototype/ilunch', file:'confirm_order.js')}" ></script>
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
          <p id="area_name">上海凌空科技园</p>
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
        <input type="hidden" id="user_id" value="3" />
        <div class="clear"></div>
      </div>
    </div>
  </div>
  <!--header end-->
  <div class="content">
    <div class="c_c">
      <div class="dd_4"></div>
      <div class="pcbg">
      <div class="pc_l">
        <div class="title">
          <p><strong>请核对信息，如信息正确请点击”在线支付”通过支付宝网上支付。如信息有误，请返回<a href="#">上一步</a>修改：</strong></p>
        </div>
        <div class="zf_list">
          <ul id="order_info">
            <li>
              <p><span class="stress">价格10元</span>尝鲜套餐：地中海之珠 + 酸酸豆角 + 蔬菜沙拉 + 苹果汁 </p>
              <p>配送时间：周一  2011-03-11 </p>
            </li>
            <li>
              <p><span class="stress">价格10元</span>尝鲜套餐：地中海之珠 + 酸酸豆角 + 蔬菜沙拉 + 苹果汁 </p>
              <p>配送时间：周一  2011-03-11 </p>
            </li>
            <li>
              <p><span class="stress">价格10元</span>尝鲜套餐：地中海之珠 + 酸酸豆角 + 蔬菜沙拉 + 苹果汁 </p>
              <p>配送时间：周一  2011-03-11 </p>
            </li>
            <li iclass="lxr">
              <p>联系电话：13800138000   联系人：老王</p>
              <p>配送地址：上海凌空园区 腾讯大厦 正门外</p>
            </li>
          </ul>
            <div class="pay_online">
              <p><span class="stress">一共需支付：<span id="in_total"></span>元<a href="@"><img src="/prototype/images/pay_online.png" /></a></span></p>
            </div>
        </div>
      </div>
      <div class="pc_2">
        <div class="title">
        <input id="last_week_btn" class="month_1" onmouseover="this.className='month_1_1'" onmouseout="this.className='month_1'" name="" type="button" value="" />
       	<span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
        <input id="next_week_btn" class="month_2" onmouseover="this.className='month_2_1'" onmouseout="this.className='month_2'" name="" type="button" value="" /> 
        </div>
        <div class="pc_2_c">
        <ul id="cart_date" class="pc_time">
        </ul>
        <div class="clear"></div>
        <ul id="cart_dashboard" class="pc_p">
	        <li>
	        	<img src="/prototype/images/pic_17.jpg" />
	        	<div class="n">x10</div>
	        	<div class="no"><a href="#"><img src="/prototype/images/no.png" /></a></div>
	        </li>
	        <li><img src="/prototype/images/pic_17.jpg" /></li>
	        <li><img src="/prototype/images/pic_17.jpg" /></li>
	        <li><img src="/prototype/images/zc_w.png" /></li>
	        <li><img src="/prototype/images/zc_y.png" /></li>
	        <li><img src="/prototype/images/pic_17.jpg" /></li>
	        <li><img src="/prototype/images/pic_17.jpg" /></li>
	        <li><img src="/prototype/images/pic_17.jpg" /></li>
	        <li><img src="/prototype/images/pc_w.png" /></li>
	        <li><img src="/prototype/images/pc_y.png" /></li>
        </ul>
        </div>
        <div class="clear"></div>        
        <!-- div class="title_1">劳动节调休，本周末上班我也供应午餐哦！</div>
        <div class="pc_2_c">
        
        <ul class="pc_time">
        <li>03/10周六</li><li>03/11周日</li><li></li><li></li><li></li>
        </ul>
        <div class="clear"></div>
        <ul class="pc_p">
        <li><img src="images/pic_17.jpg" /><div class="n">x10</div><div class="no"><a href="#"><img src="images/no.png" /></a></div></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        <li><img src="images/pic_17.jpg" /></li>
        </ul>
        
        </div>
        <div class="clear"></div> -->
        </div>
      <div class="clear"></div>
      </div>
      <div class="dd_next_1">
      <form id="confirm_form" method="post">
      <input id="btn_confirm_last" class="next_2" onmouseover="this.className='next_2_1'" onmouseout="this.className='next_2'" name="" type="button" value="" />
      <input id="btn_confirm_next" class="next_1" onmouseover="this.className='next_1_1'" onmouseout="this.className='next_1'" name="" type="button" value="" />
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
