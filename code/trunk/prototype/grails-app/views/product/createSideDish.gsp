<%@ page import="cn.ilunch.domain.Location; cn.ilunch.domain.DistributionArea; cn.ilunch.domain.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/console')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
</div>
<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${productInstance}">
        <div class="errors">
            <g:renderErrors bean="${productInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save" enctype="multipart/form-data">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="地区"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'name', 'errors')}">
                        <g:select from='${DistributionArea.findAllByStatus(Location.INUSE)}' optionKey="id" optionValue="name" name="areaId" value="${principal.areaId}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="菜名"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${productInstance?.name}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="故事"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'story', 'errors')}">
                        <g:textArea cols="60" rows="10" name="story" value="${productInstance?.story}"/>
                    </td>

                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="缩略图"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'story', 'errors')}">
                        <image src="${productInstance.originalImageUrl}"></image>
                    </td>

                </tr>

                 <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="小尺寸图片"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="smallImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="中尺寸图片"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="mediumImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="大尺寸图片"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="largeImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="故事图片"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="detailImage"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="同时添加到排菜安排"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'fromDate', 'errors')}">
                        <g:checkBox name="createSchedule" checked="false"></g:checkBox>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="供应日期从"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'fromDate', 'errors')}">
                        <g:datePicker name="fromDate" precision="day" years="${2011 .. 2020}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="供应日期到"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'toDate', 'errors')}">
                        <g:datePicker name="toDate" precision="day" years="${2011 .. 2020}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="价格"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">
                         <g:textField name="price"/>
                         <g:hiddenField name="isMainDish" value="false" />
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="数量"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'quantity', 'errors')}">
                         <g:textField name="quantity"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save" value="提交"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
