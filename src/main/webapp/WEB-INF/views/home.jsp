<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

  <body>

    <div class="row">
      <div class="col-md-2">
          <div class="well sidebar-nav">
              <c:import url="/WEB-INF/views/tags/menu.jsp"/>
          </div>
      </div>

      <div class="col-md-10">
          <div class="jumbotron">
              <c:import url="/WEB-INF/views/tags/banner.jsp"/>
          </div>

          <div class="row">
              <div class="col-md-12">
                  <div class="container">
                      <iframe class="github-btn" src="http://ghbtns.com/github-btn.html?user=priyatam&repo=springmvc-twitterbootstrap-showcase&type=watch&count=true" allowtransparency="true" frameborder="0" scrolling="0" width="100px" height="20px"></iframe>

                      <a href="https://twitter.com/share" class="twitter-share-button" data-url="http://springmvc-twitterbootstrap-showcase.cloudfoundry.com" data-text="Spring MVC Twitter Bootstrap Showcase!">Tweet</a>
                      <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
                  </div>

                  <hr class="soften">

                  <div class="row">
                      <div class="col-md-4">
                          <h3>
                              Click and Learn</br>
                          </h3>
                          <ul>
                              <li> Simple Ajax @Controller </li>
                              <li> Mapping Requests </li>
                              <li> Obtaining Request Data </li>
                              <li> Generating Responses </li>
                              <li> Rendering Views </li>
                              <li> Forms </li>
                              <li> File Upload </li>
                              <li> Validation </li>
                              <li> Exception Handling </li>
                              <li> Message Converters </li>
                              <li> Type Conversion </li>
                              <li> Layout Decoration (Sitemesh) </li>
                          </ul>
                                  </h3>
                      </div>
                      <div class="col-md-8">
                          <h3>
                              Next Steps? Read the code.</br>
                          </h3>

                          <ol>
                              <li>Clone: <br/> <code> git clone git://github.com/priyatam/springmvc-bootstrap-showcase.git </code> </li>
                              <li>Build: <br/> <code> $ mvn clean install </code> </li>
                              <li>Run: <br/>  <code> $ mvn jetty:run </code></li>
                              <li>See: <br/>  <code>http://localhost:8080/spring-mvc-showcase/ </code> </li>
                          </ol>
                          </h3>
                      </div>
                  </div>

              </div>
          </div><!--/col-->
      </div><!--/row-->
    </div>
  </body>
</html>