
<%@ page import="cn.ilunch.domain.DistributionArea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distributionArea.label', default: 'DistributionArea')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'distributionArea.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="latitude" title="${message(code: 'distributionArea.latitude.label', default: 'Latitude')}" />
                        
                            <g:sortableColumn property="longitude" title="${message(code: 'distributionArea.longitude.label', default: 'Longitude')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'distributionArea.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${distributionAreaInstanceList}" status="i" var="distributionAreaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${distributionAreaInstance.id}">${fieldValue(bean: distributionAreaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: distributionAreaInstance, field: "latitude")}</td>
                        
                            <td>${fieldValue(bean: distributionAreaInstance, field: "longitude")}</td>
                        
                            <td>${fieldValue(bean: distributionAreaInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${distributionAreaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
