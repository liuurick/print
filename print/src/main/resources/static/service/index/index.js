/**
 * Created by wangqian on 2019/3/29.
 */
var vue = new Vue({
    el: "#content",
    data: {
        list: [],
        image: "",
        pageNum: '',
        pages: '',
        hasNextPage: null,
        hasPreviousPage: null,
        isFirstPage: null,
        isLastPage: null,
        navigatepageNums: [],
        imageList: [],
        total: null,
        size: null,
        shopId: "",
        currentPageNum: 1,
        currentPageSize: 3,
        creditSelected: true,
        dealSelected: false,
        allSelected: false,
        sortType: "credit"
    },
    methods: {
        getAllShopList: function ( pageNum, pageSize ) {
            this.$http.get('/shop/get_all_shop.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(this.successCallback, this.errorCallback)
        },
        getShopList: function (pageNum, pageSize, type) {
            this.$http.get('/shop/shop_list.do', {params: {pageNum: pageNum, pageSize: pageSize, type: type}})
                .then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.list = res.data.list;
                this.pageNum = res.data.pageNum;
                this.hasNextPage = res.data.hasNextPage;
                this.hasPreviousPage = res.data.hasPreviousPage;
                this.isFirstPage = res.data.isFirstPage;
                this.isLastPage = res.data.isLastPage;
                this.total = res.data.total;
                this.size = res.data.size;
                this.pages = res.data.pages;
                this.navigatepageNums = res.data.navigatepageNums;
            }else {
                util.errorTips( res.msg )
            }
        },
        errorCallback: function ( ) {
            util.errorTips( "请求店铺列表发生错误！" )
        },
        handleOrderClick: function () {
            window.location.href = "/order/info";
        },
        handleCreditClick: function () {
            this.creditSelected = true;
            this.dealSelected= false;
            this.allSelected= false;
            this.sortType = "credit";
            this.getShopList(this.currentPageNum, this.currentPageSize, "credit");
        },
        handleDealClick: function () {
            this.creditSelected = false;
            this.dealSelected= true;
            this.allSelected= false;
            this.sortType = "deal_num";
            this.getShopList(this.currentPageNum, this.currentPageSize, "deal_num");
        },
        handleAllClick: function () {
            this.creditSelected = false;
            this.dealSelected= false;
            this.allSelected= true;
            this.sortType = "all";
            this.getAllShopList(this.currentPageNum, this.currentPageSize);
        },
        handleMoreClick: function () {

            if (  this.sortType == "all" ){
                this.getAllShopList(1, this.size + 3);
            }else {
                this.getShopList(1, this.size + 3, this.sortType);
            }


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
        shopStatusFilter: function ( value ) {

            if ( value == "0" ){
                return "进店";
            }else {
                return "打烊";
            }

        }
    },
    created: function () {
        this.getShopList(this.currentPageNum, this.currentPageSize, "credit");
    }
})