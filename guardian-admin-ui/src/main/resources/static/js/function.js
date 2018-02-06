$(function () {
    $("#createFunction").click(function () {
        member_index = layer.open({
            title: '新增功能权限',
            type: 1,
            area: ['500px', '400px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#config_detail")
        });
    });

    $("#searchFunctionName").keydown(function (e) {//当按下按键时
        if (e.which == 13) {//.which属性判断按下的是哪个键，回车键的键位序号为13
            $("#queryFunction").trigger("click");//触发搜索按钮的点击事件
        }
    });

    $("#cancel_button").click(function () {
        layer.closeAll(); //关闭单个
    });


    $("#queryFunction").click(function () {
        var searchFunctionName = $("#searchFunctionName").val();
        window.location.href = '/function/searchFunction?searchFunctionName=' + searchFunctionName;
    })
});

function deleteFunction(obj) {
    var functionId = $(obj).attr("value");
    layer.confirm('确认要删除该功能权限？', {
        btn: ['确定','取消'], //按钮
        shade: false //不显示遮罩
    }, function(index){
        window.location.href = '/function/deleteFunction?functionId=' + functionId;
        layer.close(index);
    });

}

function editFunction(obj){
    member_index = layer.open({
        title: '编辑功能权限',
        type: 1,
        area: ['500px', '400px'],
        shadeClose: false, //点击遮罩关闭
        scrollbar: false,
        content: $("#config_detail")
    });
    $("#function_id").val($(obj).attr("value"));
    $("#function_type").val($(obj).parent().parent().find('td[id^="td_function_type"]').attr("value"));
    $("#function_url").val($(obj).parent().parent().find('td[id^="td_function_url"]').attr("value"));
    $("#function_name").val($(obj).parent().parent().find('td[id^="td_function_name"]').attr("value"));
}


