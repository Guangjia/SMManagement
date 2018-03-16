$(function(){
    $(".find_nav_list").css("left",sessionStorage.left+"px");
    $(".find_nav_list li").each(function(){
        if($(this).find("a").text()==sessionStorage.pagecount){
            $(".sideline").css({left:$(this).position().left});
            $(".sideline").css({width:$(this).outerWidth()});
            $(this).addClass("find_nav_cur").siblings().removeClass("find_nav_cur");
            navName(sessionStorage.pagecount);
            return false
        }
        else{
            $(".sideline").css({left:0});
            $(".find_nav_list li").eq(0).addClass("find_nav_cur").siblings().removeClass("find_nav_cur");
        }
    });
    var nav_w=$(".find_nav_list li").first().width();
    $(".sideline").width(nav_w);
    $(".find_nav_list").on('click','li', function(){
    	var num = $(this).index();
    	$('.aui-noborder').eq(num).css('display','block').siblings('div.aui-noborder').css('display','none')
        nav_w=$(this).innerWidth();
        $(".sideline").stop(true);
        $(".sideline").animate({left:$(this).position().left},300);
        $(".sideline").animate({width:nav_w});
        $(this).addClass("find_nav_cur").siblings().removeClass("find_nav_cur");
        var fn_w = ($(".find_nav").width() - nav_w) / 2;
        var fnl_l;
        var fnl_x = parseInt($(this).position().left);
        if (fnl_x <= fn_w) {
            fnl_l = 0;
        } else if (fn_w - fnl_x <= flb_w - fl_w) {
            fnl_l = flb_w - fl_w;
        } else {
            fnl_l = fn_w - fnl_x;
        }
        $(".find_nav_list").animate({
            "left" : fnl_l
        }, 300);
        sessionStorage.left=fnl_l;
        var c_nav=$(this).find("a").text();
        navName(c_nav);
    });
    var fl_w=$(".find_nav_list").width();
    var flb_w=$(".find_nav_left").width();
    $(".find_nav_list").on('touchstart', function (e) {
        var touch1 = e.originalEvent.targetTouches[0];
        x1 = touch1.pageX;
        y1 = touch1.pageY;
        ty_left = parseInt($(this).css("left"));
    });
    $(".find_nav_list").on('touchmove', function (e) {
        var touch2 = e.originalEvent.targetTouches[0];
        var x2 = touch2.pageX;
        var y2 = touch2.pageY;
        if(ty_left + x2 - x1>=0){
            $(this).css("left", 0);
        }else if(ty_left + x2 - x1<=flb_w-fl_w){
            $(this).css("left", flb_w-fl_w);
        }else{
            $(this).css("left", ty_left + x2 - x1);
        }
        if(Math.abs(y2-y1)>0){
            e.preventDefault();
        }
    });
});
function navName(c_nav) {
    switch (c_nav) {
        case "环境监控":
            sessionStorage.pagecount = "环境监控";
            break;
        case "电量仪":
            sessionStorage.pagecount = "电量仪";
            break;
        case "Ups":
            sessionStorage.pagecount = "Ups";
            break;
        case "空调":
            sessionStorage.pagecount = "空调";
            break;
        case "非智能空调":
            sessionStorage.pagecount = "非智能空调";
            break;
        case "火警系统":
            sessionStorage.pagecount = "火警系统";
            break;
       	case "视频主机":
            sessionStorage.pagecount = "视频主机";
            break;
        case "蓄电池监测系统":
            sessionStorage.pagecount = "蓄电池监测系统";
            break;
        case "PDU":
            sessionStorage.pagecount = "PDU";
            break;
        case "ATS":
            sessionStorage.pagecount = "ATS";
            break;
         case "通信电源":
            sessionStorage.pagecount = "通信电源";
            break;
         case "发电机":
            sessionStorage.pagecount = "发电机";
            break;
    }
}