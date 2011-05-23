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
    <span class="menuButton"><g:link action="indexQueryByDP">根据配送点查询</g:link></span>
    <span class="menuButton"><g:link action="indexQueryByArea">根据配送点查询</g:link></span>
</div>
<div class="body">
    <g:form action="listShipmentByDateAndArea">
        从
        <g:datePicker name="fromDate" precision="day"></g:datePicker>
        <br/>
        到
        <g:datePicker name="toDate" precision="day"></g:datePicker>
        <br/>
        配送点
        <g:select noSelection="['-1':'所有']" name="areaId" from="${areas}" optionKey="id" optionValue="name"></g:select>
        <g:submitButton name="submit" value="查询"/>
    </g:form>
</div>
</body>
</html>
