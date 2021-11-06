package com.hhx4.gmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhx4.gmall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hhx4.gmall.product.entity.CategoryBrandRelationEntity;
import com.hhx4.gmall.product.service.CategoryBrandRelationService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.common.utils.R;



/**
 * Ʒ
 *
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:34:21
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 获取当前品牌关联的分类
     */
    @GetMapping("/catelog/list")
//    @RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list
                (new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.ok().put("data", data);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("product:categorybrandrelation:list")
    public R cateloglist(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 品牌列表
     */
    @RequestMapping("/brands/list")
//    @RequiresPermissions("product:categorybrandrelation:list")
    public R catelogBrandslist(@RequestParam(value = "catId",required = true)Long catId){
        //从service中查到品牌集合；再封装为Vo对象，返回前端
        List<BrandVo> collect = categoryBrandRelationService.listBrandsByCatId(catId);
        return R.ok().put("data", collect);
    }
    /**
     * 品牌id查分类
     */
    @RequestMapping("/catelog/list")
//    @RequiresPermissions("product:categorybrandrelation:list")
    public R catelogCateloglist(@RequestParam(value = "brandId",required = true)Long brandId){
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId)
        );
        /**
         * 未封装文档
         * data.stream().map((item)-> new CateLogVo(item.getCatelogId(),item.getCatelogName())).collect(Collectors.toList());
         */

        return R.ok().put("data", data);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
 //   @RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
