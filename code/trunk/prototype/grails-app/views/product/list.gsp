<%@ page import="cn.ilunch.domain.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="createMainDish" >新增主菜</g:link></span>
    <span class="menuButton"><g:link class="create" action="createSideDish">新增配菜</g:link></span>
</div>
<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list" style="width:100%;">
        <table style="width:900px;vertical-align:middle;">
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'product.id.label', default: 'Id')}"/>

                <g:sortableColumn property="name" title="${message(code: 'product.name.label', default: '菜名')}"/>

                <g:sortableColumn property="story" title="${message(code: 'product.story.label', default: '故事')}"/>

                <g:sortableColumn property="class" title="${message(code: 'product.status.label', default: '类别')}"/>

                <g:sortableColumn property="date" title="${message(code: 'product.date.label', default: '供应安排')}"/>

                <g:sortableColumn property="smallImageUrl" title="${message(code: 'product.smallImageUrl.label', default: '图片预览')}"/>

                <g:sortableColumn property="status" title="${message(code: 'product.status.label', default: '状态')}"/>

                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <g:each in="${productInstanceList}" status="i" var="productInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: productInstance, field: "name")}</td>

                    <td>${fieldValue(bean: productInstance, field: "story")}</td>



                    <td>${productInstance.type()}</td>

                <td>
                    <g:each in="${productInstance.scheduleDates}" var="scheduleDate">
                        从 ${scheduleDate.fromDate.format("yyyy-MM-dd")} 到 ${scheduleDate.toDate?scheduleDate.toDate.format("yyyy-MM-dd"):"未来"}
                    </g:each>
                    </td>
                    <td><img src="${fieldValue(bean: productInstance, field: "smallImageUrl")}"/></td>
                     <td>${productInstance.statusDesc()}</td>
                    <td><g:link action="edit" id="${productInstance.id}" >修改产品信息</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${productInstanceTotal}"/>
    </div>
</div>
</body>
</html>
