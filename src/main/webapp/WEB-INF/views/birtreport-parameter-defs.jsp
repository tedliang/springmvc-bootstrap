<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table">
  <thead>
    <tr>
      <th>Name</th>
      <th>Display Name</th>
      <th>Type</th>
      <th>Help Text</th>
      <th>Prompt Text</th>
      <th>Data Type</th>
      <th>Control Type</th>
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
          <td>${def.dataType}</td>
          <td>${def.controlType}</td>
        </tr>
      </c:if>
    </c:forEach>
  </tbody>
</table>