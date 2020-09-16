/**
 * Created by wangqian on 2019/4/10.
 */
var vue = new Vue({
    el: "#user",
    data: {
        overOrderSuccess: true,
        dealItem: true,
        accountItem: true,
        fileItem: true,
        userItem: true,
        orderDeal: true,
        item: "user", /* 获取后端用户接口的时候会把item进行赋值，如果是user就展示基本信息，file就展示文件信息 */
        answer: "",
        email: "",
        headerPic: "",
        integral: "0",
        phone: "",
        question: "",
        username: "",
        list: [],
        total: null,
        size: null,
        noOrder: true, /* 如果没有订单，控制显示 无订单 的提示图片 */
        noIntegral: true,/* 如果没有积分，控制显示 无交易 的提示图片 */
        noFile: true,
        showMore: true,
        modUsername: true,
        modEmail: true,
        modPhone: true,
        newUsername: "",
        newEmail: "",
        newPhone: "",
        oldPassword: "",
        newPassword: "",
        secondPassword: "",
        orderNo:null
    },
    methods: {
        getUserInfo: function () {
            this.$http.post('/user/get_user_info.do',{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.username = res.data.username;
                    this.question = res.data.question;
                    this.integral = res.data.integral;
                    this.headerPic = res.data.headerPic;
                    this.email = res.data.email;
                    this.answer = res.data.answer;
                    this.phone = res.data.phone;
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "获取用户信息失败" );
            })
        },
        getDealOrderInfo: function (pageNum, pageSize) {
            this.$http.get('/order/get_order_list.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.list = res.data.list;
                    this.total = res.data.total;
                    this.size = res.data.size;
                    if ( (res.data.total) == 0) {
                        this.noIntegral = true;
                        this.noOrder = false;
                    }
                    console.log( res.data )
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "获取订单信息失败" );
            })
        },
        getFileInfo: function (pageNum, pageSize) {
            this.$http.get('/file/get_file_list.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.list = res.data.list;
                    this.total = res.data.total;
                    this.size = res.data.size;
                    if ( this.total == 0 ){
                        this.noFile = false
                    }
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "获取订单信息失败" );
            })
        },
        getIntegralInfo: function (pageNum, pageSize) {
            this.$http.get('/score/get_score_list.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    console.log(res.data)
                    this.list = res.data.list;
                    this.total = res.data.total;
                    this.size = res.data.size;
                    if ( this.total == 0 ){
                        this.noOrder = true;
                        this.noIntegral = false;
                    }
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "获取订单信息失败" );
            })
        },
        handleOrderDetailClick: function ( value ) {

            location.href = "/order/detail/" + value;

        },
        handleShareClick: function ( value ) {
            location.href = "/share/create?file=" + value;
        },
        handleUserClick: function () {
            this.showValueItem("user");
        },
        handleDealClick: function () {
            this.getDealOrderInfo(1, 5);
            this.showValueItem("deal");
        },
        handleFileClick: function () {
            this.getFileInfo(1, 5);
            this.showValueItem("file");
        },
        handleAccountClick: function () {
            this.showValueItem("account");
        },
        handleIntegralSelect: function () {
            this.orderDeal = false;
            this.getIntegralInfo(1, 5);
        },
        handleOrderSelect: function () {
            this.orderDeal = true
            this.getDealOrderInfo(1, 5);
        },
        showValueItem: function ( value ) {
            if ( value == "user" ) {
                this.resetItemDisplay();
                this.userItem = false;
            }else if ( value == "deal" ) {
                this.resetItemDisplay();
                this.dealItem = false;
            }else if ( value == "file" ) {
                this.resetItemDisplay();
                this.fileItem = false;
            }else if ( value == "account" ){
                this.resetItemDisplay();
                this.accountItem = false;
            }else {
                this.resetItemDisplay();
                this.userItem = false;
            }
        },
        resetItemDisplay: function () {
            this.dealItem = true;
            this.fileItem = true;
            this.accountItem = true;
            this.userItem = true;
            this.orderDeal = true;
        },
        handleMoreBtnClick: function () {


            if ( !this.fileItem ){
                console.log( " fileItem ")
                this.getFileInfo(1, this.size + 5);
            }else if ( !this.dealItem ){
                if (  this.orderDeal ){
                    console.log( " orderDeal ")
                    this.getDealOrderInfo(1, this.size + 5);
                }else {
                    console.log( " inter ")
                    this.getIntegralInfo(1, this.size + 5);
                }
            }

        },
        handleShareDetailClick: function ( id ) {
            location.href = "/share/detail/" + id;
        },
        handleDownloadScoreFileClick: function ( id ) {
            location.href = "/share/download?id=" + id;
        },
        handleModUsernameClick: function () {
            this.modUsername = false;
            this.newUsername = this.username
        },
        handleModEmailClick: function () {
            this.modEmail = false;
            this.newEmail  = this.email
        },
        handleModPhoneClick: function () {
            this.modPhone = false;
            this.newPhone = this.phone
        },
        handleSaveUsernameClick: function () {

            if ( !util.validate(this.newUsername, "username")){
                util.errorTips("请输入 3~10 位字母或者数字")
                return
            }

            this.$http.post('/user/username.do',{
                username: this.newUsername,
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.getUserInfo();
                    this.handleCancelUsernameClick();
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "更新失败" );
            })

        },
        handleSaveEmailClick: function () {

            if ( !util.validate(this.newEmail, "email")){
                util.errorTips("请输入正确格式的邮箱地址")
                return
            }

            this.$http.post('/user/email.do',{
                email: this.newEmail,
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.getUserInfo();
                    this.handleCancelEmailClick();
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "更新失败" );
            })

        },
        handleSavePhoneClick: function () {

            if ( !util.validate(this.newPhone, "phone")){
                util.errorTips("请输入正确格式的手机号")
                return
            }

            this.$http.post('/user/phone.do',{
                phone: this.newPhone
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.getUserInfo();
                    this.handleCancelPhoneClick();
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "更新失败" );
            })

        },
        handleCancelUsernameClick: function () {
            this.newUsername = "";
            this.modUsername = true;
        },
        handleCancelEmailClick: function () {
            this.newEmail = "";
            this.modEmail = true;
        },
        handleCancelPhoneClick: function () {
            this.newPhone = "";
            this.modPhone = true;
        },
        handleSavePasswordClick: function () {

            if ( this.oldPassword == "" || this.oldPassword == undefined ){
                util.errorTips("请输入旧密码后进行提交")
                return
            }

            if ( this.newPassword == "" || this.newPassword == undefined ){
                util.errorTips("请输入新密码后进行提交")
                return
            }

            if ( this.secondPassword == "" || this.secondPassword == undefined ){
                util.errorTips("请确认密码后进行提交")
                return
            }

            if ( this.secondPassword != this.newPassword ){
                util.errorTips("两次输入的密码不一致")
                return
            }

            this.$http.post('/user/password.do',{
                passwordNew: this.newPassword,
                passwordOld: this.oldPassword
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){
                    this.getUserInfo();
                    location.href = "/user/info"
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "更新失败" );
            })
        },
        handleOrderOverClick: function ( value ) {

            this.$http.post('/order/over',{
                orderNo: value
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){
                    this.evaluation(value);
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "完结订单失败！" );
            })

        },
        evaluation: function ( value ) {
            this.overOrderSuccess = false;
            this.orderNo = value;
        },
        handleCreditClick: function () {

            var star = this.$refs.starRef.value;

            this.$http.post('/shop/credit',{
                orderNo: this.orderNo,
                star: star
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){

                    alert("评分成功!");
                    location.href = "/order/info"

                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "评分失败！" );
            })



        },
        handleCloseCreditClick: function () {
            location.href = "/order/info"
        },
        handleOrderCloseClick: function (value) {

            this.$http.post('/order/close',{
                orderNo: value
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){
                    location.href = "/order/info"
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "关闭订单失败！" );
            })

        },
        handleRefundClick: function (value) {

            this.$http.post('/pay/refund.do',{
                orderNo: value
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){
                    location.href = "/order/info"
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "退款失败！" );
            })

        }

    },
    filters: {
        getKeyFilter: function ( value ) {

            if ( value == null ){
                return ""
            }else {
                return "取货码：" + value;
            }

        },
        refuseReasonFilter: function( value ){

            if ( value == null ){
                return ""
            }else {
                return "拒绝理由：" + value;
            }

        },
        /* 根据订单的状态来动态显示按钮的名字 */
        orderStatusFilter: function ( value ) {

            if ( value == "未支付" ){
                return "去支付"
            }else if ( value == "已付款" ){
                return "退款"
            }else if ( value == "订单完结" ){
                return "关闭"
            }else {
                return ""
            }

        },
        /* 根据文件的分享状态来动态显示 */
        fileShareFilter: function ( value ) {

            if ( value == "0" ){
                return "未分享"
            }else {
                return "已分享  "
            }

        },
        /* 根据文件的状态来动态显示 */
        fileShareButtonFilter: function ( value ) {

            if ( value == "0" ){
                return "分享"
            }else {
                return "取消"
            }

        },
        integralFilter: function ( value ) {

            if ( value == null ){
                return ""
            }else {
                return value + "分"
            }

        }
    },
    mounted: function () {
        this.getUserInfo();
        this.item = this.$refs.itemValue.value.trim();
        switch (this.item) {
            case "user":
                this.showValueItem("user");
                break;
            case "file":
                this.getFileInfo(1, 5);
                this.showValueItem("file");
                break;
            case "account":
                //this.getAccountInfo();
                this.showValueItem("account");
                break;
            case "dealOrder":
                this.getDealOrderInfo(1, 5);
                this.showValueItem("deal");
                break;
            case "dealIntegral":
                //this.geDealInfo();
                this.showValueItem("deal");
                break;
        }
    }
});