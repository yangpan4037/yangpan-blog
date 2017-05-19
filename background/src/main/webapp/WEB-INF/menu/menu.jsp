<%-- 头部 --%>
<%@include file="/WEB-INF/include/header.jsp"%>

<%-- 菜单 --%>
<%@include file="/WEB-INF/include/sidebar.jsp"%>

<%-- 主要内容 --%>
<!-- start: Content -->
<div class="main sidebar-minified">

	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header"> <i class="fa fa-table"></i>
				Tables
			</h3>
			<ol class="breadcrumb">
				<li> <i class="fa fa-home"></i>
					<a href="index.html">Home</a>
				</li>
				<li>
					<i class="fa fa-table"></i>
					Tables
				</li>
			</ol>
		</div>
	</div>

	<%-- 工具栏 --%>
	<div id="toolbar">
		<div class="btn-group">
			<button type="button" class="btn btn-danger btn-sm">Left</button>
			<button type="button" class="btn btn-primary btn-sm">Middle</button>
			<button type="button" class="btn btn-success btn-sm">Right</button>
		</div>
	</div>

	<%-- 列表 --%>
	<table class="table table-striped table-bordered" data-toolbar="#toolbar" data-search="true" data-show-header="true" data-toggle="table" data-url="${contextPath}/menu_list">
		<thead>
			<tr>
				<th data-field="id">ID</th>
				<th data-field="name">NAME</th>
				<th data-field="url">url</th>
				<th data-field="icon">icon</th>
				<th data-field="parent.name">parent</th>
			</tr>
		</thead>
	</table>

</div>
<!-- end: Content -->

<%-- 尾部 --%>
<%@include file="/WEB-INF/include/footer.jsp"%>

<!-- inline scripts related to this page -->
<script src="${contextPath}/resources/assets/js/pages/table.js"></script>
<!-- end: JavaScript-->