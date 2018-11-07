$(function () {
    $('#btn_ajax').click(function () {
        $.ajax({
            url: '/ajaxTest',
            dataType: 'json',
            success: function (data) {
                console.log("===ajax返回数据===" + JSON.stringify(data));
            }
        })

    });

    $('#btn_ajaxText').click(function () {
        // debugger;
        var tem = $.ajax({
            url: '/ajaxTextTest',
            // dataType: 'json', // 1.如果返回的是非json格式的String:"aa",不指定dataType:'text'，不能进入success，
                              // 2.如果后台返回json格式的String，并手动设置dataType:'json'，
                                // 会自动将返回的json字符串转换为json对象（不设置的话是json字符串）
                              // 3.如果返回的是java对象（User,Map），就算不设置dataType，也可以解析为JSON对象
            async: false, // 关闭异步，responseText才能获取到值（另外：进入success或error，responseText一样能获取到值）
            success: function (data) {
                // debugger;
                console.log("=====正确======== " + data.name);
            },
            error: function (data) {
                console.log("======错误====" + data);
            }
        }).responseText;
        // debugger;
        console.log(tem);

        // var data2 = $.ajax({
        //     url: '/ajaxTextTest',
        //     dataType: "text",
        //     success: function (data) {
        //         console.log("=====data======== " + data);
        //     }
        // }).responseText;
        // debugger;
        // console.log("===ResponseTest的值" + data2);

    });
})
