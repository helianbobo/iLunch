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
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></span>
</div>
<div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${productInstance}">
        <div class="errors">
            <g:renderErrors bean="${productInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${productInstance?.id}"/>
        <g:hiddenField name="version" value="${productInstance?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="originalImageUrl"><g:message code="product.originalImageUrl.label" default="Original Image Url"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'originalImageUrl', 'errors')}">
                        <g:textField name="originalImageUrl" value="${productInstance?.originalImageUrl}"/>
                    </td>
                </tr>


                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="status"><g:message code="product.status.label" default="Status"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'status', 'errors')}">
                        <g:select name='status' optionKey="value" optionValue="key" from="${[[key:'使用中',value:0],[key:'已删除',        value:1]]}" value="${productInstance.status}"></g:select>   //little li li is a genius

                    </td>
                </tr>


                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${productInstance?.name}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="product.story.label" default="Story"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'story', 'errors')}">
                        <g:textField name="story" value="${productInstance?.story}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
