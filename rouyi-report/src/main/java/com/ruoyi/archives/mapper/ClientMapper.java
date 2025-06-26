package com.ruoyi.archives.mapper;


import com.ruoyi.archives.domain.ClientModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClientMapper
{
     List<ClientModel> selectListAll();
     Integer insertClient(ClientModel clientModel);
     Integer insertClientone(ClientModel clientModel);

     Integer deleteReportById(String id);

     ClientModel selectClientById(String id);

     List<ClientModel> selectByTj(ClientModel clientModel);

     //根据名字进行验证
     ClientModel selectClientByName(String name);

     //更新客户护具
     Integer updateClient(ClientModel clientModel);

     Integer updateStatusById(String id);

     //查询所有所属销售名下的客户数量
     List<ClientModel> selectAllGroupBySsxs();

     //各地区客户数量 不包含未设置地区名字客户
     List<ClientModel> selectAllByDqtj();

     //客户类型分析
     List<ClientModel> selectAllByType();

}
