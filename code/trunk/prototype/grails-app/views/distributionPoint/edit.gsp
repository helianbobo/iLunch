

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
                                  <label for="status"><g:message code="distributionPoint.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'status', 'errors')}">
                                    <g:textField name="status" value="${fieldValue(bean: distributionPointInstance, field: 'status')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="area"><g:message code="distributionPoint.area.label" default="Area" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'area', 'errors')}">
                                    <g:select name="area.id" from="${cn.ilunch.domain.DistributionArea.list()}" optionKey="id" value="${distributionPointInstance?.area?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="buildings"><g:message code="distributionPoint.buildings.label" default="Buildings" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'buildings', 'errors')}">
                                    
<ul>
<g:each in="${distributionPointInstance?.buildings?}" var="b">
    <li><g:link controller="building" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="building" action="create" params="['distributionPoint.id': distributionPointInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'building.label', default: 'Building')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kitchen"><g:message code="distributionPoint.kitchen.label" default="Kitchen" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: distributionPointInstance, field: 'kitchen', 'errors')}">
                                    <g:select name="kitchen.id" from="${cn.ilunch.domain.Kitchen.list()}" optionKey="id" value="${distributionPointInstance?.kitchen?.id}"  />
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
