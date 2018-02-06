$(function () {

    $(window).on('load', function () {
        $('#usertype').selectpicker({
            'selectedText': 'cat'
        });
    });
    /*筛选条件多选控件*/
    $('#o_status_list').multiselect({
        enableClickableOptGroups: true,
        includeSelectAllOption: true,
        selectAll:true,
        selectAllText:'全部',
        buttonWidth: '200px',
        dropRight: true,
        maxHeight: 200,
        nonSelectedText: '请选择',
        numberDisplayed: 10,
        allSelectedText:'全部'
    });
    $('#destination').multiselect({
        enableClickableOptGroups: true,
        includeSelectAllOption: true,
        selectAll:true,
        selectAllText:'全部',
        buttonWidth: '200px',
        dropRight: true,
        maxHeight: 200,
        nonSelectedText: '请选择',
        numberDisplayed: 10,
        allSelectedText:'全部'
    });
    $('#platform').multiselect({
        enableClickableOptGroups: true,
        includeSelectAllOption: true,
        selectAll:true,
        selectAllText:'全部',
        buttonWidth: '200px',
        dropRight: true,
        maxHeight: 200,
        nonSelectedText: '请选择',
        numberDisplayed: 10,
        allSelectedText:'全部'
    });
    $('#resourceType').multiselect({
        enableClickableOptGroups: true,
        includeSelectAllOption: true,
        selectAll:true,
        selectAllText:'全部',
        buttonWidth: '200px',
        dropRight: true,
        maxHeight: 200,
        nonSelectedText: '请选择',
        numberDisplayed: 10,
        allSelectedText:'全部'
    });
    $.ajax({
        type: "GET",
        url: 'get_data_dictionary',
        dataType: 'json',
        data: {
            'table_name': 'product_order_tag'
        },
        cache: false,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(XMLHttpRequest.status==401) {
                alert("登录信息失效，请刷新！");
            }
        },
        success: function (data) {
            $.each(data, function (index, item) {
                $("#vision_condition_1").append('<option value="' + data[index].id + '">' + data[index].name + '</option>');
            });
            $('#vision_condition_1').multiselect('rebuild');
            $('#vision_condition_1').multiselect('refresh');
        }
    });
    performAjaxDestination();


    /*视角切换按钮点击事件*/
    $(":radio").click(function () {
        var vision = $("input[type='radio']:checked").val();
        var table_name;
        if (vision == 1) {
            table_name = "product_order_tag";
            cleanMultiSelectData();
            performAjaxDestination;
            $("#vision_condition_2").show();
            $(".btn-group").eq(3).show();
        }
        if (vision == 2) {
            table_name = "order_platform";
            cleanMultiSelectData();
            $("#vision_condition_2").hide();
            $(".btn-group").eq(3).hide();
        }
        if (vision == 3) {
            table_name = "order_platform";
            cleanMultiSelectData();
            performAjaxDestination();
            $("#vision_condition_2").show();
            $(".btn-group").eq(3).show();
        }
        $.ajax({
            type: "GET",
            url: 'get_data_dictionary',
            dataType: 'json',
            data: {
                'table_name': table_name
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401){
                    alert("登录信息失效，请刷新！");
                }
            },
            success: function (data) {
                console.log(data);
                if(vision !=1){
                    $.each(data, function (index, item) {
                        var str = '';
                        if(typeof(data[index].children)!="undefined"){
                            $.each(data[index].children,function (secondindex) {
                                str = str +'<option value="' +  data[index].children[secondindex].id + '">' +data[index].children[secondindex].name + '</option>'
                            });
                            $("#vision_condition_1").append('<optgroup label="'+ data[index].name + '" value="' + data[index].id + '">' + '<option value="' +  data[index].id + '">' +data[index].name + '</option>' +str +'</optgroup>');
                        }else {
                            $("#vision_condition_1").append('<optgroup label="'+ data[index].name + '" value="' + data[index].id + '">' + '<option value="' +  data[index].id + '">' +data[index].name + '</option>' + '</optgroup>');
                        }
                    });
                }else{
                    $.each(data, function (index, item) {
                        $("#vision_condition_1").append('<option value="' + data[index].id + '">' + data[index].name + '</option>');
                    });
                }
                $('#vision_condition_1').multiselect('rebuild');
                $('#vision_condition_1').multiselect('refresh');
            }
        })
    });

    /*查询数据提交事件*/
    $("#submit_button").click(function () {
        bindData();
    });
    //excel下载
    $("#download").click(function () {
        bindData();
        if (checkData())
        {
            $("#salesDaily").attr('action', $("#newurl").val());    //通过jquery为action属性赋值
            $("#salesDaily").submit();    //提交表单
        }

        $("#salesDaily").attr('action', '/sales/daily-search');    //通过jquery为action属性赋值
    });

    //JS数据校验
    function checkData(){
        var vision = $("input[type='radio']:checked").val();
        if(($("#date_range").val() == '')){
            alert("请选择日期");
            $("#date_range").focus();
            return false;
        }
        if(($("#o_status_list").val() == '')){
            alert("请选择订单状态");
            $("#o_status_list").focus();
            return false;
        }
        if(($("#vision_condition_1").val() == '')){
            alert("请选择筛选条件");
            $("#vision_condition_1").focus();
            return false;
        }
        if (vision==1||vision==3)
        {
            if(($("#vision_condition_2").val() == '')){
            alert("请选择筛选条件");
            $("#vision_condition_2").focus();
            return false;
        }}

        return true;
    }

    function performAjaxDestination() {
        $.ajax({
            type: "GET",
            url: 'get_data_dictionary',
            dataType: 'json',
            data: {
                'table_name': 'tag'
            },
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==401){
                    alert("登录信息失效，请刷新！");
                }
            },
            success: function (data) {
                $.each(data, function (index, item) {
                    $("#vision_condition_2").append('<option value="' + data[index].id + '">' + data[index].name + '</option>');
                });
                $('#vision_condition_2').multiselect('rebuild');
                $('#vision_condition_2').multiselect('refresh');
            }
        });

    }
    
    function cleanMultiSelectData() {
        $("#vision_condition_1").empty();
        $("#vision_condition_2").empty();
    }
    
    function bindData() {
        var date_range = $("#date_range").val();
        $("#o_status").attr("value", $("#o_status_list").val());
        var vision = $("input[type='radio']:checked").val();
        if(vision==1){
            $("#product_type_list").attr("value", $("#vision_condition_1").val());
            $("#destination_list").attr("value", $("#vision_condition_2").val());
            $("#order_platform_list").empty();
        }
        else if(vision==2){
            $("#order_platform_list").attr("value", $("#vision_condition_1").val());
            $("#product_type_list").empty();
            $("#destination_list").empty();
        }
        else if(vision==3){
            $("#order_platform_list").attr("value", $("#vision_condition_1").val());
            $("#destination_list").attr("value", $("#vision_condition_2").val());
            $("#product_type_list").empty();
        }
    }

});