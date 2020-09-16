/**
 * Created by wangqian on 2019/5/12.
 */
var vue = new Vue({
    el: "#shop",
    data: {
        name: null,
        address: null,
        desc: null,
        workTime: null,
        closeTime: null,
        mainImg: null,
        miniImg: null,
        content: null,
        phone: null,
        email: null
    },
    methods: {
        getShopDetail: function (pageNum, pageSize) {
            this.$http.get('/store/shop/detail').then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.name = res.data.name;
                this.address = res.data.address;
                this.desc = res.data.desc;
                this.workTime = res.data.workTime;
                this.closeTime = res.data.closeTime;
                this.mainImg = res.data.mainImg;
                this.miniImg = res.data.miniImg;
                this.content = res.data.content;
                this.phone = res.data.phone;
                this.email = res.data.email;
            }else {
                util.errorTips( res.msg );
                if ( (res.msg) == "NEED_LOGIN") {
                    location.href = "/store/login";
                    return
                }else {
                    location.href = "/store/list"
                }
            }
        },
        errorCallback: function ( ) {
            util.errorTips( " 请求店铺信息发生错误！" )
        },
        handleModifyClick: function () {
            location.href = "/store/modify"
        }
    },
    created: function(){
        this.getShopDetail();
    }
});