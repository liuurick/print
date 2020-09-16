package com.wrq.service;

import com.wrq.pojo.PageSize;

/**
 * Created by wangqian on 2019/4/1.
 */
public interface IPageSizeService {

    PageSize getPageSizeByShopIdAndSize(Integer shopId, Integer pageSize);

}
