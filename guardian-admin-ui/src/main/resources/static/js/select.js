$(function (){
    $('#info-save').validate();
    $('#info-edit').validate();
    var citynodes = $.parseJSON($("#depWithEcho").val());
    var zTreeDemo = $.fn.zTree.init($("#cityTree"),setting, citynodes);
    var c = JSON.parse( $("#depWithEcho").val() );
    var n = "";
    for (var i=0;i<c.length;i++){
        if (c[i].checked != null){
            n += c[i].name + ",";
        }
    }
    $("#depTree").html(n.substring(0,n.length-1));
    if (n == "")
    {$("#depTree").html("部门");}
    $("#aaa").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 100,
        width:440,
        placeholder: "请选择部门"
    });

    $("#roleList").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 150,
        width:450,
        placeholder: "请选择角色",
        selectAll: true
    });
    $("#platform").multipleSelect({
        onCheckAll: function () {
            console.log($("#platform").val());
            $("#platform").next().find("button").children().eq(0).html("全部");
        },
        filter: true,
        multiple: true,
        multipleWidth: 200,
        width:440,
        placeholder: "请选择渠道",
        selectAll: true,
        selectAllText:"全部"
    });
    if (null!=$("#platform").val()){
        if ($("#platform").val().length==$("#platform")[0].options.length){
            $("#platform").next().find("button").children().eq(0).html("全部");
        }
    }
    $("#destination").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 200,
        width:440,
        placeholder: "请选择目的地",
        selectAll: true,
        selectAllText:"全部"
    });

    $("#productType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 200,
        width:440,
        placeholder: "请选择产品类型",
        selectAll: true,
        selectAllText:"全部"
    });

    $("#resourceType").multipleSelect({
        filter: true,
        multiple: true,
        multipleWidth: 200,
        width:440,
        placeholder: "请选择资源类型",
        selectAll: true,
        selectAllText:"全部"
    });
    $("#position").multipleSelect({
        filter: true,
        multiple: false,
        placeholder: "请选择岗位",
        multipleWidth: 120,
        width:440,
        selectAll: false,
        single:true
    });

    $("#roleType").multipleSelect({
        filter: true,
        multiple: false,
        multipleWidth: 120,
        width:440,
        placeholder: "请选择序列",
        selectAll: false,
        single:true
    });

    $("#ethnic").multipleSelect({
        filter: true,
        multiple: false,
        placeholder: "请选择族群",
        multipleWidth: 120,
        width:440,
        selectAll: false,
        single:true
    });

});
