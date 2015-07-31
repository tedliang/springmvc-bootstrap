<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<body>

  <div class="tabbable" id="demo-tab"> <!-- Only required for left/right tabs -->
    <!-- Tab Definitions-->
    <ul class="nav nav-tabs">
      <li><a href="<c:url value="/report" />">Birt Report</a></li>
      <li class="active"><a href="#parameterDefs" data-toggle="tab">Parameter Defs</a></li>
    </ul>

    <!-- Tab Content-->
    <div class="tab-content">                  
      <div class="tab-pane active" id="parameterDefs">
        <h2>Birt Report - Parameter Defs</h2>
        <p>
          See the <code>org.springframework.samples.mvc.birtreport</code> package for the @Controller code
        </p>
        <table class="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Display Name</th>
              <th>Type</th>
              <th>Help Text</th>
              <th>Prompt Text</th>
              <th>ControlType</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${paramDefs}" var="def">
              <c:if test="${def.parameterType == 0}">
                <tr>
                  <td>${def.name}</td>
                  <td>${def.displayName}</td>
                  <td>${def.scalarParameterType}</td>
                  <td>${def.helpText}</td>
                  <td>${def.promptText}</td>
                  <td>${def.controlType}</td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div> <!--tab-content-->
  </div> <!--tabbable-->   

</body>
</html>