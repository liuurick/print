package com.wrq.service;

import com.github.pagehelper.PageInfo;
import com.wrq.commons.ServerResponse;

/**
 * Created by wangqian on 2019/4/29.
 */
public interface IScoreService {

    ServerResponse<PageInfo> getScoreList(Integer userId, Integer pageNum, Integer pageSize);

}
