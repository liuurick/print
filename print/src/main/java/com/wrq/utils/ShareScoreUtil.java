package com.wrq.utils;

/**
 * Created by wangqian on 2019/4/22.
 */
public class ShareScoreUtil {

    public static int getScore(Integer pageNum){

        int score = 5;

        if ( pageNum > 1 && pageNum < 5 ){
            score =  2;
        }else  if ( pageNum == 1 ){
            score = 1;
        }else  if ( pageNum >= 5 && pageNum < 10 ){
            score = 3;
        }else  if ( pageNum >= 10 && pageNum < 30 ){
            score = 5;
        }else  if ( pageNum >= 30 && pageNum < 80 ){
            score = 6;
        } else  if ( pageNum >= 80 && pageNum < 100 ){
            score = 7;
        } else  if ( pageNum >= 100 ){
            score = 9;
        }

        return score;
    }

}
