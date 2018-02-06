/**
 * Created by MiracleXu on 2017/9/8.
 */
$(function () {

    //JS数据校验
    function checkData(){
        if($(".password").val() == ''){
            return false;
        }
        return true;
    }

    //表单提交
    function formSubmit(url){
        $("#form1").attr('action',url);    //通过jquery为action属性赋值
        $("#form1").submit();    //提交表单
        $("#form1").removeAttr('action');//提交后移除
    }

    $(".hidePassword").show();
    $(".password").hide();
    $(".hidePassword").click(function () {
        $(".hidePassword").hide();
        $(".password").show();
    });
    $(".btn").click(function () {
        if(checkData()){
            formSubmit("/department/personalPasswordChange");
        }
    });
    //导入
    $(".import").click(function () {
        if(confirm("请确保信息准确，否则将会导致员工信息错误哦。")){
            if($(".fileUpdate").val() == ''){
                alert("请选择文件上传");
                return false;
            }
            console.log($(".import").html());
            $("#importForm").submit();    //提交表单
        }
    });
});