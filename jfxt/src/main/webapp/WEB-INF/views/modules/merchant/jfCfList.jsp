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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
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
							<img src="${jfimg}" style="width:20px; height:20px" onclick="showImg(this.src)">
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
</body>
</html>