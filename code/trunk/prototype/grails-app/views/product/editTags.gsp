<%--
  Created by IntelliJ IDEA.
  User: lsha6086
  Date: 4/27/11
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="cn.ilunch.domain.Tag" contentType="text/html;charset=UTF-8" %>
<html>
<head><title>标签管理</title><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="gmain"/></head>
<body>
<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
${productInstance.name}的标签列表
<g:if test="${tags}">
    <g:each in="${tags}" var="tag">
        <div>${tag.value}</div>
        <div>
            <g:link action="deleteTag" id="${tag.id}" params="[productId:productInstance.id]">删除标签</g:link>
        </div>

    </g:each>
</g:if>
<g:else>
    <div>还没有标签，在下面的标签名中输入表情并确定以添加标签</div>
</g:else>
关联标签
<g:form action="linkTag">
    <g:select from="${Tag.findAll()-productInstance.tags}" optionKey="id" optionValue="value" name="tagId"/>
    <g:hiddenField name="productId" value="${productInstance.id}"></g:hiddenField>
    <g:submitButton  value="添加" name="link"></g:submitButton>
</g:form>
新增标签
<g:form action="addTag">
    <g:hiddenField name="productId" value="${productInstance.id}"></g:hiddenField>
    标签名<g:field name="value"></g:field><g:submitButton  value="添加" name="submit"></g:submitButton>
</g:form>
</body>
</html>