<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处罚管理</title>
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
		<li><a href="${ctx}/merchant/jfCf/list">处罚列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfCf/form?id=${jfCf.id}">处罚<shiro:hasPermission name="merchant:jfCf:edit">${not empty jfCf.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfCf:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfCf" action="${ctx}/merchant/jfCf/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">处罚通知单号：</label>
			<div class="controls">
				<form:input path="cftzd" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">巡检人员：</label>
			<div class="controls">
				<form:input path="kzzd3" value="${loginName}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚网元：</label>
			<div class="controls">
				<form:select path="cfjf" class="input-xlarge required"  cssStyle="width:176px;">
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日期：</label>
			<div class="controls">
				<input name="cfrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${jfCf.cfrq}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚对象：</label>
			<div class="controls">
				<form:select path="cfdx" class="input-xlarge ">
					<form:options items="${fns:getDictList('cfdx')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚梯度：</label>
			<div class="controls">
				<form:select path="cftd" class="input-xlarge ">
					<form:options items="${fns:getDictList('cftd')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">处罚权限拥有者：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="cfqxyyz" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">处罚原因描述：</label>
			<div class="controls">
				<form:textarea path="cfyyms" id="cfyyms" htmlEscape="false"  rows="4"  class="input-xxlarge"/>
				<%--<sys:ckeditor replace="cfyyms" uploadPath="/merchant/jfCf"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚金额（元）：</label>
			<div class="controls">
				<form:input path="kzzd4" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚现场照片：</label>
			<div class="controls">
				<form:hidden id="cfxczp" path="cfxczp" htmlEscape="false" class="input-xlarge" cssStyle="border: 0px;"
							 placeholder="请上传处罚现场照片！" readonly="true"/>
				<sys:ckfinder input="cfxczp" type="images" uploadPath="/merchant/jfCf" selectMultiple="true"/>
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
			<%--<label class="control-label">扩展字段4：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="kzzd4" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="merchant:jfCf:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>