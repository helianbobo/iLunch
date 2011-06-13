<%@ page import="cn.ilunch.domain.MainDish" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'mainDish.label', default: 'MainDish')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
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
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.id.label" default="Id"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "id")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.originalImageUrl.label"
                                                         default="Original Image Url"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "originalImageUrl")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.detailImageUrl.label"
                                                         default="Detail Image Url"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "detailImageUrl")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.largeImageUrl.label"
                                                         default="Large Image Url"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "largeImageUrl")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.mediumImageUrl.label"
                                                         default="Medium Image Url"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "mediumImageUrl")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.smallImageUrl.label"
                                                         default="Small Image Url"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "smallImageUrl")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.status.label" default="Status"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "status")}</td>

            </tr>

            %{--<tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.productAreaPriceSchedules.label"
                                                         default="Product Area Price Schedules"/></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${mainDishInstance.productAreaPriceSchedules}" var="p">
                            <li><g:link controller="productAreaPriceSchedule" action="show"
                                        id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </tr>--}%

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.name.label" default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "name")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.story.label" default="Story"/></td>

                <td valign="top" class="value">${fieldValue(bean: mainDishInstance, field: "story")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="mainDish.tags.label" default="Tags"/></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${mainDishInstance.tags}" var="t">
                            <li><g:link controller="tag" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </tr>

            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${mainDishInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
