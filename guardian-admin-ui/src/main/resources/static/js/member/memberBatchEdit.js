/**
 * @Description: 成员批量修改
 * Created by Miracle Xu on 2017/12/20.
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 */
$(function () {
    $("#roleList").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择角色",
        selectAll: true
    });
    $("#productType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择产品类型",
        selectAll: true
    });
    $("#platform").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择渠道",
        selectAll: true
    });
    $("#tag").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择目的地",
        selectAll: true
    });
    $("#resourceType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择资源类型",
        selectAll: true
    });

    //修改
    $("#save").click(function () {
        dataHandle();
        if(checkData()) {
            formSubmit("/user/batchMemberEdit");
        }
    });

    //取消
    $("#cancel").click(function () {
        parent.layer.closeAll(); //关闭单个
    });

    $(".error").hide();

    //JS数据校验
    function checkData(){
        if($("#diffDepartment").val() != 1){
            if($("#departmentList").val() == ''){
                alert("部门不能为空！");
                return false;
            }
        }
        return true;
    }
    //数据转存处理，方便后台处理
    function dataHandle() {
        $(".productTypeBack").val($(".productType").val());
        $(".orderPlatformBack").val($(".orderPlatform").val());
        $(".ressourceTypeBack").val($(".resourceType").val());
        $(".tagBack").val($(".tag").val());
        $(".roleListBack").val($(".roleList").val());
    }
    //表单提交
    function formSubmit(url){
        $("#form1").attr('action',url);    //通过jquery为action属性赋值
        $("#form1").submit();    //提交表单
        $("#form1").removeAttr('action');//提交后移除
    }
});