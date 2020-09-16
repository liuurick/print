/**
 * Created by wangqian on 2019/4/16.
 */
var vue = new Vue({
    el: "#list",
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
        currentPageNum: 1,
        currentPageSize: 5,
        total: null
    },
    methods: {
        getOrderList: function (pageNum, pageSize) {
            this.$http.get('/store/order/list.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(this.successCallback, this.errorCallback);
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
                this.pages = res.data.pages;
                this.navigatepageNums = res.data.navigatepageNums;
                console.log( res.data );
            }else {
                util.errorTips( res.msg )
                if ( (res.msg) == "NEED_LOGIN") {
                    location.href = "/store/login"
                }
            }
        },
        errorCallback: function ( ) {
            util.errorTips( " 请求订单列表发生错误！" )
        },
        handleNumber: function (item) {
            // 点击数字请求
            this.getOrderList(item, this.currentPageSize);
        },
        handleLeftClick: function () {
            // 点击左箭头按钮
            this.getOrderList(this.pageNum - 1,  this.currentPageSize);
        },
        handleRightClick: function () {
            // 点击右箭头按钮
            this.getOrderList(this.pageNum + 1,  this.currentPageSize);
        }
    },
    computed: {
        notHasNextPage: function () {
            // 当没有上一页的时候按钮禁用
            return !this.hasNextPage;
        },
        notHasPreviousPage: function () {
            // 当没有下一页的时候按钮禁用
            return !this.hasPreviousPage;
        }
    },
    created: function () {
        this.getOrderList(this.currentPageNum, this.currentPageSize);
    }
})