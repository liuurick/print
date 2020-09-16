package com.wrq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.ScoreMapper;
import com.wrq.dao.ShareMapper;
import com.wrq.dao.UserMapper;
import com.wrq.pojo.*;
import com.wrq.service.IScoreService;
import com.wrq.utils.DateTimeUtil;
import com.wrq.vo.ScoreListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangqian on 2019/4/29.
 */
@Service("iScoreService")
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    /**
     * 用户界面 获取积分记录
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getScoreList(Integer userId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Score> scoreList = scoreMapper.selectScoreListByUserId(userId);

        List<ScoreListVo> result = assembleScoreListVo(scoreList);

        PageInfo pageResult = new PageInfo(scoreList);

        pageResult.setList(result);

        return ServerResponse.createBySuccess(pageResult);
    }

    private List<ScoreListVo> assembleScoreListVo(List<Score> scoreList){

        List<ScoreListVo> scoreListVo = Lists.newArrayList();

        for( Score score : scoreList ){

            ScoreListVo scoreVo = new ScoreListVo();

            scoreVo.setId(score.getId());

            scoreVo.setIntegral(score.getIntegral());
            scoreVo.setCreateTime( DateTimeUtil.dateToStr(score.getCreateTime(), "yyyy-MM-dd HH:mm"));

            scoreVo.setFileNewName(score.getFileNewName());

            Integer ownerId = score.getOwnerId();

            User user = userMapper.selectByPrimaryKey(ownerId);

            if ( user != null ){
                scoreVo.setOwnerName(user.getUsername());
            }else {
                scoreVo.setOwnerName("账户已注销");
            }

            Integer shareId = score.getShareId();


            scoreVo.setShareId(shareId);

            Share share = shareMapper.selectByPrimaryKey(shareId);

            String fileExtensionName = score.getFileNewName().substring(score.getFileNewName().lastIndexOf(".")+1);

            scoreVo.setTitle( share.getTitle() );
            scoreVo.setFileTypeImg( parameterConfig.getImageHost() + fileExtensionName + ".png" );

            scoreListVo.add(scoreVo);
        }
        return scoreListVo;
    }

}
