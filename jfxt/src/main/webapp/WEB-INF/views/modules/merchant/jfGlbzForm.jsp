<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网元管理标准和要求管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/merchant/jfGlbz/list">网元管理标准和要求列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfGlbz/form?id=${jfGlbz.id}">网元管理标准和要求<shiro:hasPermission name="merchant:jfGlbz:edit">${not empty jfGlbz.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfGlbz:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfGlbz" action="${ctx}/merchant/jfGlbz/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="ddbh" id="ddbh" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标准制定：</label>
			<div class="controls">
				<form:textarea path="shdz" id="shdz" htmlEscape="false"  rows="4"  class="input-xxlarge"/>
				<sys:ckeditor replace="shdz" uploadPath="/merchant/jfGlbz"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="merchant:jfGlbz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>