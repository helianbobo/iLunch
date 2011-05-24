<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>

    <title>确认信息</title>
    <meta name="layout" content="main"/>


    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'confirm_info.js')}"></script>
  </head>
  <body>

      <g:render template="/shared/header" model="[current:2]"/>

      <div class="content">
      
      	<div id="logon_dialog" class="alert_div_3" style="position:absolute;display:none;z-index:1000">
		    <div class="login_l">
		      <ul>
		        <li class="log_on"><a href="#">登 录</a></li>
		        <li class="reg_off"><a href="#" id="logon_dialog_reg_btn">注 册</a></li>
		      </ul>
		    </div>
		    <div class="login_2">
		      <div class="title"><a href="#"><img src="images/close.png" /></a></div>
		      <ul>
		        <li><strong>用户登录</strong></li>
		        <li id="dialog_err"></li>
		        <li>手机号码：
		          <input id="logon_dialog_un" class="log_srk" name="" type="text" />
		        </li>
		        <li>用户密码：
		          <input id="logon_dialog_pwd" class="log_srk" name="" type="password" />
		          <div><a href="#">忘记密码了怎么办？</a></div>
		        </li>
		        <li>
		          <input id="logon_dialog_confirm" class="button_11" onmouseover="this.className='button_11_1'" onmouseout="this.className='button_11'"  name="" type="button" />
		          <input id="logon_dialog_cancel" class="button_12" onmouseover="this.className='button_12_1'" onmouseout="this.className='button_12'"  name="" type="button" />
		        </li>
		      </ul>
		    </div>
		  </div>
		  
		  <div id="reg_dialog" class="alert_div_4" style="position:absolute;display:none;z-index:1000">
		    <div class="login_l">
		      <ul>
		        <li class="log_off"><a id="reg_dialog_logon_btn" href="#">登 录</a></li>
		        <li class="reg_on"><a href="#">注 册</a></li>
		      </ul>
		    </div>
		    <div class="login_3">
		      <div class="title"><a href="#"><img src="images/close.png" /></a></div>
		      <ul>
		        <li><strong>用户注册</strong></li>
		        <li id="dialog_err_reg"></li>
		        <li>手机号码：
		          <input id="reg_dialog_phone" class="reg_srk" name="" type="text" />
		          <div>手机号码是您登录网站和接受取餐识别码的唯一凭证</div>
		        </li>
		        <li>用户密码：
		          <input id="reg_dialog_pwd" class="reg_srk" name="" type="password" />
		          <div>密码少于12个字符，由英文和数字组成，区分大小写</div>
		        </li>
		        <li>确认密码：
		          <input id="reg_dialog_pwd2" class="reg_srk" name="" type="password" />
		        </li>
		        <li>绑定帐号：
		          <input name="" type="checkbox" value="" />
		          <span>直接使用<strong>新浪微博</strong>账号</span></li>
		        <li>
		          <div>
		            <input id="reg_dialog_agree" name="" type="checkbox" value="" />
		            <span>我同意接受网站协议 <a href="#">查看网站协议</a></span></div>
		        </li>
		        <li>
		          <input id="reg_dialog_confirm" class="button_13" onmouseover="this.className='button_13_1'" onmouseout="this.className='button_13'"  name="" type="button" />
		          <input id="reg_dialog_cancel" class="button_12" onmouseover="this.className='button_12_1'" onmouseout="this.className='button_12'"  name="" type="button" />
		        </li>
		      </ul>
		    </div>
		  </div>
		  
        <div class="c_c">
          <div class="dd_3"></div>
          <div class="pcbg">
            <div class="pc_l">
              <div class="title"><p><span id="logon_tip">请点击<a href="#" id="logon_lnk">这里</a>登录/注册。</span></p></div>
              <div id="user_info">
                <div class="no_reg">
                  <table width="610" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="100">您的手机号码：</td>
                      <td width="285"><input name="" type="text" class="srk" maxlength="36"/><div class="tishi">请输入您的手机号码，方便您接收取餐短信通知</div></td>
                      <td><div class="done"></div></td>
                    </tr>
                    <tr>
                      <td>您的密码：</td>
                      <td><input name="" type="text" class="srk" maxlength="36"/><div class="tishi">设置密码，方便您保留订餐信息</div></td>
                      <td><div class="wrong">密码包括数字、字母，区分大小写</div></td>
                    </tr>
                    <tr>
                      <td colspan="3"><input class="reg_button" onmouseover="this.className = 'reg_button_1'" onmouseout="this.className = 'reg_button'" name="" type="button"/></td>
                    </tr>
                  </table>
                </div>
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
                <div><img src="/prototype/images/qc_map.jpg"/></div>
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
                  <li>
                    <img src="/prototype/images/pic_17.jpg"/>
                    <div class="n">x10</div>
                    <div class="no"><a href="#"><img src="/prototype/images/no.png"/></a></div>
                  </li>
                  <li><img src="/prototype/images/pic_17.jpg"/></li>
                  <li><img src="/prototype/images/pic_17.jpg"/></li>
                  <li><img src="/prototype/images/zc_w.png"/></li>
                  <li><img src="/prototype/images/zc_y.png"/></li>
                  <li><img src="/prototype/images/pic_17.jpg"/></li>
                  <li><img src="/prototype/images/pic_17.jpg"/></li>
                  <li><img src="/prototype/images/pic_17.jpg"/></li>
                  <li><img src="/prototype/images/pc_w.png"/></li>
                  <li><img src="/prototype/images/pc_y.png"/></li>
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
            <form id="confirm_form" method="post">
              <input id="btn_confirm_last" class="next_2" onmouseover="this.className = 'next_2_1'" onmouseout="this.className = 'next_2'" name="" type="button" value=""/>
              <input id="btn_confirm_next" class="next_1" onmouseover="this.className = 'next_1_1'" onmouseout="this.className = 'next_1'" name="" type="button" value=""/>
            </form>
          </div>
        </div>
      </div>

  </body>
</html>
