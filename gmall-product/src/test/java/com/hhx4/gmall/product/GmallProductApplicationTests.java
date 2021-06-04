package com.hhx4.gmall.product;

import com.hhx4.gmall.product.entity.BrandEntity;
import com.hhx4.gmall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmallProductApplicationTests {
	@Autowired
	private BrandService brandService;

	@Test
	void contextLoads() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setDescript("描述1号");
		brandEntity.setName("华为");
		brandService.save(brandEntity);
		System.out.println("保存成功");
	}
	@Test
	void contextLoads2() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setBrandId(1L);
		brandEntity.setDescript("修改");
		brandService.updateById(brandEntity);
		System.out.println("修改成功");
	}

}
