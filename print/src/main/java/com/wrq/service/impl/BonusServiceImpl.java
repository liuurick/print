package com.wrq.service.impl;

import com.wrq.dao.BonusMapper;
import com.wrq.pojo.Bonus;
import com.wrq.service.IBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangqian on 2019/4/1.
 */
@Service(value = "iBonusService")
public class BonusServiceImpl implements IBonusService {

    @Autowired
    private BonusMapper bonusMapper;

    @Override
    public Bonus getBonus(Integer shopId) {
        return bonusMapper.selectBonusByShopId(shopId);
    }
}
