/**
 * Created by banma on 2017/9/21.
 */
/**ztree的参数配置，setting主要是设置一些tree的属性，是本地数据源，还是远程，动画效果，是否含有复选框等等**/
var setting = {
    check: { /**复选框**/
        enable: true,
        chkboxType: {"Y":"", "N":""},
        nocheckInherit: false,
        chkStyle: "checkbox",
    },
    view: {
        dblClickExpand: true,
        showIcon: true,
        showLine: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: 0   //根节点
        }
    },
    callback: {
    onCheck: onCheck
    }
};
function onCheck(e,setting,treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("cityTree");
    nodes = treeObj.getCheckedNodes(true);
    var v = "";
    var n = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].id + ",";
        n += nodes[i].name + ",";
    }
    v=v.substring(0,v.length-1);
    $("#departmentList").val(v);
    $("#depTree").html(n.substring(0,n.length-1));

}

$(document).ready(function(){//初始化ztree对象
    var citynodes = $.parseJSON($("#depWithEcho").val());
    var zTreeDemo = $.fn.zTree.init($("#cityTree"),setting, citynodes);

});
