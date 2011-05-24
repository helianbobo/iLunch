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
    <g:form action="listShipmentByDate">
        日期
        <g:datePicker name="date" precision="day"></g:datePicker>
        <br/>
        配送点
        <g:select name="areaId" from="${distributionPoints}" optionKey="id" optionValue="name"></g:select>
        <g:submitButton name="submit" value="查询"/>
    </g:form>
</div>
</body>
</html>
