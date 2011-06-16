<sec:ifLoggedIn>
   <input type="hidden" id="userId" value="${sec.loggedInUserInfo(field:'id')}"/>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
    <input type="hidden" id="userId"/>
</sec:ifNotLoggedIn>

<div id="authLink" class="link" >
    <sec:ifLoggedIn>
        当前用户：<sec:username/>
        <g:link controller="logout" action="index">登出</g:link>
    </sec:ifLoggedIn>
    <sec:ifNotLoggedIn>
        <a href="#" id="logon_lnk">登录</a>
    </sec:ifNotLoggedIn>
</div>