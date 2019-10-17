
//获取项目根路径
function getContextPath() {
     // return "http://do.anssy.com:8082/publicity";
    // return "http://in.anssy.com:8086/publicity";


    return "http://39.97.246.149:8080/jfxt";
    // return "http://192.168.1.64:8080/jfxt";
}

//获取文件地址
// function getFilePath(){
//     return "http://192.168.1.66:8080";
// }

//获取路径中的参数
function GetArgsFromHref(sArgName) {
    var url = window.location.href;
    var args = url.split("?");
    var retval = "";
    if (args[0] == url) /*参数为空*/
    {
        return retval;
        /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for (var i = 0; i < args.length; i++) {
        str = args[i];
        var arg = str.split("=");
        if (arg.length <= 1) continue;
        if (arg[0] == sArgName) retval = arg[1];
    }
    return retval;
}

function crtTimeFtt(date) {
    if (date != null) {
        var date = new Date(date);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
}



