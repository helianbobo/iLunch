

<%@ page import="cn.ilunch.domain.DistributionArea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distributionArea.label', default: 'DistributionArea')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${distributionAreaInstance}">
            <div class="errors">
                <g:renderErrors bean="${distributionAreaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${distributionAreaInstance?.id}" />
                <g:hiddenField name="version" value="${distributionAreaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="distributionPoints"><g:message code="distributionArea.distributionPoints.label" default="Distribution Points" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionAreaInstance, field: 'distributionPoints', 'errors')}">
                                    
<ul>
<g:each in="${distributionAreaInstance?.distributionPoints?}" var="d">
    <li><g:link controller="distributionPoint" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="distributionPoint" action="create" params="['distributionArea.id': distributionAreaInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'distributionPoint.label', default: 'DistributionPoint')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kitchens"><g:message code="distributionArea.kitchens.label" default="Kitchens" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionAreaInstance, field: 'kitchens', 'errors')}">
                                    <g:select name="kitchens" from="${cn.ilunch.domain.Kitchen.list()}" multiple="yes" optionKey="id" size="5" value="${distributionAreaInstance?.kitchens*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="latitude"><g:message code="distributionArea.latitude.label" default="Latitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionAreaInstance, field: 'latitude', 'errors')}">
                                    <g:textField name="latitude" value="${fieldValue(bean: distributionAreaInstance, field: 'latitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="longitude"><g:message code="distributionArea.longitude.label" default="Longitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionAreaInstance, field: 'longitude', 'errors')}">
                                    <g:textField name="longitude" value="${fieldValue(bean: distributionAreaInstance, field: 'longitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="distributionArea.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionAreaInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${distributionAreaInstance?.name}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
