package com.ruoyi.archives.mapper;



import com.ruoyi.archives.domain.ClientFollowModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ClientFollowMapper
{
    /**
     * 跟进客户时候的插入方法
     * @param fModel
     * @return
     */
    Integer insertClientFollow(ClientFollowModel fModel);

    /**
     * 根据客户 id 查询跟进详情
     * @param id
     * @return
     */
    List<ClientFollowModel> selectClientFollowById(String id);

    /**
     * 跟进表中的每个员工的跟进次数
     */
    List<ClientFollowModel> selectAllGroupBySsxs();

}
