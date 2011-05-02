<%@ page import="cn.ilunch.domain.DistributionArea; cn.ilunch.domain.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1>产品排菜修改</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${productInstance}">
        <div class="errors">
            <g:renderErrors bean="${productInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <div class="dialog">

        <div>
            菜名:${productInstance.name}
        </div>
        <br/>


        <g:each in="${schedules}" var="s">

            <div>
                地区:${s.key.name}
            </div>
            <table>

                <tbody>
                <g:each in="${s.value}" var="s1">
                    <g:form method="post">
                        <g:hiddenField name="id" value="${s1.id}"/>
                        <g:hiddenField name="version" value="${s1.version}"/>
                        <div>

                            <tr>
                                <td>
                                    从
                                    <g:datePicker precision="day" name="fromDate" value="${s1.fromDate}"/>
                                    到
                                    <g:datePicker precision="day" name="toDate" value="${s1.toDate?s1.toDate:Date.parse('yyyy-MM-dd','2099-01-01')}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    总量
                                    <g:textField name="quantity" value="${s1.quantity as int}" size="3"/>
                                    剩余
                                    <g:textField name="remain" value="${s1.remain as int}" size="3"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <g:actionSubmit class="save" action="updateSchedule" value="修改安排"/>
                                    <g:actionSubmit class="delete" action="deleteSchedule" value="删除安排"/>
                                </td>
                            </tr>
                        </div>
                    </g:form>
                </g:each>
                </tbody>

            </table>
            <br/>
        </g:each>

    </div>
    新增安排
    <g:form action="addNewSchedule">
        <table>
            <g:hiddenField name="oldProductId" value="${productInstance.id}"/>
            <tr><td>地区</td><td><g:select name="areaId" from="${user.areaId==null?DistributionArea.findAllByStatus(DistributionArea.INUSE):[DistributionArea.get(user.areaId)]}" optionKey="id" optionValue="name"></g:select></td></tr>
            <tr><td>菜名</td><td><g:select name="productId" from="${Product.findAllByStatus(DistributionArea.INUSE)}" optionKey="id" optionValue="name"></g:select></td></tr>
            <tr class="prop">
                <td valign="top" class="name">
                    供应日期从</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'fromDate', 'errors')}">
                    <g:datePicker name="fromDate" precision="day" years="${2011 .. 2020}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    供应日期到</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'toDate', 'errors')}">
                    <g:datePicker name="toDate" precision="day" years="${2011 .. 2020}"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <g:message code="product.story.label" default="价格"/>
                </td>
                <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">
                    <g:textField name="price"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <g:message code="product.story.label" default="数量"/>
                </td>
                <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'quantity', 'errors')}">
                    <g:textField name="quantity"/>
                </td>
            </tr>
        </table>
        <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="addNewSchedule" value="添加"/></span>
    </div>
    </g:form>


</div>
</body>
</html>
