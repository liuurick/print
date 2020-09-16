/**
 * Created by wangqian on 2019/4/9.
 */
var vue = new Vue({
    el: "#pay",
    data: {
        orderNo: null,
        qrUrl: util.getImgHost() + "loading.gif",
        getQr: true,
        paymentTimer: null,
        paySuccess: true
    },
    methods: {
        pay: function () {
            this.getQr = false;
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
                    res = res.body;
                    if( res.status == 0 ){
                        if ( res.data == true ){
                            console.log("closeTimer");
                            window.clearInterval(_this.paymentTimer);
                            console.log("看一下还有没有 timer ，如果没有就可以展示付款成功啦！");
                            _this.paySuccess = false;
                        }else {
                            console.log( "轮询中，等待支付....");
                        }
                    }else {
                        util.errorTips( res.msg )
                    }
                }, function () {
                    util.errorTips( "查询支付状态失败！" );
                    window.clearInterval(_this.paymentTimer);
                });
            },5000);

        },
        handlePayClick: function () {
            this.pay();
        },
        handleGoIndex: function () {
            window.location.href = '/index';
        },
        handleGoShareClick: function () {
            location.href = "/share/create";
        }
    },
    mounted: function () {
        this.orderNo = this.$refs.orderNoRef.value.trim();
        console.log(this.orderNo)
    }
})