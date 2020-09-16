/**
 * Created by wangqian on 2019/4/20.
 */

var vue = new Vue({
    el: "#order",
    data: {
        getKeyItem: false,
        refuseItem: false,
        getKey: "",
        orderNo: "",
        reason: ""
    },
    methods: {
        handleGetFileCLick: function () {
            /* 点击通知取货 */
            this.noticeGetFile();
        },
        handleGetKeyItemClose: function () {
            this.getKeyItem = false
        },
        handleAcceptOrder: function () {

            this.getKeyItem = true;

        },
        handleCloseOrderClick: function () {

            this.$http.post('/store/order/close',{
                orderNo: this.orderNo
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){

                    location.href = "/store/list";

                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "关闭订单失败！" );
            })

        },
        handleRefuseOrder: function () {

            this.refuseItem = true

        },
        handleRefuseItemClose: function () {
            this.refuseItem = false
        },
        handleRefuseUserCLick: function () {

            var length = this.$refs.radios.querySelectorAll(".radioItem").length;

            for (var i = 0; i < length ; i++){

                if ( this.$refs.radios.querySelectorAll(".radioItem")[i].checked ){

                    var reason = this.$refs.radios.querySelectorAll(".radioItem")[i].value;

                    var fileName = this.$refs.fileName.value;

                    this.refuseNotice(fileName, reason);
                }

            }

            //util.errorTips("请选择原因后再进行提交");

        },
        noticeGetFile: function () {
            this.$http.get("/store/order/notice.do", {params: {getKey: this.getKey, orderNo: this.orderNo}}).then(function ( res ) {
                res = res.body;
                if( res.status == 0){

                    alert(" 通知用户成功！ ");

                    location.href = "/store/order/detail/" + this.orderNo;

                }else {
                    util.errorTips( res.msg );
                    if ( (res.msg) == "NEED_LOGIN") {
                        location.href = "/store/login"
                    }
                }
            }, function () {
                util.errorTips(" 请求通知用户取货码失败，请稍后再试！ ");
            });
        },
        refuseNotice: function (fileName, reason) {
            this.$http.get("/store/order/refuse.do", {params: {fileName: fileName,reason: reason, orderNo: this.orderNo}}).then(function ( res ) {
                res = res.body;
                if( res.status == 0){

                    alert(" 通知用户成功！ ");

                    location.href = "/store/order/detail/" + this.orderNo;

                }else {
                    util.errorTips( res.msg );
                    if ( (res.msg) == "NEED_LOGIN") {
                        location.href = "/store/login"
                    }
                }
            }, function () {
                util.errorTips(" 通知用户失败，请稍后再试！ ");
            });
        }
    },
    mounted: function(){
        this.orderNo = this.$refs.orderNo.value;
    }
});