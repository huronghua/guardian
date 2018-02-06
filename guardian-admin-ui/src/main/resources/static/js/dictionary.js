$(function () {
    $("#createDictionary").click(function () {
        member_index = layer.open({
            title: '新增字典数据',
            type: 1,
            area: ['500px', '350px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#dictionary_detail")
        });
    });

    $("#searchDictionaryName").keydown(function (e) {//当按下按键时
        if (e.which == 13) {//.which属性判断按下的是哪个键，回车键的键位序号为13
            $("#queryDictionary").trigger("click");//触发搜索按钮的点击事件
        }
    });

    $("#cancel_button").click(function () {
        layer.closeAll(); //关闭单个
    });


    $("#queryDictionary").click(function () {
        var searchDictionaryName = $("#searchDictionaryName").val();
        window.location.href = '/dictionary/searchDictionary?searchDictionaryName=' + searchDictionaryName;
    })
});

function deleteDictionary(obj) {
    var dictionaryId = $(obj).attr("value");
    layer.confirm('确认要删除该字典数据？', {
        btn: ['确定','取消'], //按钮
        shade: false //不显示遮罩
    }, function(index){
        window.location.href = '/dictionary/deleteDictionary?dictionaryId=' + dictionaryId;
        layer.close(index);
    });

}

function editDictionary(obj){
    member_index = layer.open({
        title: '编辑字典数据',
        type: 1,
        area: ['500px', '350px'],
        shadeClose: false, //点击遮罩关闭
        scrollbar: false,
        content: $("#dictionary_detail")
    });
    $("#dictionary_id").val($(obj).attr("value"));
    $("#dictionary_type").val($(obj).parent().parent().find('td[id^="td_dictionary_type_id"]').attr("value"));
    $("#dictionary_code").val($(obj).parent().parent().find('td[id^="td_dictionary_code"]').attr("value"));
    $("#dictionary_name").val($(obj).parent().parent().find('td[id^="td_dictionary_name"]').attr("value"));
}


//返回首页前信息展示
var suc = $("#success").val();
if(suc != "") {
    alert(suc);
    $("#success").empty();
}

