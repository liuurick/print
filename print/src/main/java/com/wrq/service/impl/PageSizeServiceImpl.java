package com.wrq.service.impl;

import com.wrq.dao.PageSizeMapper;
import com.wrq.pojo.PageSize;
import com.wrq.service.IPageSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangqian on 2019/4/1.
 */
@Service("iPageSizeService")
public class PageSizeServiceImpl implements IPageSizeService {

    @Autowired
    private PageSizeMapper pageSizeMapper;

    @Override
    public PageSize getPageSizeByShopIdAndSize(Integer shopId, Integer pageSize) {
        return pageSizeMapper.getPageSizeByShopIdAndSize(shopId, pageSize);
    }
}
