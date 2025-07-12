package com.ruoyi.web.controller.archives;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.ruoyi.archives.domain.FileModel;
import com.ruoyi.archives.mapper.FileMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.report.domain.OrdersModel;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import io.minio.*;
import io.minio.errors.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ruoyi.archives.config.MinioConfig.bucketName;
import static com.ruoyi.archives.util.FileUploadUtils.removeFile;
import static com.ruoyi.archives.util.FileUploadUtils.uploadMinio;


/**
 * About archives
 */
@RequiresRoles(value = {"admin", "archives"}, logical = Logical.OR)
@Controller
@RequestMapping("/archives/listdata")
public class ArchivesController extends BaseController {


    @Autowired
    private FileMapper fMapper;
    @Autowired
    private ISysDeptService deptService;



    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private String prefix = "archives/listdata";
    private final static Map<String, OrdersModel> clients = new LinkedHashMap<String, OrdersModel>();
    private RuoYiConfig Global;
    @Resource
    private MinioClient minioClient;




    /**
     * 表格
     */
    @RequiresPermissions("archives:listdata:view")
    @GetMapping("")
    public String tz(ModelMap mmap)
    {
        Subject subject = ShiroUtils.getSubject();
        if (subject.hasRole("don"))
        {
            mmap.put("rol",1);
        } else{
            mmap.put("rol",0);
        }
        return prefix + "/archiveslist";
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FileModel fileModel)
    {
        startPage();
        SysUser use = ShiroUtils.getSysUser();
        fileModel.setDid(use.getDeptId().toString());
       // fileModel.setUserid(use.getUserId().toString());
        return getDataTable(fMapper.selectAllByUserIdAndDid(fileModel));
    }

    /**
     * 统计该部门人员上传次数
     */
    @Log(title = "销售热力图", businessType = BusinessType.UPDATE)
    @PostMapping("/updtj")
    @ResponseBody
    public List updtj()
    {
        List list = new ArrayList();

        List list1 = new ArrayList<>();
        List list2 = new ArrayList<>();
        SysUser use = ShiroUtils.getSysUser();
        for (FileModel fModel : fMapper.selectAllCou(use.getDeptId().toString())) {
            list1.add(fModel.getUploadname());
            list2.add(fModel.getCou());
        }
        list.add(list1);
        list.add(list2);
        return list;
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        String [] idss = Convert.toStrArray(ids);
        for (String id : idss)
        {
            FileModel fileModels = fMapper.selectFileById(id);
            removeFile(bucketName,fileModels.getFilename());
            fMapper.deleteFilesById(id);
        }
        return AjaxResult.success();
    }

    public boolean doesObjectExist(String bucketName, String objectName) {
        try {
            boolean found = false;
            try {
                found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            if (found) {
                ObjectStat result = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
                return result != null;  // 如果对象存在，返回true；否则返回false。
            } else {
                return false; // 如果桶不存在，则假定对象也不存在。
            }
        } catch (MinioException | Error e) {
            System.out.println("Error occurred: " + e);
            return false; // 出现错误时假定对象不存在。
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 上传
     *
     * @param file 文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> csad (@RequestParam("file") MultipartFile file, FileModel fileModel) throws IOException, MinioException {
        //check fileName is empty ?
        boolean b = doesObjectExist(bucketName, file.getOriginalFilename());
        if (b){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(file.getOriginalFilename()+"该文件重复，请更改名称 继续上传");
        }
        String s = uploadMinio(file);
        if(!"".equals(s) || null!=s){
            SysUser use = ShiroUtils.getSysUser();

            SysDept dept = deptService.selectDeptById(use.getDeptId());//department  info
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            fileModel.setFilename(file.getOriginalFilename());
            fileModel.setUploadname(use.getLoginName());
            fileModel.setDate(formatter.format(new Date()));
            fileModel.setFileurl(s);
            fileModel.setBz("文件路径已隐藏");
            fileModel.setUserid(use.getUserId().toString());
            fileModel.setDid(dept.getDeptId().toString());
            fileModel.setDname(dept.getDeptName());
            fileModel.setId(String.valueOf(Math.random()*999999999));

            Integer integer = fMapper.insertFile(fileModel);
            return ResponseEntity.status(HttpStatus.OK).body("上传成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败");
        }

    }

    /**
     * 根据文件路径得到预览文件绝对地址
     *
     * @param minioClient
     * @param fileName    文件路径
     * @return
     */
    public String getPreviewFileUrl(MinioClient minioClient, String fileName) {
        try {
            return minioClient.presignedGetObject(bucketName,fileName);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 文件预览
     * liuiqngchen
     */
    @PostMapping("/showfile")
    @ResponseBody
    public AjaxResult showfile(String id, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String name = fMapper.selectFileById(id).getFilename();
        String previewUrl = getPreviewFileUrl(minioClient,name);
        return AjaxResult.warn(previewUrl);

    }
}
