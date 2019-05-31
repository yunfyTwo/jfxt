/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.service.JfXxService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.merchant.entity.JfXjgc;
import com.thinkgem.jeesite.modules.merchant.service.JfXjgcService;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检过程Controller
 * @author wangdandan
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/jfXjgc")
public class JfXjgcController extends BaseController {

	@Autowired
	private JfXjgcService jfXjgcService;
	@Autowired
	private JfXxService jfXxService;
	
	@ModelAttribute
	public JfXjgc get(@RequestParam(required=false) String id) {
		JfXjgc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jfXjgcService.get(id);
		}
		if (entity == null){
			entity = new JfXjgc();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:jfXjgc:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfXjgc jfXjgc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JfXjgc> page = jfXjgcService.findPage(new Page<JfXjgc>(request, response), jfXjgc);
		model.addAttribute("page", page);
		List<JfXx> jfXxList=jfXxService.findList(new JfXx());
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfXjgcList";
	}

	@RequiresPermissions("merchant:jfXjgc:view")
	@RequestMapping(value = "form")
	public String form(JfXjgc jfXjgc, Model model) {
		model.addAttribute("jfXjgc", jfXjgc);
		List<JfXx> jfXxList=jfXxService.findList(new JfXx());
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfXjgcForm";
	}

	@RequiresPermissions("merchant:jfXjgc:edit")
	@RequestMapping(value = "save")
	public String save(JfXjgc jfXjgc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfXjgc)){
			return form(jfXjgc, model);
		}
		if(StringUtils.isBlank(jfXjgc.getXjsftg())){
			jfXjgc.setXjsftg("0");
		}
		jfXjgcService.save(jfXjgc);
		addMessage(redirectAttributes, "保存巡检过程成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXjgc/?repage";
	}
	
	@RequiresPermissions("merchant:jfXjgc:edit")
	@RequestMapping(value = "delete")
	public String delete(JfXjgc jfXjgc, RedirectAttributes redirectAttributes) {
		jfXjgcService.delete(jfXjgc);
		addMessage(redirectAttributes, "删除巡检过程成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXjgc/?repage";
	}

}