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
		brandEntity.setDescript("哈哈1哈");
		brandEntity.setName("华为");
		brandService.save(brandEntity);
		System.out.println("保存成功");
	}

}
