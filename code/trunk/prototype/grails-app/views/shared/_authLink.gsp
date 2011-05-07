<sec:ifLoggedIn>
    <g:link controller="logout" action="index">登出</g:link>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
    <g:link controller="login" action="auth">登录</g:link>
</sec:ifNotLoggedIn>