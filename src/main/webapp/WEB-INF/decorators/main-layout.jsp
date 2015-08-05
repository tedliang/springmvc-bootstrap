<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Spring-MVC-Showcase</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <jwr:style src="/css/common.css"/>
  <sitemesh:write property='head'/>
  <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

</head>

<body>
    <div id="wrap">

        <c:import url="/WEB-INF/views/tags/navbar.jsp"/>

        <div class="container">
            <sitemesh:write property='body'/>
        </div><!--/container-->

        <hr class="soften">
    </div>

    <c:import url="/WEB-INF/views/tags/footer.jsp"/>

    <jwr:script src="/js/common.js" />
    <sitemesh:write property='javascript'/>
</body>
</html>
