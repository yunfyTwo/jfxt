
//获取项目根路径
/*window.baseUrl = "http://localhost:8080/tx/";*/
function getContextPath() {
    /*var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    //return result;*/
   
    return "http://192.168.31.201:8888/jfxt";
    //return "http://192.168.1.64:8080/jfxt";
//  return "http://116.255.175.24:8888/jfxt";
}

//获取文件地址
function getFilePath(){
	return "http://192.168.31.201:8888/jfxt";
//	 return "http://116.255.175.24:8888/jfxt";
}

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

function crtTimeFtt(date){
    if (date != null){
        var date = new Date(date);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
