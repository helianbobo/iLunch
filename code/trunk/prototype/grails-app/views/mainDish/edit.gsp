<%@ page import="cn.ilunch.domain.MainDish" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'mainDish.label', default: 'MainDish')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${mainDishInstance}">
        <div class="errors">
            <g:renderErrors bean="${mainDishInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${mainDishInstance?.id}"/>
        <g:hiddenField name="version" value="${mainDishInstance?.version}"/>
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
                        <label for="productAreaPriceSchedules"><g:message
                                code="mainDish.productAreaPriceSchedules.label"
                                default="Product Area Price Schedules"/></label>
                    </td>
                    <td valign="top"
                        class="value ${hasErrors(bean: mainDishInstance, field: 'productAreaPriceSchedules', 'errors')}">

                        <ul>
                            <g:each in="${mainDishInstance?.productAreaPriceSchedules?}" var="p">
                                <li><g:link controller="productAreaPriceSchedule" action="show"
                                            id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                            </g:each>
                        </ul>
                        <g:link controller="productAreaPriceSchedule" action="create"
                                params="['mainDish.id': mainDishInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'productAreaPriceSchedule.label', default: 'ProductAreaPriceSchedule')])}</g:link>

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

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="tags"><g:message code="mainDish.tags.label" default="Tags"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: mainDishInstance, field: 'tags', 'errors')}">

                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update"
                                                 value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
