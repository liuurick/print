package com.wrq.service;

import com.github.pagehelper.PageInfo;
import com.wrq.commons.ServerResponse;
import com.wrq.form.ShopForm;
import com.wrq.form.ShopPriceForm;
import com.wrq.vo.DetailVo;
import com.wrq.vo.OtherShopVo;

import java.util.List;

/**
 * Created by wangqian on 2019/3/30.
 */
public interface IShopService {

    ServerResponse<PageInfo> getShopListByTypeSort(int pageNum, int pageSize, String type);

    ServerResponse<DetailVo> getShopDetailById(Integer shopId);

    ServerResponse<List<OtherShopVo>> getOtherShopByShopId(Integer shopId);

    ServerResponse<PageInfo> getShopList(int pageNum, int pageSize);

    ServerResponse getShopInfoByUserId(Integer userId);

    ServerResponse getShopDetailByUserId(Integer userId);

    ServerResponse updateShopInfo(Integer userId, ShopForm form);

    ServerResponse changeShopStatus(Integer userId);

    ServerResponse creditShop( Integer userId, String star, String orderNo );

    ServerResponse getShopPriceByUserId(Integer userId);

    ServerResponse updatePrice(ShopPriceForm form, Integer userId);

}
