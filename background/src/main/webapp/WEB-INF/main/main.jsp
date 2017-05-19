<%-- 头部 --%>
<%@include file="/WEB-INF/include/header.jsp"%>

<%-- 菜单 --%>
<%@include file="/WEB-INF/include/sidebar.jsp"%>

<%-- 主要内容 --%>
<%@include file="/WEB-INF/main/content.jsp"%>

<%-- 主要内容iframe方式 --%>
<%--
	<iframe src="${contextPath}/main_content" id="rightIframe" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" name="main" onload="autoHeight();"></iframe>
	<script type="text/javascript">
		function autoHeight() {
			var iframe = document.getElementById("rightIframe");
			if (iframe.Document){//ie自有属性
				iframe.style.height = iframe.Document.documentElement.scrollHeight;
				iframe.style.width = iframe.Document.documentElement.scrollWidth;
			} else if (iframe.contentDocument) {//ie,firefox,chrome,opera,safari
				iframe.height = iframe.contentDocument.body.offsetHeight;
				iframe.width = screen.width- $("#sidebar").width() - 20;
			}
		}
	</script>
--%>

<%-- 尾部 --%>
<%@include file="/WEB-INF/include/footer.jsp"%>

<!-- inline scripts related to this page -->
<script src="${contextPath}/resources/assets/js/pages/index.js"></script>	
<!-- end: JavaScript-->