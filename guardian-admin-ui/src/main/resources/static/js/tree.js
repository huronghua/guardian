$(function($) {
  $('.menu').bind('click', function(event){
     event.stopPropagation();
      if($(".second").css("display")=="block"){
          $(".second").css({display:"none"});
          $(".sec").children("i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
          $(this).children("i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
          $(".menu i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
      }else{
          $(".second").css({display:"block"});
          $(this).children("i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
          $(".menu i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
      }
      for (var j = 0; j < $(".third").length; j++) {
          if($(".third").eq(j).css("display")=="block"){
              $(".third").eq(j).css({display:"none"});
          }
      }
  });

  $('.sec').bind('click', function(event){
     event.stopPropagation();
      $(this).next().siblings('.third').css({display:"none"});
      for (var i = 0; i < $(".third").length; i++) {
          if ($(".third").eq(i).css("display") == "none") {
              $(".sec").eq(i).children("i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
          } else {
              $(".sec").eq(i).children("i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
          }
      }
      if($(this).next().css("display")=="block"){
          $(this).next().css({display:"none"})
          $(this).children("i").css({'background': 'url(\'../img/yousanjiao.png\') center no-repeat'});
      }else{
          $(this).next().css({display:"block"});
          $(this).children("i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
      }
      $(".third").each(function(index,element){
          if(element.children.length == 0){
              $(element).prev().children("i").css({'background': 'url(\'../img/blank.png\') center no-repeat'});
          }
      });
       
  });

  $(".third li").click(function(){
    $(".third li").css({background: '#FFF', color: '#666', 'box-shadow': '0px 0px 0px grey'});
    $(".icon-yonghu").css({color: 'grey'});
    $(this).css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
    $(this).children(".icon-yonghu").css({color: '#FFF'});
  })

  // tab_con_tit
  $("#tab_con_tit li span").click(function(){
      $(".confir_reset").show();
    $("#tab_con_tit li span").css({color: '#666', 'border-bottom': '2px solid transparent'});
    $(this).css({color: '#f95d5d', 'border-bottom': '2px solid #f95d5d'});
  })
    $("#role_member").click(function(){
        $(".confir_reset").hide();
    })


  $(".resource_selec_tit").on("click","span",function(){
    $(this).css({color: "#f95d5d"});
    $(this).siblings().css({color: '#000'});
  })


  var des_h = $(".des_r li").height()*$(".des_r li").length;
  // $(".des_con").height(des_h);
  // $(".des_con").css({lineHeight: des_h+"px"})
  $(".des_r").on("click","input",function(){
    if($(this).attr("checked") == "checked"){
      $(this).parent().siblings().children("input").removeAttr("checked");
    }
  })

    //添加的js
    $(".resource_selec_tit").on("click","span",function(){
        debugger;
        // var ind = $(this).index();
        // $(".resource_selec ul").hide();
        // $(".resource_selec ul").eq(ind).css({'display': 'block'});
    })

    var body_wid = $("body").width() - 20;
    $("body").width(body_wid);
    $(".icon-quanxianguanli").css({color:'#f95d5d'});
});














