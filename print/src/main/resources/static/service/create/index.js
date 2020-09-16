/**
 * Created by wangqian on 2019/4/4.
 */
var vue = new Vue({
    el: "#create",
    data: {
        color: false,  /* 根据此字段判断用户是否有彩打服务 */
        single: false, /* 根据此字段判断用户是否有单页服务 */
        black: false, /* 根据此字段判断用户是否有黑白服务 */
        double: false, /* 根据此字段判断用户是否有双页服务 */
        colorActive: false,
        fileUploadDisplay: false,/* 未选择文件时候，上传不能点击 */
        singleActive: false, /* 点击单页，通过此字段来激活样式 */
        blackActive: false, /* 点击黑白，通过此字段来激活样式 */
        doubleActive: false, /* 点击双页，通过此字段来激活样式 */
        singleOrDoubleErrorText: true,/* 用户没选择单双页就提交 */
        colorOrBlackErrorText: true,/* 用户没选择彩打就提交 */
        createActive: true, /* 创建订单按钮样式控制 */
        fileText: "点击浏览本地文件", /* 控制选择上传文件的样式 */
        fileName: "", /* 控制选择上传文件的样式 */
        shopId: "",
        pageSizeList: [],
        phone: "",
        userId: null,
        username: "",
        file: '',
        singleOrDouble: null, /* 提交表单时，选择的是单页还是双页 */
        pageSize: 4, /* 提交表单时，选择的页面尺寸 */
        colorOrBlack: null, /* 提交表单时，选择的颜色 */
        userDesc: "备注你想说的话，如：老板辛苦啦，急~", /* 缓存用户评论 */
        fileQuantity: 1, /* 文件份数 */
        filePageCount: null,
        fileNewName: null,
        fileId: null,
        isUpload: false, /* 是否上传？ */
        orderPrice: "", /* 用来展示 */
        fileOrderPrice: "", /* 用来给后端 */
    },
    methods: {
        getFormInfo: function () {
            //此店铺表单信息，支不支持打印？
            this.$http.get('/order/get_form_info.do', {params: {id: this.shopId}}).then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.black = res.data.hasBlack;
                this.color  =  res.data.hasColorful;
                this.double  = res.data.hasDouble;
                this.single  =  res.data.hasSingle;
                this.pageSizeList =  res.data.pageSizeList;
                this.phone  = res.data.phone;
                this.userId  = res.data.userId;
                this.username  = res.data.username;
            }else {
                util.errorTips( res.msg );
                window.location.href = '/login';
            }
        },
        errorCallback: function () {
            util.errorTips("获取 店铺服务 时出现错误！")
        },
        handleFileUpload: function(event) {
            /* 监听 file input，发生改变获取上传的文件以及改变样式！ */
            this.file = event.target.files[0]; // get input file object
            var filePath = this.$refs.fileUploadInput.value;
            if ( util.checkFileType(filePath.toLowerCase())) {
                var arr = filePath.split('\\');
                var fileName = arr[arr.length - 1];
                this.fileText = "更改选择文件";
                this.fileName = fileName;
            } else {
                this.fileName = "请选择正确的文件格式！";
                return false
            }

            if ( this.isUpload ){
                if (confirm("文件已经上传成功，是否更改文件？")){
                    location.reload();
                }
            }else {
                this.fileUploadDisplay = true;
            }

        },
        handleUploadSubmitClick: function () {
            /* form标签上面 @submit.prevent 触发的事件 */
            var file = this.file;
            var formData = new FormData();
            formData.append("upload_file", file);
            this.$http.post('/file/upload.do', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            }).then(this.fileUploadSuccessCallback, this.fileUploadErrorCallback);
        },
        fileUploadSuccessCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0 ){
                this.isUpload = true;
                this.filePageCount = res.data.pageNum;
                this.fileNewName = res.data.fileName;
                this.fileId = res.data.fileId;
                this.fileUploadDisplay = false;
                this.$refs.fileUploadButtonText.value = "上传成功";
                console.log(res.data);
            }else {
                util.errorTips( res.msg );
            }
        },
        fileUploadErrorCallback: function () {
            util.errorTips( "发生错误, 请选择文件重新上传！" );
        },
        handleSingleOrDoubleClick: function ( type ) {
            if ( this.isUpload ){
                if ( type == "single"){
                    this.doubleActive = false;
                    this.singleActive = true;
                    this.singleOrDouble = 0;
                }else {
                    this.singleActive = false;
                    this.doubleActive = true;
                    this.singleOrDouble = 1;
                }

                /* 当用户选择单页、彩页后重新选择了双页，也要发起请求 */
                if ( this.blackActive || this.colorActive ) {
                    this.getOrderPriceInfo();
                }
                this.singleOrDoubleErrorText = true;
            }else {
                util.errorTips("请上传文件后操作！");
                return
            }
        },
        handleBlackOrColorClick: function ( type ) {

            if ( this.isUpload ){

                /* 首先选择单双页才可以选择彩打 */
                if ( !this.singleActive && !this.doubleActive ){
                    this.singleOrDoubleErrorText = false;
                    return
                }
                if ( type == "color" ){
                    this.blackActive = false;
                    this.colorActive = true;
                    this.colorOrBlack = 1;
                }else {
                    this.colorActive = false;
                    this.blackActive = true;
                    this.colorOrBlack = 0;
                }
                this.colorOrBlackErrorText = true;

                this.getOrderPriceInfo();

            }else{
                util.errorTips("请上传文件后操作！");
                return
            }
        },
        handleFileSizeChange: function (event){
            this.pageSize = parseInt(event.target.value);
            this.getOrderPriceInfo();
        },
        handleOrderCreate: function () {
            if ( this.checkParamBeforeCreate() ){
                /* 通过验证！ */
                this.$http.post('/order/create_order.do', {
                    shopId: parseInt(this.shopId),
                    pageCount: parseInt(this.filePageCount),
                    fileQuantity: parseInt(this.fileQuantity),
                    singleOrDouble: parseInt(this.singleOrDouble),
                    blackOrColor: parseInt(this.colorOrBlack),
                    pageSize: parseInt(this.pageSize),
                    orderPrice: this.fileOrderPrice,
                    userDes: this.userDes,
                    fileId: parseInt(this.fileId)
                },{emulateJSON:true}).then(this.createOrderSuccessCallback, this.createOrderErrorCallback);
            }else {
                /* 没有通过验证，直接返回 */
                return
            }
        },
        createOrderSuccessCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0 ){
                location.href = "/order/get_order.do?orderNo=" + res.data.data;
            }else {
                util.errorTips( res.msg );
            }
        },
        createOrderErrorCallback: function () {
            util.errorTips( "创建订单失败！" );
        },
        checkParamBeforeCreate: function (){
            if ( !this.isUpload ){
                util.errorTips("未上传打印文件！");
                return false
            }else if ( !this.singleActive && !this.doubleActive ){
                this.singleOrDoubleErrorText = false;
                return false
            }else if (  !this.blackActive && !this.colorActive ){
                this.colorOrBlackErrorText = false;
                return false
            }else {
                return true
            }
        },
        getOrderPriceInfo: function (){
            this.$http.post('/order/get_price.do', {
                shopId: parseInt(this.shopId),
                pageCount: parseInt(this.filePageCount),
                fileQuantity: parseInt(this.fileQuantity),
                singleOrDouble: parseInt(this.singleOrDouble),
                blackOrColor: parseInt(this.colorOrBlack),
                pageSize: parseInt(this.pageSize)
            }, {emulateJSON:true}).then(this.getPriceSuccessCallback, this.getPriceErrorCallback);
        },
        getPriceSuccessCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.createActive = false;
                this.fileOrderPrice = res.data;
                this.orderPrice = "￥ " + res.data;
            }else {
                util.errorTips( res.msg )
            }
        },
        getPriceErrorCallback: function () {
            util.errorTips( "计算订单价格时发生了错误！" );
        },
        handleComplaintClick: function () {
            alert("投诉此店，请将详细叙述以及证据发送至邮箱：printonline@163.com");
        }
    },
    mounted: function () {
        this.shopId = this.$refs.shopIdRef.value;
        this.getFormInfo();
    },
    watch:{
        fileQuantity:function(){this.getOrderPriceInfo();}
    }
});