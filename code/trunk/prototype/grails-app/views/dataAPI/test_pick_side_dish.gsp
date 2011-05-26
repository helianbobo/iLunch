<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>选择配菜</title>
    <meta name="layout" content="main"/>

    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'pick_side_dish.js')}"></script>
  </head>
  <body>

      <g:render template="/shared/header" model="[current:2]"/>
      <div class="content">
        <div class="c_c">
          <div class="dd_2"></div>
          <div class="pcbg">
            <div class="pc_l">
              <!-- div class="title">新菜推荐<img src="/prototype/images/new.gif" /></div>
        <div class="xc_li">
          <div class="xc_p"><a href="#"><img src="/prototype/images/pic_16.jpg" /></a></div>
          <div class="xc_t">东坡肉</div>
          <div class="cai_s">
            <div class="xc_sl">
              <input class="jianyi" name="" type="button" value="" />
              <input name="" type="text" class="shuliang" maxlength="3" />
              <input class="jiayi" name="" type="button" value="" /> 份
            </div>
            <input class="xuangou" onmouseover="this.className='xuangou_1'" onmouseout="this.className='xuangou'" name="" type="button" value="" />
          </div>
        </div>
        <div class="xc_li">
          <div class="xc_p"><a href="#"><img src="/prototype/images/pic_16.jpg" /></a></div>
          <div class="xc_t">东坡肉</div>
          <div class="cai_s">
            <div class="xc_sl">
              <input class="jianyi" name="" type="button" value="" /><input name="" type="text" class="shuliang" maxlength="3" /><input class="jiayi" name="" type="button" value="" /> 份</div>
            <input class="xuangou" onmouseover="this.className='xuangou_1'" onmouseout="this.className='xuangou'" name="" type="button" value="" />
          </div>
        </div>
        <div class="xc_li">
          <div class="xc_p"><a href="#"><img src="/prototype/images/pic_16.jpg" /></a></div>
          <div class="xc_t">东坡肉</div>
          <div class="cai_s">
            <div class="xc_sl">
              <input class="jianyi" name="" type="button" value="" /><input name="" type="text" class="shuliang" maxlength="3" /><input class="jiayi" name="" type="button" value="" /> 份</div>
            <input class="xuangou" onmouseover="this.className='xuangou_1'" onmouseout="this.className='xuangou'" name="" type="button" value="" />
          </div>
        </div>
        <div class="xc_li">
          <div class="xc_p"><a href="#"><img src="/prototype/images/pic_16.jpg" /></a></div>
          <div class="xc_t">东坡肉</div>
          <div class="cai_s">
            <div class="xc_sl">
              <input class="jianyi" name="" type="button" value="" /><input name="" type="text" class="shuliang" maxlength="3" /><input class="jiayi" name="" type="button" value="" /> 份</div>
            <input class="xuangou" onmouseover="this.className='xuangou_1'" onmouseout="this.className='xuangou'" name="" type="button" value="" />
          </div>
        </div> -->
              <div class="clear"></div>
              <div class="title1">更多配菜</div>
              <div id="tag_list" class="title1_select">分类标签：
                <a href="#">全部</a> | <a href="#" class="on">本帮菜</a> | <a href="#">川菜</a> | <a href="#">湘菜</a> | <a href="#">粤菜</a> | <a href="#">东北菜</a> | <a href="#">甜口味</a> | <a href="#">咸口味</a> | <a href="#">辣口味</a> | <a href="#">其他</a>
              </div>
              <div id="date_picker" class="alert_div_7" style="position:absolute;display:none;z-index:10000">
              	<input type="hidden" value="" />
                <div class="title_">选购下周的配菜，别忘了先将日历切换到下周哦~</div>
                <ul>
                </ul>
                <div class="btnbox_week" style="background:#FFF; color:#03F; text-align:center;"><a style="color:#0183C1; margin-right:30px;" onclick="lastWeek(this);">上一周</a> <a style="color:#0183C1;" onclick="nextWeek(this);">下一周</a></div>
              </div>
              <div id="sd_list">
              </div>

            </div>
            <div class="pc_2">
              <div class="title">
                <input onclick="lastWeek();" class="month_1" onmouseover="this.className = 'month_1_1'" onmouseout="this.className = 'month_1'" name="" type="button" value=""/>
                <span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
                <input onclick="nextWeek();" class="month_2" onmouseover="this.className = 'month_2_1'" onmouseout="this.className = 'month_2'" name="" type="button" value=""/>
              </div>
              <div class="pc_2_c">
                <ul id="cart_date" class="pc_time">
                </ul>
                <div class="clear"></div>
                <ul id="cart_dashboard" class="pc_p">
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
        <li><img src="/prototype/images/pic_17.jpg" /><div class="n">x10</div><div class="no"><a href="#"><img src="/prototype/images/no.png" /></a></div></li>
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
        <div class="clear"></div> -->
            </div>

            <div class="clear"></div>
            <div class="page">
              <input id="last_page_btn" class="p_up" onmouseover="this.className = 'p_up_1'" onmouseout="this.className = 'p_up'" name="" type="button" value=""/>
              <input id="next_page_btn" class="p_down" onmouseover="this.className = 'p_down_1'" onmouseout="this.className = 'p_down'" name="" type="button" value=""/>
              当前<span id="current_page">1</span>/<span id="total_page">3</span>页
            </div>
            <div class="price">共计：<span id="in_total"></span>元</div>
            <div class="clear"></div>
          </div>
          <div class="dd_next_1">
            <form id="confirm_form" method="post">
              <input id="btn_confirm_last" class="next_2" onmouseover="this.className = 'next_2_1'" onmouseout="this.className = 'next_2'" name="" type="button" value=""/>
              <input id="btn_confirm_next" class="next_1" onmouseover="this.className = 'next_1_1'" onmouseout="this.className = 'next_1'" name="" type="button" value=""/>
            </form>
          </div>
        </div>
      </div>

  </body>
</html>
