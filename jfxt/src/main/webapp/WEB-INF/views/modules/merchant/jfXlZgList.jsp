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
            $("#searchForm").attr("action","${ctx}/merchant/jfXlZg/list");
			$("#searchForm").submit();
        	return false;
        }

	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出线路整改单数据吗？（仅导出当前页数据，如需导出全部请在页面最下方(“30”数字处)输入要导出的条数按回车即可）","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/merchant/jfXlZg/export");
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
			$("#searchForm").attr("action","${ctx}/merchant/jfXlZg/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/merchant/jfXlZg/list">线路整改列表</a></li>
		<shiro:hasPermission name="merchant:jfXlZg:edit"><li><a href="${ctx}/merchant/jfXlZg/form">线路整改添加</a></li></shiro:hasPermission>
	</ul>
	
	<form:form id="searchForm" modelAttribute="jfXlZg" action="${ctx}/merchant/jfXlZg/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" >
			<li><label>整改线路：</label>
				<form:select path="zgjf" class="input-xlarge"  cssStyle="width:150px;">
					<form:option value="" label="请选择"/>
					<form:options items="${jfXxList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否已整改：</label>
				<form:select path="iszg" class="input-xlarge"  cssStyle="width:80px;">
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
					   value="<fmt:formatDate value="${jfXlZg.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				&nbsp;--
				<input name="overDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${jfXlZg.overDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>

			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page()"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li style="color:red" class="clearfix">满足条件的记录数：${count}条</li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed container">
		<thead>
			<tr>
				<th>整改线路</th>
				<th>巡检人员</th>
			    <th style="color: red;">是否已整改</th>
				<th>整改单号</th>
				<th>日期</th>
				<th>问题情况</th>
				<th>整改现场照片</th>
				<th>处理方式</th>
				<th>需新建杆路（km）</th>
				<th>更换吊线（km）</th>
				<th>更换电杆（根）</th>
				<th>扶正（偏杆/倒杆）（根）</th>
				<th>新建拉线（条）</th>
				<th>更换拉线（条）</th>
				<th>需更换光缆（km）</th>
				<th>预估费用（元）</th>
				<th>备注</th>
				<shiro:hasPermission name="merchant:jfXlZg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach items="${page.list}" var="jfXlZg">
			<tr>
				<td>${jfXlZg.zgjf.name}</td>
				<td>${jfXlZg.kzzd4}</td>
				<td style="color: red;">${jfXlZg.iszg}</td>
				<td>${jfXlZg.zgdh}</td>
				<td><fmt:formatDate value="${jfXlZg.zgrq}" pattern="yyyy-MM-dd"/></td>
				<td>${jfXlZg.zgwt}</td>
				<td>
					<c:if test="${not empty jfXlZg.xctps}">
						<c:forEach items="${jfXlZg.xctps}" var="jfimg">
							<img src="/jfxt/userfiles/1/images/photo/2019/09/ljz.gif" alt="1" style="width:20px; height:20px" data-src="${jfimg}" onclick="showImg(this.src)">
						</c:forEach>	
					</c:if>
					<c:if test="${empty jfXlZg.xctps}">
						暂未上传图片
					</c:if>
				</td>
				<td>${jfXlZg.zgfs}</td>
				<td>${jfXlZg.xjgl}</td>
				<td>${jfXlZg.ghdx}</td>
				<td>${jfXlZg.ghdg}</td>
				<td>${jfXlZg.dgfz}</td>
				<td>${jfXlZg.xjlx}</td>
				<td>${jfXlZg.ghlx}</td>
				<td>${jfXlZg.ghgl}</td>
				<td>${jfXlZg.ygfy}</td>
				<td>${jfXlZg.remarks}</td>

					<shiro:hasPermission name="merchant:jfXlZg:edit"><td>
    				<a href="${ctx}/merchant/jfXlZg/form?id=${jfXlZg.id}">修改</a>
					<a href="${ctx}/merchant/jfXlZg/delete?id=${jfXlZg.id}" onclick="return confirmx('确认要删除该整改吗？', this.href)">删除</a>
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
</div>
</body>
</html>