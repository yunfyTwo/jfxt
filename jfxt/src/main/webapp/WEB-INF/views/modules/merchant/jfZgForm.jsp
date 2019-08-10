<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>整改管理</title>
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
		<li><a href="${ctx}/merchant/jfZg/list">整改列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfZg/form?id=${jfZg.id}">整改<shiro:hasPermission name="merchant:jfZg:edit">${not empty jfZg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfZg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfZg" action="${ctx}/merchant/jfZg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">整改网元：</label>
			<div class="controls">
				<form:select path="zgjf" class="input-xlarge required"  cssStyle="width:176px;">
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改单号：</label>
			<div class="controls">
				<form:input path="zgdh" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日期：</label>
			<div class="controls">
				<input name="zgrq" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${jfZg.zgrq}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改要求：</label>
			<div class="controls">
				<form:textarea path="zgyq" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改现场照片：</label>
			<div class="controls">
				<form:hidden id="cfxczp" path="cfxczp" htmlEscape="false" class="input-xlarge" cssStyle="border: 0px;"
							 placeholder="请上传整改现场照片！" readonly="true"/>
				<sys:ckfinder input="cfxczp" type="images" uploadPath="/merchant/jfZg" selectMultiple="true"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否存在安全隐患：</label>
			<div class="controls">
				<form:textarea path="isSafetyHazard" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患简要说明：</label>
			<div class="controls">
				<form:textarea path="briefDescription" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患原因：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有ODF架/柜：</label>
			<div class="controls">
				<form:textarea path="isODF" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要网络设备整治割接：</label>
			<div class="controls">
				<form:textarea path="isCutOver" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患级别：</label>
			<div class="controls">
				<form:select path="isCutOverContent" htmlEscape="false" maxlength="25" class="input-xlarge " style="width: 100px;">
					<form:option value="" label="请选择"/>
					<form:option value="特级" label="特级"/>
					<form:option value="紧急" label="紧急"/>
					<form:option value="重要" label="重要"/>
					<form:option value="一般 " label="一般"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要光缆割接：</label>
			<div class="controls">
				<form:textarea path="isOpticalCable" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">光缆割接量（条/芯）：</label>
			<div class="controls">
				<form:textarea path="opticalCableCutting" htmlEscape="false" rows="1" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整治批次：</label>
			<div class="controls">
				<form:select path="opticalCableContent" htmlEscape="false" maxlength="25" class="input-xlarge " style="width: 100px;">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="1"/>
					<form:option value="2" label="2"/>
					<form:option value="3" label="3"/>
					<form:option value="4 " label="4"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要环境整治：</label>
			<div class="controls">
				<form:textarea path="needRemediation" htmlEscape="false" rows="1" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">环境整治内容描述（门、窗、墙面等）：</label>
			<div class="controls">
				<form:textarea path="contentDescription" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">是否已整改：</label>
			<div class="controls">
				<form:input path="kzzd2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用：</label>
			<div class="controls">
				<form:input path="kzzd3" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
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
			<shiro:hasPermission name="merchant:jfZg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>