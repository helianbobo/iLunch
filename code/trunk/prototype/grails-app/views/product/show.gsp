
<%@ page import="cn.ilunch.domain.Product" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.originalImageUrl.label" default="Original Image Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "originalImageUrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.detailImageUrl.label" default="Detail Image Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "detailImageUrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.largeImageUrl.label" default="Large Image Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "largeImageUrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.mediumImageUrl.label" default="Medium Image Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "mediumImageUrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.smallImageUrl.label" default="Small Image Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "smallImageUrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "status")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.productAreaPriceSchedules.label" default="Product Area Price Schedules" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${productInstance.productAreaPriceSchedules}" var="p">
                                    <li><g:link controller="productAreaPriceSchedule" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.story.label" default="Story" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "story")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${productInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
