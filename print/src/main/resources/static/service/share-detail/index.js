/**
 * Created by wangqian on 2019/4/24.
 */
var vue = new Vue({
    el: "#share-detail",
    data: {
        list: [],
        total: null,
        username: null,
        headerPic: null,
        shareId: null,
        size: null,
        content: null
    },
    methods: {
        getCommentList: function () {
            this.shareId = this.$refs.shareId.value;
            this.$http.get('/comment/list', {params: {pageNum: 1, pageSize: 100, targetId: this.shareId }}).then(function (res) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.list = res.data.list;
                    this.total = res.data.total;
                    this.size = res.data.size;
                }else {
                    util.errorTips( res.msg )
                }
            }, function () {
                util.errorTips("请求评论发生错误");
            })
        },
        prepareForDownload: function ( value ) {
            this.$http.get('/share/before', {params: {id: value }}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){

                    if (confirm("积分消耗后无法撤回，是否继续？")){
                        location.href = "/share/download/" + value;
                    }

                }else {
                    util.errorTips( res.msg )
                }
            }, function () {
                util.errorTips("兑换文件时出现了错误，请稍后再试")
            })
        },
        getUserInfo: function () {
            this.$http.post('/user/get_user_info.do',{emulateJSON:true}).then(function (res) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.username = res.data.username;
                    this.headerPic = res.data.headerPic;
                }
            }, function () {

            })
        },
        handleDownloadClick: function ( value ) {
            this.prepareForDownload(value);
        },
        handleSubmitComment: function () {

            if  ( this.content == undefined || this.content == ""){
                util.errorTips("请评论后再进行提交");
                return
            }

            this.$http.post('/comment/create',{
                targetId: parseInt(this.shareId),
                parentId: 0,
                content: this.content
            },{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if(res.status == 0){
                    this.getCommentList();
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "评论失败" );
            })

        }
    },
    mounted: function () {
        this.getCommentList();
        this.getUserInfo();
    },
    filters: {
        dataFormat: function (time) {
            // 时间格式
            var d = new Date(time)
            var year = d.getFullYear();
            var month = d.getMonth() + 1;
            var day = d.getDate() <10 ? '0' + d.getDate() : '' + d.getDate();
            return  year+ '-' + month + '-' + day;
        }
    }
});
