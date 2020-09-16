/**
 * Created by wangqian on 2019/4/9.
 */
var vue = new Vue({
    el: "#order",
    data: {
        orderNo: null,
        qrUrl: "http://image.qian.com/loading.gif",
        getQr: true,
        payMethods: true,
        paymentTimer: null,
        paySuccess: true,
    },
    methods: {
        pay: function () {
            this.payMethods = false;
            this.getQr = false;
            console.log(" 请求支付... ")
            this.$http.get('/pay/pay.do', {params: {orderNo: this.orderNo }}).then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.orderNo = res.data.data.orderNo;
                this.qrUrl = res.data.data.qrUrl;
                this.listenOrderStatus();
            }else {
                util.errorTips( res.msg )
            }
        },
        errorCallback: function ( res ) {
            util.errorTips( "请求支付发生错误！" )
        },
        listenOrderStatus: function () {

            var _this = this;

            this.paymentTimer = window.setInterval(function(){
                _this.$http.get('/pay/query_order_pay_status.do', {params: {orderNo: _this.orderNo }}).then(function ( res ) {
                    console.log( res );
                    res = res.body;
                    console.log( res );
                    if( res.status == 0 ){
                        if ( res.data == true ){
                            window.clearInterval(_this.paymentTimer);
                            _this.paySuccess = false;
                        }else {
                            console.log( "轮询中，等待支付....");
                        }
                    }else {
                        util.errorTips( res.msg )
                    }
                }, function () {
                    util.errorTips( "查询支付状态失败！" )
                });
            },5000);

        },
        handlePayClick: function () {
            this.pay();
        },
        handleGoIndex: function () {
            window.location.href = '/index';
        },
        handleGoBackClick: function () {
            window.location.href = "/order/info";
        },
        handleGoShareClick: function () {
            location.href = "/share/create";
        },
        closeOrder: function () {
            this.$http.get('/order/close', {params: { orderNo: this.orderNo }}).then(function ( res ) {
                res = res.body;
                if( res.status == 0 ){
                    alert("更改订单状态成功！");
                }else {
                    util.errorTips( res.msg )
                }

            }, function () {
                util.errorTips(" 关闭订单失败！ ");
            });
        },
        handleCloseOrderClick: function () {
            this.closeOrder();
        },
        handleGetKeyClick: function () {
            this.$http.get('/order/get_key.do', {params: {orderNo: this.orderNo }}).then(function ( res ) {

                res = res.body;
                if( res.data && res.status == 0){

                    console.log(res.data)

                    var getKey = res.data.getKey;
                    var shopName = res.data.shopName;
                    var shopOwnerPhone = res.data.shopOwnerPhone;
                    var shopAddress = res.data.shopAddress;
                    var closeTime = res.data.closeTime;

                    alert (shopName + "已完成文件打印，取货码为：" + getKey + "，请在 " + closeTime + " 之前到 " + shopAddress + " 进行取货，如果有任何疑问请联系店主："+ shopOwnerPhone + "");

                }else {
                    util.errorTips( res.msg )
                }

            }, function () {
                util.errorTips(" 获取取货码失败！ ");
            });
        },
        handleRefundClick: function () {

        }
    },
    mounted: function () {
        this.orderNo = this.$refs.orderNoRef.value.trim();
    }
});