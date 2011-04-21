
<%@ page import="cn.ilunch.domain.DistributionPoint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distributionPoint.label', default: 'DistributionPoint')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'distributionPoint.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="status" title="${message(code: 'distributionPoint.status.label', default: 'Status')}" />
                        
                            <th><g:message code="distributionPoint.area.label" default="Area" /></th>
                        
                            <th><g:message code="distributionPoint.kitchen.label" default="Kitchen" /></th>
                        
                            <g:sortableColumn property="latitude" title="${message(code: 'distributionPoint.latitude.label', default: 'Latitude')}" />
                        
                            <g:sortableColumn property="longitude" title="${message(code: 'distributionPoint.longitude.label', default: 'Longitude')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${distributionPointInstanceList}" status="i" var="distributionPointInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${distributionPointInstance.id}">${fieldValue(bean: distributionPointInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: distributionPointInstance, field: "status")}</td>
                        
                            <td>${fieldValue(bean: distributionPointInstance, field: "area")}</td>
                        
                            <td>${fieldValue(bean: distributionPointInstance, field: "kitchen")}</td>
                        
                            <td>${fieldValue(bean: distributionPointInstance, field: "latitude")}</td>
                        
                            <td>${fieldValue(bean: distributionPointInstance, field: "longitude")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${distributionPointInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
