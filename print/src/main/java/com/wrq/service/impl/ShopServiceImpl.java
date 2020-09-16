package com.wrq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.*;
import com.wrq.enums.*;
import com.wrq.form.ShopForm;
import com.wrq.form.ShopPriceForm;
import com.wrq.pojo.*;
import com.wrq.service.IShopPriceService;
import com.wrq.service.IShopService;
import com.wrq.vo.DetailVo;
import com.wrq.vo.OtherShopVo;
import com.wrq.vo.ShopVo;
import com.wrq.vo.backend.BackendDetailVo;
import com.wrq.vo.backend.BackendPriceVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DoubleValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by wangqian on 2019/3/30.
 */
@Service(value = "iShopService")
@Slf4j
public class ShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private ParameterConfig paramConfig;

    @Autowired
    private ColorMapper  colorMapper;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private SingleDoubleMapper  singleDoubleMapper;

    @Autowired
    private PageSizeMapper  pageSizeMapper;

    @Autowired
    private IShopPriceService iShopPriceService;

    /**
     * 得到店面列表-评分排序
     * @param pageNum 页面
     * @param pageSize 个数
     * @return 分页店面列表
     */
    @Override
    public ServerResponse<PageInfo> getShopListByTypeSort(int pageNum, int pageSize, String type) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shop> shopList = shopMapper.selectShopListByTypeSort(type);
        List<ShopVo> shopVoList = assembleShopVoList(shopList);
        PageInfo pageResult = new PageInfo(shopList);
        pageResult.setList(shopVoList);
        return  ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 得到所有店面，默认按照 创建时间
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getShopList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shop> shopList = shopMapper.selectAllShopByCredit();
        List<ShopVo> shopVoList = assembleShopVoList(shopList);
        PageInfo pageResult = new PageInfo(shopList);
        pageResult.setList(shopVoList);
        return  ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 后端 加载的时候获取店铺信息
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getShopInfoByUserId(Integer userId) {

        Shop shop = shopMapper.selectShopByUserId(userId);

        if ( shop == null ){
            return ServerResponse.createByErrorMessage("还未进行店铺登记，请联系官方人员进行登记！");
        }

        return ServerResponse.createBySuccess(shop);
    }

    /**
     * shopList -> shopVoList
     * @param shopList shopList
     * @return shopVoList
     */
    private List<ShopVo> assembleShopVoList(List<Shop> shopList){
        List<ShopVo> shopVoList = Lists.newArrayList();
        for(Shop shop : shopList){
            ShopVo shopVo = new ShopVo();
            shopVo.setShopId(shop.getId());
            shopVo.setShopName(shop.getShopName());
            shopVo.setCredit(shop.getCredit());
            shopVo.setDealNum(shop.getDealNum());
            shopVo.setShopDescription(shop.getShopDescription());
            shopVo.setNormalDouble(iShopPriceService.getNormalDouble(shop.getId()));
            shopVo.setNormalSingle(iShopPriceService.getNormalSingle(shop.getId()));
            shopVo.setColorfulDouble(iShopPriceService.getColorfulDouble(shop.getId()));
            shopVo.setColorfulSingle(iShopPriceService.getColorfulSingle(shop.getId()));
            shopVo.setStatus(shop.getStatus());
            shopVo.setImgAddress(paramConfig.getImageHost() + shop.getSubImg());
            shopVoList.add(shopVo);
        }
        return shopVoList;
    }


    /**
     * 获取店铺详情
     * @param shopId 店铺ID
     * @return 获取店铺情况
     */
    @Override
    public ServerResponse<DetailVo> getShopDetailById(Integer shopId) {

        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        if (shop == null){
            return ServerResponse.createByErrorMessage("此店铺不存在！");
        }
        User user = userMapper.selectByPrimaryKey(shop.getOwnerId());
        DetailVo detailVo = new DetailVo();

        BeanUtils.copyProperties(shop, detailVo);
        detailVo.setMiniImage(paramConfig.getImageHost() + shop.getMiniImg());
        detailVo.setOwnerAddress(user.getAnswer());
        detailVo.setOwnerPhone(user.getPhone());
        detailVo.setOwnerEmail(user.getEmail());
        detailVo.setShopId(shop.getId());
        return ServerResponse.createBySuccess(detailVo);
    }

    /**
     * 获取其他店铺，除了此shopId
     * @param shopId 除了shopId
     * @return 其他店铺
     */
    @Override
    public ServerResponse<List<OtherShopVo>> getOtherShopByShopId(Integer shopId) {

        List<Shop> shops = shopMapper.selectOtherShopSortByCredit(shopId);
        if (shops == null){
            return ServerResponse.createByErrorMessage("没有其他店铺推荐！");
        }else {
            List<OtherShopVo> otherShopVos = assembleOtherShopVoList(shops);
            return ServerResponse.createBySuccess(otherShopVos);
        }
    }


    /**
     * List<Shop> -> List<OtherShopVo>
     * @param shopList shop详细信息列表
     * @return
     */
    private List<OtherShopVo> assembleOtherShopVoList ( List<Shop> shopList ) {
        List<OtherShopVo> otherShopList = Lists.newArrayList();
        for(Shop shop : shopList){
            OtherShopVo otherShopVo = new OtherShopVo();

            otherShopVo.setCredit(shop.getCredit());
            otherShopVo.setDealNum(shop.getDealNum());
            otherShopVo.setShopAddress(shop.getShopAddress());
            otherShopVo.setShopId(shop.getId());
            otherShopVo.setShopName(shop.getShopName());
            otherShopVo.setMiniAddress( paramConfig.getImageHost() + shop.getMiniImg());

            otherShopList.add(otherShopVo);
        }
        return otherShopList;
    }


    /**
     * 店家端 店铺详情
     * @param userId
     * @return
     */
    @Override
    public ServerResponse getShopDetailByUserId(Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);

        Shop shop = shopMapper.selectShopByUserId(userId);

        if ( shop == null ){
            return ServerResponse.createByErrorMessage("尚未进行店铺登记，请联系管理员");
        }

        BackendDetailVo backendDetailVo = new BackendDetailVo();

        backendDetailVo.setAddress(shop.getShopAddress());
        backendDetailVo.setCloseTime(shop.getCloseTime());
        backendDetailVo.setContent(shop.getContent());
        backendDetailVo.setDesc(shop.getShopDescription());
        backendDetailVo.setMiniImg(paramConfig.getImageHost() + shop.getMiniImg());
        backendDetailVo.setName(shop.getShopName());
        backendDetailVo.setMainImg(paramConfig.getImageHost() + shop.getMainImg());
        backendDetailVo.setWorkTime(shop.getWorkTime());
        backendDetailVo.setId(shop.getId());
        backendDetailVo.setPhone(user.getPhone());
        backendDetailVo.setEmail(user.getEmail());


        return ServerResponse.createBySuccess(backendDetailVo);
    }

    /**
     * 更新店铺信息
     * @param userId
     * @param form
     * @return
     */
    public ServerResponse updateShopInfo(Integer userId, ShopForm form) {

        Shop shop = shopMapper.selectShopByUserId(userId);

        if ( shop == null ){
            return ServerResponse.createByErrorMessage("当前用户并未注册店铺信息");
        }

        Shop newShop = new Shop();

        Integer id = form.getId();

        newShop.setMainImg(form.getMainImgNewName());
        newShop.setWorkTime(form.getWorkTime());
        newShop.setSubImg(form.getMainImgNewName());
        newShop.setShopName(form.getName());
        newShop.setShopDescription(form.getDesc());
        newShop.setShopAddress(form.getAddress());
        newShop.setMiniImg(form.getMiniImgNewName());
        newShop.setContent(form.getRichText());
        newShop.setCloseTime(form.getCloseTime());
        newShop.setId(id);

        int result = shopMapper.updateByPrimaryKeySelective(newShop);

        if ( result < 0 ){
            return ServerResponse.createByErrorMessage("更新失败！");
        }

        String email = form.getEmail();

        String phone = form.getPhone();

        int updateResultForEmail = userMapper.updateEmailByUserId(userId, email);

        if ( updateResultForEmail < 0 ){
            return ServerResponse.createByErrorMessage("更新失败！");
        }

        int updateResultForPhone = userMapper.updatePhoneByUserId(userId, phone);

        if ( updateResultForPhone < 0 ){
            return ServerResponse.createByErrorMessage("更新失败！");
        }

        return ServerResponse.createBySuccess("更新成功");
    }

    /**
     * 修改店铺状态
     * @param userId
     * @return
     */
    public ServerResponse changeShopStatus( Integer userId ){

        HashMap map = new HashMap();

        Shop shop = shopMapper.selectShopByUserId(userId);

        if ( shop == null ){
            return ServerResponse.createByErrorMessage("当前用户并未注册店铺信息");
        }

        if ( shop.getStatus().equals(ShopStatusEnum.OPEN.getCode()) ){
            int result = shopMapper.changeStoreStatus(shop.getId(), ShopStatusEnum.CLOSE.getCode());
            if ( result < 0 ){
                return ServerResponse.createByErrorMessage("暂停营业失败");
            }

            map.put("status", ShopStatusEnum.CLOSE.getCode());

        }

        if ( shop.getStatus().equals(ShopStatusEnum.CLOSE.getCode()) ){
            int result = shopMapper.changeStoreStatus(shop.getId(), ShopStatusEnum.OPEN.getCode());
            if ( result < 0 ){
                return ServerResponse.createByErrorMessage("开始营业失败");
            }
            map.put("status", ShopStatusEnum.OPEN.getCode());
        }

        return ServerResponse.createBySuccess(map);

    }


    /**
     * 店铺打分
     * @param userId
     * @param star
     * @param orderNo
     * @return
     */
    public ServerResponse creditShop( Integer userId, String star, String orderNo ){

        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderNo);

        if ( orderMaster == null ){
            return ServerResponse.createByErrorMessage("此订单不存在，无法进行店铺评分");
        }

        Integer shopId = orderMaster.getShopId();

        Integer buyerId = orderMaster.getBuyerId();

        if ( userId != buyerId ){
            return ServerResponse.createByErrorMessage("非当前用户订单，不可操作");
        }

        if ( orderMaster.getOrderStatus() < OrderStatusEnum.ORDER_SUCCESS.getCode() ){
            return ServerResponse.createByErrorMessage("订单状态错误，不可评分");
        }

        Shop shop = shopMapper.selectByPrimaryKey(shopId);

        if ( shop == null ){
            return ServerResponse.createByErrorMessage("找不到此订单的店铺，无法评论");
        }

        String credit = shop.getCredit();

        Integer creditPeopleNum = shop.getCreditPeopleNum();

        double creditSum = creditPeopleNum * Double.valueOf(credit);

        creditSum  = creditSum + Double.valueOf(star);

        double targetCredit = creditSum / (creditPeopleNum + 1);

        int result = shopMapper.updateCreditByPrimaryKey(shopId, String.valueOf(targetCredit));

        if ( result < 0 ){
            return ServerResponse.createByErrorMessage("更新评分失败");
        }

        return ServerResponse.createBySuccess("评分成功");

    }


    /**
     * 店铺价格
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public ServerResponse getShopPriceByUserId(Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);

        Shop shop = shopMapper.selectShopByUserId(userId);

        Integer shopId = shop.getId();


        if ( shop == null ){
            return ServerResponse.createByErrorMessage("尚未进行店铺登记，请联系管理员");
        }

        BackendPriceVo priceVo = new BackendPriceVo();

        Color black = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.BLACK.getCode());

        priceVo.setBlack(black.getPrice());

        Bonus bonus = bonusMapper.selectBonusByShopId(shopId);

        if ( StringUtils.isEmpty(bonus.getBonus()) ){
            priceVo.setHasBonus(false);
            priceVo.setBonus("0");
        }else {
            priceVo.setHasBonus(true);
            priceVo.setBonus(bonus.getBonus());
            priceVo.setThreshold(bonus.getThreshold());
        }

        Color color = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());

        if ( color == null ){
            priceVo.setColor(BigDecimal.ZERO);
            priceVo.setHasColor(false);
        }else {
            priceVo.setColor(color.getPrice());
            priceVo.setHasColor(true);
        }

        SingleDouble single = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.SINGLE.getCode());
        SingleDouble doublePage = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());

        priceVo.setSinglePage(single.getVariable());

        if ( doublePage == null ){
            priceVo.setDoublePage("0");
            priceVo.setHasDouble(false);
        }else {
            priceVo.setDoublePage(doublePage.getVariable());
            priceVo.setHasDouble(true);
        }

        ArrayList list = Lists.newArrayList();

        for ( PageSizePriceEnum each: PageSizePriceEnum.values() ){
            PageSize result = pageSizeMapper.getPageSizeByShopIdAndSize(shopId, each.getCode());
            if (result != null){

                HashMap map = new HashMap();

                map.put(each.getMessage(), result.getVariable());

                list.add(map);
            }
        }

        priceVo.setPageSizeList(list);

        return ServerResponse.createBySuccess(priceVo);
    }

    /**
     * 更新价格
     * @param form
     * @param userId
     * @return
     */
    public ServerResponse updatePrice(ShopPriceForm form, Integer userId){

        Shop shop = shopMapper.selectShopByUserId(userId);
        if ( shop == null ){
            return ServerResponse.createByErrorMessage("未注册店铺");
        }

        Integer shopId = shop.getId();

        // 1.更新单页和黑白
        BigDecimal black = new BigDecimal(form.getBlack());

        String singlePage = form.getSinglePage();

        int resultForColor = colorMapper.updatePriceByPrimaryKey(shopId, black, ColorTypeEnum.BLACK.getCode().toString());

        if ( resultForColor<= 0 ){
            return ServerResponse.createByErrorMessage("更新时出现错误");
        }

        int resultForSinDou = singleDoubleMapper.updatePrice(shopId, PageTypeEnum.SINGLE.getCode().toString(), singlePage);

        if ( resultForSinDou<= 0 ){
            return ServerResponse.createByErrorMessage("更新时出现错误");
        }

        // 查询当前店铺有没有相关服务
        Boolean hasBonus = form.getHasBonus();

        Boolean hasDoublePage = form.getHasDoublePage();

        Boolean hasColor = form.getHasColor();


        // 处理优惠活动
        Bonus bonus = bonusMapper.selectBonusByShopId(shopId);

        if ( bonus == null ){

            if ( hasBonus ){
                // 生成bonus

                Double newBonus = Double.valueOf(form.getBonus()) / 10;

                String threshold = form.getThreshold();

                Bonus bonusNew = new Bonus();

                bonusNew.setBonus(newBonus.toString());
                bonusNew.setThreshold(threshold);
                bonusNew.setShopId(shopId);

                int insert = bonusMapper.insert(bonusNew);

                if ( insert <= 0 ){
                    return ServerResponse.createByErrorMessage("创建优惠时出现了错误");
                }

            }

        }else{

            if ( hasBonus ){
                // 更新

                Double newBonus = Double.valueOf(form.getBonus()) / 10;
                int i = bonusMapper.updateBonus(shopId, form.getThreshold(), newBonus.toString());
                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("更新优惠时出现了错误");
                }
            }else{
                //删除

                int i = bonusMapper.deleteByPrimaryKey(bonus.getId());

                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("删除优惠时出现了错误");
                }
            }

        }


        // 处理彩打
        Color colorful = colorMapper.selectBlackOrColorByShopId(shopId, ColorTypeEnum.COLORFUL.getCode());

        if ( colorful == null ){

            if ( hasColor ){
                // 生成color
                Color color = new Color();
                color.setShopId(shopId);
                color.setColorType(ColorTypeEnum.COLORFUL.getCode().toString());
                color.setPrice(new BigDecimal(form.getColor()));

                int insert = colorMapper.insert(color);

                if ( insert <= 0){
                    return ServerResponse.createByErrorMessage("删除彩打时出现错误");
                }
            }

        }else{

            if ( hasColor ){
                // 更新
                int i = colorMapper.updatePriceByPrimaryKey(shopId, new BigDecimal(form.getColor()), ColorTypeEnum.COLORFUL.getCode().toString());
                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("更新彩打数据时出现了错误");
                }

            }else{
                //删除

                int i = colorMapper.deleteByPrimaryKey(colorful.getId());

                if (  i <= 0 ){
                    return ServerResponse.createByErrorMessage("删除彩打数据时出现了错误");
                }

            }

        }

        // 处理双页
        SingleDouble doublePageNow = singleDoubleMapper.selectSingleOrDoubleByShopId(shopId, PageTypeEnum.DOUBLE.getCode());

        if ( doublePageNow == null ){

            if ( hasDoublePage ){
                // 生成double

                SingleDouble singleDouble = new SingleDouble();

                singleDouble.setShopId(shopId);
                singleDouble.setPageType(PageTypeEnum.DOUBLE.getCode().toString());
                singleDouble.setVariable(form.getDoublePage());

                int insert = singleDoubleMapper.insert(singleDouble);

                if ( insert <= 0){
                    return ServerResponse.createByErrorMessage("添加双页数据时出错");
                }
            }

        }else{

            if ( hasDoublePage ){
                // 更新
                int i = singleDoubleMapper.updatePrice(shopId, PageTypeEnum.DOUBLE.getCode().toString(), form.getDoublePage());

                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("更新双页数据时出现了错误");
                }

            }else{
                //删除


                int i = singleDoubleMapper.deleteByPrimaryKey(doublePageNow.getId());
                if ( i <= 0 ){
                    return ServerResponse.createByErrorMessage("删除双页数据时出现了错误");
                }

            }

        }

        // 处理尺寸

        HashMap map = new HashMap();

        map.put("A10", form.getPriceA10());
        map.put("A9",  form.getPriceA9());
        map.put("A8", form.getPriceA8());
        map.put("A7", form.getPriceA7());
        map.put("A6", form.getPriceA6());
        map.put("A5", form.getPriceA5());
        map.put("A4", form.getPriceA4());
        map.put("A3", form.getPriceA3());
        map.put("A2", form.getPriceA2());
        map.put("A1", form.getPriceA1());
        map.put("A0", form.getPriceA0());
        map.put("0A0", form.getPrice0A0());
        map.put("4A0", form.getPrice4A0());


        for ( PageSizePriceEnum each: PageSizePriceEnum.values() ){
            PageSize result = pageSizeMapper.getPageSizeByShopIdAndSize(shopId, each.getCode());

            Object object = map.get(each.getMessage());

            if (result == null){

                if ( !StringUtils.isEmpty(object) ){

                    PageSize pageSize = new PageSize();

                    pageSize.setVariable(object.toString());

                    pageSize.setShopId(shopId);
                    pageSize.setSizeType(each.getCode().toString());

                    int insert = pageSizeMapper.insert(pageSize);

                    if ( insert <= 0){
                        return ServerResponse.createByErrorMessage("新建尺寸价格时出现错误");
                    }

                }
            }else {


                if ( !StringUtils.isEmpty(object) ){
                    // 更新

                    int i = pageSizeMapper.updateByPrice(shopId, each.getCode().toString(), object.toString());

                    if ( i <= 0 ){
                        return ServerResponse.createByErrorMessage("更新尺寸价格数据时出现了错误");
                    }

                }else{
                    //删除

                    int i = pageSizeMapper.deleteByPrimaryKey(result.getId());
                    if ( i <= 0 ){
                        return ServerResponse.createByErrorMessage("删除价格尺寸数据时出现了错误");
                    }

                }


            }
        }


        return ServerResponse.createBySuccess("更新成功");

    }


}
