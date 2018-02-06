$(function () {
/*    // 初始给二级菜单插入button
    $('#box .second .sec > span').each(function(i, sec){
        var btn = $('<button class="btn" data-type="false">...</button>');
        var $sec = $(sec);
        $sec.after(btn);
        var sec_btn = $sec.siblings('button');
        sec_btn.on('click', function() {
            var status = $(this).attr('data-type');

            if(status == 'false'){
                $(this).attr('data-type', 'true')
                $sec.css({'display': 'none'})
                $sec.siblings('input').css({'display': 'inline-block'})
            } else {
                $(this).attr('data-type', 'false')
                $sec.css({'display': 'inline-block'})
                $sec.siblings('input').css({'display': 'none'})
            }
        })
    })*/

    /*tab选项卡点击事件*/
    var tab_index;
    var roleId = $("#roleId").val();
    $("#role_member").click(function(){
        tab_index = $(this).parent().index();
        $("#tab_index").val(tab_index);
        $(".role_table").css({display: 'block'});
        $(".fun").css({display: 'none'});
        $(".data_range").css({display: 'none'});
    })
    $("#fun_qx").click(function(){
        tab_index = $(this).parent().index();
        $("#tab_index").val(tab_index);
        $(".fun").css({display: 'block'});
        $(".role_table").css({display: 'none'});
        $(".data_range").css({display: 'none'});
    })
    $("#data_range").click(function(){
        tab_index = $(this).parent().index();
        $("#tab_index").val(tab_index);
        $(".data_range").css({display: 'block'});
        $(".fun").css({display: 'none'});
        $(".role_table").css({display: 'none'});
    });
    if($("#tab_index").val()==0){
        $(".confir_reset").hide();
        $(".role_table").css({display: 'block'});
        $(".fun").css({display: 'none'});
        $(".data_range").css({display: 'none'});
    }
    if($("#tab_index").val()==1){
        $(".fun").css({display: 'block'});
        $(".role_table").css({display: 'none'});
        $(".data_range").css({display: 'none'});
    }
    if($("#tab_index").val()==2){
        $(".data_range").css({display: 'block'});
        $(".fun").css({display: 'none'});
        $(".role_table").css({display: 'none'});
    }

    $(".tab_con_tit span").eq($("#tab_index").val()).css({color: '#f95d5d', 'border-bottom': '2px solid #f95d5d'});


    if(roleId!=null&&roleId!="") {
        $.ajax({
            type: "GET",
            url: '/role/getRoleMember',
            dataType: 'json',
            data: {
                'roleId': $("#roleId").val()
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401) {
                    alert("登录信息失效，请刷新！");
                }
            },
            success: function (data) {
                $("#roleId").attr("value", data.roleId);
                $("#roleId1").attr("value", data.roleId);
                $("#roleGroupId").attr("value",data.role.groupId);
                $("#countWithoutRole").html(data.countWithoutRole);

                var roleDelete = $('input[name="roleDelete"]').val();
                var table_data = '<tr><td style="width: 80px;">姓名</td><td style="width: 230px;">职位</td><td style="width: 200px;">企业邮箱</td><td style="width: 80px;">工号</td><td style="width: 160px;">手机号</td><td style="width: 80px;">操作</td></tr>';
                if (!roleDelete) {
                    $.each(data.memberVoList, function (index, item) {
                        var itemposition = item.position == null ? "" : item.position;
                        var itememail = item.email == null ? "" : item.email;
                        var itememployeeId = item.employeeId == null ? "" : item.employeeId;
                        var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                        table_data += '<tr><td>' + item.memberName + '</td> <td>' + itemposition + '</td> <td>' + itememail + '</td> <td>' + itememployeeId + '</td> <td>' + itemcellphone + '</td></tr>'

                    });
                } else {
                    $.each(data.memberVoList, function (index, item) {
                        var itemposition = item.position == null ? "" : item.position;
                        var itememail = item.email == null ? "" : item.email;
                        var itememployeeId = item.employeeId == null ? "" : item.employeeId;
                        var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                        table_data += '<tr><td>' + item.memberName + '</td> <td>' + itemposition + '</td> <td>' + itememail + '</td> <td>' + itememployeeId + '</td> <td>' + itemcellphone + '</td> <td><a style="color: #f96868;"  id="delete_member" onclick="deleteMember(' + item.id + ',' + item.roleId + ');return false" href="/role/deleteRoleMember?id=' + item.id + '&roleId=' + item.roleId + '">删除</a></td></tr>'
                    });
                }
                /*初始化功能权限*/

                getRoleFunction(data.functionVoList);
                /*$.each(data.roleGroupList, function (key, val) {
                    $("#groupId").append('<option value="' + val.id + '">' + val.name + '</option>');
                });*/
                if (data.role != null) {
                    /*$("#groupId option[value= " + data.role.groupId + "]").attr("selected", true);*/
                    $("input[name=destinationType][value=" + data.role.destinationType + "]").attr("checked", true);
                    $("input[name=platformType][value=" + data.role.platformType + "]").attr("checked", true);
                    $("input[name=productType][value=" + data.role.productType + "]").attr("checked", true);
                    $("input[name=resourceType][value=" + data.role.resourceType + "]").attr("checked", true);
                }
                $("#data_table").html(table_data);

            }
        });
    }
    /*初始化子标题和选中的树节点*/
    $(".second").css({'display': 'block'});
    $(".first i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});

    if(roleId!=null&&roleId!="") {
        var tree_role = $('#box').find('.third').find('li').find('i[value=' + roleId + ']');
        $(".third").hide();
        $("#role_title").html(tree_role.next().html());
        tree_role.parent().parent().show();
        tree_role.parent().css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        tree_role.parent().parent().prev().find('i').css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        tree_role.parent().css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        tree_role.css({color: '#FFF'});
    }
        $(".third").each(function (index, element) {
            if (element.children.length == 0) {
                $(element).prev().children("i").css({'background': 'url(\'../img/blank.png\') center no-repeat'});
            }
        });
/*        $(".second").css({'display': 'block'});
        $(".first i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});*/
/*        tree_role.parent().parent().prev().find('i').css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        tree_role.parent().css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        tree_role.css({color: '#FFF'});*/

    $(".sec").css({'overflow': 'hidden', 'text-overflow':'ellipsis', 'overflow':'hidden', 'white-space':'nowrap' });
    $("#third li").css({'overflow': 'hidden', 'text-overflow':'ellipsis', 'overflow':'hidden', 'white-space':'nowrap' });
    for (var i = 0; i < $(".sec").length; i++) {
        $(".sec").eq(i).attr("title", $(".sec").eq(i).children("span").html());
    }

    for (var j = 0; j < $(".third").length; j++) {
        for (var k = 0; k < $(".third li").length; k++) {
            $(".third").eq(j).children("li").eq(k).attr("title", $(".third").eq(j).children("li").eq(k).children("span").html());
        }
    }

    //未配置权限的成员信息展示页
    $("#memberWithoutRole").click(function () {

        $.ajax({
            type: "GET",
            url: '/role/getMemberWithoutRole',
            dataType: 'json',
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401) {
                    alert("登录信息失效，请刷新！");
                }
            },
            success: function (data) {
                var table_data = '<tr style="font-size: initial"><td style="width: 80px;">姓名</td><td style="width: 230px;">职位</td><td style="width: 200px;">企业邮箱</td><td style="width: 80px;">工号</td><td style="width: 160px;">手机号</td><td>操作</td></tr>';
                $.each(data.memberVoList, function (index, item) {
                    var itemposition = item.position == null ? "" : item.position;
                    var itememail = item.email == null ? "" : item.email;
                    var itememployeeId = item.employeeId == null ? "" : item.employeeId;
                    var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                    table_data += '<tr><td hidden="true">' + item.id + '</td><td>' + item.memberName + '</td> <td>' + itemposition + '</td> <td>' + itememail + '</td> <td>' + itememployeeId + '</td><td style="color: currentColor">' + itemcellphone + '</td><td><a class="btn btn-danger btn-sm dropdown-toggle" th:value="' + item.id +'" style="height: 30px;border: 0px;line-height: 19px" onclick="editMemberInfo(' + item.id + ',' + 0 + ',1,2' + ');return false" href="/user/infoEditShow/' + item.id + '" role="button">编辑角色</a></td></tr>'
                });
                $("#unconfigured_member").html(table_data);
            }
        });
        index = layer.open({
            skin: 'layui-layer-rim',
            title: ["未分配角色成员",'text-align:center;background: rgb(255, 255, 255)'],
            type: 1,
            area: ['900px', '500px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: $("#unconfigured_member"),
            end: function () {
                $("#unconfigured_member").html("");
            }
        });
    });


    $("#third li").click(function(){
        var role_id = $(this).find('i').attr('value');
        $("#roleId").attr("value", role_id);
	    $("#roleId1").attr("value", role_id);
        $("#role_title").html($(this).find('span').text());
        if (typeof (role_id) != "undefined") {
            /*获取角色成员*/
            $.ajax({
                type: "GET",
                url: '/role/getRoleMember',
                dataType: 'json',
                data: {
                    'roleId': role_id
                },
                cache: false,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status==401){
                        alert("登录信息失效，请刷新！");
                    }
                },
                success: function (data) {
                    //console.log(data);
                    //绑定未配置权限的成员人数
                    $("#countWithoutRole").html(data.countWithoutRole);
                    var roleDelete = $('input[name="roleDelete"]').val();
                    $("#roleGroupId").attr("value",data.role.groupId);
                    var table_data = '<tr><td style="width: 80px;">姓名</td><td style="width: 230px;">职位</td><td style="width: 200px;">企业邮箱</td><td style="width: 80px;">工号</td><td style="width: 160px;">手机号</td><td style="width: 80px;">操作</td></tr>';
                    if (!roleDelete) {
                        $.each(data.memberVoList, function (index, item) {
                            var itemposition = item.position == null ? "" : item.position;
                            var itememail = item.email == null ? "" : item.email;
                            var itememployeeId = item.employeeId == null ? "" : item.employeeId;
                            var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                            table_data += '<tr><td>' + item.memberName + '</td> <td>' + itemposition + '</td> <td>' + itememail + '</td> <td>' + itememployeeId + '</td> <td>' + itemcellphone + '</td></tr>'

                        });
                    } else {
                        $.each(data.memberVoList, function (index, item) {
                            var itemposition = item.position == null ? "" : item.position;
                            var itememail = item.email == null ? "" : item.email;
                            var itememployeeId = item.employeeId == null ? "" : item.employeeId;
                            var itemcellphone = item.cellphone == null ? "" : item.cellphone;
                            table_data += '<tr><td>' + item.memberName + '</td> <td>' + itemposition + '</td> <td>' + itememail + '</td> <td>' + itememployeeId + '</td> <td>' + itemcellphone + '</td> <td><a style="color: #f96868;" id="delete_member" onclick="deleteMember(' + item.id + ',' + item.roleId + ');return false" href="/role/deleteRoleMember?id=' + item.id+ '&roleId=' + item.roleId + '">删除</a></td></tr>'

                        });
                    }
                    /*$("#groupId").empty();
                    $.each(data.roleGroupList, function(key, val) {
                        $("#groupId").append('<option value="' + val.id + '">' + val.name + '</option>');
                    });
                    $("#groupId option[value= " + data.role.groupId + "]").attr("selected",true);*/
                    $("input").filter(":radio").removeAttr("checked");
                    $("input[name=destinationType][value="+data.role.destinationType+"]").attr("checked",true);
                    $("input[name=platformType][value="+data.role.platformType+"]").attr("checked",true);
                    $("input[name=productType][value="+data.role.productType+"]").attr("checked",true);
                    $("input[name=resourceType][value="+data.role.resourceType+"]").attr("checked",true);
                    $("#data_table").html(table_data);
                }
            });
            /*获取功能权限*/
            $.ajax({
                type: "GET",
                url: '/role/getRoleFunction',
                dataType: 'json',
                data: {
                    'roleId': role_id
                },
                cache: false,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status==401) {
                        alert("登录信息失效，请刷新！");
                    }
                },
                success: function (data) {
                    getRoleFunction(data);
                }

            });
        }});

    /*保存按钮点击事件*/

    $("#confir").click(function () {
        if($("#roleId").val()==null || $("#roleId").val()=="") {
            alert("请选择角色!");
            return false;
        }
        var new_check_array =[];
        var new_roleFunction = '';
        $('input[name="functionData"]:checked').each(function(){
            new_check_array.push($(this).attr("id"));
        });
        new_roleFunction = new_check_array.toString();
        $("#roleFunction").attr("value",new_roleFunction);
        $("#roleInfo").submit();
    });

    /*添加角色组跳转事件*/
    /*$("#roleGroup").click(function () {
        layer.open({
            title: '添加角色组',
            type: 1,
            area: ['500px', '300px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#add_role_group")
        });
    });*/

    function getRoleGroupList() {
        $.ajax({
            type: "GET",
            url: '/role_group/role_group_list',
            dataType: 'json',
            data: {
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401){
                    alert("登录信息失效，请刷新！");
                }
            },
            success: function (data) {
                $("#groupId").empty();
                $.each(data, function(key, val) {
                    $("#groupId").append('<option value="' + val.id + '">' + val.name + '</option>');
                });
                $("#groupId option[value= " + $("#roleGroupId").val() + "]").attr("selected",true);
            }
        });
    }

    $("#role").click(function () {
        /*获取角色组列表*/
        getRoleGroupList();
        /*打开弹窗*/
        layer.open({
            title: '添加角色',
            type: 1,
            area: ['500px', '300px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#add_role")
        })
    });

    $("#updateRole").click(function () {
        /*获取角色组列表*/
        getRoleGroupList();
        /*打开弹窗*/
        layer.open({
            title: '修改角色',
            type: 1,
            area: ['500px', '300px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#add_role")
        })
        $("#roleName").empty();
        $("#roleName").val($("#role_title").html());
        $("#roleId2").val($("#roleId").val());
    });

    /*添加成员图标点击事件*/
    $("#add_member").click(function () {
         member_index = layer.open({
            title: '添加成员',
            type: 1,
            area: ['500px', '420px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#edit_html")
        });
        $("#search_result").hide();
        $("#search_data").val("");
        $("#memberName").val("");
        $("#id").val("");
        $("#position").val("");
        $("#email").val("");
        $("#employeeId").val("");
        $("#cellphone").val("");
        $("#memberId").val("");
    });

    /*添加成员取消按钮点击事件*/
    $("#cancel_button").click(function () {
        layer.closeAll(); //关闭单个
    });


/*    /!*搜索成员按钮点击事件*!/
    $("#searchRoleMember").click(function () {
        $.ajax({
            type: "GET",     //AJAX提交方式为GET提交
            url: "ajaxSearch",   //处理页的URL地址
            dataType: 'json',
            data: {
                'textSearch': $("#roleMemberSearchText").val(),
                'roleId':$('#roleId').val()
            },
            cache: false,
            success: function (data) {
                console.log(data);
            }

        });
    });*/

    /*添加角色组取消按钮点击事件*/
    $("#role_group_cancel_button").click(function () {
        layer.closeAll();
    });

    /*添加角色取消按钮点击事件*/
    $("#role_cancel_button").click(function () {
        layer.closeAll();
    });
    var res_arr = [];
    /*搜索框动态输入事件*/
    $('#search_data').keyup(function () {   //输入框的id为search_data，这里监听输入框的keyup事件
        $("#search_result").empty();
        $.ajax({
            type: "GET",     //AJAX提交方式为GET提交
            url: "/role/ajaxSearch",   //处理页的URL地址
            dataType: 'json',
            data: {
                'textSearch': $('#search_data').val(),
                'roleId':$('#roleId').val()
            },
            cache: false,
            success: function (data) {
                //成功后执行的方法
                $("#search_result").show();
                for(var i = 0; i < data.length; i++){
                    res_arr.push(data[i].name);
                    $("#search_result").append('<li style="cursor: pointer;" cellphone="' + data[i].cellphone + '" employeeId="' + data[i].employeeId + '" email="' + data[i].email + '" position="' + data[i].position + '" id="' + data[i].id +'">'+data[i].memberName+'</li>');
                }
                $("#search_result").on("click","li",function(){
                    //console.log($(this).html());
                    $("#search_data").val($(this).html());
                    $("#search_result").hide();
                    $("#memberName").val($(this).html());
                    $("#id").val($(this).attr('id'));
                    $("#position").val($(this).attr('position'));
                    $("#email").val($(this).attr('email'));
                    $("#employeeId").val($(this).attr('employeeId'));
                    $("#cellphone").val($(this).attr('cellphone'));
                    $("#memberId").val($(this).attr('id'));
                });
            }

        });
    });

});

function getRoleFunction(data) {
    var function_data = '';
    var connect_data = '';
    var group_data = '';
    var no_sub_group_data = '';
    var sub_group_data = '';
    var no_sub_child_data = '';
    var child_data = '';
    var child_data_ul = '';
    //console.log(data);
    $.each(data, function (index, item) {
        if (typeof(data[index].subFunction) != "undefined") {
            connect_data ='<li class="resource_con "><ul>';
            group_data = '<li class="resource_con_tit fl"><input type="checkbox" onclick="selectAll(this)" id="' + item.functionType + '"/>' + item.typeName + '</li><li class="fl resource_con_r"><div class="resource_cla"><ul><li class="resource_selec_tit fl">';
            var subFunction = item.subFunction;
            sub_group_data = '';
            child_data_ul = '';
            $.each(subFunction,function (index) {
                sub_group_data +='<span>' + subFunction[index].subTypeName + '</span>';
                var roleFunction = subFunction[index].roleFunction;
                child_data = '';
                $.each(roleFunction, function (index) {
                    child_data +='<li><input type="checkbox" name="functionData" onclick="checkSelectAll(this)" class="check_condition" id="' + roleFunction[index].functionId + '" value="' + roleFunction[index].roleId + '">' +  roleFunction[index].functionName + '</li>';
                });
                child_data_ul +='<ul>' + child_data + '</ul>';
            });
            group_data += sub_group_data + '</li></ul></div>';
            function_data += connect_data + group_data +'<div class="resource_selec">'+ child_data_ul + '</div></li></ul></li>';
        } else {
            no_sub_group_data = '<li class="product_con p_top border_top"><ul><ul class="clearfix"><li class="fl product_tit tit_wid"><input type="checkbox" onclick="selectAll(this)" value="' + item.functionType + '"/>' + item.typeName + '</li><li class="fl border_left product_selec con_wid"><ul class="clearfix">';
            var no_sub_function = item.roleFunction;
            no_sub_child_data = '';
            $.each(no_sub_function,function (index) {
                no_sub_child_data += '<li class="fl margi"><input type="checkbox" name="functionData" onclick="checkSelectAll(this)" class="check_condition" id="' + no_sub_function[index].functionId + '" value="' + no_sub_function[index].roleId + '"/>' + no_sub_function[index].functionName + '</li>';
            });
            no_sub_group_data +=no_sub_child_data + '</ul></li></ul></ul></li>';
            function_data += no_sub_group_data;
        }
    });
    $("#function_data").html(function_data);
    //用于刷新的js
    $(".resource_selec_tit").on("click","span",function(){
        $(".resource_selec_tit span").css({color: "#000"});
        $(this).css({color: "red"});
        var ind = $(this).index();
        $(".resource_selec ul").hide();
        $(".resource_selec ul").eq(ind).css({'display': 'block'});
    });
    $(".check_condition").each(function () {
        if ($(this).val() != 'null') {
            $(this).attr("checked", 'true')
        }
    });
    /*用于判断选中的checkbox是否发生变化*/
    var old_check_array =[];
    var old_roleFunction = '';
    $('input[name="functionData"]:checked').each(function(){
        old_check_array.push($(this).attr("id"));
    });
    old_roleFunction = old_check_array.toString();
    $("#old_roleFunction").attr("value",old_roleFunction);
    
}

function selectAll(obj) {
    //console.log($(obj).prop( "checked"));
    if(($(obj).prop( "checked"))){
        $(obj).parent().parent().children().eq(1).find(':checkbox').prop("checked", true);
    }else{
        $(obj).parent().parent().children().eq(1).find(':checkbox').prop("checked", false);
    }
}

function checkSelectAll(obj) {
    //点击判断是否全选
        var sumcheckbox = $(obj).parent().parent().parent().find(':checkbox').length; //获取[name='checkbox[]']的个数
        var sumchecked = $(obj).parent().parent().parent().find(':checkbox:checked').length; //获取选中的[name='checkbox[]']的个数
        if (sumchecked == sumcheckbox) {
            $(obj).parent().parent().parent().parent().parent().parent().find(':checkbox').eq(0).prop("checked", true);
        }else {
            $(obj).parent().parent().parent().parent().parent().parent().find(':checkbox').eq(0).prop("checked", false);
        }
}

function deleteMember(id,roleId) {

    layer.confirm('确认要删除该角色成员？', {
        btn: ['确定','取消'], //按钮
        shade: false //不显示遮罩
    }, function(index){
        window.location.href = '/role/deleteRoleMember?id=' + id + '&roleId=' + roleId;
        layer.close(index);
    });

}

function check_is_present() {
    if($("#id").val()==""){
        layer.msg('请选择一个已有的成员！');
        return false;
    }else {
        return true;
    }
}

/*
function defaul(roleId){
    /!*初始化子标题和选中的树节点*!/
    if(roleId!=null&&roleId!="") {
        var tree_role = $('#box').find('.third').find('li').find('i[value=' + roleId + ']');
        $(".third").hide();
        $("#role_title").html(tree_role.next().html());
        tree_role.parent().parent().show();
        tree_role.parent().css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        $(".third").each(function (index, element) {
            if (element.children.length == 0) {
                $(element).prev().children("i").css({'background': 'url(\'../img/blank.png\') center no-repeat'});
            }
        });

        $(".second").css({'display': 'block'});
        $(".first i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        tree_role.parent().parent().prev().find('i').css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        tree_role.parent().css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        tree_role.css({color: '#FFF'});


        /!*$(".first i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        $(".second").css({'display': 'block'});
        if($("#roleId").val()!=null&&$("#roleId").val()!="") {
            $(".third").eq(0).css({'display': 'block'});
        }
        $(".sec").eq(0).children("i").css({'background': 'url(\'../img/daosanjiao.png\') center no-repeat'});
        // $(".tab_con_tit span").css({color: '#666', 'border-bottom': 'transparent'});
        $(".third").eq(0).children("li").eq(0).css({background: '#f95d5d', color: '#FFF', 'box-shadow': '4px 3px 6px grey'});
        $(".third").eq(0).children("li").eq(0).children(".iconfont").css({color: '#FFF'});*!/
}
*/

//返回首页前信息展示
var suc = $("#success").val();
if(suc != "") {
    alert(suc);
    $("#success").empty();
}

//成员信息修改
var member_index;
function editMemberInfo(id,dep,type,origin) {
    if(type == 1) {
        member_index = layer.open({
            title: '编辑成员信息',
            type: 2,
            area: ['500px', '550px'],
            shadeClose: true, //点击遮罩关闭
            scrollbar: false,
            content: '/user/infoEditShow/' + id + '/' + dep + '/' + origin
        });
    }
}

