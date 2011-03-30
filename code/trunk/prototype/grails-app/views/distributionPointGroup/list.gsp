
<%@ page import="cn.ilunch.domain.DistributionPointGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distributionPointGroup.label', default: 'DistributionPointGroup')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'distributionPointGroup.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="latitude" title="${message(code: 'distributionPointGroup.latitude.label', default: 'Latitude')}" />
                        
                            <g:sortableColumn property="longitude" title="${message(code: 'distributionPointGroup.longitude.label', default: 'Longitude')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'distributionPointGroup.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${distributionPointGroupInstanceList}" status="i" var="distributionPointGroupInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${distributionPointGroupInstance.id}">${fieldValue(bean: distributionPointGroupInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: distributionPointGroupInstance, field: "latitude")}</td>
                        
                            <td>${fieldValue(bean: distributionPointGroupInstance, field: "longitude")}</td>
                        
                            <td>${fieldValue(bean: distributionPointGroupInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${distributionPointGroupInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
