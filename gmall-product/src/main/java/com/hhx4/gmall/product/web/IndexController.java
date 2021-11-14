package com.hhx4.gmall.product.web;

import com.hhx4.gmall.product.entity.CategoryEntity;
import com.hhx4.gmall.product.service.CategoryService;
import com.hhx4.gmall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @created by wt at 2021-11-12 18:07
 **/
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){

        System.out.println(""+Thread.currentThread().getId());
        //TODO 1、查出所有的1级分类
        List<CategoryEntity> categoryEntities =  categoryService.getLevel1Categorys();

        // 视图解析器进行拼串：
        // classpath:/templates/ +返回值+  .html
        model.addAttribute("categorys",categoryEntities);
        return "index";
    }


    //index/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }


}