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
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出巡检过程数据吗？（仅导出当前页数据，如需导出全部请在页面最下方(“30”数字处)输入要导出的条数按回车即可）","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/merchant/jfXjgc/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			/* $("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			}); */
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/merchant/jfXjgc/list");
			$("#searchForm").submit();
	    	return false;
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
			<li>&nbsp;&nbsp;&nbsp;巡检网元：
				<form:select path="xjjf" class="input-xlarge"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			 <li>&nbsp;&nbsp;&nbsp;网元属性：
				<form:select path="xjjf.jfwz" class="input-xlarge"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
						<form:option value="B类机房" label="B类机房"/>
						<form:option value="C类机房" label="C类机房"/>
						<form:option value="D类机房" label="D类机房"/>
						<form:option value="自留基站" label="自留基站"/>
						<form:option value="线路" label="线路"/>
				</form:select>
			</li>  
			<li>&nbsp;&nbsp;&nbsp;巡检是否通过：
				<form:select path="xjsftg" class="input-xlarge"  cssStyle="width:176px;">
					<form:option value="" label="请选择"/>
						<form:option value="1" label="是"/>
						<form:option value="0" label="否"/>
				</form:select>
			</li>
			<li>
			<li>&nbsp;&nbsp;&nbsp;巡检时间：
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfXjgc.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfXjgc.overDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page()"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed container">
		<thead>
			<tr>
				<th>巡检网元</th>
				<th>网元属性</th>
				<th>巡检时间</th>
				<th>巡检人员</th>
				<th>现场图片</th>
				<th>整改判断</th>
				<th style="color: red;">巡检是否通过</th>
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
					${jfXjgc.xjjf.jfwz}
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
							<img src="/jfxt/userfiles/1/images/photo/2019/09/ljz.gif" alt="1" style="width:20px; height:20px" data-src="${jfimg}" onclick="showImg(this.src)">
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfXjgc.xctps}">
						暂未上传图片
					</c:if>
				</td>
				<td>
					${jfXjgc.zgpd}
				</td>
				<td style="color: red;">
					${fns:getDictLabel(jfXjgc.xjsftg, 'yes_no', '')}
				</td>
				
				
				<td>
				<c:if test="${jfXjgc.xjdf=='10'}">
				 ★★★★★★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='9'}">
				 ★★★★★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='8'}">
				 ★★★★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='7'}">
				 ★★★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='6'}">
				 ★★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='5'}">
				★★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='4'}">
				 ★★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='3'}">
				 ★★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='2'}">
				 ★★
				</c:if>
				<c:if test="${jfXjgc.xjdf=='1'}">
				 ★
				</c:if>
				
				</td>
				
				
				<shiro:hasPermission name="merchant:jfXjgc:edit"><td>
    				<a href="${ctx}/merchant/jfXjgc/form?id=${jfXjgc.id}">修改</a>
					<a href="${ctx}/merchant/jfXjgc/delete?id=${jfXjgc.id}" onclick="return confirmx('确认要删除该巡检过程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="img_content" id="imgContent">
	<div style="margin-left: 10px;margin-top: 10px;"><img src="" style="width:400px; height:500px" id="imgCon"></div>
	<div  class="colse_btn" onclick="col()">关闭</div>
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