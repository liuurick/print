/**
 * Created by wangqian on 2019/5/12.
 */
var vue = new Vue({
    el: "#mod-price",
    data: {
        hasDoublePage: false,
        hasColor: false,
        hasBonus: false,
        has0A0: false,
        has4A0: false,
        hasA0: false,
        hasA1: false,
        hasA2: false,
        hasA3: false,
        hasA4: false,
        hasA5: false,
        hasA6: false,
        hasA7: false,
        hasA8: false,
        hasA9: false,
        hasA10: false,

        price0A0: null,
        price4A0: null,
        priceA0: null,
        priceA1: null,
        priceA2: null,
        priceA3: null,
        priceA4: null,
        priceA5: null,
        priceA6: null,
        priceA7: null,
        priceA8: null,
        priceA9: null,
        priceA10: null,

        singlePage: null,
        doublePage: null,
        black: null,
        color: null,
        bonus: null,
        newBonus: 9,
        pageSizeList: [],
        threshold: null,
        updateBonus: false
    },
    methods: {
        getShopPrice: function (pageNum, pageSize) {
            this.$http.get('/store/shop/price').then(this.successCallback, this.errorCallback);
        },
        successCallback: function ( res ) {
            res = res.body;
            if( res.data && res.status == 0){
                this.singlePage = res.data.singlePage;
                this.doublePage = res.data.doublePage;
                this.black = res.data.black;
                this.color = res.data.color;
                this.hasDoublePage = res.data.hasDouble;
                this.hasColor = res.data.hasColor;
                this.hasBonus = res.data.hasBonus;
                this.pageSizeList = res.data.pageSizeList;
                this.bonus = res.data.bonus;
                this.threshold = res.data.threshold;
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
        handleColorChange: function () {
            this.hasColor = !this.hasColor ;
        },
        handleDoublePageChange: function () {
            this.hasDoublePage = !this.hasDoublePage;
        },
        handleBonusChange: function () {
            this.hasBonus = !this.hasBonus;
        },
        handleA0Click: function (){

            this.hasA0 = !this.hasA0;

            if ( !this.hasA0 ){
                this.priceA0 = null;
            }

        },
        handleA1Click: function (){

            this.hasA1 = !this.hasA1

            if ( !this.hasA1 ){
                this.priceA1 = null;
            }

        },
        handleA2Click: function (){

            this.hasA2 = !this.hasA2;

            if ( !this.hasA2 ){
                this.priceA2 = null;
            }

        },
        handleA3Click: function (){

            this.hasA3 = !this.hasA3;
            if ( !this.hasA3 ){
                this.priceA3 = null;
            }
        },
        handleA4Click: function (){
            this.hasA4 = !this.hasA4;
            if ( !this.hasA4 ){
                this.priceA4 = null;
            }
        },
        handleA5Click: function (){
            this.hasA5 = !this.hasA5;

            if ( !this.hasA5 ){
                this.priceA5 = null;
            }
        },
        handleA6Click: function (){
            this.hasA6 = !this.hasA6;

            if ( !this.hasA6 ){
                this.priceA6 = null;
            }
        },
        handleA7Click: function (){
            this.hasA7 = !this.hasA7;

            if ( !this.hasA7 ){
                this.priceA7 = null;
            }
        },
        handleA8Click: function (){
            this.hasA8 = !this.hasA8;

            if ( !this.hasA8 ){
                this.priceA8 = null;
            }
        },
        handleA9Click: function (){
            this.hasA9 = !this.hasA9;

            if ( !this.hasA9 ){
                this.priceA9 = null;
            }
        },
        handle0A0Click: function (){
            this.has0A0 = !this.has0A0;

            if ( !this.has0A0 ){
                this.price0A0 = null;
            }
        },
        handle4A0Click: function (){
            this.has4A0 = !this.has4A0;

            if ( !this.has4A0 ){
                this.price4A0 = null;
            }
        },
        handleA10Click: function (){
            this.hasA10 = !this.hasA10;

            if ( !this.hasA10 ){
                this.priceA10 = null;
            }
        },
        handleBonusUpdateClick: function () {
            this.updateBonus = !this.updateBonus;
        },
        handleCloseClick: function () {
            location.href = "/store/price"
        },
        handleSubmitClick: function () {

            this.$http.post('/store/shop/price/update', {
                singlePage: this.singlePage,
                doublePage: this.doublePage,
                black: this.black,
                color: this.color,
                threshold: this.threshold,
                bonus: this.newBonus,
                hasDoublePage: this.hasDoublePage,
                hasColor: this.hasColor,
                hasBonus: this.hasBonus,
                price0A0: this.price0A0,
                price4A0: this.price4A0,
                priceA0: this.priceA0,
                priceA1: this.priceA1,
                priceA2: this.priceA2,
                priceA3: this.priceA3,
                priceA4: this.priceA4,
                priceA5: this.priceA5,
                priceA6: this.priceA6,
                priceA7: this.priceA7,
                priceA8: this.priceA8,
                priceA9: this.priceA9,
                priceA10: this.priceA10
            }, {emulateJSON:true}).then(function (res) {

                res = res.body;
                if( res.data && res.status == 0){
                   alert("!!!!")
                }else {
                    util.errorTips( res.msg );
                    if ( (res.msg) == "NEED_LOGIN") {
                        location.href = "/store/login";
                        return
                    }else {
                        location.href = "/store/list"
                    }
                }

            }, function () {

            });

        }
    },
    watch:{

        pageSizeList:function(){

            var list = this.pageSizeList;

            for ( var i in list ) {

                for( var key in list[i]) {

                    switch (key) {
                        case "A0":
                            this.hasA0 = true;
                            this.priceA0 = list[i][key];
                            break;
                        case "A1":
                            this.hasA1 = true;
                            this.priceA1 = list[i][key];
                            break;
                        case "A2":
                            this.hasA2 = true;
                            this.priceA2 = list[i][key];
                            break;
                        case "A3":
                            this.hasA3 = true;
                            this.priceA3 = list[i][key];
                            break;
                        case "A4":
                            this.hasA4 = true;
                            this.priceA4 = list[i][key];
                            break;
                        case "A5":
                            this.hasA5 = true;
                            this.priceA5 = list[i][key];
                            break;
                        case "A6":
                            this.hasA6 = true;
                            this.priceA6 = list[i][key];
                            break;
                        case "A7":
                            this.hasA7 = true;
                            this.priceA7 = list[i][key];
                            break;
                        case "A8":
                            this.hasA8 = true;
                            this.priceA8 = list[i][key];
                            break;
                        case "A9":
                            this.hasA9 = true;
                            this.priceA9 = list[i][key];
                            break;
                        case "A10":
                            this.hasA10 = true;
                            this.priceA10 = list[i][key];
                            break;
                        case "0A0":
                            this.has0A0 = true;
                            this.price0A0 = list[i][key];
                            break;
                        case "4A0":
                            this.has4A0 = true;
                            this.price4A0 = list[i][key];
                            break;
                    }
                }

            }
        }
    },
    created: function(){
        this.getShopPrice();
    }
});