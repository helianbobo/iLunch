(function($) {
	if ('position' in $) throw "$.position is already in use";
	$.position = function( sender, ref ) {
        if (sender && sender.jquery) sender = sender[0];
        if (!sender) return;
        if (ref && ref.jquery) ref = ref[0];
        if (!ref) ref = null;
        var e = sender, E = e, 
            x = E.scrollLeft, y = E.scrollTop;
        all: while (e.offsetParent) {
            x += e.offsetLeft, y += e.offsetTop;
            e = e.offsetParent;
            do {
                if (E === ref) break all;
                x-= E.scrollLeft, y-= E.scrollTop;
            } while (ref === (E = E.parentNode) || e !== E)
        }
        return {"x":x, "y":y};
    }
})(jQuery);