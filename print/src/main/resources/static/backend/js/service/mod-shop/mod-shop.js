/**
 * Created by wangqian on 2019/5/12.
 */
var vue = new Vue({
    el: "#mod-shop",
    data: {
        name: null,
        address: null,
        desc: null,
        workTime: null,
        closeTime: null,
        mainImg: null,
        miniImg: null,
        content: null,
        shopId: null,
        phone: null,
        email: null,

        miniImgUploadDisplay: false,/* 未选择文件时候，上传不能点击 */
        mainImgUploadDisplay: false,
        fileUploadButtonTextForMini: "",
        fileUploadButtonTextForMain: "",
        mainImgText: "请选择主图文件", /* 控制选择上传文件的样式 */
        miniImgText: "请选择副图文件",
        mainImgName: "",
        miniImgName:"",
        mainImgNewName: "",
        miniImgNewName:"",
        miniImgFile: '',
        mainImgFile: '',
        mainImgIsUpload: false, /* 是否上传？ */
        miniImgIsUpload: false,
        richText: "",
        editorItem: null,
    },
    methods: {
        getShopDetail: function () {
            this.$http.get('/store/shop/detail').then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.name = res.data.name;
                this.phone = res.data.phone;
                this.email = res.data.email;
                this.address = res.data.address;
                this.desc = res.data.desc;
                this.workTime = res.data.workTime;
                this.closeTime = res.data.closeTime;
                this.mainImg = res.data.mainImg;
                this.miniImg = res.data.miniImg;
                this.miniImgNewName = util.getImgName(res.data.miniImg);
                this.mainImgNewName = util.getImgName(res.data.mainImg);
                this.richText = res.data.content;
                this.shopId = res.data.id;
                this.editorItem.setContent( this.richText );
            }else {
                util.errorTips( res.msg );
                if ( ( res.msg ) == "NEED_LOGIN") {
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
        handleMainImgUpload: function(event) {
            /* 监听 file input，发生改变获取上传的文件以及改变样式！ */
            this.mainImgFile = event.target.files[0]; // get input file object
            var filePath = this.$refs.mainImgUploadInput.value;
            if ( util.checkFileType(filePath.toLowerCase())) {
                var arr = filePath.split('\\');
                var mainImgName = arr[arr.length - 1];
                this.mainImgText = "更改选择文件";
                this.mainImgName = mainImgName;
            } else {
                this.mainImgName = "请选择正确的文件格式！";
                return false
            }

            if ( this.mainImgIsUpload ){
                if (confirm("文件已经上传成功，是否更改文件？")){
                    location.reload();
                }
            }else {
                this.mainImgUploadDisplay = true;
            }

        },
        handleMiniImgUpload: function(event) {
            /* 监听 file input，发生改变获取上传的文件以及改变样式！ */
            this.mainImgFile = event.target.files[0]; // get input file object
            var filePath = this.$refs.miniImgUploadInput.value;
            if ( util.checkFileType(filePath.toLowerCase())) {
                var arr = filePath.split('\\');
                var miniImgName = arr[arr.length - 1];
                this.miniImgText = "更改选择文件";
                this.miniImgName = miniImgName;
            } else {
                this.mainImgName = "请选择正确的文件格式！";
                return false
            }

            if ( this.miniImgIsUpload ){
                if (confirm("文件已经上传成功，是否更改文件？")){
                    location.reload();
                }
            }else {
                this.miniImgUploadDisplay = true;
            }

        },
        handleUploadSubmitMainImgClick: function () {
            /* form标签上面 @submit.prevent 触发的事件 */
            var file = this.mainImgFile;
            var formData = new FormData();
            formData.append("upload_file", file);
            this.$http.post('/store/shop/upload', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            }).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0 ){
                    this.mainImgIsUpload = true;
                    this.mainImgUploadDisplay = false;
                    this.mainImgNewName = res.data.img;
                    this.$refs.mainImgUploadButtonText.value = "上传成功";
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "发生错误, 请选择文件重新上传！" );
            });
        },
        handleUploadSubmitMiniImgClick: function () {
            /* form标签上面 @submit.prevent 触发的事件 */
            var file = this.miniImgFile;
            var formData = new FormData();
            formData.append("upload_file", file);
            this.$http.post('/store/shop/upload', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            }).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0 ){
                    this.miniImgIsUpload = true;
                    this.miniImgUploadDisplay = false;
                    this.miniImgNewName = res.data.img;
                    this.$refs.miniImgUploadButtonText.value = "上传成功";
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "发生错误, 请选择文件重新上传！" );
            });
        },
        editorFunction: function () {
            var _this = this;
            var options = ['code', '|', 'bold', 'italic', 'underline', 'strikethrough', 'forecolor', 'backcolor', 'removeformat',
                '|', 'quotes', 'fontname', 'fontsize', 'heading', 'indent', 'outdent',
                'insertorderedlist', 'insertunorderedlist', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', '|',
                'createlink', 'insertimage', 'insertvideo', 'insertcode'
            ];
            this.editorItem = Edit.getEditor('editorItem', {
                // toolbars: options,
                focus: true,
                events: {
                    contentchange: function(editor) {
                        _this.richText = _this.editorItem.getContent();
                    },
                },
                resize: true
            })
        },
        handleCloseClick: function () {
            location.href = "/store/detail"
        },
        handleSubmitClick: function () {

            if ( !util.validate(this.email, "email")){
                util.errorTips("请输入正确的邮箱格式！");
                return
            }

            if ( !util.validate(this.phone, "phone")){
                util.errorTips("请输入正确的手机格式！");
                return
            }

            if ( (this.name) != null & (this.workTime) != null & (this.closeTime) != null & (this.desc) != null & (this.richText) != null& (this.email) != null& (this.phone) != null & (this.address) != null ){

                this.$http.post('/store/shop/update_shop.do', {
                    name: this.name,
                    workTime: this.workTime,
                    closeTime: this.closeTime,
                    desc: this.desc,
                    richText: this.richText,
                    address: this.address,
                    miniImgNewName: this.miniImgNewName,
                    mainImgNewName: this.mainImgNewName,
                    id: this.shopId,
                    phone: this.phone,
                    email: this.email
                }, {emulateJSON:true}).then(function ( res ) {
                    res = res.data;
                    if( res.status == 0 ){

                        location.href = "/store/detail"

                    }else {
                        util.errorTips( res.msg );
                    }
                }, function () {
                    util.errorTips( "提交时出现错误！" );
                })

            }else {
                util.errorTips(" 请填写完整，再进行提交！ ");
            }

        }
    },
    mounted: function () {
        this.editorFunction();
        this.getShopDetail();
    }
});