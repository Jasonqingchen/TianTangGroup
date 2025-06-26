package com.ruoyi.archives.mapper;


import com.ruoyi.archives.domain.FileModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper
{
    /**
     * add file
     * @param fModel
     * @return
     */
    Integer insertFile(FileModel fModel);
    /**
     * 列表
     */
    List<FileModel> selectAllByUserIdAndDid(FileModel fileModel);

    /**
     * 删除
     */
    Integer deleteFilesById(String id);

    /**
     * 统计本部门人员
     */
    List<FileModel> selectAllCou(String id);

    /**
     * 根据 id 查询对象 文件对象
     * @param id
     * @return
     */
    FileModel selectFileById(String id);
}
