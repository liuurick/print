/**
 * Created by wangqian on 2019/4/16.
 */
var vue = new Vue({
    el: "#navbar",
    data: {
        shopId: null,
        shopName: null,
        status: null,
        modelShow: false,
        orderNo: null,
        business: true
    },
    methods: {
        getShopInfo: function () {
            this.$http.get('/store/shop/get_shop_info.do').then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){

                this.shopName = res.data.shopName;
                this.shopId = res.data.id;
                this.status = res.data.status;

                if ( this.status == 0 ){
                    this.business = true
                }else {
                    this.business = false
                }

                this.webSocket();

            }else {
                util.errorTips( res.msg )
                if ( res.msg == "NEED_LOGIN") {
                    location.href = "/store/login"
                }
            }
        },
        errorCallback: function ( ) {
            util.errorTips( "请求店铺信息发生错误！" )
        },
        webSocket: function () {

            var webSocket = null;

            var _this = this;

            var shopKey = this.shopId;

            console.log(" this.shopId: " +  shopKey)

            /* 判断当前浏览器是否支持 WebSocket */

            if ( 'WebSocket' in window ) {
                webSocket = new WebSocket("ws://"+ util.getHost() +"/webSocket/" + shopKey);
            }
            else {
                alert(' Not support webSocket ')
            }

            /* 连接发生错误的回调方法 */
            webSocket.onerror = function () {
                console.log(" WebSocket连接发生错误 ");
            };

            /* 连接成功建立的回调方法 */
            webSocket.onopen = function () {
                console.log(" WebSocket连接成功 ");
            };


            /* 接收到消息的回调方法 */
            webSocket.onmessage = function (event) {
                _this.alertShopOwner(event.data);
            };

            /* 连接关闭的回调方法 */
            webSocket.onclose = function () {
                console.log(" WebSocket连接关闭 ");
            };

            /* 监听窗口关闭事件，当窗口关闭时，主动去关闭webSocket连接，防止连接还没断开就关闭窗口，server端会抛异常 */
            window.onbeforeunload = function () {
                webSocket.close();
            };

        },
        alertShopOwner: function ( message ) {
            this.$refs.audio.play();
            this.modelShow = true;
            this.orderNo = message;
        },
        handleModelOrderClick: function () {
            location.href = "/store/order/detail/" + this.orderNo;
            /* 获取订单详情 */
        },
        handleModelCloseClick: function () {
            this.$refs.audio.pause();
            this.modelShow = false;
        },
        handleBusinessChange: function () {

            if ( this.status == 0 ){
                this.business = false;
            }

            if ( this.status == 1 ){
                this.business = true;
            }

            this.$http.post('/store/shop/change_status.do',  {emulateJSON:true}).then(function ( res ) {
                res = res.data;
                if( res.data && res.status == 0 ){

                    this.status = res.data.status

                    console.log(this.status)
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "更改营业状态出现错误" );
            })

        }
    },
    created: function () {
        this.getShopInfo();
    }
});