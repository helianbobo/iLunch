
<%@ page import="cn.ilunch.domain.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/console')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>
<div class="body">
    <h1>产品修改</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${productInstance}">
        <div class="errors">
            <g:renderErrors bean="${productInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post" enctype="multipart/form-data">
        <g:hiddenField name="id" value="${productInstance?.id}"/>
        <g:hiddenField name="version" value="${productInstance?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="小尺寸图片"/></label>
                        <img src="${resource(dir:'/')}${fieldValue(bean: productInstance, field: "smallImageUrl")}"/>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="smallImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="小尺寸图片路径"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <g:textField name="smallImageUrl" value="${productInstance?.smallImageUrl}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="中尺寸图片"/></label>
                        <img src="${resource(dir:'/')}${fieldValue(bean: productInstance, field: "mediumImageUrl")}"/>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="mediumImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="中尺寸图片路径"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <g:textField name="mediumImageUrl" value="${productInstance?.mediumImageUrl}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="大尺寸图片"/></label>
                        <img src="${resource(dir:'/')}${fieldValue(bean: productInstance, field: "largeImageUrl")}"/>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="largeImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="大尺寸图片路径"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <g:textField name="largeImageUrl" value="${productInstance?.largeImageUrl}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="故事图片"/></label>
                        <img src="${resource(dir:'/')}${fieldValue(bean: productInstance, field: "detailImageUrl")}"/>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <input type="file" name="detailImage"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="故事尺寸图片路径"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'image', 'errors')}">

                        <g:textField name="detailImageUrl" value="${productInstance?.detailImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="status"><g:message code="product.status.label" default="状态"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'status', 'errors')}">
                        <g:select name='status' optionKey="value" optionValue="key" from="${[[key:'使用中',value:0],[key:'已删除',        value:1]]}" value="${productInstance.status}"></g:select>

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
                        <g:textField name="story" value="${productInstance?.story}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update" value="保存"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
