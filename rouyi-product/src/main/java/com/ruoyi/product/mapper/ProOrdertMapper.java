package com.ruoyi.product.mapper;


import com.ruoyi.product.domain.ProOrderModel;
import com.ruoyi.product.domain.ProductModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProOrdertMapper
{
   Integer insertProOrder(ProOrderModel pr);

   Integer delByOid(String id);
}
