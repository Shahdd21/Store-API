package com.microservices.store_api.feign;

import com.microservices.store_api.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "PRODUCTSERVICE", url = "${product.service.url}")
public interface ProductServiceClient {

    @PostMapping("/products/stores")
    ProductDTO getProductDTOForStore(@RequestParam(name = "product") Long id);

    @GetMapping("/products/stores")
    public List<ProductDTO> getAllProductsForStore();
}
