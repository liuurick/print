package com.wrq.service;

import com.wrq.commons.ServerResponse;
import com.wrq.form.ShareCreateForm;
import com.wrq.pojo.User;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangqian on 2019/4/21.
 */
public interface IShareService {

    ServerResponse insertShareRecode(ShareCreateForm shareCreateForm, User user);

    ServerResponse getShareList(int pageNum, int pageSize);

    ServerResponse getShareDetailById(Integer shareId, User user);

    ServerResponse prepareForDownload(Integer shareId, User user);

    ServerResponse downloadForUserCenter(String path, Integer id,Integer userId, HttpServletResponse response);

    ServerResponse downloadShareByShopId(String path, Integer shareId, User user, HttpServletResponse response);

    ServerResponse getShareListTypeSort(int pageNum, int pageSize, int type);

}
