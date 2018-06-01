<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="/WEB-INF/tlds/sitemesh-decorator.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %> --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title><sitemesh:title /> - 基建项目管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- basic styles -->

<link rel="stylesheet" href="${ctx}/static/assets/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/static/assets/css/font-awesome.css" />
<!-- page specific plugin styles -->
<!-- text fonts -->
<link rel="stylesheet" href="${ctx}/static/assets/css/ace-fonts.css" />

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

<!--[if lte IE 8]>
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="${ctx}/static/assets/js/ace-extra.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
	<script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->
<link href='${ctx}/static/uploadify/uploadify.css' rel='stylesheet'>
<sitemesh:head />

<!-- basic scripts -->
<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctx}/static/assets/js/jquery.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctx}/static/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/static/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="${ctx}/static/assets/js/bootstrap.js"></script>

<!-- page specific plugin scripts -->
<script src="${ctx}/static/assets/js/jquery.dataTables.js"></script>
<script src="${ctx}/static/assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->
<script src="${ctx}/static/assets/js/ace/elements.scroller.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.colorpicker.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.fileinput.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.typeahead.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.wysiwyg.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.spinner.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.treeview.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.wizard.js"></script>
<script src="${ctx}/static/assets/js/ace/elements.aside.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.ajax-content.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.touch-drag.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.sidebar.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.submenu-hover.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.widget-box.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.settings.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.settings-rtl.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.settings-skin.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.widget-on-reload.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.searchbox-autocomplete.js"></script>
<!-- inline scripts related to this page -->
		

<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		

<script type="text/javascript"> ace.vars['base'] = '..'; </script>
<script src="${ctx}/static/assets/js/ace/elements.onpage-help.js"></script>
<script src="${ctx}/static/assets/js/ace/ace.onpage-help.js"></script>
<script src="${ctx}/static/docs/assets/js/rainbow.js"></script>
<script src="${ctx}/static/docs/assets/js/language/generic.js"></script>
<script src="${ctx}/static/docs/assets/js/language/html.js"></script>
<script src="${ctx}/static/docs/assets/js/language/css.js"></script>
<script src="${ctx}/static/docs/assets/js/language/javascript.js"></script>

<script type="text/javascript">
	//将form转为AJAX提交
	function ajaxSubmit(frm, fn) {
		var dataPara = getFormJson(frm);
		$.ajax({
			url : frm.action,
			type : frm.method,
			data : dataPara,
			success : fn
		});
	}

	//将form中的值转换为键值对。
	function getFormJson(frm) {
		var o = {};
		var a = $(frm).serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});

		return o;
	}
</script>
</head>
<body>
	<sitemesh:body />
	
	<script type="text/javascript">
		$(function() {
			var colorbox_params = {
				reposition : true,
				scalePhotos : true,
				scrolling : false,
				//previous : '<i class="icon-arrow-left"></i>',
				//next : '<i class="icon-arrow-right"></i>',
				close : '&times;',
				//current : '{current} of {total}',
				maxWidth : '100%',
				maxHeight : '100%',
				onOpen : function() {
					document.body.style.overflow = 'hidden';
				},
				onClosed : function() {
					document.body.style.overflow = 'auto';
				},
				onComplete : function() {
					$.colorbox.resize();
				}
			};
			$('[data-rel="colorbox"]').colorbox(colorbox_params);
		});
	</script>
</body>
</html>