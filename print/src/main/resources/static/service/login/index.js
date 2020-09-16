/**
 * Created by wangqian on 2019/3/29.
 */
var vue = new Vue({
    el: "#app",
    data: {
        username: "",
        password: "",
        url: ""
    },
    methods: {
        handleClick: function () {
            this.$http.post('/user/login.do', {
                username: this.username,
                password: this.password
            }, {emulateJSON:true}).then(this.successCallback, this.errorCallback)
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                console.log("this.url > 0:" + (this.url.length > 0));
                if ( this.url.length > 0 ){
                    window.location.href = this.url ;
                }else {
                    window.location.href = '/index';
                }
            }else {
                util.errorTips( res.msg )
            }
        },
        errorCallback: function ( res ) {
            util.errorTips()
        }
    },
    mounted: function () {
        this.url = this.$refs.urlRef.value.trim();
        console.log( "this.url:" + this.url );
    }
});