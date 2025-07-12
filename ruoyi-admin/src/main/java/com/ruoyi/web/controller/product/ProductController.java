package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.domain.ProductModel;
import com.ruoyi.product.mapper.ProductMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * about product
 * liuqingchen
 */
@RequiresRoles(value = {"admin", "product"}, logical = Logical.OR)
@RequestMapping("/product/list")
@Controller
public class ProductController  extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ProductMapper proMapper;



    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private String prefix = "product/list";

    /**
     * table search
     */
    @RequiresPermissions("product:list:view")
    @GetMapping("")
    public String tz()
    {
        return prefix + "/productlist";
    }

    /**
     * search data for list
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProductModel proModel)
    {
        startPage();
        return getDataTable(proMapper.selectAll(proModel));
    }

    /**
     * return select data
     */
    @GetMapping("/productsellist")
    @ResponseBody
    public List productsellist(ProductModel proModel)
    {
        List list = new ArrayList();
        List<ProductModel> productModels = proMapper.selectAll(null);
        productModels.forEach(pro->{
            list.add(pro.getProductname().toString()+"型号："+pro.getPsize());
        });
        return list;
    }



    /**
     * delete product
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        String [] idss = Convert.toStrArray(ids);
        for (String id : idss)
        {
            proMapper.deleteProById(id);
        }
        return AjaxResult.success();
    }


    /**
     *  new add product information
     */
    @GetMapping("/add")
    public String addtz()
    {
        return prefix + "/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated ProductModel proModel)
    {
        Integer integer=0;
        ProductModel pro = proMapper.selectClientByNameAndSize(proModel);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(StringUtils.isNull(pro)){
            SysUser use = ShiroUtils.getSysUser();
            SysDept dept = deptService.selectDeptById(use.getDeptId());//department  info
            proModel.setId(String.valueOf(Math.random()*999999999));
            proModel.setDate(formatter.format(new Date()));
            proModel.setRkname(use.getUserName());
            proModel.setDepname(dept.getDeptName());
            proModel.setUserid(use.getUserId().toString());
            integer = proMapper.insertPro(proModel);
        }

        return toAjax(integer);
    }

    /**
     * update product information
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        mmap.put("proModel", proMapper.selectProById(id));
        return prefix + "/edit";
    }


    /**
     * update save product
     */
    @Log(title = "产品修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProductModel proModel)
    {
        return AjaxResult.success(proMapper.updateProduct(proModel));
    }

    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ProductModel> util = new ExcelUtil<ProductModel>(ProductModel.class);
        List<ProductModel> pModel = util.importExcel(file.getInputStream());
        String message = this.importClient(pModel, updateSupport);
        return AjaxResult.success(message);
    }
    // 下载模板
    @GetMapping("/importTemplate")
    @ResponseBody
    public void importTemplate(Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        ExcelUtil<ProductModel> util = new ExcelUtil<ProductModel>(ProductModel.class);
        util.importTemplateExcel("入库模版数据");
    }

    /**
     * 导入数据
     *
     * @param pModel  入库内容
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importClient(List<ProductModel> pModel, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(pModel) || pModel.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        SysUser use = ShiroUtils.getSysUser();

        SysDept dept = deptService.selectDeptById(use.getDeptId());//department  info
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (ProductModel item : pModel)
        {
            try
            {
                ProductModel ps = null;
                if(item.getPcode().isEmpty()){
                    // 验证是否存在这个数据 By 物料编码-pcode
                     ps = proMapper.selectClientByPcode(item.getPcode());
                }
                if (StringUtils.isNull(ps))
                {
                    item.setId(String.valueOf(Math.random()*999999999));
                    item.setDate(formatter.format(new Date()));
                    item.setUserid(use.getUserId().toString());
                    item.setRkname(use.getUserName());
                    item.setDepname(dept.getDeptName());
                    item.setDid(dept.getDeptId().toString());
                    proMapper.insertPro(item);
                    successNum++;
                    successMsg.append("<br/>" + failureNum +"导入成功");
                }
                else if (isUpdateSupport)
                {
                    item.setId(ps.getId());
                    item.setDate(formatter.format(new Date()));
                    item.setUserid(use.getUserId().toString());
                    item.setRkname(use.getUserName());
                    item.setDepname(dept.getDeptName());
                    item.setDid(dept.getDeptId().toString());
                    //更新用户
                    proMapper.updateProduct(item);
                    successNum++;
                    successMsg.append("<br/> 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;

                String msg = "<br/>" + failureNum + "、账号  导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
