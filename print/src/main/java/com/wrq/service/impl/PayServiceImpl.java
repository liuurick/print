package com.wrq.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wrq.commons.Const;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.FileMapper;
import com.wrq.dao.OrderItemMapper;
import com.wrq.dao.OrderMasterMapper;
import com.wrq.dao.PayInfoMapper;
import com.wrq.enums.OrderStatusEnum;
import com.wrq.enums.PayPlatformEnum;
import com.wrq.pojo.OrderItem;
import com.wrq.pojo.OrderMaster;
import com.wrq.pojo.PayInfo;
import com.wrq.service.IPayService;
import com.wrq.service.WebSocket;
import com.wrq.utils.BigDecimalUtil;
import com.wrq.utils.DateTimeUtil;
import com.wrq.utils.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangqian on 2019/4/9.
 */
@Service(value = "iPayService")
@Slf4j
public class PayServiceImpl implements IPayService {

    private static AlipayTradeService tradeService;
    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private WebSocket webSocket;

    /**
     * 支付，当前端【点击支付】的时候，就会调用 /pay/pay.do 接口，接口拿到订单号。我们要给前端返回生产的二维码的位置以及订单号！
     * 1. 判断用户是否登陆和订单号是否存在
     * 2. 调用支付接口的时候填充一些参数，比如outTradeNo、totalAmount、body、goodsDetailList
     * 3. 创建扫码支付请求builder，请求参数拼接起来。
     * 4. 调用 tradeService.tradePrecreate(builder) 来发起预支付。（tradeService一定提前创建，并且init参数）
     * 5. 判断 tradeService.tradePrecreate(builder) 的结果，如果是success，返回的结果会是二维码和订单号
     * 6. 使用 ZxingUtils， google zxing作为二维码生成工具，把结果获得的二维码生成img
     * 7. 把生成的 img 放在ftp服务器，凭借拼装二维码http地址返回给前端
     * @param orderNo
     * @param userId
     * @param path
     * @return
     */

    public ServerResponse pay(String orderNo, Integer userId, String path) {

        Map<String, String> resultMap = Maps.newHashMap();
        OrderMaster order = orderMasterMapper.selectByUserIdAndOrderNo(userId, orderNo);

        if (order == null) {
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }

        resultMap.put("orderNo", order.getOrderNo());

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("在线打印平台扫码支付，订单号：").append(order.getOrderNo()).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append(",购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();


        List<OrderItem> orderItemList = orderItemMapper.getOrderItemByOrderNoUserId(orderNo, userId);
        for (OrderItem orderItem : orderItemList) {

            com.wrq.pojo.File file = fileMapper.selectByPrimaryKey(orderItem.getFileId());

            if (file == null) {
                return ServerResponse.createByErrorMessage("未找到待打印文件！");
            }

            String fileName = file.getFileName();

            /* 第一个参数应该是物品Id，此处我使用的是 file Id */
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getFileId().toString(), fileName,
                    BigDecimalUtil.mul(orderItem.getCurrentPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getFileQuantity());
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(parameterConfig.getAlipayCallbackUrl())
                //支付宝服务器主动通知商户服务器里指定的页面 http 路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }


                // 需要修改为运行机器上的路径, 获取的二维码字符串转化成二维码，上传到ftp服务器
                // 细节细节
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path, qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile), "img");
                } catch (IOException e) {
                    log.error("上传二维码异常", e);
                }

                targetFile.delete();

                log.info("qrPath:" + qrPath);
                String qrUrl = parameterConfig.getImageHost() + targetFile.getName();
                resultMap.put("qrUrl", qrUrl);

                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                log.error("支付宝预下单失败!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!");
            case UNKNOWN:
                log.error("系统异常，预下单状态未知!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!");
            default:
                log.error("不支持的交易状态，交易返回异常!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!");
        }
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    /**
     * 回调处理，更新数据库 订单的信息
     * @param params
     * @return
     */
    public ServerResponse aliCallback( Map<String,String> params ){
        String orderNo     = params.get("out_trade_no");
        /* 获取回调的交易号 */
        String tradeNo     = params.get("trade_no");
        /* 获取回调的交易状态 */
        String tradeStatus = params.get("trade_status");

        OrderMaster order = orderMasterMapper.selectByPrimaryKey(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("非在线打印平台的订单，回调忽略");
        }
        if( order.getOrderStatus() >= OrderStatusEnum.PAID.getCode() ){
            return  ServerResponse.createBySuccess("支付宝重复回调");
        }
        /* 如果回调获得的交易状态是成功，则会更改orderMaster，如果回调获取的交易状态不是成功就不用更新orderMaster */
        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){

            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setOrderStatus(OrderStatusEnum.PAID.getCode());
            orderMasterMapper.updateByPrimaryKeySelective(order);
        }

        /* 如果回调获得的交易状态是成功还是失败，都会记录交易信息 */
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getBuyerId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insert(payInfo);

        return  ServerResponse.createBySuccess();
    }

    /**
     * 查询订单的状态
     * @param userId
     * @param orderNo
     * @return
     */
    public  ServerResponse queryOrderPayStatus(Integer userId, String orderNo ){

        OrderMaster order = orderMasterMapper.selectByUserIdAndOrderNo(userId, orderNo);

        log.info("正在轮询, userId= {}， orderNo = {}",userId, orderNo);

        if(order == null){
            log.info("正在轮询, 用户没有该订单");
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if( order.getOrderStatus() >= OrderStatusEnum.PAID.getCode() ){

            log.info("正在轮询, 判断已经支付！");

            /* 支付成功，则提示店铺 */

            String orderNoAndShopId = new StringBuilder().append(order.getShopId()).append(".").append(orderNo).toString();

            webSocket.toStore(orderNoAndShopId);

            /* webSocket提示 */

            return  ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 退款
     * @param orderNo
     * @return
     */
    public ServerResponse trade_refund(String orderNo, Integer userId) {

        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderNo);

        if ( orderMaster == null ){
            return ServerResponse.createByErrorMessage("找不到此订单号对应的订单");
        }

        OrderMaster order = orderMasterMapper.selectByUserIdAndOrderNo(userId, orderNo);

        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }

        // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = orderNo;

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = orderMaster.getPayment().toString();

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "";

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:

                int i = orderMasterMapper.updateOrderStatusByOrderNo(OrderStatusEnum.ORDER_SUCCESS.getCode(), orderNo);

                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("更新订单状态失败");
                }

                return ServerResponse.createBySuccess("支付宝退款成功");

            case FAILED:
                return  ServerResponse.createByErrorMessage("支付宝退款失败");

            case UNKNOWN:
                return  ServerResponse.createByErrorMessage("系统异常，订单退款状态未知!!!");

            default:
                return  ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }
}
