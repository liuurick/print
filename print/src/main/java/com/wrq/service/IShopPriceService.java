package com.wrq.service;

import com.wrq.commons.ServerResponse;
import com.wrq.form.GetPriceForm;
import com.wrq.pojo.User;
import com.wrq.vo.FormVo;
import com.wrq.vo.PriceVo;

import java.math.BigDecimal;

/**
 * Created by wangqian on 2019/3/30.
 */
public interface IShopPriceService {

    BigDecimal getNormalDouble (Integer shopId);

    BigDecimal getNormalSingle (Integer shopId);

    BigDecimal getColorfulDouble (Integer shopId);

    BigDecimal getColorfulSingle (Integer shopId);

    PriceVo getPriceVoByShopId (Integer shopId);

    FormVo getFormVoByShopId (Integer shopId, User user);

    ServerResponse getOrderPrice(GetPriceForm form);

}
