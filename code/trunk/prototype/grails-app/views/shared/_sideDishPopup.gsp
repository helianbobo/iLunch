<div class="alert_div_2" id="sd_detail_dialog"
     style="position: absolute; display: none; z-index: 2000">
    <div style="background:none;padding:0;text-indent:0px;" class="title">
        <a href="#" onclick="closeSDDetail();"><img
                src="${resource(dir: 'images', file: 'close.png')}"/></a>配菜信息
    </div>

    <div class="pic">
        <img name="img" src=""/>
    </div>

    <div class="pic_text">
        <ul>
            <li><strong name="name"></strong></li>
            <li name="flavor"></li>
            <li name="story"></li>
            <li class="sl"></li>
            <li class="sub"><input class="button_9"
                                   onmouseover="this.className = 'button_9_1'"
                                   onmouseout="this.className = 'button_9'" name="" type="button"
                                   value="" onclick="closeSDDetail();"/></li>
        </ul>

        <div></div>

        <div></div>
    </div>
</div>