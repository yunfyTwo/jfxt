<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>"/> 
	<title>处罚管理</title>
	<script src="static/js/wbCommon.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="static/css/imgs.css" charset="utf-8"/>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/merchant/jfCf/list");
			$("#searchForm").submit();
        	return false;
        }

        function showCfImg(id) {
            layer.open({
                type: 2,
                title: '图片展示',
                shadeClose: true,
                shade: 0.5,
                maxmin: true,
                area: ["60%", "90%"],
                content:"${loc}/show/showCfImg.html?id="+id,
                end: function () {
                    layer.close(); //再执行关闭
                    location.reload();
                }
            });
        }

	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出处罚单数据吗？（仅导出当前页数据，如需导出全部请在页面最下方(“30”数字处)输入要导出的条数按回车即可）","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/merchant/jfCf/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			/* $("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			}); */
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/merchant/jfCf/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/jfCf/list">处罚列表</a></li>
		<%--<shiro:hasPermission name="merchant:jfCf:edit"><li><a href="${ctx}/merchant/jfCf/form">处罚添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="jfCf" action="${ctx}/merchant/jfCf/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

			<li>
				处罚通知单号：
				<form:input path="cftzd" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>

			<li>巡检人员：
				<form:input path="kzzd3" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li>&nbsp;&nbsp;&nbsp;处罚网元：
				<form:select path="cfjf" class="input-xlarge required"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li>&nbsp;&nbsp;&nbsp;网元属性：
				<form:select path="cfjf.jfwz" class="input-xlarge required"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
					<form:option value="B类机房" label="B类机房"/>
						<form:option value="C类机房" label="C类机房"/>
						<form:option value="D类机房" label="D类机房"/>
						<form:option value="自留基站" label="自留基站"/>
						<form:option value="线路" label="线路"/>
				</form:select>
			</li>
			
			<li>&nbsp;&nbsp;&nbsp;
				日期：
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfCf.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfCf.overDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<br>
			<br>
			<li>
				<label>处罚对象：</label>
				<form:select path="cfdx" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('cfdx')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>处罚梯度：</label>
				<form:select path="cftd" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('cftd')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page()"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed container">
		<thead>
			<tr>
				<th>处罚通知单号</th>
				<th>巡检人员</th>
				<th>处罚网元</th>
				<th>网元属性</th>
				<th>日期</th>
				<th>处罚对象</th>
				<th>处罚梯度</th>
				<th>处罚金额（元）</th>
				<th>处罚现场照片</th>
				<shiro:hasPermission name="merchant:jfCf:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jfCf">
			<tr>
				<%--<td><a href="${ctx}/merchant/jfCf/form?id=${jfCf.id}">--%>
						<%--${jfCf.cftzd}</a>--%>
				<%--</td>--%>
				<td>
						${jfCf.cftzd}
				</td>
				<td>
						${jfCf.kzzd3}
				</td>
				<td>
					${jfCf.cfjf.name}
				</td>
				<td>
					${jfCf.cfjf.jfwz}
				</td>
				<td>
					<fmt:formatDate value="${jfCf.cfrq}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(jfCf.cfdx, 'cfdx', '')}
				</td>
				<td>
					${fns:getDictLabel(jfCf.cftd, 'cftd', '')}
				</td>
				<td>
					${jfCf.kzzd4}
				</td>
				<td>
					<c:if test="${not empty jfCf.xctps}">
						<c:forEach items="${jfCf.xctps}" var="jfimg">
							<img src="/jfxt/userfiles/1/images/photo/2019/09/ljz.gif" alt="1" style="width:20px; height:20px" data-src="${jfimg}" onclick="showImg(this.src)" >
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfCf.xctps}">
						暂未上传图片
					</c:if>
				</td>
				<shiro:hasPermission name="merchant:jfCf:edit"><td>
    				<a href="${ctx}/merchant/jfCf/form?id=${jfCf.id}">修改</a>
					<a href="${ctx}/merchant/jfCf/delete?id=${jfCf.id}" onclick="return confirmx('确认要删除该处罚吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="img_content" id="imgContent">
	<div style="margin-left: 10px;margin-top: 10px;"><img src="" style="width:400px; height:500px" id="imgCon"></div>
	<div  class="colse_btn" onclick="col()">关闭</div>
	<script>
            // 一开始没有滚动的时候，出现在视窗中的图片也会加载
            start();
            // 当页面开始滚动的时候，遍历图片，如果图片出现在视窗中，就加载图片
            var clock; //函数节流
            $(window).on('scroll',function(){
                if(clock){
                    clearTimeout(clock);
                }
                clock = setTimeout(function(){
                    start()
                },200)
            })
            function start(){
                 $('.container img').not('[data-isLoading]').each(function () {
                    if (isShow($(this))) {
                        loadImg($(this));
                    }
                })
            }
            // 判断图片是否出现在视窗的函数
            function isShow($node){
                return $node.offset().top <= $(window).height()+$(window).scrollTop();
            }

            // 加载图片的函数，就是把自定义属性data-src 存储的真正的图片地址，赋值给src
            function loadImg($img){
                    $img.attr('src', $img.attr('data-src'));

                    // 已经加载的图片，我给它设置一个属性，值为1，作为标识
                    // 弄这个的初衷是因为，每次滚动的时候，所有的图片都会遍历一遍，这样有点浪费，所以做个标识，滚动的时候只遍历哪些还没有加载的图片
                    $img.attr('data-isLoading',1);
            }
        </script>
</body>
</html>