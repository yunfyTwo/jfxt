/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.service.JfXxService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 网元信息Controller
 * @author wangdandan
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/jfXx")
public class JfXxController extends BaseController {

	@Autowired
	private JfXxService jfXxService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public JfXx get(@RequestParam(required=false) String id) {
		JfXx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jfXxService.get(id);
		}
		if (entity == null){
			entity = new JfXx();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:jfXx:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfXx jfXx, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String loginName = String.valueOf(request.getSession().getAttribute("loginName"));
		List<Office> list = officeService.findByLoginName(loginName);
		if (!list.isEmpty()) {
			String jfjjName = list.get(0).getName();
			if(jfjjName.contains(UserUtils.NETWORK_OPERATIONS_BRANCH)) {
				jfXx.setJfjj(list.get(0).getName());
			}
			
		}
		
		Page<JfXx> page = jfXxService.findPage(new Page<JfXx>(request, response), jfXx); 
		model.addAttribute("page", page);
		return "modules/merchant/jfXxList";
	}

	@RequiresPermissions("merchant:jfXx:view")
	@RequestMapping(value = "form")
	public String form(JfXx jfXx, Model model) {
		model.addAttribute("jfXx", jfXx);
		return "modules/merchant/jfXxForm";
	}

	@RequiresPermissions("merchant:jfXx:edit")
	@RequestMapping(value = "save")
	public String save(JfXx jfXx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfXx)){
			return form(jfXx, model);
		}
		jfXxService.save(jfXx);
		addMessage(redirectAttributes, "保存网元信息成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXx/?repage";
	}
	
	@RequiresPermissions("merchant:jfXx:edit")
	@RequestMapping(value = "delete")
	public String delete(JfXx jfXx, RedirectAttributes redirectAttributes) {
		jfXxService.delete(jfXx);
		addMessage(redirectAttributes, "删除网元信息成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXx/?repage";
	}

}