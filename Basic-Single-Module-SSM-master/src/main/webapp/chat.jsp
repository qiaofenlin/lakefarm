<%@ page import="com.lakefarm.socket.URLGetPostMethod" %>
<%@ page import="org.jsoup.nodes.Document" %>
<%@ page import="org.jsoup.Jsoup" %>
<%@ page import="org.jsoup.select.Elements" %>
<%@ page import="org.jsoup.Connection" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.lakefarm.pojo.User" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.lakefarm.service.UserService" %>
<%@ page import="org.springframework.stereotype.Service" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
body {
	padding: 20px;
}

#message {
	height: 300px;
	border: 1px solid;
	overflow: auto;
}

.details {
	width: 300px;
	height: 400px;
	border: 1px solid;
	margin-bottom: 10px;
	float: left;
}

#canvas {
	float: left;
	height: 400px;
	width: 400px;
	border: 1px solid;
	margin-bottom: 10px;
	margin-left: 10px;
	background-color: white;
}
#webRtc {
	float: left;
	width: 400px;
	height: 400px;
	border: 1px solid;
	margin-left: 10px;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebIM</title>
<script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
<%

//	URLGetPostMethod urlGetPostMethod=new URLGetPostMethod();
//	String name = urlGetPostMethod.sendPost("/lakeFarm/GetUserInfo.from","");
	String name="";
    String u_id="";
	try {
		Cookie[] cookies = request.getCookies();
		User user=new User();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().trim().equals("u_id")) {
					u_id=c.getValue().trim();//对u_id进行赋值
				}
			}
			if ("".equals(user.getU_id())) {

			} else {

				//建立映射
		Connection.Response res = Jsoup.connect("http://localhost:8080/lakeFarm/GetUserInfo.do")
				.header("Accept", "*/*")
				.header("Accept-Encoding", "gzip, deflate")
				.header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
				.timeout(10000)
				.cookie("u_id",u_id.trim())
				.execute();//.post();
		String body = res.toString();
		System.out.println(body);
		}
		}

		}catch (Exception e){
		System.out.println(e);
		}


//	JSONObject json = JSONObject.fromObject(body);

	session.setAttribute("user", name);
	System.out.println(name);
%>

</head>
<body onload="startWebSocket();" oncontextmenu="return false;"
	onselectstart="return false;">
	<div class='details'>
		<h1>WebIM</h1>
		登录状态： <span id="denglu" style="color: red;">正在登录</span> <br> 昵称：
		<span id="userName"></span> <br> <br> To： <select
			id='userlist'>
		</select> <span style="color: red;">*</span>请选择聊天对象 <br> 发送内容： <input
			type="text" id="writeMsg" value="嗨~" /> <br> 聊天框：<br>
			<button id="run">开启网络摄像头</button><Br/>
	</div>

	<div id='canvas'>
		<canvas id="c1" width="400" height="400">  
		<span>不支持canvas浏览器</span>  
		</canvas>
	</div>

	<div id = "webRtc">
		<video id="webcam" width=100% height=100%></video>
	</div>

	<div style="clear: both;"></div>

	
	<div id="message"></div>
	<br>
	<input type="button" value="send" onclick="sendMsg()" />
	<br>


	<script type="text/javascript">
	/** 判断浏览器是否支持视频聊天 **/
	navigator.getUserMedia || (navigator.getUserMedia = navigator.mozGetUserMedia || navigator.webkitGetUserMedia || navigator.msGetUserMedia);
	if (!navigator.getUserMedia) {
	    alert('not support');
	}
	
	var startCamBtn = $('#run');
	startCamBtn.click(startWebCam);
	
	function startWebCam(e){
		navigator.getUserMedia({
			video:true,
			audio:true
		},onSuccess,onError);
		
		function onSuccess(stream){
			var video = document.getElementById('webcam');
			if(window.URL){
				console.log(stream);
				video.src = window.URL.createObjectURL(stream);
			}else{
				video.src = stream;
			}
			
			video.autoplay = true;
		}
		
		function onError(){
			alert('have onKnow Error!');
		}
	}
	
	
	
	var self = "<%=name%>";
	var ws = null;
	
	var oC = document.getElementById('c1');
	var oGC = oC.getContext('2d');

	function startWebSocket() {
		if ('WebSocket' in window)
			ws = new WebSocket("ws://localhost:8080/websocket");
		else if ('MozWebSocket' in window)
			ws = new MozWebSocket("ws://localhost:8080/websocket");
		else
			alert("not support");

		ws.onmessage = function(evt) {
			var data = evt.data;
			var o = eval('(' + data + ')');//将字符串转换成JSON
			console.log(o);
			if (o.type == 'message') {
				setMessageInnerHTML(o.data);
			} else if (o.type == 'user') {
				var userArry = o.data.split(',');
				$("#userlist").empty();
				$("#userlist").append("<option value ='all'>所有人</option>");
				$.each(userArry, function(n, value) {
					if (value != self && value != 'admin') {
						$("#userlist").append(
								'<option value = '+value+'>' + value
										+ '</option>');
					}
				});
			}else if(o.type == 'coord'){
				
				var coordArry = o.data.split("_");
				var x = coordArry[0];
				var y = coordArry[1];
				oGC.lineWidth = 1;	
				oGC.moveTo(x-1,y-1);
				oGC.lineTo(x,y);
				oGC.stroke();
			}
		};
		ws.onclose = function(evt) {
			$('#denglu').html("离线");
		};

		ws.onopen = function(evt) {
			$('#denglu').html("在线");
			$('#userName').html(self);
		};
	}

	function setMessageInnerHTML(innerHTML) {
		var temp = $('#message').html();
		temp += innerHTML + '<br/>';
		$('#message').html(temp);
	}

	function sendMsg() {
		var fromName = self;
		var toName = $("#userlist").val(); //发给谁
		var content = $("#writeMsg").val(); //发送内容
		var type = 'message';
		var msg = fromName + "," + toName + "," + content + "," + type;
		ws.send(msg);
	}

	initCanvas();
	
	function drawPanel(x,y){
		
	}
	
	function initCanvas() {
		var oC = document.getElementById('c1');
		var oGC = oC.getContext('2d');
		oC.onmousedown = function(ev) {
			var ev = ev || window.event;
			oGC.moveTo(ev.clientX - oC.offsetLeft, ev.clientY - oC.offsetTop);
			document.onmousemove = function(ev) {
				 var ev = ev || window.event;  
				 	var x = ev.clientX-oC.offsetLeft;
		            var y = ev.clientY-oC.offsetTop;
		            oGC.lineTo(x,y);
		            
		            var fromName = self;
		    		var toName = $("#userlist").val(); //发给谁
		    		var type = "coord";
		    		var content = x + '_' + y;
		    		var msg = fromName + "," + toName + "," + content + "," + type;
		    		ws.send(msg);
		    		oGC.stroke();
			};
			document.onmouseup = function() {
				document.onmousemove = null;
				document.onmouseup = null;
			};
		};
	}
</script>

</body>
</html>