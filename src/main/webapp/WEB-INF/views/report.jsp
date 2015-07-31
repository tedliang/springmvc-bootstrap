<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<body>

  <div class="tabbable" id="demo-tab"> <!-- Only required for left/right tabs -->
    <!-- Tab Definitions-->               
    <ul class="nav nav-tabs">
      <li class="active"><a href="#birtreport" data-toggle="tab">Birt Report</a></li>
      <li><a href="<c:url value="/birtreport/parameterDefs" />">Parameter Defs</a></li>
    </ul>

    <!-- Tab Content-->
    <div class="tab-content">                  
      <div class="tab-pane active" id="birtreport">
        <h2>Birt Report</h2>
        <p>
          See the <code>org.springframework.samples.mvc.birtreport</code> package for the @Controller code
        </p>
        <ul>
          <li>
            <a href="<c:url value="/birtreport.html" />">HTML</a>
          </li>
          <li>
            <a href="<c:url value="/birtreport.pdf" />">PDF</a>
          </li>
          <li>
            <a href="<c:url value="/birtreport.xlsx" />">EXCEL</a>
          </li>
        </ul>
      </div>
    </div> <!--tab-content-->
  </div> <!--tabbable-->   

</body>
</html>