package com.ruoyi.report.mapper;



import com.ruoyi.report.domain.TargetModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TargetMapper
{

    /**
     *  查询
     * @param tModel 实体类
     * @return
     */
    List<TargetModel> selectTargetModel(TargetModel tModel);


    /**
     * delete target bu id
     */

    Integer deleteTargetById(String id);

    /**
     * update target model
     */
    Integer updateTarget(TargetModel tModel);
}
