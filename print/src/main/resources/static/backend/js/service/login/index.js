/**
 * Created by wangqian on 2019/4/16.
 */
var vue = new Vue({
    el: "#login",
    data: {
        username: "",
        password: "",
        url: ""
    },
    methods: {
        handleLoginClick: function () {
            this.$http.post('/store/user/login.do', {
                username: this.username,
                password: this.password
            }, {emulateJSON:true}).then(this.successCallback, this.errorCallback)
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){

                window.location.href = '/store/list';

            }else {
                util.errorTips( res.msg )
            }
        },
        errorCallback: function ( res ) {
            util.errorTips("请求登陆失败！")
        }
    }
});