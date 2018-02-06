/**
 * Created by banma on 2017/9/21.
 */
var _flag;
$("#submit_button").click(function () {
    $("#platformList").val($("#platform").val());
    $("#destinationList").val($("#destination").val());
    $("#productTypeList").val($("#productType").val());
    $("#resourceTypeList").val($("#resourceType").val());
    $("#roleTypeList").val($("#roleType").val());
    $(".roleListBack").val($(".roleList").val());
    $("#ethnicList").val($("#ethnic").val());
    if($("#position").val()==""){
        alert("岗位不能为空!");
        return false;
    }
    var nameAccTemp = $("#userNameCheck").val().replace("[",'').replace("]",'').replace(/\s+/g,"").split(",");
    if (isInArray(nameAccTemp, $("#name").val())) {
        alert("姓名已存在!");
        return false;
    }
    var reg = /^([a-zA-Z0-9_-])+(@banmatrip)+[.]+(com)$/;
    if(!reg.test($("#email").val())){
        alert("邮箱仅限以@banmatrip.com的邮箱为有效邮箱地址");
        return false;
    }
    var treeObj = $.fn.zTree.getZTreeObj("cityTree");
    nodes = treeObj.getCheckedNodes(true);
    var v = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].id + ",";
    }
    v = v.substring(0, v.length - 1);
    $("#departmentList").val(v);
    var dep = $("#departmentList").val();
    if (dep == "") {
        alert("部门不能为空");
        $("#cityTree").focus();
        return false;
    }
    var emailTemp = $("#userEmailList").val().replace("[",'').replace("]",'').replace(/\s+/g,"").split(",");
    if (($("#email").val() != '')&&(isInArray(emailTemp,$("#email").val()))) {
        alert("邮箱已存在!");
        return false;
    }
    var userAccTemp = $("#userOrangeAcc").val().replace("[",'').replace("]",'').replace(/\s+/g,"").split(",");
    if (isInArray(userAccTemp, $("#orangeAccount").val())) {
        alert("orange账号已存在!");
        return false;
    }
});
$("#confir").click(function () {
    $("#Alert").hide();
})

$("#cancle").click(function () {
    var citynodes = $.parseJSON($("#depWithEcho").val());
    var zTreeDemo = $.fn.zTree.init($("#cityTree"), setting, citynodes);
    $("#depTree").html("部门");
    $("#Alert").hide();
})

$("#depart").click(function () {
    $("#Alert").show();
})

function cancel_save() {
    parent.layer.closeAll();
}

function gradeChange() {
    $("#des").hide();
    $("#pla").hide();
    $("#pro").hide();
    $("#res").hide();
    /**当角色为[资源采购]时，信息编辑页显示资源类型**/
    if (isInArray($("#roleType").val(), '6')) {
        $("#res").show();
    }
    /**当角色为[产品/销售/定制师/运营]时，信息编辑页显示目的地，产品类型，渠道**/
    if (isInArray($("#roleType").val(), '7') ||
        isInArray($("#roleType").val(), '8') ||
        isInArray($("#roleType").val(), '9')
    ) {
        $("#des").show();
        $("#pla").show();
        $("#pro").show();
    }
    /**当角色为[管家]时，信息编辑页显示目的地**/
    if (isInArray($("#roleType").val(), '10')
    ) {
        $("#des").show();
    }
}

function isInArray(arr, value) {
    if (null==arr){
        return false;
    }else{
        for (var i = 0; i < arr.length; i++) {
            if (value == arr[i]) {
                return true;
            }
        }
    }
    return false;
}
