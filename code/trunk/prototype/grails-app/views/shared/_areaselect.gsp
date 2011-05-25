<script type="text/javascript">
    $(document).ready(function($) {

        $('#changeAreaBtn').click(function(e) {
            $('#changeAreaDialog').css({"right":(e.clientX+30) + "px","top":(e.clientY-5) + "px"});
            $('#changeAreaDialog').show();
        });

        $('#selectAreaCancelBtn').click(function(){
            $('#changeAreaDialog').hide();
        });

    });
</script>




<div class="place">
    <p id="area_name">上海凌空科技园</p>

    <p><a href="#" id="changeAreaBtn">[切换其他地区]</a></p>
    <input id="area_id" type="hidden" value="1"/>
</div>

<!-- 切换地区 -->
<div class="alert_div_1" style="position:absolute;display:none;z-index:1000" id="changeAreaDialog">
    <p>选择你所在的地区，我们为你提供更多特价餐：</p>

    <p>城市：
        <select name="" size="1" class="select_1">
            <option selected="selected">上海</option>
%{--            <option>天津</option>
            <option>南京</option>
            <option>成都</option>--}%
        </select>
        区域：
        <select name="" size="1" class="select_1">
            <option selected="selected">长宁区</option>
            %{--<option>黄浦区</option>
            <option>普陀区</option>
            <option>浦东新区</option>--}%
        </select>
    </p>

    <p>园区：
        <select name="" size="1" class="select_2">
            <option selected="selected">凌空科技园区</option>
            %{--<option>交大慧普区</option>
            <option>普陀区</option>
            <option>浦东新区</option>--}%
        </select>
    </p>

    <div align="center">
        <input class="button_7" onmouseover="this.className = 'button_7_1'" onmouseout="this.className = 'button_7'"
               name="" type="button"/>
        <input id="selectAreaCancelBtn" class="button_8" onmouseover="this.className = 'button_8_1'" onmouseout="this.className = 'button_8'"
               name="" type="button"/>
    </div>
</div>
<!-- 切换地区 end-->