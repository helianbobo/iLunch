<%@ page import="org.springframework.web.servlet.mvc.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>订单成功</title>
    <meta name="layout" content="main"/>

    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'cart.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'order_success.js')}"></script>
</head>
<body>
<g:render template="/shared/header" model="[current:2]"/>
<div class="content">
    <div class="c_c">
        <div class="dd_5"></div>
        <div class="sub_list">
            <div class="title">订单提交成功！</div>
            <p>待金额到账后，您会收到一条确认短信和取餐识别码，<span class="stress">凭识别码取餐</span>，请妥善保管识别码短信。</p>
            <p>送餐人员到达时，您会收到短信通知，<span class="stress">届时请到送餐地址等候取餐</span>。</p>
            <p>如果识别码短信误删或未收到，请用刚才输入的手机号码和密码登录网站，在<g:link controller="order" action="listWithinMonth">我的订单</g:link>页面，点击“发送识别码”按钮。<span class="stress">每份订单您有两次重新发送识别码的机会</span>。</p>
            <div class="sub_tab">
                <table width="600" border="0" cellspacing="0" cellpadding="0" id="info_tab">
                    <tr>
                        <th colspan="2">订单信息：</th>
                    </tr>
                    <tr>
                        <td width="80">尝鲜套餐：</td>
                        <td id="order_info">
                            <g:each in="${shipments}" status="i" var="shipment">
                                <p><span>${shipment.shipmentDate} 取餐</span>${shipment.getDisplayingProductName()}</p>
                            </g:each>
                        </td>
                    </tr>
                    <tr>
                        <td>联系电话：</td>
                        <td>${contactor.cellNumber}</td>
                    </tr>
                    <tr>
                        <td>联系人：</td>
                        <td>${contactor.name}</td>
                    </tr>
                    <tr>
                        <td>配送地址：</td>
                        <td>${distributionPoint.area.name} ${distributionPoint.name}</td>
                    </tr>
                </table>
            </div>
            <div class="zy">
                <p>如果您想退订，请在${order.orderDate.format("yyyy-MM-dd HH:mm:ss")} 前退订。登录网站，在查看订单页面中操作。如过期，我们会有原材料损耗，所以不能申请退订，望见谅。</p>
                <p>建议您可以通过转让的形式让他人享受套餐。您只需要把识别码通过短信转发给他人，以便他人顺利取餐。</p>
            </div>
        </div>
        <div class="dd_next_1">
            <input class="next_3"
                    onclick="window.location.href = '${createLink(controller:'order',action:'listWithinMonth')}'"
                    onmouseover="this.className = 'next_3_1'"
                    onmouseout="this.className = 'next_3'"
                    name="" type="button" value=""/>
        </div>
    </div>
</div>

</body>
</html>
