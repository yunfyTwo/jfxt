<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网元商品信息管理</title>
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
		<li><a href="${ctx}/merchant/jfSpxx/list">网元商品信息列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfSpxx/form?id=${jfSpxx.id}">网元商品信息<shiro:hasPermission name="merchant:jfSpxx:edit">${not empty jfSpxx.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfSpxx:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfSpxx" action="${ctx}/merchant/jfSpxx/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品编号：</label>
			<div class="controls">
				<form:input path="cpbh" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品型号：</label>
			<div class="controls">
				<form:input path="cpxh" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重量：</label>
			<div class="controls">
				<form:input path="cpzl" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
				<form:input path="cpgys" htmlEscape="false" maxlength="1000" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成本价：</label>
			<div class="controls">
				<form:input path="cpcbj" htmlEscape="false" class="input-xlarge number required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售价上限：</label>
			<div class="controls">
				<form:input path="cpsjsx" htmlEscape="false" class="input-xlarge number "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售价下限：</label>
			<div class="controls">
				<form:input path="cpsjxx" htmlEscape="false" class="input-xlarge number "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">折扣：</label>
			<div class="controls">
				<form:input path="cpzk" htmlEscape="false" maxlength="10" class="input-xlarge number "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存量：</label>
			<div class="controls">
				<form:input path="cpkcl" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="cpsl" htmlEscape="false" class="input-xlarge digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期：</label>
			<div class="controls">
				<form:input path="cpyxq" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价格更新时间：</label>
			<div class="controls">
				<input name="cpjggxsj" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${jfSpxx.cpjggxsj}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
			<shiro:hasPermission name="merchant:jfSpxx:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>