package com.wrq.controller.backend;

import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wangqian on 2019/4/16.
 */
@Controller
@RequestMapping("/store/order")
@Slf4j
public class OrderStoreController {


    @Autowired
    private IOrderService iOrderService;


    /**
     * 店家端 订单列表分页展示
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize, HttpSession session){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

        if(user.getRole() != Const.Role.ROLE_STORE){
            return ServerResponse.createByErrorMessage("不是店主，无法操作！");
        }

         return  iOrderService.getOrderListForShop( user.getId(), pageNum,  pageSize );

    }

    /**
     * 订单详情 页面跳转
     * @param orderNo
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "detail/{orderNo}", method = RequestMethod.GET)
    public ModelAndView getOrder(@PathVariable("orderNo") String orderNo, Map<String, Object> map, HttpSession session) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            map.put("msg", ResponseCode.NEED_LOGON_FOR_ORDER_DETAIL_INFO.getDesc());
            map.put("url", "/store/order/detail/" + orderNo);
            return new ModelAndView("backend/login" , map);
        }

        if(user.getRole() != Const.Role.ROLE_STORE){
            map.put("msg", "不是店主,无法操作!");
            map.put("url", "/store/order/list");
            return new ModelAndView("backend/common/page/error" , map);
        }

        ServerResponse result = iOrderService.getOrderDetailForBackend(user.getId(), orderNo);

        if ( result.isSuccess() ) {
            map.put("orderInfo", result.getData());
            return new ModelAndView("backend/order" , map);
        }else {
            map.put("msg", result.getMsg());
            map.put("url", "/store/order/list");
            return new ModelAndView("backend/common/page/error" , map);
        }

    }

    /**
     * 店主端 输入取货码后 通知用户取货
     * @param getKey
     * @param orderNo
     * @param session
     * @return
     */
    @RequestMapping(value = "notice.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse notice(String getKey, String orderNo , HttpSession session){

        log.info(" 请求 notice.do 接口，getKey = {}, orderNo = {} ",getKey, orderNo);

        if (  StringUtils.isEmpty(getKey) ){
            log.info("取货码为空");
            return ServerResponse.createByErrorMessage("请输入取货码后再进行通知！");
        }

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

        if( user.getRole() != Const.Role.ROLE_STORE ){
            return ServerResponse.createByErrorMessage("不是店主，无法操作！");
        }

        return  iOrderService.noticePackingForBackend( user, getKey,  orderNo);

    }


    /**
     * 拒绝订单 发送短信
     * @param reason
     * @param orderNo
     * @param session
     * @return
     */
    @RequestMapping(value = "refuse.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse refuse(String reason, String fileName,String orderNo , HttpSession session){

        log.info("refuse");

        if (  StringUtils.isEmpty(reason) ){
            return ServerResponse.createByErrorMessage("请选择原因后再进行通知！");
        }

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

        if( user.getRole() != Const.Role.ROLE_STORE ){
            return ServerResponse.createByErrorMessage("不是店主，无法操作！");
        }

        return  iOrderService.refuseOrderForBackend( user, reason, fileName, orderNo);

    }

}
