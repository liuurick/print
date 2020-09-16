/**
 * Created by wangqian on 2019/3/29.
 */
var vue = new Vue({
    el: "#detail",
    data: {
        priceStyle: {
            display: 'none'
        },
        contentStyle: {
            display: ''
        },
        contentActive: true,
        priceActive: false,
        bonusDescription: "",
        colorfulDouble: null,
        colorfulSingle: null,
        colorfulVariable: "",
        doubleColorfulVariable: "",
        doubleVariable: "",
        normalDouble: null,
        normalSingle: null,
        otherSizePriceList: [],
        shopId: null,
        otherShopList: [],
        shopStatus: 1,
        printBtnDisable: true
    },
    methods: {
        getPriceInfo: function (id) {
            this.$http.get('/shop/shop_price.do', {params: {id: id}}).then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.bonusDescription = res.data.bonusDescription;
                this.bonusThreshold= res.data.bonusThreshold;
                this.bonusValue = res.data.bonusValue;
                this.colorfulDouble = res.data.colorfulDouble;
                this.colorfulSingle = res.data.colorfulSingle;
                this.colorfulVariable = res.data.colorfulVariable;
                this.doubleColorfulVariable = res.data.doubleColorfulVariable;
                this.doubleVariable = res.data.doubleVariable;
                this.normalDouble = res.data.normalDouble;
                this.normalSingle = res.data.normalSingle;
                this.otherSizePriceList = res.data.otherSizePrice;
                //this.shopId = res.data.shopId;
                console.log(res.data)
            }else {
                util.errorTips( res.msg )
            }
        },
        errorCallback: function ( res ) {
            util.errorTips("获取店铺价格规格的时候出现错误！")
        },
        handlePriceItem: function (id) {
            this.contentStyle.display = 'none';
            this.priceStyle.display = '';
            this.contentActive = false;
            this.priceActive = true;
            this.getPriceInfo(id);
        },
        handleContentItem: function () {
            this.contentStyle.display = '';
            this.priceStyle.display = 'none';
            this.contentActive = true;
            this.priceActive = false;
        },
        getOtherShopList: function () {
            this.$http.get('/shop/other_shop.do', {params: {id: this.shopId}}).then(this.otherSuccessCallback, this.otherErrorCallback);
        },
        otherSuccessCallback: function( res ){
            res = res.body;
            if( res.data && res.status == 0){
                this.otherShopList = res.data;
                console.log(res.data)
            }else {
                util.errorTips( res.msg )
            }
        },
        otherErrorCallback: function ( ) {
            util.errorTips(" 获取其他店铺出现错误！ ")
        },
        handlePrintClick: function ( value ) {

            if ( this.shopStatus == "0"){
                location.href= "/order/create/" + value;
            }else {
                util.errorTips("此店已经打烊啦~")
            }

        },
        handleComplaintClick: function () {
          alert("投诉此店，请将详细叙述以及证据发送至邮箱：printonline@163.com");
        },
        handleOrderClick: function () {
            window.location.href = "/order/info";
        }
    },
    filters: {
        onService: function ( value ) {
            if (value != "-1"){
                return value + "元";
            }else {
                return "无此服务";
            }
        },
        onOtherSizeService: function( value ){

            if (value != "-1"){
                return  "在 单页非彩印 价格的基础上 * " + value;
            }else {
                return "暂时不提供此规格的服务，可通过上方店铺联系方式联系店主。";
            }

        },
        onSizeService: function( value ){
            if (value != "无服务"){
                return  value + "元";
            }else {
                return "无服务";
            }
        },
        shopStatusFilter: function ( value ) {

            if ( value == "0" ){
                this.printBtnDisable = false;
                return "打印";
            }else {
                this.printBtnDisable = false;
                return "已打烊";
            }
        }
    },
    mounted: function () {
        this.shopId = this.$refs.shopIdRef.value;
        this.shopStatus = this.$refs.shopStatusRef.value;
        this.getOtherShopList();
    }
})