/**
 * @Description: 系统配置
 * Created by Miracle Xu on 2017/12/13.
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 */
$(function () {
    //返回首页前信息展示
    $(".id").val("");
    var info = $("#info").val();
    if(info != '' && info != 'undefined'){
        alert(info);
        $("#info").val("");
    }
    //部门修改
    $(".change",this).click(function () {
        var systemId = $(this).parents("tr").find("td").eq(0).text();//获取systemId
        layer.open({
            title: '系统配置修改',
            type: 2,
            area: ['500px', '350px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/system/edit/'+systemId
        })
    });
    //修改
    $("#save").click(function () {
        if(checkData()) {
            if (confirm("确定修改该系统配置吗？")) {
                formSubmit("/system/update");
            }
        }
    });
    //删除
    $(".delete",this).click(function () {
        if (confirm("确定删除该系统配置吗？")) {
            $(".id").val($(this).parents("tr").find("td").eq(0).text());//获取systemId
            formSubmit("/system/delete");
            $(".id").val("");
        }
    });
    //取消
    $("#cancel").click(function () {
        parent.layer.closeAll(); //关闭单个
    });
    //新增
    $("#systemAdd").click(function () {
        member_index = layer.open({
            title: '添加系统配置',
            type: 2,
            area: ['500px', '350px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/system/preAdd'
        });
    });
    //新增确定
    $("#saveToAdd").click(function () {
        if(checkData()) {
            formSubmit("/system/add");
        }
    });

    function checkData(){
        if($("#name").val() == ''){
            alert("系统名称不能为空");
            $("#name").focus();
            return false;
        }
        return true;
    };
    //表单提交
    function formSubmit(url){
        $("#form1").attr('action',url);    //通过jquery为action属性赋值
        $("#form1").submit();    //提交表单
        $("#form1").removeAttr('action');//提交后移除
    };

    /**
     *  系统数据迁移
     */
    $("#systemMigrate").click(function() {
        client.ayncPost("/system/migrateData","",function(data) {
            alert(data.message);
        })
    })
})
