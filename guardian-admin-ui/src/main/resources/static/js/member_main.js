$(document).ready(function(){
    document.onkeydown = function(e) {
        //捕捉回车事件
        var ev = (typeof event!= 'undefined') ? window.event : e;
        if(ev.keyCode == 13 && document.activeElement.id == "msg") {
            return false;//禁用回车事件
        }
    };

    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var treenode = treeObj.getNodeByParam("id", $("#departmentId").val(), null);
    treeObj.expandNode(treenode.getParentNode(), true, false, true);
    treeObj.selectNode(treenode);
    $.ajax({
        type: "GET",
        url: '/department/member',
        dataType: 'json',
        data: {
            'department_id': $("#departmentId").val()
        },
        cache: false,
        success: function (data) {
            setTableData(data);
        }
    });
    $(".button").css({width: 16, height: 16});
    $(".switch").hide();
});

function department_edit_click() {
    //部门修改
    $(".depart-edit").click(function () {
        var departmentId = $("#departmentId").val();
        layer.open({
            title: '部门修改',
            type: 2,
            area: ['500px', '550px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/department/edit/'+departmentId
        })
    });
}

function department_add_click() {
    //部门新增
    $(".department-add").click(function () {
        var departmentId = $("#departmentId").val();
        layer.open({
            title: '部门新增',
            type: 2,
            area: ['500px', '550px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/department/preAdd/'+departmentId
        })
    });
}

function department_delete_click() {
    //部门删除
    $(".department-delete").click(function () {
        var departmentId = $("#departmentId").val();
        layer.open({
            title: '部门删除',
            type: 2,
            area: ['440px', '200px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/department/preDelete/'+departmentId
        })
    });
}

department_edit_click();
department_add_click();
department_delete_click();

$("#treeDemo").on("click",".node_name",function(e){
    // 先删除上一次添加的button和点击的样式
    $(".btn-group").remove();
    $(".node_active").removeClass('node_active');
    $(".font-notUnderLine").removeClass("font-underLine");
    $(".index-member").addClass("font-underLine");

    $(this).parent().addClass('node_active');
    var deptEditRole = $(".deptEditRole").val();
    var deptAddRole = $(".deptAddRole").val();
    var node_btn = $('<div class="btn-group" style="float: right;"></div>');
    var node_btn_content = `
            <button type="button" class="btn btn-default dropdown-toggle" style="height: 80%; width: 40px;border: 0;background-color: #f96868;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ... <span class="caret"></span>
              </button>
              <ul class="dropdown-menu" style="z-index: 999"> `
    if(deptEditRole){
        node_btn_content = node_btn_content + `
            <li>
                <a title="部门设置" class="glyphicon depart-edit" style="font-size: 14px;cursor: pointer;color: #f96868;" >部门设置</a>&nbsp;
            </li>
            <li>
                <a title="删除部门"  class="glyphicon department-delete" style="font-size: 14px;cursor: pointer;color: #f96868;" >删除部门</a>&nbsp;
            </li> `
    }
    if(deptAddRole){
        node_btn_content = node_btn_content + `
            <li>
                <a title="添加下级部门"  class="glyphicon department-add" style="font-size: 14px;cursor: pointer;color: #f96868;" >添加下级部门</a>&nbsp;
            </li>
        `
    }
    node_btn_content = node_btn_content + `</ul>`
    node_btn.html(node_btn_content);
    $(this).after(node_btn);

    department_edit_click();
    department_add_click();
    department_delete_click();
});

//批量导入
$(".glyphicon-download-alt").click(function () {
    layer.open({
        title: '批量导入',
        type: 2,
        area: ['500px', '550px'],
        shadeClose: true, //点击遮罩关闭
        scrollbar: false,
        content: '/department/batchImport'
    })
});

var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    view: {
        dblClickExpand: false,
        showIcon: true,
        showLine: false
    },
    callback: {
        onClick: onClick
    }
};
var zNodes = $.parseJSON($("#list").val());
var depId;
function onClick(e,treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode);
    $("#departmentId").empty();
    $("#departmentId").val(treeNode.id);
    $("#department_title").html(treeNode.onlyName);
    $(".department_title_tmp").val(treeNode.onlyName);
    $("#departmentId").val(treeNode.id);
    $.ajax({
        type: "GET",
        url: '/department/member',
        dataType: 'json',
        data: {
            'department_id': treeNode.id
        },
        cache: false,
        success: function (data) {
            setTableData(data);
        }
    });
    $(".batchOption").hide();
}

/*查询角色*/
$("#selec").hide();
$('.search_txt').keyup(function () {
    $("#selec").empty();
    $.ajax({
        type: "GET",
        url: "/user/getUserName",
        dataType: 'json',
        data: {
            'charge': $('.search_txt').val()
        },
        cache: false,
        success: function (data) {
            //成功后执行的方法
            $("#selec").show();
            for(var i = 0; i < data.length; i++){
                $("#selec").append('<li style="cursor: pointer;" name="' + data[i].name + '" id="' + data[i].id + '" employeeId="' + data[i].employee_id + '">'+data[i].name+'</li>');
            }
            $("#selec").on("click","li",function(){
                $(".search_txt").val($(this).attr('name'));
                $("#selec").hide();
            })
        }
    });
});
$(window).click(function(e){
    var e = e || window.event;
    if(e.target.nodeName.toLocaleLowerCase() == "body" || e.target.nodeName.toLocaleLowerCase() == "html" ||
        e.target.nodeName.toLocaleLowerCase() == "p" || e.target.nodeName.toLocaleLowerCase() == "td" || e.target.nodeName.toLocaleLowerCase() == "div"){
        $("#selec").hide();
    }
})
//快速查询成员信息
$("#queryUser").click(function () {
    $.ajax({
        type: "GET",
        url: '/user/queryuser',
        dataType: 'json',
        data: {
            'name':$("#userName").val()
        },
        cache: false,
        success: function (data) {
            setTableData(data);
            $("#department_title").html("查询结果");
        }
    });
})
//查询已禁用成员名单
$(".disableQuery").click(function () {
    $(".font-notUnderLine").removeClass("font-underLine");
    $(".disableQuery").addClass("font-underLine");
    $.ajax({
        type: "GET",
        url: '/user/memberDisableList',
        dataType: 'json',
        cache: false,
        success: function (data) {
            setTableData(data);
            $("#department_title").html("已禁用账户");
            $(".batchOption").hide();
            $(".checkAll").hide();
        }
    });
})
//回到主页
$(".index-member").click(function () {
    updateIndex();
    $("#department_title").html($(".department_title_tmp").val());
});
//成员信息修改/禁用
var member_index;
function editMemberInfo(id,dep,type,origin) {
    //id 成员ID; dep 当前部门id; type 操作类型1编辑成员2停用; origin 来源，1成员架构管理2角色权限管理
    if(type == 1) {
        member_index = layer.open({
            title: '编辑成员信息',
            type: 2,
            area: ['500px', '550px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/user/infoEditShow/' + id + '/' + dep + '/' + origin
        });
    }else if (type == 2){
        //禁用用户，发送ajax请求并刷新界面
        if(confirm("是否确认停用该账户？")){
            var url = "/user/deleteUser";
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "userId":id
                },
                async : true,
                dataType: "text",
                success: function () {
                    alert("停用成功，可前往“已禁用账户”查询全部已被停用账户");
                    updateIndex();
                },
            });
        }
    }else {
        //密码重置
        if(confirm("确定要重置该用户密码吗？")) {
            $.ajax({
                type: "GET",
                url: "/user/passwordReset",
                dataType: 'text',
                data: {
                    'userId': id
                },
                async: true,
                success: function () {
                },
            });
        }
    }
}
//更新成员信息首页
function updateIndex() {
    $(".font-notUnderLine").removeClass("font-underLine");
    $(".index-member").addClass("font-underLine");
    $.ajax({
        type: "GET",
        url: '/department/member',
        dataType: 'json',
        data: {
            'department_id': $("#departmentId").val()
        },
        cache: false,
        success: function (data) {
            setTableData(data);
        }
    });
}
function setTableData(data) {
    var memberEdit = $('input[name="memberEdit"]').val();
    var passwordReset = $(".passwordReset").val();
    var table_data = '<tr><td style="width: 80px;"><div class="checkAll"><input type="checkbox" name="memberIdAll" class="memberIdAll" value="all" />全选</div></td><td style="width: 80px;">姓名</td><td style="width: 230px;">职位</td><td style="width: 200px;">企业邮箱</td><td style="width: 80px;">工号</td><td style="width: 160px;color: #676a6c">手机号</td><td style="width: 80px;">操作</td></tr>';
    if (!memberEdit) {
        $.each(data, function (index, item) {
            if(item.disable_status == 0) {
                if (item.leader == 1) {
                    var itemposition_name = item.position_name == null ? "" : item.position_name;
                    var itememail = item.email == null ? "" : item.email;
                    var itememployee_id = item.employee_id == null ? "" : item.employee_id;
                    var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                    table_data += '<tr><td><input type="checkbox" name="memberId" class="memberId" value="' + item.id + '" /></td><td class="head_leader_img">' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #676a6c">' + itemcellphone + '</td>'
                    if(passwordReset){
                        table_data += '<td style="width:180px;"><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a></td>';
                    }
                    table_data += '</tr>';
                }
            }
        });
        $.each(data, function (index, item) {
            var itemposition_name = item.position_name == null ? "" : item.position_name;
            var itememail = item.email == null ? "" : item.email;
            var itememployee_id = item.employee_id == null ? "" : item.employee_id;
            var itemcellphone = item.cellphone == null ? "" : item.cellphone;
            if(item.disable_status == 0) {
                if (item.leader != 1) {
                    table_data += '<tr><td><input type="checkbox" name="memberId" class="memberId" value="' + item.id + '" /></td><td>' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #676a6c">' + itemcellphone + '</td>';
                    if(passwordReset){
                        table_data += '<td style="width:180px;"><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a></td>';
                    }
                    table_data += '</tr>';
                }
            }else{
                table_data += '<tr class="disable_member_tr"><td></td><td>' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #676a6c">' + itemcellphone + '</td>';
                if(passwordReset){
                    table_data += '<td style="width:180px;"><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a></td>';
                }
                table_data += '</tr>';
            }
        });
    } else {
        $.each(data, function (index, item) {
            var itemposition_name = item.position_name == null ? "" : item.position_name;
            var itememail = item.email == null ? "" : item.email;
            var itememployee_id = item.employee_id == null ? "" : item.employee_id;
            var itemcellphone = item.cellphone == null ? "" : item.cellphone;
            if(item.disable_status == 0) {
                if (item.leader == 1) {
                    table_data += '<tr><td><input type="checkbox" name="memberId" class="memberId" value="' + item.id + '" /></td><td class="head_leader_img">' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #676a6c">' + itemcellphone + '</td><td style="width:180px;"><a style="color: #f96868;" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',1,1' + ');return false" href="/user/infoEditShow/' + item.id + '">编辑</a><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',2,1' + ');return false" href="/user/infoEditShow/' + item.id + '">停用</a>'
                    if(passwordReset){
                        table_data += '<a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a>';
                    }
                    table_data += '</td></tr>';
                }
            }
        });
        $.each(data, function (index, item) {
            var itemposition_name = item.position_name == null ? "" : item.position_name;
            var itememail = item.email == null ? "" : item.email;
            var itememployee_id = item.employee_id == null ? "" : item.employee_id;
            var itemcellphone = item.cellphone == null ? "" : item.cellphone;
            if(item.disable_status == 0) {
                if (item.leader != 1) {
                    table_data += '<tr><td><input type="checkbox" name="memberId" class="memberId" value="' + item.id + '" /></td><td>' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #676a6c">' + itemcellphone + '</td><td style="width:180px;"><a style="color: #f96868;" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',1,1' + ');return false" href="/user/infoEditShow/' + item.id + '">编辑</a><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',2,1' + ');return false" href="/user/infoEditShow/' + item.id + '">停用</a>'
                    if(passwordReset){
                        table_data += '<a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a>';
                    }
                    table_data += '</td></tr>';
                }
            }else {
                table_data += '<tr class="disable_member_tr"><td></td><td>' + item.name + '</td> <td>' + itemposition_name + '</td> <td>' + itememail + '</td> <td>' + itememployee_id + '</td> <td style="color: #dbd8d8;cursor: no-drop">' + itemcellphone + '</td><td style="width:"100px;"></td>';
                if(passwordReset){
                    table_data += '<td style="width:180px;"><a style="color: #f96868;margin-left: 22px" onclick="editMemberInfo(' + item.id + ',' + item.department_id + ',3,1' + ');return false" href="/user/infoEditShow/' + item.id + '">密码重置</a></td>';
                }
                table_data += '</tr>';
            }
        });
    }
    $("#data_table").html(table_data);
}

//因为table内数据是js拼接出来的，因此此处用到事件委托，将table内的相关元素的相关触发动作委托给上级DOM元素
$(".batchOption").hide();//批量操作图标默认隐藏
$("#data_table").on('change','.memberIdAll', function(){
    var batchEditRole = $(".batchEditRole").val();
    if(batchEditRole) {
        if ($("input[name='memberIdAll']:checked").val() == "all") {
            $(".batchOption").show();
            $(".memberId").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $(".batchOption").hide();
            $(".memberId").each(function () {
                $(this).prop("checked", false);
            });
        }
    }
});
$("#data_table").on('change','.memberId', function(){
    var total = 0;
    var selectNum = 0;
    $(".memberId").each(function () {
        if(!$(this).is(':checked')){
            $(".memberIdAll").prop("checked",false);
            total++;
        }else {
            selectNum++;
        }
    });
    if(total == 0){
        $(".memberIdAll").prop("checked",true);
    }
    var batchEditRole = $(".batchEditRole").val();
    if(batchEditRole) {
        if (selectNum > 1) {
            $(".batchOption").show();
        } else {
            $(".batchOption").hide();
        }
    }
});
//批量编辑操作
$(".editSome").click(function () {
    var result="";
    if($("input[name='memberIdAll']:checked").val() == "all") {
        $(".memberId").each(function () {
            result += $(this).val() + ',';
        });
    }else{
        $("input[name='memberId']:checked").each(function () {
            result += $(this).val() + ',';
        });
    }
    if(result!=""){
        result=result.substring(0,result.lastIndexOf(','));
        var departmentId = $("#departmentId").val();
        layer.open({
            title: '批量编辑成员信息',
            type: 2,
            area: ['500px', '550px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/user/batchEdit/'+result+'/'+departmentId
        })
    }else{

    }
});


//人员新增
$(".glyphicon-user").click(function () {
    var deptId = $("#departmentId").val();
    if(deptId == null || deptId == ""){
        deptId = 0;
    }
    member_index = layer.open({
        title: '添加成员',
        type: 2,
        area: ['500px', '550px'],
        shadeClose: true, //点击遮罩关闭
        scrollbar: false,
        content: '/user/infoSavaShow/'+ deptId
    });
});
//返回首页前信息展示
var suc = $("#success").val();
var fail = $("#fail").val();
if(fail != ''){
    alert(fail);
    $("#fail").val("");
    layer.open({
        title: '批量导入',
        type: 2,
        area: ['500px', '550px'],
        shadeClose: true, //点击遮罩关闭
        scrollbar: false,
        content: '/department/batchImport'
    })
}else{
    if(suc != '') {
        $("#success").val("");
    }
}