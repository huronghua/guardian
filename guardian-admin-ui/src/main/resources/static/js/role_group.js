$(function () {
    /*添加或编辑角色组跳转事件*/
    $("#createRoleGroup").click(function () {
        layer.open({
            title: '添加角色组',
            type: 1,
            area: ['500px', '200px'],
            shadeClose: false, //点击遮罩关闭
            scrollbar: false,
            content: $("#add_role_group")
        });
    });

    /*添加或编辑角色组取消按钮点击事件*/
    $("#cancel_button").click(function () {
        layer.closeAll();
    });
})



function deleteRoleGroup(obj) {
    var role_group_id = $(obj).attr("value");
    layer.confirm('确认要删除该角色组？', {
        btn: ['确定','取消'], //按钮
        shade: false //不显示遮罩
    }, function(index){
        window.location.href = '/role_group/deleteRoleGroup?roleGroupId=' + role_group_id;
        layer.close(index);
    });

}

function editRoleGroup(obj){
    member_index = layer.open({
        title: '编辑角色组',
        type: 1,
        area: ['500px', '220px'],
        shadeClose: false, //点击遮罩关闭
        scrollbar: false,
        content: $("#add_role_group")
    });
    $("#role_group_id").val($(obj).attr("value"));
    $("#roleGroupName").val($(obj).parent().parent().find('td[id^="td_role_group_name"]').attr("value"));
}
