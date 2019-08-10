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
	
<title>整改管理</title>
<script src="static/js/wbCommon.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="static/css/imgs.css" charset="utf-8"/>
<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/merchant/jfZg/list");
			$("#searchForm").submit();
        	return false;
        }

        function showZgImg(id) {
            alert(id);
            layer.open({
                type: 2,
                title: '图片展示',
                shadeClose: true,
                shade: 0.5,
                maxmin: true,
                area: ["60%", "90%"],
                content:"${loc}/show/showZgimg.html?id="+id,
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
		<li class="active"><a href="${ctx}/merchant/jfZg/list">整改列表</a></li>
		<%--<shiro:hasPermission name="merchant:jfZg:edit"><li><a href="${ctx}/merchant/jfZg/form">整改添加</a></li></shiro:hasPermission>--%>
	</ul>
	
	<form:form id="searchForm" modelAttribute="jfZg" action="${ctx}/merchant/jfZg/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" >
			<li><label>整改网元：</label>
				<form:select path="zgjf" class="input-xlarge"  cssStyle="width:150px;">
					<form:option value="" label="请选择"/>
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否已整改：</label>
				<form:select path="kzzd2" class="input-xlarge"  cssStyle="width:80px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			<li><label>单号：</label>
				<form:input path="zgdh" htmlEscape="false" maxlength="55" class="input-medium"/>
			</li>
			<li><label>日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfZg.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfZg.overDate}" pattern="yyyy-MM-dd"/>"
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
				<th>整改网元</th>
				<th>整改单号</th>
				<th>日期</th>
				<th>整改要求</th>
				<th>整改现场照片</th>
				<th>是否存在安全隐患</th>
				<th>隐患简要说明</th>
				<th>隐患原因</th>
				<th>是否有ODF架/柜</th>
				<th>是否需要网络设备整治割接</th>
				<th>网络设备整治割接内容描述</th>
				<th>是否需要光缆割接</th>
				<th>光缆割接量（条/芯）</th>
				<th>光缆整治割接内容描述</th>
				<th>是否需要环境整治</th>
				<th>环境整治内容描述（门、窗、墙面等）</th>
				<th>是否已整改</th>
				<th>整改费用</th>
				<shiro:hasPermission name="merchant:jfZg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${page.list}" var="jfZg">
			<tr>
				<%--<td><a href="${ctx}/merchant/jfZg/form?id=${jfZg.id}">--%>
					<%--${jfZg.zgjf.name}--%>
				<%--</a></td>--%>
					<td>
						${jfZg.zgjf.name}
					</td>
				<td>
					${jfZg.zgdh}
				</td>
				<td>
					<fmt:formatDate value="${jfZg.zgrq}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${jfZg.zgyq}
				</td>
				<td>
					<c:if test="${not empty jfZg.xctps}">
						<c:forEach items="${jfZg.xctps}" var="jfimg">
							<img src="${jfimg}" style="width:20px; height:20px" onclick="showImg(this.src)">
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfZg.xctps}">
						暂未上传图片
					</c:if>
				</td>
					<td>${jfZg.isSafetyHazard}</td>
					<td>${jfZg.briefDescription}</td>
					<td>${jfZg.reason}</td>
					<td>${jfZg.isODF}</td>
					<td>${jfZg.isCutOver}</td>
					<td>${jfZg.isCutOverContent}</td>
					<td>${jfZg.isOpticalCable}</td>
					<td>${jfZg.opticalCableCutting}</td>
					<td>${jfZg.opticalCableContent}</td>
					<td>${jfZg.needRemediation}</td>
					<td>${jfZg.contentDescription}</td>
					<td>${jfZg.kzzd2}</td>
					<td>${jfZg.kzzd3}</td>


					<shiro:hasPermission name="merchant:jfZg:edit"><td>
    				<a href="${ctx}/merchant/jfZg/form?id=${jfZg.id}">修改</a>
					<a href="${ctx}/merchant/jfZg/delete?id=${jfZg.id}" onclick="return confirmx('确认要删除该整改吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="img_content" id="imgContent">
	<div style="margin-left: 10px;margin-top: 10px;"><img src="" style="width:400px; height:500px" id="imgCon"></div>
	<div  class="colse_btn" onclick="col()">关闭</div>
</div>
</body>
</html>