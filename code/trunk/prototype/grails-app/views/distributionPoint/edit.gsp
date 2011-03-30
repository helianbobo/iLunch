

<%@ page import="cn.ilunch.domain.DistributionPoint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'distributionPoint.label', default: 'DistributionPoint')}" />
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
            <g:hasErrors bean="${distributionPointInstance}">
            <div class="errors">
                <g:renderErrors bean="${distributionPointInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${distributionPointInstance?.id}" />
                <g:hiddenField name="version" value="${distributionPointInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="buildings"><g:message code="distributionPoint.buildings.label" default="Buildings" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'buildings', 'errors')}">
                                    <g:select name="buildings" from="${cn.ilunch.domain.Building.list()}" multiple="yes" optionKey="id" size="5" value="${distributionPointInstance?.buildings*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="group"><g:message code="distributionPoint.group.label" default="Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'group', 'errors')}">
                                    <g:select name="group.id" from="${cn.ilunch.domain.DistributionPointGroup.list()}" optionKey="id" value="${distributionPointInstance?.group?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="latitude"><g:message code="distributionPoint.latitude.label" default="Latitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'latitude', 'errors')}">
                                    <g:textField name="latitude" value="${fieldValue(bean: distributionPointInstance, field: 'latitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="longitude"><g:message code="distributionPoint.longitude.label" default="Longitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'longitude', 'errors')}">
                                    <g:textField name="longitude" value="${fieldValue(bean: distributionPointInstance, field: 'longitude')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="distributionPoint.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${distributionPointInstance?.name}" />
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
