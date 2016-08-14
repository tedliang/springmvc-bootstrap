<html>
<body>
  <div class="row">
    <div class="col-md-12">
      <h2>WebSocket Calculator</h2>
      <p>
        See the <code>org.springframework.samples.mvc.websocket</code> package for the @Controller code
      </p>
      <div>
        <div>
          <button class="btn btn-default" id="connect" onclick="connect();">Connect</button>
          <button class="btn btn-default" id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button><br/><br/>
        </div>
        <div id="calculationDiv" style="visibility: hidden;">
          <label>Number One:</label> <input class="form-control" type="number" id="num1" /><br/>
          <label>Number Two:</label> <input class="form-control" type="number" id="num2" /><br/><br/>
          <button class="btn btn-primary" id="sendNum" onclick="sendNum();">Calculate</button>
          <p id="calResponse"></p>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript">
    var stompClient = null;
    function setConnected(connected) {
      document.getElementById('connect').disabled = connected;
      document.getElementById('disconnect').disabled = !connected;
      document.getElementById('calculationDiv').style.visibility = connected ? 'visible' : 'hidden';
      document.getElementById('calResponse').innerHTML = '';
    }
    function connect() {
      var socket = new SockJS(window.location.pathname);
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function(frame) {
        setConnected(true);
        stompClient.subscribe('/topic/showResult', function(calResult){
          document.getElementById('sendNum').disabled = false;
          showResult(JSON.parse(calResult.body).result);
        });
      });
    }
    function disconnect() {
      stompClient.disconnect();
      setConnected(false);
    }
    function sendNum() {
      var num1 = document.getElementById('num1').value;
      var num2 = document.getElementById('num2').value;
      document.getElementById('sendNum').disabled = true;
      document.getElementById('calResponse').innerHTML = '';
      stompClient.send("/calcApp/calculate", {}, JSON.stringify({ 'num1': num1, 'num2': num2 }));
    }
    function showResult(message) {
      var response = document.getElementById('calResponse');
      var p = document.createElement('p');
      p.style.wordWrap = 'break-word';
      p.appendChild(document.createTextNode(message));
      response.appendChild(p);
    }
  </script>
</body>
</html>