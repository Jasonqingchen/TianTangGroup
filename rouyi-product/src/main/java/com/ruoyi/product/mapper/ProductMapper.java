package com.ruoyi.product.mapper;


import com.ruoyi.product.domain.ProductModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper
{
    /**
     * select  All
     * @return
     */
    List<ProductModel> selectAll(ProductModel proModel);

    /**
     * delete pro By id
     * @param id
     * @return
     */
    Integer deleteProById(String id);

    /**
     * By product name and size search product list
     * @param proModel
     * @return
     */
    ProductModel selectClientByNameAndSize(ProductModel proModel);

    /**
     *  insert product
     */
    Integer insertPro(ProductModel proModel);

    /**
     * By proid select product information
     * @param id
     * @return
     */
    ProductModel selectProById(String id);

    /**
     * uodate prod information
     * @param proModel
     * @return
     */
    Integer updateProduct(ProductModel proModel);

}
