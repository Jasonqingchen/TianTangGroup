package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * about product out
 * liuqingchen
 */
@RequiresRoles(value = {"admin", "out"}, logical = Logical.OR)
@RequestMapping("/product/out")
@Controller
public class ProOutController   extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private String prefix = "product/out";

    /**
     * table search
     */
    @RequiresPermissions("product:out:view")
    @GetMapping("")
    public String tz()
    {
        return prefix + "/outlist";
    }
}
