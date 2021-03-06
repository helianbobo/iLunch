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
              <div class="clear"></div>
              <div class="title1">更多配菜</div>
              <div id="tag_list" class="title1_select">分类标签：
                <a href="#">全部</a> | <a href="#" class="on">本帮菜</a> | <a href="#">川菜</a> | <a href="#">湘菜</a> | <a href="#">粤菜</a> | <a href="#">东北菜</a> | <a href="#">甜口味</a> | <a href="#">咸口味</a> | <a href="#">辣口味</a> | <a href="#">其他</a>
              </div>
              
              <div id="sd_list">
              </div>
            </div>
            <div class="pc_2">
              <div class="title">
                <input onclick="lastWeek();" class="week_1" onmouseover="this.className = 'week_1_1'" onmouseout="this.className = 'week_1'" name="" type="button" value=""/>
                <span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
                <input onclick="nextWeek();" class="week_2_1" onmouseover="this.className = 'week_2_1'" onmouseout="this.className = 'week_2'" name="" type="button" value=""/>
              </div>
              <div class="pc_2_c">
                <ul id="cart_date" class="pc_time">
                </ul>
                <div class="clear"></div>
                <ul id="cart_dashboard" class="pc_p">
                </ul>
              </div>
              <div class="clear"></div>
            </div>

            <div class="clear"></div>
            <br/>
            <div class="page">
              <input id="last_page_btn" class="p_up" onmouseover="this.className = 'p_up_1'" onmouseout="this.className = 'p_up'" name="" type="button" value=""/>
              <input id="next_page_btn" class="p_down" onmouseover="this.className = 'p_down_1'" onmouseout="this.className = 'p_down'" name="" type="button" value=""/>
              当前<span id="current_page">1</span>/<span id="total_page">3</span>页
            </div>
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
