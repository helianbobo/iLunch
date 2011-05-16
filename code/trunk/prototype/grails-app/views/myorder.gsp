<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>我的订单</title>
    <meta name="layout" content="main"/>
  </head>
  <body>

      <g:render template="/shared/header" model="[current:3]"/>

      <div class="content">
        <div class="c_c">
          <div class="my_list">
            <div class="ml_c">
              <div class="mlc_l">
                <div class="title">账户信息</div>
                <p>昵称：程瑶瑶<br/ >
                  <a href="#">[修改昵称]</a></p>
                <p>手机号：1380138000<br/>
                  订餐次数：500次<br/>
                  等级：食神</p>
                <p>账户余额：0元<br/>
                  <a href="#">[充值]</a><br/>
                  积分：500</p>
              </div>
              <div class="mlc_r">
                <div class="title">订单记录</div>
                <div class="title_menu">
                  <ul>
                    <li><a href="#">未付款的订单</a></li>
                    <li class="on"><a href="#">近一个月的订单</a></li>
                    <li><a href="#">历史订单</a></li>
                  </ul>
                  <div class="clear"></div>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <th><input name="" type="checkbox" value=""/></th>
                      <th>订餐时间</th>
                      <th>套餐名</th>
                      <th>金额</th>
                      <th>地址</th>
                      <th>识别码</th>
                      <th>状态</th>
                      <th>支付</th>
                    </tr>
                    <tr>
                      <td><input name="" type="checkbox" value=""/></td>
                      <td>2011-03-11 10:10:24</td>
                      <td><a href="#">周三地中海之珠</a></td>
                      <td>10元</td>
                      <td>上海凌空科技园 腾讯大厦 正门</td>
                      <td>未生成
                        <div class="pa">[发至手机]</div></td>
                      <td>订餐中</td>
                      <td><input class="zhifu" onmouseover="this.className = 'zhifu_1'" onmouseout="this.className = 'zhifu'" name="" type="button"/></td>
                    </tr>
                    <tr>
                      <td><input name="" type="checkbox" value=""/></td>
                      <td>2011-03-11 10:10:24</td>
                      <td><a href="#">周三地中海之珠</a></td>
                      <td>10元</td>
                      <td>上海凌空科技园 腾讯大厦 正门</td>
                      <td>未生成
                        <div class="pa">[发至手机]</div></td>
                      <td>订餐中</td>
                      <td><input class="zhifu" onmouseover="this.className = 'zhifu_1'" onmouseout="this.className = 'zhifu'" name="" type="button"/></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>2011-03-11 10:10:24</td>
                      <td><a href="#">周三地中海之珠</a></td>
                      <td>10元</td>
                      <td>上海凌空科技园 腾讯大厦 正门</td>
                      <td>JSHDL379017
                        <div class="pa"><a href="#">[发至手机]</a></div></td>
                      <td>陪送中</td>
                      <td>支付成功</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>2011-03-11 10:10:24</td>
                      <td><a href="#">周三地中海之珠</a></td>
                      <td>10元</td>
                      <td>上海凌空科技园 腾讯大厦 正门</td>
                      <td>JSHDL379017
                        <div class="pa">[发至手机]</div></td>
                      <td>陪送中</td>
                      <td>支付成功</td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>2011-03-11 10:10:24</td>
                      <td><a href="#">周三地中海之珠</a></td>
                      <td>10元</td>
                      <td>上海凌空科技园 腾讯大厦 正门</td>
                      <td>JSHDL379017
                        <div class="pa">[发至手机]</div></td>
                      <td>陪送中</td>
                      <td><a href="#" class="stress">支付失败?</a></td>
                    </tr>
                  </table>
                  <div class="notice"><input class="td" onmouseover="this.className = 'td_1'" onmouseout="this.className = 'td'" name="" type="button"/>注：退餐服务只针对状态在“订餐中”的订单。如果您要退订“配餐中”的订单，请<a href="#">点击这里</a>。</div>
                  <div class="page"><input class="p_up" onmouseover="this.className = 'p_up_1'" onmouseout="this.className = 'p_up'" name="" type="button" value=""/> <input class="p_down" onmouseover="this.className = 'p_down_1'" onmouseout="this.className = 'p_down'" name="" type="button" value=""/> 当前1/3页</div>
                </div>
              </div>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
        </div>
      </div>



  </body>
</html>