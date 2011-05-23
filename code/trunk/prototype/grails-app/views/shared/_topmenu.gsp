<div class="h_menu">
  <ul>
    <g:if test="${current == 1}"><li class="on"></g:if><g:else><li></g:else><g:link url="${request.getContextPath()}/" class="h_m_1"/></li>
    <g:if test="${current == 2}"><li class="on"></g:if><g:else><li></g:else><g:link controller="dataAPI" action="pickMainDish" class="h_m_2"/></li>
    <g:if test="${current == 3}"><li class="on"></g:if><g:else><li></g:else><g:link url="${request.getContextPath()}/order/listWithinMonth" class="h_m_3"/></li>
    <g:if test="${current == 4}"><li class="on"></g:if><g:else><li></g:else><g:link url="${request.getContextPath()}/aboutus.gsp" class="h_m_4"/></li>
    <g:if test="${current == 5}"><li class="on"></g:if><g:else><li></g:else><g:link url="${request.getContextPath()}/faq.gsp" class="h_m_5"/></li>
  </ul>
</div>