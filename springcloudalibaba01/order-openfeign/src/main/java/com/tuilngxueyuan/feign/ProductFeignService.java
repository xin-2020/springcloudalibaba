package com.tuilngxueyuan.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * @date 2021年10月06日14:32
 */
@FeignClient(name = "product-service",path = "/product")
public interface ProductFeignService {

    @RequestMapping(" /{id}")
    public String get(@PathVariable("id") Integer id);
}
/**
*@Author
*@date
 *
 * @RequestMapping("/product")
 * @RestController
 * public class Productcontroller {
 *
 *     @Value("${server.port}")
 *     String port;
 *
 *     @RequestMapping("/{id}")
 *     public String get(@PathVariable Integer id){
 *         System.out.println("查询商品 : "+id);
 *         return "查询商品 "+id+":"+port;
 *     }
 * }
*/