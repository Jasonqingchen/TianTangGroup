package com.ruoyi.web.controller.product;

import com.ruoyi.archives.domain.ClientModel;
import com.ruoyi.archives.domain.FileModel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
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

import java.text.SimpleDateFormat;
import java.util.*;

import static com.ruoyi.archives.util.FileUploadUtils.removeFile;
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
        List<Map<String,String>> list = new ArrayList();
        List<ProductModel> productModels = proMapper.selectAll(null);
        productModels.forEach(pro->{
            Map map = new HashMap();
            map.put("value",pro.getId());
            map.put("productname",pro.getProductname());
            list.add(map);
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
}
