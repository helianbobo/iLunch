<%@ page import="cn.ilunch.domain.MainDish" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'mainDish.label', default: 'MainDish')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${mainDishInstance}">
        <div class="errors">
            <g:renderErrors bean="${mainDishInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="originalImageUrl"><g:message code="mainDish.originalImageUrl.label"
                                                                 default="Original Image Url"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'originalImageUrl', 'errors')}">
                        <g:textField name="originalImageUrl" value="${mainDishInstance?.originalImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="detailImageUrl"><g:message code="mainDish.detailImageUrl.label"
                                                               default="Detail Image Url"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'detailImageUrl', 'errors')}">
                        <g:textField name="detailImageUrl" value="${mainDishInstance?.detailImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="largeImageUrl"><g:message code="mainDish.largeImageUrl.label"
                                                              default="Large Image Url"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'largeImageUrl', 'errors')}">
                        <g:textField name="largeImageUrl" value="${mainDishInstance?.largeImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="mediumImageUrl"><g:message code="mainDish.mediumImageUrl.label"
                                                               default="Medium Image Url"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'mediumImageUrl', 'errors')}">
                        <g:textField name="mediumImageUrl" value="${mainDishInstance?.mediumImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="smallImageUrl"><g:message code="mainDish.smallImageUrl.label"
                                                              default="Small Image Url"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'smallImageUrl', 'errors')}">
                        <g:textField name="smallImageUrl" value="${mainDishInstance?.smallImageUrl}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="status"><g:message code="mainDish.status.label" default="Status"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: mainDishInstance, field: 'status', 'errors')}">
                        <g:textField name="status" value="${fieldValue(bean: mainDishInstance, field: 'status')}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="mainDish.name.label" default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: mainDishInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${mainDishInstance?.name}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="story"><g:message code="mainDish.story.label" default="Story"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: mainDishInstance, field: 'story', 'errors')}">
                        <g:textField name="story" value="${mainDishInstance?.story}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
