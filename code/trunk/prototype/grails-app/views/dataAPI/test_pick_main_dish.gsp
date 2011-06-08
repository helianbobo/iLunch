<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>选择主菜</title>
    <meta name="layout" content="main"/>

    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'pick_main_dish.js')}"></script>
  </head>
  <body>

      <g:render template="/shared/header" model="[current:2]"/>
      <div class="content">
        <div class="c_c">
          <div class="dd_1"></div>
          <div class="rili">
            <input id="last_month_btn" class="week_1" onmouseover="this.className = 'week_1_1'" onmouseout="this.className = 'week_1'" name="" type="button" value=""/>
            <span id="current_date">今天##YY##-##MM##-##DD## 星期##WW##</span>
            <input id="next_month_btn" class="week_2_1" onmouseover="this.className = 'week_2_1'" onmouseout="this.className = 'week_2'" name="" type="button" value=""/>
          </div>
          <div id="md_list" class="dd_list">
          </div>
          <div class="clear"></div>
          <div class="clear"></div>
          <div class="dd_list_bottom">共计：<span id="in_total"></span>元</div>
          <div class="dd_next_1">
              <input id="btn_confirm" class="next_1" onmouseover="this.className = 'next_1_1'" onmouseout="this.className = 'next_1'" name="" type="button" value=""/>
          </div>
        </div>
      </div>

  </body>
</html>
