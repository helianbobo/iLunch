<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>我的订单</title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'jquery.tmpl.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/prototype/ilunch', file: 'userinfo.js')}"></script>
</head>

<body>

<script id="userInfoTemplate" type="text/x-jQuery-tmpl">

    <div class="title">账户信息</div>
        %{--<p>--}%
        %{--<div style='display:inline'>昵称：</div>--}%
    %{--<div style='display:inline' id='nickname'>${'$'}{nickname}</div>--}%
%{--<br/>--}%
        %{--<a href="#" onclick='javascript:fillNickname();'>[修改昵称]</a>--}%
    %{--</p>--}%
    <p>手机号：${'$'}{phoneNumber}
        <br/>
        订餐次数：500次
        <br/>
        等级：食神
    </p>

    <p>账户余额：${'$'}{balance}元
        <br/>
        <a href="#">[充值]</a>
        <br/>
        积分：${'$'}{points}</p>

</script>

<g:render template="/shared/header" model="[current:3]"/>

<div class="content">
    <div class="c_c">
        <div class="my_list">
            <div class="ml_c">
                <div class="mlc_l">
                </div>

                <div class="mlc_r">
                    <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                    <div class="title">订单记录</div>

                    <div class="title_menu">
                        <ul>
                            <g:if test="${optionOrder == 'listPending'}">
                                <li class="on"><g:link action="listPending"
                                                       params="[optionShipment:optionShipment,optionOrder:optionOrder]">等待付款的订单</g:link></li>
                            </g:if>
                            <g:else>
                                <li><g:link action="listPending"
                                            params="[optionShipment:optionShipment,optionOrder:optionOrder]">等待付款的订单</g:link></li>
                            </g:else>

                            <g:if test="${optionOrder == 'listWithinMonth'}">
                                <li class="on"><g:link action="listWithinMonth"
                                                       params="[optionShipment:optionShipment,optionOrder:optionOrder]">近一个月的订单</g:link></li>
                            </g:if>
                            <g:else>
                                <li><g:link action="listWithinMonth"
                                            params="[optionShipment:optionShipment,optionOrder:optionOrder]">近一个月的订单</g:link></li>
                            </g:else>

                            <g:if test="${optionOrder.equals('listCompleted')}">
                                <li class="on"><g:link action="listCompleted"
                                                       params="[optionShipment:optionShipment,optionOrder:optionOrder]">历史订单</g:link></li>
                            </g:if>
                            <g:else>
                                <li><g:link action="listCompleted"
                                            params="[optionShipment:optionShipment,optionOrder:optionOrder]">历史订单</g:link></li>
                            </g:else>

                        </ul>

                        <div class="clear"></div>
                        <g:if test="${optionOrder=='listPending'}">
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
                            <g:each in="${orders}" status="i" var="order">

                                <tr>

                                    <td><input name="" type="checkbox" value=""/></td>
                                    <td>${order.orderDate.format("yyyy/MM/dd HH:mm:ss")}</td>
                                    <td></td>
                                    <td>${order.amount}元</td>
                                    <td>${order.distributionPoint.name}</td>
                                    <td>未生成</td>
                                    <td>${order.displayStatus}</td>
                                    <td>
                                        <g:if test="${order.status=='SUBMITTED'}">
                                            <g:form action="tryAcknowledge" style="display:inline;">
                                                <g:hiddenField name="id" value="${order.id}"/>
                                                <g:submitButton name="submit" class="zhifu" onmouseover="this.className = 'zhifu_1'" onmouseout="this.className = 'zhifu'" value=""/>
                                            </g:form>
                                        </g:if>
                                        <g:if test="${order.status!='CANCELLED'}">
                                            <g:form action="cancel">
                                                <g:hiddenField name="id" value="${order.id}"/>
                                                <g:submitButton name="submit" class="td" onmouseover="this.className = 'td_1'" onmouseout="this.className = 'td'" value=""/>
                                            </g:form>
                                        </g:if>
                                    </td>

                                </tr>

                            </g:each>

                        </table>
                        </g:if>
                         <g:if test="${optionOrder!='listPending'}">
                        <g:each in="${orders}" status="i" var="order">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <th><input name="" type="checkbox" value=""/></th>
                                <th colspan="7">${order.orderDate.format("yyyy/MM/dd HH:mm")}</th>
                            </tr>
                            <g:each in="${shipments.collect {shipment->shipment.productOrder==order}}" status="j" var="shipment">
                            <tr>
                                <td class="foods-name">${shipment.displayingProductName}</td>
                                <td class="foods-price">${shipment.getAmount()}元</td>
                                <td class="foods-address">${shipment.productOrder.distributionPoint.name}</td>
                                <td class="order-code">${shipment.serialNumber ?: "未生成"}
                                        <div class="pa"><g:link action="sendSN" params='[shipmentId:shipment.id]'>[发至手机]</g:link></div></td>
                                <td class="order-status">${shipment.getDisplayStatus()}</td>
                                <td rowspan="2" class="overlay">总价:${order.amount}元</td>
                                <td rowspan="2" class="overlay"><input class="zhifu"
                                                                       onmouseover="this.className = 'zhifu_1'"
                                                                       onmouseout="this.className = 'zhifu'" name=""
                                                                       type="button"/></td>
                            </tr>
                            </g:each>
                        </table>
                        </g:each>
                        </g:if>

                        <div class="notice"><input class="td" onmouseover="this.className = 'td_1'"
                                                   onmouseout="this.className = 'td'" name=""
                                                   type="button"/>注：退餐服务只针对状态在“订餐中”的订单。如果您要退订“配餐中”的订单，请<a
                                href="#">点击这里</a>。</div>

                        <div class="page"><input class="p_up" onmouseover="this.className = 'p_up_1'"
                                                 onmouseout="this.className = 'p_up'" name="" type="button"
                                                 value=""/> <input class="p_down"
                                                                   onmouseover="this.className = 'p_down_1'"
                                                                   onmouseout="this.className = 'p_down'" name=""
                                                                   type="button" value=""/> 当前1/3页</div>
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