/**
 * Created by MiracleXu on 2017/9/8.
 */
$(function () {
    $("#productType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择产品类型",
        selectAll: true,
        selectAllText:"全部"
    });
    $("#platform").multipleSelect({
        onCheckAll: function () {
            console.log($("#platform").val());
            $("#platform").next().find("button").children().eq(0).html("全部");
        },
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择渠道",
        selectAll: true,
        selectAllText:"全部"
    });
    if (null!=$("#platform").val()){
        if ($("#platform").val().length==$("#platform")[0].options.length){
            $("#platform").next().find("button").children().eq(0).html("全部");
        }
    }
    $("#tag").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择目的地",
        selectAllText:"全部"
    });
    $("#resourceType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择资源类型",
        selectAll: true,
        selectAllText:"全部"
    });

    //修改
    $("#save").click(function () {
        dataHandle();
        if(checkData()) {
            formSubmit("/department/update");
        }
    });
    //删除
    $("#delete").click(function () {
        dataHandle();
        formSubmit("/department/delete");
    });
    //取消
    $("#cancel").click(function () {
        parent.layer.closeAll(); //关闭单个
    });
    //新增
    $("#saveToAdd").click(function () {
        dataHandle();
        if(checkData()) {
            formSubmit("/department/add");
        }
    });
    //重名校验
    $(".error").hide();
    $(".departmentName").blur(function () {
        $.ajax({
            type:"GET",
            url:'/department/editDepartmentNameCheck',
            data: {
                'departmentName': $(".departmentName").val(),
                'departmentId':$("#departmentId").val()
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401){
                    alert("登录信息失效，请刷新！");
                }
            },
            success:function(data){
                var flag = data.flag;
                console.log(flag);
                if(flag != "0"){
                    $(".error").show();
                    $(".departmentName").focus();
                }else{
                    $(".error").hide();
                }
            }
        })
    });
    $(".newDepartmentName").blur(function () {
        $.ajax({
            type:"GET",
            url:'/department/addDepartmentNameCheck',
            data: {
                'departmentName': $(".newDepartmentName").val()
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401){
                    alert("登录信息失效，请刷新！");
                }
            },
            success:function(data){
                var flag = data.flag;
                console.log(flag);
                if(flag != "0"){
                    $(".error").show();
                    $(".newDepartmentName").focus();
                }else{
                    $(".error").hide();
                }
            }
        })
    });
    //JS数据校验
    function checkData(){
        if($("#parentId").val() == ''){
            alert("上级部门不能为空");
            return false;
        }
        if($("#departmentName").val() == ''){
            alert("部门名称不能为空");
            $("#departmentName").focus();
            return false;
        }
        if($(".employee").val() == ''){
            alert("请选择部门主管");
            $("#chargeMan").focus();
            return false;
        }
        return true;
    }
    //数据转存处理，方便后台处理
    function dataHandle() {
        $(".productTypeBack").val($(".productType").val());
        $(".orderPlatformBack").val($(".orderPlatform").val());
        $(".ressourceTypeBack").val($(".resourceType").val());
        $(".tagBack").val($(".tag").val());
        $(".departmentTypeBack").val($(".departmentType").val());
        $(".charge").val($(".dis").children("input").attr("value"));
    }
    //表单提交
    function formSubmit(url){
        $("#form1").attr('action',url);    //通过jquery为action属性赋值
        $("#form1").submit();    //提交表单
        $("#form1").removeAttr('action');//提交后移除
    }

    //部门主管ajax查询
    $("#selec").hide();
    $('#chargeMan').keyup(function () {
        $("#selec").empty();
        $(".employee").val("");
        $.ajax({
            type: "GET",
            url: "/department/getCharge",
            dataType: 'json',
            data: {
                'charge': $('#chargeMan').val()
            },
            cache: false,
            success: function (data) {
                //成功后执行的方法
                $("#selec").show();
                for(var i = 0; i < data.length; i++){
                    $("#selec").append('<li style="cursor: pointer;" name="' + data[i].name + '" id="' + data[i].id + '" employeeId="' + data[i].employee_id + '">'+data[i].name+'</li>');
                }
                $("#selec").on("click","li",function(){
                    $("#chargeMan").val($(this).attr('name'));
                    $(".employeeShow").val($(this).attr('employeeId'));//仅展示
                    $(".employee").val($(this).attr('id'));
                    $("#selec").hide();
                })
            }
        });
    });
    $(window).click(function(e){
        var e = e || window.event;
        console.log(e.target.nodeName)
        if(e.target.nodeName.toLocaleLowerCase() == "body" || e.target.nodeName.toLocaleLowerCase() == "html" || e.target.nodeName.toLocaleLowerCase() == "p" || e.target.nodeName.toLocaleLowerCase() == "td"){
            $("#selec").hide();
        }
    })
});