/**
 * Created by wangqian on 2019/4/22.
 */

var vue = new Vue({
    el: "#shared",
    data: {
        list: [],
        size:null,
        image: "",
        pageNum: '',
        pages: '',
        hasNextPage: null,
        hasPreviousPage: null,
        isFirstPage: null,
        isLastPage: null,
        navigatepageNums: [],
        total: null,
        postgraduate: false,
        all: true,
        exam: false,
        paper: false,
        civil: false,
        cet: false,
        other: false
    },
    methods: {
        getShareList: function (pageNum, pageSize) {
            this.$http.get('/share/list.do', {params: {pageNum: pageNum, pageSize: pageSize}}).then(function ( res ) {
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
                    this.size = res.data.size;
                    this.navigatepageNums = res.data.navigatepageNums;
                    console.log(res.data)
                }else {
                    util.errorTips( res.msg )
                }
            }, function () {
                util.errorTips("获取列表失败！")
            })
        },
        getListSortByType: function (pageNum, pageSize, type) {

            this.$http.get('/share/sort', {params: {pageNum: pageNum, pageSize: pageSize, type:type }}).then(function ( res ) {
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
                    this.size = res.data.size;
                    this.navigatepageNums = res.data.navigatepageNums;
                    console.log(res.data)
                }else {
                    util.errorTips( res.msg )
                }
            }, function () {
                util.errorTips("获取列表失败！")
            })

        },
        handleGoShareCLick: function () {
            location.href = "/share/create";
        },
        handleShareDetail: function ( value ){
            location.href = "/share/detail/" + value;
        },
        handleAllClick: function () {

            this.resetClass();
            this.all = true;

            this.getShareList(1, 5);

        },
        handlePostgraduateClick: function () {

            this.resetClass();
            this.postgraduate = true;

            this.getListSortByType(1,5,1);

        },
        handleExaminationClick: function () {


            this.resetClass();
            this.exam = true;

            this.getListSortByType(1,5,2);

        },
        handlePaperClick: function () {


            this.resetClass();
            this.paper = true;

            this.getListSortByType(1,5,3);

        },
        handleCivilClick: function () {


            this.resetClass();
            this.civil = true;

            this.getListSortByType(1,5,4);

        },
        handleOtherClick: function () {


            this.resetClass();
            this.other = true;

            this.getListSortByType(1,5,0);

        },
        handleCETClick: function () {


            this.resetClass();
            this.cet = true;

            this.getListSortByType(1,5,5);

        },
        handleMoreClick: function ( ) {

            if ( this.postgraduate ){
                this.getListSortByType(1,this.size + 3, 1);
            }else if ( this.all ){
                this.getShareList(1,this.size + 3);
            }else if ( this.exam ){
                this.getShareList(1,this.size + 3, 2);
            }else if ( this.paper ){
                this.getShareList(1,this.size + 3, 3);
            }else if ( this.civil ){
                this.getShareList(1,this.size + 3, 4);
            }else if ( this.cet ){
                this.getShareList(1,this.size + 3, 5);
            }else if ( this.other ){
                this.getShareList(1,this.size + 3, 0);
            }

        },
        resetClass: function () {
            this.postgraduate = false;
            this.all = false;
            this.exam = false;
            this.paper = false;
            this.civil = false;
            this.cet = false;
            this.other = false;
        }
    },
    filters: {
        headImgFilter: function ( value ) {
            if (value != ""){
                return value;
            }else {
                return util.getImgHost() + "default_header.jpg";
            }
        }
    },
    created: function () {
        this.getShareList(1, 5);
    }
});