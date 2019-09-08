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
		<li><a href="${ctx}/merchant/jfXlZg/list">线路整改列表</a></li>
		<li class="active"><a href="${ctx}/merchant/jfXlZg/form?id=${jfXlZg.id}">线路整改<shiro:hasPermission name="merchant:jfXlZg:edit">${not empty jfXlZg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="merchant:jfXlZg:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jfXlZg" action="${ctx}/merchant/jfXlZg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">整改线路：</label>
			<div class="controls">
				<form:select path="zgjf" class="input-xlarge required"  cssStyle="width:176px;">
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">巡检人员：</label>
			<div class="controls">
			<form:input path="kzzd4" value="${loginName}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
					value="<fmt:formatDate value="${jfXlZg.zgrq}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">问题情况：</label>
			<div class="controls">
				<form:textarea path="zgwt" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改现场照片：</label>
			<div class="controls">
				<form:hidden id="cfxczp" path="cfxczp" htmlEscape="false" class="input-xlarge" cssStyle="border: 0px;"
							 placeholder="请上传整改现场照片！" readonly="true"/>
				<sys:ckfinder input="cfxczp" type="images" uploadPath="/merchant/jfXlZg" selectMultiple="true"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理方式：</label>
			<div class="controls">
				<form:textarea path="zgfs" htmlEscape="false" rows="1" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">需新建杆路（km）：</label>
			<div class="controls">
				<form:textarea path="xjgl" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更换吊线（km）：</label>
			<div class="controls">
				<form:textarea path="ghdx" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更换电杆（根）：</label>
			<div class="controls">
				<form:textarea path="ghdg" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扶正（偏杆/倒杆）（根）：</label>
			<div class="controls">
				<form:textarea path="dgfz" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新建拉线（条）：</label>
			<div class="controls">
				<form:textarea path="xjlx" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更换拉线（条）：</label>
			<div class="controls">
				<form:textarea path="ghlx" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">需更换光缆（km）：</label>
			<div class="controls">
				<form:textarea path="ghgl" htmlEscape="false" rows="1" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否已整改：</label>
			<div class="controls">
				<form:select path="iszg" htmlEscape="false" maxlength="10" class="input-xlarge " style="width: 100px;">
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用：</label>
			<div class="controls">
				<form:input path="ygfy" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="merchant:jfXlZg:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>