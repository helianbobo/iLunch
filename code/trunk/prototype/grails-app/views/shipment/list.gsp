<%@ page import="cn.ilunch.domain.DistributionArea; cn.ilunch.domain.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><g:link action="listShipmentByDate">查询配送</g:link></span>
</div>
<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>

    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <div class="list" style="width:100%;">
        <table style="width:900px;vertical-align:middle;">
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'product.id.label', default: 'Id')}"/>

                <g:sortableColumn property="name" title="${message(code: 'product.name.label', default: '配送地点')}"/>

                <g:sortableColumn property="story" title="${message(code: 'product.story.label', default: '配送日期')}"/>

                <g:sortableColumn property="class" title="${message(code: 'product.status.label', default: '配送项目')}"/>

                <g:sortableColumn property="status" title="${message(code: 'product.status.label', default: '状态')}"/>
                <g:sortableColumn property="status" title="${message(code: 'product.status.label', default: '序列号')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${shipments}" status="i" var="shipment">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>${shipment.id}</td>

                    <td>${shipment.productOrder.distributionPoint.name}</td>

                    <td>${shipment.shipmentDate.format("yyyy-MM-dd")}</td>

                    <td>${shipment.getDisplayingProductName()}</td>

                    <td>${shipment.getDisplayStatus()}</td>
                    <td>${shipment.serialNumber}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
