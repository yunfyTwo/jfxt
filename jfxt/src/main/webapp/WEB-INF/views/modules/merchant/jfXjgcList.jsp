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
	<title>巡检过程管理</title>
	<script src="static/js/wbCommon.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="static/css/imgs.css" charset="utf-8"/>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/merchant/jfXjgc/list");
			$("#searchForm").submit();
        	return false;
        }

        function showXjGcImg(id) {
            alert(id);
            layer.open({
                type: 2,
                title: '图片展示',
                shadeClose: true,
                shade: 0.5,
                maxmin: true,
                area: ["60%", "90%"],
                content:"${loc}/show/showXjGcImg.html?id="+id,
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
		<li class="active"><a href="${ctx}/merchant/jfXjgc/list">巡检过程列表</a></li>
		<%--<shiro:hasPermission name="merchant:jfXjgc:edit"><li><a href="${ctx}/merchant/jfXjgc/form">巡检过程添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="jfXjgc" action="${ctx}/merchant/jfXjgc/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>&nbsp;&nbsp;&nbsp;处罚网元：
				<form:select path="xjjf" class="input-xlarge"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li>&nbsp;&nbsp;&nbsp;巡检时间：
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfXjgc.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfXjgc.overDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>巡检网元</th>
				<th>巡检时间</th>
				<th>巡检人员</th>
				<th>现场图片</th>
				<th>整改判断</th>
				<th>巡检是否通过</th>
				<th>巡检打分</th>
				<shiro:hasPermission name="merchant:jfXjgc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jfXjgc">
			<tr>
				<%--<td><a href="${ctx}/merchant/jfXjgc/form?id=${jfXjgc.id}">--%>
					<%--${jfXjgc.xjjf.name}--%>
				<%--</a></td>--%>
				<td>
						${jfXjgc.xjjf.name}
				</td>
				<td>
					<fmt:formatDate value="${jfXjgc.xjsj}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${jfXjgc.xjry.name}
				</td>
				<td>
					<c:if test="${not empty jfXjgc.xctps}">
						<c:forEach items="${jfXjgc.xctps}" var="jfimg">
							<img src="${jfimg}" width="40px" height="50px" onclick="showImg(this.src)">
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfXjgc.xctps}">
						暂未上传图片
					</c:if>
				</td>
				<td>
					${jfXjgc.zgpd}
				</td>
				<td>
					${fns:getDictLabel(jfXjgc.xjsftg, 'yes_no', '')}
				</td>
				<td>
					${jfXjgc.xjdf}
				</td>
				<shiro:hasPermission name="merchant:jfXjgc:edit"><td>
    				<%--<a href="${ctx}/merchant/jfXjgc/form?id=${jfXjgc.id}">修改</a>--%>
					<a href="${ctx}/merchant/jfXjgc/delete?id=${jfXjgc.id}" onclick="return confirmx('确认要删除该巡检过程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!-- <div id="fade" class="black_overlay"  onClick="closeImgWindow()"/> -->
	<div class="img_content" id="imgContent">
	<div style="margin-left: 10px;margin-top: 10px;"><img src="" width="400px" height="500px" id="imgCon"></div>
	<div  class="colse_btn" onclick="col()">关闭</div>
</body>
</html>