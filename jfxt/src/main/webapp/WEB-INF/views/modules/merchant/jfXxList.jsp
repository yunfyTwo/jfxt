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
	<title>网元信息管理</title>
	<script src="static/js/wbCommon.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="static/css/imgs.css" charset="utf-8"/>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/merchant/jfXx/list");
			$("#searchForm").submit();
        	return false;
        }

        function showXxImg(id) {
            alert(id);
            layer.open({
                type: 2,
                title: '图片展示',
                shadeClose: true,
                shade: 0.5,
                maxmin: true,
                area: ["60%", "90%"],
                content:"${loc}/show/showJfImg.html?id="+id,
                end: function () {
                    layer.close(); //再执行关闭
                    location.reload();
                }
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/jfXx/list">网元信息列表</a></li>
		<shiro:hasPermission name="merchant:jfXx:edit"><li><a href="${ctx}/merchant/jfXx/form">网元信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jfXx" action="${ctx}/merchant/jfXx/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>网元名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li>
			<label class="control-label" >网元属性：</label>
				<form:select path="jfwz" htmlEscape="false" maxlength="255" class="input-xlarge " style="width: 100px;">
				<form:option value="" label="请选择"/>
				<form:option value="机房" label="机房"/>
				<form:option value="基站" label="基站"/>
				<form:option value="线路" label="线路"/>
				</form:select>
			</li>
			<li>
			<label class="control-label">所属区域：</label>
				<form:select path="jfjj" htmlEscape="false" maxlength="255" class="input-xlarge ">
				<form:option value="" label="请选择"/>
				<form:option value="苍溪网络运营分局" label="苍溪网络运营分局"/>
				<form:option value="朝天网络运营分局" label="朝天网络运营分局"/>
				<form:option value="城区网络运营分局" label="城区网络运营分局"/>
				<form:option value="剑阁网络运营分局" label="剑阁网络运营分局"/>
				<form:option value="郊区网络运营分局" label="郊区网络运营分局"/>
				<form:option value="青川网络运营分局" label="青川网络运营分局"/>
				<form:option value="旺苍网络运营分局" label="旺苍网络运营分局"/>
				<form:option value="昭化网络运营分局" label="昭化网络运营分局"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>网元名称</th>
				<th>网元属性</th>
				<th>所属区域</th>
				<th>问题描述</th>
				<th>现场图片</th>
				<shiro:hasPermission name="merchant:jfXx:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jfXx">
			<tr>
				<td>
					<a href="${ctx}/merchant/jfXx/form?id=${jfXx.id}">
						${jfXx.jfbh}
					</a>
				</td>
				<td>
					${jfXx.name}
				</td>
				<td>
					${jfXx.jfwz}
				</td>
				<td>
					${jfXx.jfjj}
				</td>
				<td>
					${jfXx.remarks}
				</td>
				<td>
					<c:if test="${not empty jfXx.xctps}">
						<c:forEach items="${jfXx.xctps}" var="jfimg">
							<img src="${jfimg}" style="width:20px; height:20px" onclick="showImg(this.src)">
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfXx.xctps}">
						暂未上传图片
					</c:if>
				</td>
				<shiro:hasPermission name="merchant:jfXx:edit"><td>
    				<a href="${ctx}/merchant/jfXx/form?id=${jfXx.id}">修改</a>
					<a href="${ctx}/merchant/jfXx/delete?id=${jfXx.id}" onclick="return confirmx('确认要删除该网元信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!-- <div id="fade" class="black_overlay"  onClick="closeImgWindow()"/> -->
	<div class="img_content" id="imgContent">
	<div style="margin-left: 10px;margin-top: 10px;"><img src="" style="width:400px; height:500px" id="imgCon"></div>
	<div  class="colse_btn" onclick="col()">关闭</div>
</body>

</html>