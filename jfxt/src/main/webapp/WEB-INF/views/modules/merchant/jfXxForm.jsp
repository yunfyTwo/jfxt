<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网元信息管理</title>
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
		<li><a href="${ctx}/merchant/jfXx/list">网元信息列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfXx/form?id=${jfXx.id}">网元信息<shiro:hasPermission name="merchant:jfXx:edit">${not empty jfXx.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfXx:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfXx" action="${ctx}/merchant/jfXx/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
			<c:if test="${empty jfXx.id}">
				<form:input path="jfbh" value="<%=System.currentTimeMillis()%>" readonly="true" htmlEscape="false"  maxlength="255" class="input-xlarge required"/>
			</c:if>
			<c:if test="${not empty jfXx.id}">
				<form:input path="jfbh"  readonly="true" htmlEscape="false"  maxlength="255" class="input-xlarge required"/>
			</c:if>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属区域：</label>
			<div class="controls">
				<form:select path="jfjj" class="input-xlarge required"  cssStyle="width:176px;">
					<form:options items="${jfjjList}" itemLabel="jfjj" itemValue="jfjj" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网元名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网元属性：</label>
			<div class="controls">
				<form:input path="jfwz" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">问题描述：</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现场图片：</label>
			<div class="controls">
				<form:hidden id="jfxctp" path="jfxctp" htmlEscape="false" class="input-xlarge" cssStyle="border: 0px;"
							 placeholder="请上传现场图片！" readonly="true"/>
				<sys:ckfinder input="jfxctp" type="images" uploadPath="/merchant/jfXx" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">扩展字段1：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="kzzd1" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">扩展字段2：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="kzzd2" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">扩展字段3：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="kzzd3" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">扩展字段4：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="kzzd4" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:jfXx:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>