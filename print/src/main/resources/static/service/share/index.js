/**
 * Created by wangqian on 2019/4/19.
 */
var vue = new Vue({
    el: "#share",
    data: {
        createSuccess: true,
        fileUploadDisplay: false,/* 未选择文件时候，上传不能点击 */
        fileText: "请选择需要分享的文件", /* 控制选择上传文件的样式 */
        fileName: "", /* 控制选择上传文件的样式 */
        file: '',
        isUpload: false, /* 是否上传？ */
        filePageCount: null,
        fileNewName: null,
        fileId: null,
        desc: "",
        list: "",
        title: "",
        tagValue: 1,
        richText: "",
        editorItem: null,
        uploadFile: true, /* true 代表当前上传文件为true 而不是我的文件 */
    },
    methods: {
        getUserInfo: function () {
            this.$http.post('',{emulateJSON:true}).then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                }else {
                    util.errorTips( res.msg );
                }
            }, function () {
                util.errorTips( "获取信息失败" );
            })
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
            }else {
                util.errorTips( res.msg );
            }
        },
        fileUploadErrorCallback: function () {
            util.errorTips( "发生错误, 请选择文件重新上传！" );
        },
        editorFunction: function () {

            var _this = this;

            var options = ['code', '|', 'bold', 'italic', 'underline', 'strikethrough', 'forecolor', 'backcolor', 'removeformat',
                '|', 'quotes', 'fontname', 'fontsize', 'heading', 'indent', 'outdent',
                'insertorderedlist', 'insertunorderedlist', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', '|',
                'createlink', 'insertimage'
            ];

            this.editorItem = Edit.getEditor('editorItem', {
                toolbars: options,
                focus: true,
                events: {
                    contentchange: function(editor) {
                        _this.richText = _this.editorItem.getContent();
                    },
                },
                upload : {
                    url : '/file/richtext_img_upload.do', //文件上传的接口地址
                    params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                    fileKey: 'upload_file', //服务器端获取文件数据的参数名
                    connectionCount: 3,
                    leaveConfirm: '正在上传文件'
                },
                resize: true
            })
        },
        getUserFileList: function () {
            this.$http.get('/file/not_share.do').then(function ( res ) {
                res = res.body;
                if( res.data && res.status == 0){
                    this.list = res.data;
                }else {
                    util.errorTips( res.msg );
                    this.uploadFile = true;
                }
            }, function () {
                util.errorTips( "获取个人文件信息失败" );
            })
        },
        selectUploadFile: function () {
            this.uploadFile = true
        },
        selectMyFile: function () {
            this.getUserFileList();

            this.uploadFile = false
        },
        handleFileChange: function (event){
            this.fileId = parseInt(event.target.value);
        },
        handleTagClick: function ( event, value ) {
            this.$refs.tag.querySelectorAll(".select-tag")[0].className = "not-select-tag";
            event.currentTarget.className = "select-tag";
            this.tagValue = value;
        },
        handleSubmitClick: function () {

            if ( (this.title) != null & (this.desc) != null & (this.richText) != null & (this.tagValue) != null & (this.fileId) != null){

                this.$http.post('/share/create', {
                    title: this.title,
                    desc: this.desc,
                    richText: this.richText,
                    tagValue: parseInt(this.tagValue),
                    fileId: parseInt(this.fileId)
                }, {emulateJSON:true}).then(function ( res ) {
                    res = res.data;
                    if( res.status == 0){
                        this.createSuccess = false
                    }else {
                        util.errorTips( res.msg );
                    }
                }, function () {
                    util.errorTips( "提交分享时出现错误！" );
                })

            }else {
                util.errorTips(" 请选择或者填写完整，再进行提交！ ");
            }

        },
        handleBackIndex: function () {
            location.href = "/index";
        },
        handleShareIndex: function () {
            location.href = "/share/list";
        }
    },
    mounted: function () {
        this.editorFunction();
    },
    created: function () {
        var fileId = util.getUrlParam("file");
        if ( fileId != null ){
            this.selectMyFile();
            this.fileId = fileId;
        }
    }
});