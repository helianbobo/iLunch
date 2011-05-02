
<%@ page import="cn.ilunch.domain.Product" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="gmain" />
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
                            <td valign="top" class="name"><g:message code="product.originalImageUrl.label" default="图片预览" /></td>
                            
                            <td valign="top" class="value"><image src="${fieldValue(bean: productInstance, field: "originalImageUrl")}"/></td>
                            
                        </tr>
                    

                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.status.label" default="状态" /></td>
                            
                            <td valign="top" class="value"> <g:select name='status' optionKey="value" optionValue="key" from="${[[key:'使用中',value:0],[key:'已删除', value:1]]}" value="${productInstance.status}"></g:select></td>

                        </tr>
                    

                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.name.label" default="菜名" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="product.story.label" default="故事" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: productInstance, field: "story")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${productInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>

                </g:form>
            </div>
        </div>
    </body>
</html>
