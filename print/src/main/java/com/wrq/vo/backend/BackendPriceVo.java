package com.wrq.vo.backend;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangqian on 2019/5/28.
 */
@Data
public class BackendPriceVo {

    private String singlePage;

    private String doublePage;

    private BigDecimal black;

    private BigDecimal color;

    private Boolean hasDouble;

    private Boolean hasColor;

    private Boolean hasBonus;

    private String bonus;

    private String threshold;

    private List pageSizeList;

}
