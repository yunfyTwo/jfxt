<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网元商品信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/merchant/jfSpxx/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/jfSpxx/list">网元商品信息列表</a></li>
		<shiro:hasPermission name="merchant:jfSpxx:edit"><li><a href="${ctx}/merchant/jfSpxx/form">网元商品信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jfSpxx" action="${ctx}/merchant/jfSpxx/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>产品编号：</label>
				<form:input path="cpbh" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>产品型号：</label>
				<form:input path="cpxh" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>产品编号</th>
				<th>产品型号</th>
				<th>重量</th>
				<th>供应商</th>
				<th>成本价</th>
				<th>售价上限</th>
				<th>售价下限</th>
				<th>折扣</th>
				<th>库存量</th>
				<th>数量</th>
				<th>有效期</th>
				<th>价格更新时间</th>
				<shiro:hasPermission name="merchant:jfSpxx:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jfSpxx">
			<tr>
				<td><a href="${ctx}/merchant/jfSpxx/form?id=${jfSpxx.id}">
					${jfSpxx.name}
				</a></td>
				<td>
					${jfSpxx.cpbh}
				</td>
				<td>
					${jfSpxx.cpxh}
				</td>
				<td>
					${jfSpxx.cpzl}
				</td>
				<td>
					${jfSpxx.cpgys}
				</td>
				<td>
					${jfSpxx.cpcbj}
				</td>
				<td>
					${jfSpxx.cpsjsx}
				</td>
				<td>
					${jfSpxx.cpsjxx}
				</td>
				<td>
					${jfSpxx.cpzk}
				</td>
				<td>
					${jfSpxx.cpkcl}
				</td>
				<td>
					${jfSpxx.cpsl}
				</td>
				<td>
					${jfSpxx.cpyxq}
				</td>
				<td>
					<fmt:formatDate value="${jfSpxx.cpjggxsj}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="merchant:jfSpxx:edit"><td>
    				<a href="${ctx}/merchant/jfSpxx/form?id=${jfSpxx.id}">修改</a>
					<a href="${ctx}/merchant/jfSpxx/delete?id=${jfSpxx.id}" onclick="return confirmx('确认要删除该商品信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>