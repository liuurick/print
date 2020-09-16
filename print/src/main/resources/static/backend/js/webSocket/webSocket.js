/**
 * Created by wangqian on 2019/4/17.
 */

var webSocket = null;

var userNo=document.getElementById('userno').innerHTML;

//判断当前浏览器是否支持WebSocket

if ('WebSocket' in window) {
    webSocket = new WebSocket("ws://localhost:9998/webSocket/" + userNo);
}
else {
    alert('当前浏览器 Not support webSocket')
}

//连接发生错误的回调方法
webSocket.onerror = function () {
    setMessageInnerHTML("WebSocket连接发生错误");
};

//连接成功建立的回调方法
webSocket.onopen = function () {
    setMessageInnerHTML("WebSocket连接成功");
}

//接收到消息的回调方法
webSocket.onmessage = function (event) {
    setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
webSocket.onclose = function () {
    setMessageInnerHTML("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭webSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//将消息显示在网页上
function setMessageInnerHTML(sendMessage) {
    document.getElementById('message').innerHTML += sendMessage + '<br/>';
}

//关闭WebSocket连接
function closeWebSocket() {
    webSocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;//要发送的消息内容
    var now=getNowFormatDate();//获取当前时间
    document.getElementById('message').innerHTML += (now+"发送人："+userno+'<br/>'+"---"+message) + '<br/>';
    document.getElementById('message').style.color="red";
    var ToSendUserno=document.getElementById('usernoto').value; //接收人编号：4567
    message=message+"|"+ToSendUserno//将要发送的信息和内容拼起来，以便于服务端知道消息要发给谁
    webSocket.send(message);
}
//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}