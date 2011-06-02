<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>

    <title>确认信息</title>
    <meta name="layout" content="main"/>


    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'confirm_info.js')}"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
  </head>
  <body>

      <g:render template="/shared/header" model="[current:2]"/>

      <div class="content">
      
        <div class="c_c">
          <div class="dd_3"></div>
          <div class="pcbg">
            <div class="pc_l">

              <div id="user_info">

              </div>
              <div id="area_selector" class="qc_place">
                <div class="title">取餐的位置：##AREA_NAME##
                  <select id="building_list" name="" size="1">
                  </select>
                </div>
                <p>此套餐供应区仅限<span class="stress">##AREA_NAME##</span><br/>
                  如果您在其他地区，<a href="#">点击这里</a>，为你推荐其他特价套餐</p>
              </div>
              <div class="qc_map">
                <div class="title"><div class="c_place">你已选择: <span id="sel_building" class="stress"></span></div>你可以在地图上点击选择你的取餐地点：</div>
                <div id="map_canvas" class="map_canvas"></div>
              </div>
            </div>
            <div class="pc_2">
              <div class="title">
                <input id="last_week_btn" class="month_1" onmouseover="this.className = 'month_1_1'" onmouseout="this.className = 'month_1'" name="" type="button" value=""/>
                <span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
                <input id="next_week_btn" class="month_2" onmouseover="this.className = 'month_2_1'" onmouseout="this.className = 'month_2'" name="" type="button" value=""/>
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
            <div id="point_control" class="zj">你有可用积分<strong id="change_point">###</strong>,每100分可低扣1元。我要使用<input id="change_point_input" name="" type="text" class="zj_input" maxlength="5"/>分 <strong id="discount_money">-0.00</strong>元</div>
            <div class="price">共计：<span id="in_total"></span>元</div>
            <div class="clear"></div>
          </div>
          <div class="dd_next_1">
              <input id="btn_confirm_last" class="next_2" onmouseover="this.className = 'next_2_1'" onmouseout="this.className = 'next_2'" name="" type="button" value=""/>
              <input id="btn_confirm_next" class="next_1" onmouseover="this.className = 'next_1_1'" onmouseout="this.className = 'next_1'" name="" type="button" value=""/>
          </div>
        </div>
      </div>

  </body>
</html>
