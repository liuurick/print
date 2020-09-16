package com.wrq.service.impl;

import com.google.common.collect.Lists;
import com.wrq.commons.ServerResponse;
import com.wrq.dao.BonusMapper;
import com.wrq.dao.ColorMapper;
import com.wrq.dao.PageSizeMapper;
import com.wrq.dao.SingleDoubleMapper;
import com.wrq.enums.ColorTypeEnum;
import com.wrq.enums.PageSizePriceEnum;
import com.wrq.enums.PageTypeEnum;
import com.wrq.enums.ServiceNotExistEnum;
import com.wrq.form.GetPriceForm;
import com.wrq.pojo.*;
import com.wrq.service.IShopPriceService;
import com.wrq.utils.BigDecimalUtil;
import com.wrq.vo.FormVo;
import com.wrq.vo.PriceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by wangqian on 2019/3/30.
 */
@Service("iShopPriceStandard")
@Slf4j
public class ShopPriceServiceImpl implements IShopPriceService {

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private PageSizeMapper pageSizeMapper;

    @Autowired
    private SingleDoubleMapper singleDoubleMapper;

    @Autowired
    private BonusMapper bonusMapper;

    /**
     * 获取 指定店铺 双页的价格
     * @param shopId 店铺ID
     * @return 此店铺的双页的价格
     */
    @Override
    public BigDecimal getNormalDouble(Integer shopId) {

        BigDecimal normalPrice = null;
        String index = null;

        /* 获取此店铺黑白的价格 */
        Color blackOrWhite = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.BLACK.getCode());
        if ( blackOrWhite != null ){
            normalPrice = blackOrWhite.getPrice();
        }else{
            return new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }


        /* 获取此店铺双页的比例系数 */
        SingleDouble doublePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());

        if ( doublePage != null ){
            index = doublePage.getVariable();
        }else{
            return new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }

        /* 黑白价格乘积，比如：黑白价格 1 元，双页面的比例系数为：1.5，则黑白双页面价格：1 * 1.5  */
        BigDecimal price = BigDecimalUtil.mul(normalPrice.doubleValue(), Double.valueOf(index));

        return price;
    }

    /**
     * 获取 指定店铺 单页价格
     * @param shopId ID
     * @return 单页价格
     */
    @Override
    public BigDecimal getNormalSingle(Integer shopId) {

        BigDecimal price = null;

        /* 获取此店铺黑白的价格 */
        Color blackOrWhite = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.BLACK.getCode());

        if ( blackOrWhite != null ){
            price = blackOrWhite.getPrice();
        }else {
            price = new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }

        return price;
    }

    /**
     * 彩打 双页
     * @param shopId ID
     * @return 彩双
     */
    @Override
    public BigDecimal getColorfulDouble(Integer shopId) {

        BigDecimal colorfulPrice = null;
        String index = null;

        /* 获取此店铺彩色的价格 */
        Color colorful = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());

        if ( colorful != null ){
            colorfulPrice = colorful.getPrice();
        }else {
            return new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }

        /* 获取此店铺双页的比例系数 */
        SingleDouble doublePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());

        if ( doublePage != null ){
            index = doublePage.getVariable();
        }else{
            return new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }

        /* 彩色价格乘积，比如：彩色价格 2 元，双页面的比例系数为：1.5，则黑白双页面价格：2 * 1.5  */
        BigDecimal price = BigDecimalUtil.mul(colorfulPrice.doubleValue(), Double.valueOf(index));

        return price;
    }

    /**
     * 彩 单
     * @param shopId ID
     * @return 彩 单
     */
    @Override
    public BigDecimal getColorfulSingle(Integer shopId) {

        BigDecimal price = null;

        /* 获取此店铺彩色的价格 */
        Color colorful = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());
        if ( colorful != null ){
            price = colorful.getPrice();
        }else {
            price = new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }

        return price;
    }

    /**
     * 店铺详情页价格获取
     * @param shopId 店铺ID
     * @return 此店铺价格详情
     */
    @Override
    public PriceVo getPriceVoByShopId(Integer shopId) {

        PriceVo priceVo = new PriceVo();

        priceVo.setShopId(shopId);

        /* Bonus */
        Bonus bonus = bonusMapper.selectBonusByShopId(shopId);
        if ( bonus == null ){
            priceVo.setBonusDescription("此店铺没有优惠活动信息！");
        }else {
            priceVo.setBonusThreshold(bonus.getThreshold());
            priceVo.setBonusDescription(bonus.getDescription());
            priceVo.setBonusValue(bonus.getBonus());
        }

        /* OtherSizePrice */

        ArrayList sizePriceList = Lists.newArrayList();
        for ( PageSizePriceEnum each: PageSizePriceEnum.values() ){
            PageSize result = pageSizeMapper.getPageSizeByShopIdAndSize(shopId, each.getCode());
            if (result == null){
                sizePriceList.add(ServiceNotExistEnum.SERVICE_NOT_EXIST.getMessage());
            }else {
                sizePriceList.add(result.getVariable());
            }
        }
        priceVo.setOtherSizePrice(sizePriceList);


        /* 彩印黑白、单双页 比例系数 */
        Color colorful = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());

        SingleDouble doublePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());

        BigDecimal doubleColorfulVariable = null;

        if (colorful == null){
            priceVo.setColorfulVariable(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode().toString());
            doubleColorfulVariable = new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }else {
            priceVo.setColorfulVariable(colorful.getPrice().toString());
        }

        if (doublePage == null){
            priceVo.setDoubleVariable(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode().toString());
            doubleColorfulVariable = new BigDecimal(ServiceNotExistEnum.SERVICE_NOT_EXIST.getCode());
        }else {
            priceVo.setDoubleVariable(doublePage.getVariable());
        }

        /* 既有彩印服务，又有双页服务 */
        if ( colorful != null && doublePage != null){
            doubleColorfulVariable = BigDecimalUtil.mul((colorful.getPrice().doubleValue()), (Double.valueOf(doublePage.getVariable())));
        }

        priceVo.setDoubleColorfulVariable(doubleColorfulVariable.toString());

        priceVo.setNormalSingle(getNormalSingle(shopId));
        priceVo.setColorfulSingle(getColorfulSingle(shopId));
        priceVo.setNormalDouble(getNormalDouble(shopId));
        priceVo.setColorfulDouble(getColorfulDouble(shopId));

        return priceVo;
    }


    /**
     * 根据 店铺ID 获取此店面 相关服务，是否有彩打？有什么尺寸？
     * @param shopId 店铺ID
     * @param user User
     * @return 店面 相关服务
     */
    public FormVo getFormVoByShopId(Integer shopId, User user) {

        FormVo formVo = new FormVo();

        Color black = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.BLACK.getCode());
        if ( black == null ){
            formVo.setHasBlack(false);
        }else {
            formVo.setHasBlack(true);
        }

        Color color = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());
        if ( color == null ){
            formVo.setHasColorful(false);
        }else {
            formVo.setHasColorful(true);
        }


        SingleDouble doublePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());
        if ( doublePage == null ){
            formVo.setHasDouble(false);
        }else {
            formVo.setHasDouble(true);
        }

        SingleDouble singlePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.SINGLE.getCode());
        if ( singlePage == null ){
            formVo.setHasSingle(false);
        }else {
            formVo.setHasSingle(true);
        }

        /* page size */
        ArrayList list = Lists.newArrayList();

        for ( PageSizePriceEnum each: PageSizePriceEnum.values() ){
            PageSize result = pageSizeMapper.getPageSizeByShopIdAndSize(shopId, each.getCode());
            if (result != null){
                list.add(each.getMessage());
            }
        }

        formVo.setPageSizeList(list);

        /* User */
        formVo.setPhone(user.getPhone());
        formVo.setUserId(user.getId());
        formVo.setUsername(user.getUsername());

        return formVo;
    }

    /**
     * 当用户选择创建订单表单参数时，获取价格！
     * @param form
     * @return
     */
    public ServerResponse getOrderPrice(GetPriceForm form){

        BigDecimal price = new BigDecimal(BigInteger.ZERO);


        /* 1.判断彩印或者黑白 */

        Integer blackOrColor = form.getBlackOrColor();

        if ( blackOrColor.equals(ColorTypeEnum.BLACK.getCode()) ){
            Color black = colorMapper.selectBlackOrColorByShopId(form.getShopId(), ColorTypeEnum.BLACK.getCode());
            price = black.getPrice();
        }else {
            Color color = colorMapper.selectBlackOrColorByShopId(form.getShopId(), ColorTypeEnum.COLORFUL.getCode());
            price = color.getPrice();
        }


        /* 2.判断单或者双 */

        Integer singleOrDouble = form.getSingleOrDouble();

        if ( singleOrDouble.equals( PageTypeEnum.SINGLE.getCode() ) ){
            SingleDouble single = singleDoubleMapper.selectSingleOrDoubleByShopId(form.getShopId(), PageTypeEnum.SINGLE.getCode());
            price = BigDecimalUtil.mul(Double.valueOf(single.getVariable()), price.doubleValue());
        }else {
            SingleDouble doubleVariable = singleDoubleMapper.selectSingleOrDoubleByShopId(form.getShopId(), PageTypeEnum.DOUBLE.getCode());
            price = BigDecimalUtil.mul(Double.valueOf(doubleVariable.getVariable()), price.doubleValue());
        }

        /* 4.判断打印尺寸 */

        Integer pageSize = form.getPageSize();

        PageSize pageSizeVariable = pageSizeMapper.getPageSizeByShopIdAndSize(form.getShopId(), pageSize);

        price = BigDecimalUtil.mul(Double.valueOf(pageSizeVariable.getVariable()), price.doubleValue());

        /* 5. 数量  */

        Integer pageCount = form.getPageCount();

        Integer fileQuantity = form.getFileQuantity();

        Integer pageNum = pageCount * fileQuantity;

        Double finalPageNum = pageNum.doubleValue();

        if ( singleOrDouble.equals(PageTypeEnum.DOUBLE.getCode()) ){
            finalPageNum = finalPageNum / 2 ;
        }

        /* 6. 活动 */

        Bonus bonus = bonusMapper.selectBonusByShopId(form.getShopId());

        if ( StringUtils.isEmpty(bonus.getBonus()) ){
            price = BigDecimalUtil.mul(finalPageNum, price.doubleValue());
        }else {
            String threshold = bonus.getThreshold();
            if ( finalPageNum >= Double.valueOf(threshold) ){
                BigDecimal bonusBeforePrice = BigDecimalUtil.mul(finalPageNum, price.doubleValue());
                String bonusVariable = bonus.getBonus();
                price = BigDecimalUtil.mul(bonusBeforePrice.doubleValue(), Double.valueOf(bonusVariable));
            }else {
                price = BigDecimalUtil.mul(finalPageNum, price.doubleValue());
            }
        }

        /* 保留两位小数， 四舍五入 */
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);

        return ServerResponse.createBySuccess(price);
    }

}
