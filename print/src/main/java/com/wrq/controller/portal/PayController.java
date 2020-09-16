package com.wrq.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.google.common.collect.Maps;
import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.service.IOrderService;
import com.wrq.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangqian on 2019/4/9.
 */
@Controller
@RequestMapping("/pay/")
@Slf4j
public class PayController {

    @Autowired
    private IPayService iPayService;

    /**
     * 前端传过来订单号，进行支付
     * @param orderNo
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "pay.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse pay(String orderNo, HttpSession session, HttpServletRequest request) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGON_FOR_CREATE.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload");

        return ServerResponse.createBySuccess(iPayService.pay(orderNo, user.getId(), path));
    }

    /**
     * 处理回调，获取回调参数，传入service层
     * @param request
     * @return
     */
    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public java.lang.Object alipayCallback(HttpServletRequest request){

        log.info(" 调用 alipay_callback.do 接口...");

        Map<String,String > params = Maps.newHashMap();

        /* 回调时候阿里会把参数放到request里面，我们需要使用迭代器取出来 */
        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for(int i = 0; i < values.length;i++){
                valueStr = (i == values.length-1)?valueStr + values[i]:valueStr + values[i] + ",";
            }
            params.put(name,valueStr);
        }
        log.info("支付宝回调，sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //非常重要，验证回调的正确性，是不是支付宝发的，并且呢还要避免重复通知

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过，如果在恶意请求我就报警了");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调异常",e);
        }

        // TODO: 2017/12/26

        ServerResponse serverResponse = iPayService.aliCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单支付接口
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "query_order_pay_status.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(String orderNo, HttpSession session){

        log.info("正在轮询...");

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse serverResponse =  iPayService.queryOrderPayStatus(user.getId(),orderNo);
        if( serverResponse.isSuccess() ) {
            log.info("正在轮询, serverResponse.isSuccess()");
            return  ServerResponse.createBySuccess(true);
        }
        log.info("正在轮询, serverResponse 不成功！");
        return  ServerResponse.createBySuccess(false);
    }

    /**
     * 退款
     * @param orderNo
     * @param session
     * @return
     */
    @RequestMapping(value = "refund.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse refund(String orderNo, HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGON_FOR_CREATE.getDesc());
        }

        return iPayService.trade_refund(orderNo,user.getId());
    }
}
