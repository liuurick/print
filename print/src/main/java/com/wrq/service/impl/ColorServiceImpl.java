package com.wrq.service.impl;

import com.wrq.dao.ColorMapper;
import com.wrq.pojo.Color;
import com.wrq.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangqian on 2019/4/1.
 */
@Service("iColorService")
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorMapper colorMapper;

}
