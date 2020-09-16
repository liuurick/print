/**
 * Created by wangqian on 2019/3/29.
 */
var vue = new Vue({
    el: "#header",
    data: {
        profileStyle: {
            display: 'none'
        },
        controlStyle: {
            display: 'none'
        },
        isShow: false,
        username: "",
        phone: "",
        owner: false,
    },
    methods: {
        getUserInfo: function () {
            this.$http.post('/user/get_user_info.do',{emulateJSON:true}).then(this.successCallback, this.errorCallback)
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.controlStyle.display = 'none';
                this.profileStyle.display = '';
                this.username = res.data.username;
                this.phone = res.data.phone;
            }else {
                // do nothing
                this.profileStyle.display = 'none'
                this.controlStyle.display = ''
            }
        },
        errorCallback: function ( res ) {
            util.errorTips()
        },
        handleUserClick: function () {
            this.isShow = !this.isShow;
        },
        handleUserCenterClick: function () {
            location.href = "/user/info";
        },
        handleOrderClick: function () {
            location.href = "/order/info";
        },
        handleIndexCenterClick: function () {
            location.href = "/index";
        },
        handleShareClick: function () {
            location.href = "/share/list";
        },
        handleHelpClick: function () {
            location.href = "/help";
        },
        handleLoginOut: function () {
            this.$http.post('/user/logout.do',{emulateJSON:true}).then(this.outSuccessCallback, this.errorCallback)
        },
        outSuccessCallback: function ( res ) {
            res = res.body;
            if( res.status == 0){
                window.location.href = '/index';
            }else {
                util.errorTips( res.msg )
            }
        }
    },
    mounted: function () {
        this.getUserInfo();
    }

});