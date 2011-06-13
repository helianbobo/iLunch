<%@ page import="cn.ilunch.domain.MainDish" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'mainDish.label', default: 'MainDish')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'mainDish.id.label', default: 'Id')}"/>

                <g:sortableColumn property="originalImageUrl"
                                  title="${message(code: 'mainDish.originalImageUrl.label', default: 'Original Image Url')}"/>

                <g:sortableColumn property="detailImageUrl"
                                  title="${message(code: 'mainDish.detailImageUrl.label', default: 'Detail Image Url')}"/>

                <g:sortableColumn property="largeImageUrl"
                                  title="${message(code: 'mainDish.largeImageUrl.label', default: 'Large Image Url')}"/>

                <g:sortableColumn property="mediumImageUrl"
                                  title="${message(code: 'mainDish.mediumImageUrl.label', default: 'Medium Image Url')}"/>

                <g:sortableColumn property="smallImageUrl"
                                  title="${message(code: 'mainDish.smallImageUrl.label', default: 'Small Image Url')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${mainDishInstanceList}" status="i" var="mainDishInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show"
                                id="${mainDishInstance.id}">${fieldValue(bean: mainDishInstance, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: mainDishInstance, field: "originalImageUrl")}</td>

                    <td>${fieldValue(bean: mainDishInstance, field: "detailImageUrl")}</td>

                    <td>${fieldValue(bean: mainDishInstance, field: "largeImageUrl")}</td>

                    <td>${fieldValue(bean: mainDishInstance, field: "mediumImageUrl")}</td>

                    <td>${fieldValue(bean: mainDishInstance, field: "smallImageUrl")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${mainDishInstanceTotal}"/>
    </div>
</div>
</body>
</html>
