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
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出网元整改单数据吗？（仅导出当前页数据，如需导出全部请在页面最下方(“30”数字处)输入要导出的条数按回车即可）","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/merchant/jfZg/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			 $("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/merchant/jfZg/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
	
</head>
<body>
     <div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/merchant/jfZg/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/merchant/jfZg/import/template">下载模板</a>
		</form>
	</div>
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
			<li><label>网元属性：</label>
				<form:select path="zgjf.jfwz" class="input-xlarge"  cssStyle="width:150px;">
					<form:option value="" label="请选择"/>
					<form:option value="B类机房" label="B类机房"/>
						<form:option value="C类机房" label="C类机房"/>
						<form:option value="D类机房" label="D类机房"/>
						<form:option value="自留基站" label="自留基站"/>
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
			<li><label>巡检人：</label>
				<form:input path="kzzd4" htmlEscape="false" maxlength="55" class="input-medium"/>
			</li>
			</ul>
			<ul class="ul-form">
			<li><label>日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfZg.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfZg.overDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>

			</li>
			<li><label>隐患级别：</label>
				<form:select path="isCutOverContent" class="input-xlarge"  cssStyle="width:80px;">
					<form:option value="" label="请选择"/>
					<form:option value="特级" label="特级"/>
					<form:option value="紧急" label="紧急"/>
					<form:option value="重要" label="重要"/>
					<form:option value="一般 " label="一般"/>
				</form:select> 
			</li>
			<li><label>整治批次：</label>
				<form:select path="opticalCableContent" class="input-xlarge"  cssStyle="width:80px;">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="1"/>
					<form:option value="2" label="2"/>
					<form:option value="3" label="3"/>
					<form:option value="4 " label="4"/>
				</form:select> 
			</li>
			<li><label>是否存在安全隐患：</label>
				<form:select path="isSafetyHazard" class="input-xlarge"  cssStyle="width:80px;height: 50px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			<li><label>是否有ODF架/柜：</label>
				<form:select path="isODF" class="input-xlarge"  cssStyle="width:80px;height: 50px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			</ul>
			<ul class="ul-form">
			<li><label>是否需要网络设备整治割接：</label>
				<form:select path="isCutOver" class="input-xlarge"  cssStyle="width:80px;height: 90px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			
			
			<li><label>是否需要环境整治：</label>
				<form:select path="needRemediation" class="input-xlarge"  cssStyle="width:80px;height: 50px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			<li><label>是否需要光缆割接：</label>
				<form:select path="isOpticalCable" class="input-xlarge"  cssStyle="width:80px;height: 50px;">
					<form:option value="" label="请选择"/>
					<form:option value="是" label="是"/>
					<form:option value="否" label="否"/>
				</form:select> 
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page()"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			<!-- <input id="btnImport" class="btn btn-primary" type="button" value="导入"/> --> </li>
			<li style="color:red" class="clearfix">满足条件的记录数：${count}条</li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed container">
		<thead>
			<tr>
				<th>整改网元</th>
				<th>网元属性</th>
				<th>巡检人员</th>
				<th style="color: red;">是否已整改</th>
				<th>整改单号</th>
				<th>整改日期</th>
				<th>整改时限</th>
				<th>整改要求</th>
				<th>整改现场照片</th>
				<th>是否存在安全隐患</th>
				<th>隐患简要说明</th>
				<th>隐患原因</th>
				<th>是否有ODF架/柜</th>
				<th>是否需要网络设备整治割接</th>
				<th>隐患级别</th>
				<th>是否需要光缆割接</th>
				<th>光缆割接量（条/芯）</th>
				<th>整治批次</th>
				<th>是否需要环境整治</th>
				<th>环境整治内容描述（门、窗、墙面等）</th>
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
						${jfZg.zgjf.jfwz}
					</td>
					<td>${jfZg.kzzd4}</td>
					<td style="color: red;">${jfZg.kzzd2}</td>
				<td>
					${jfZg.zgdh}
				</td>
				<td>
					<fmt:formatDate value="${jfZg.zgrq}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${jfZg.zgsxrq}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${jfZg.zgyq}
				</td>
				<td>
					<c:if test="${not empty jfZg.xctps}">
						<c:forEach items="${jfZg.xctps}" var="jfimg">
							<img  src="/jfxt/userfiles/1/images/photo/2019/09/ljz.gif" alt="1" style="width:20px; height:20px"  data-src="${jfimg}" onclick="showImg(this.src)">
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
         <script>
            // 一开始没有滚动的时候，出现在视窗中的图片也会加载
            start();
            // 当页面开始滚动的时候，遍历图片，如果图片出现在视窗中，就加载图片
            var clock; //函数节流
            $(window).on('scroll',function(){
                if(clock){
                    clearTimeout(clock);
                }
                clock = setTimeout(function(){
                    start()
                },200)
            })
            function start(){
                 $('.container img').not('[data-isLoading]').each(function () {
                    if (isShow($(this))) {
                        loadImg($(this));
                    }
                })
            }
            // 判断图片是否出现在视窗的函数
            function isShow($node){
                return $node.offset().top <= $(window).height()+$(window).scrollTop();
            }

            // 加载图片的函数，就是把自定义属性data-src 存储的真正的图片地址，赋值给src
            function loadImg($img){
                    $img.attr('src', $img.attr('data-src'));

                    // 已经加载的图片，我给它设置一个属性，值为1，作为标识
                    // 弄这个的初衷是因为，每次滚动的时候，所有的图片都会遍历一遍，这样有点浪费，所以做个标识，滚动的时候只遍历哪些还没有加载的图片
                    $img.attr('data-isLoading',1);
            }
        </script>
</body>
</html>