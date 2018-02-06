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
        radioType: "all"
    },
    view: {
        dblClickExpand: false,//屏蔽掉双击事件
        showIcon: true,
        showLine: false
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
        onClick: onClick,
        onCheck: onClick
    }
};
function onClick(e,setting, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("cityTree");
    zTree.expandNode(treeNode);
    var v = "";
    nodes = zTree.getCheckedNodes(true);
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].id + ",";
    }
    v=v.substring(0,v.length-1);
    $("#parentId").val(v);
}


$(document).ready(function(){
    //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
    var citynodes = $.parseJSON($("#depWithEcho").val());
    var zTreeDemo = $.fn.zTree.init($("#cityTree"),setting, citynodes);
});