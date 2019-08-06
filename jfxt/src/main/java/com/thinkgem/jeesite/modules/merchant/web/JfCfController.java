/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserveda.
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
import com.thinkgem.jeesite.modules.merchant.entity.JfCf;
import com.thinkgem.jeesite.modules.merchant.service.JfCfService;

import java.util.Date;
import java.util.List;

/**
 * 处罚Controller
 * @author wangdandan
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/jfCf")
public class JfCfController extends BaseController {

	@Autowired
	private JfCfService jfCfService;

	@Autowired
	private JfXxService jfXxService;

	@ModelAttribute
	public JfCf get(@RequestParam(required=false) String id) {
		JfCf entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jfCfService.get(id);
		}
		if (entity == null){
			entity = new JfCf();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:jfCf:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfCf jfCf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JfCf> page = jfCfService.findPage(new Page<JfCf>(request, response), jfCf); 
		model.addAttribute("page", page);

		List<JfXx> jfXxList=jfXxService.findList(new JfXx());
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfCfList";
	}

	@RequiresPermissions("merchant:jfCf:view")
	@RequestMapping(value = "form")
	public String form(JfCf jfCf, Model model) {
		model.addAttribute("jfCf", jfCf);

		List<JfXx> jfXxList=jfXxService.findList(new JfXx());
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfCfForm";
	}

	@RequiresPermissions("merchant:jfCf:edit")
	@RequestMapping(value = "save")
	public String save(JfCf jfCf, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfCf)){
			return form(jfCf, model);
		}
		jfCf.setCreateDate(new Date());
		jfCf.setUpdateDate(new Date());



		jfCfService.save(jfCf);
		addMessage(redirectAttributes, "保存处罚成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfCf/?repage";
	}
	
	@RequiresPermissions("merchant:jfCf:edit")
	@RequestMapping(value = "delete")
	public String delete(JfCf jfCf, RedirectAttributes redirectAttributes) {
		jfCfService.delete(jfCf);
		addMessage(redirectAttributes, "删除处罚成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfCf/?repage";
	}

}