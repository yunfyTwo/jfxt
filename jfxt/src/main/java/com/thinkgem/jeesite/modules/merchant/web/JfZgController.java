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
import com.thinkgem.jeesite.modules.merchant.entity.JfZg;
import com.thinkgem.jeesite.modules.merchant.service.JfZgService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整改Controller
 * @author wangdandan
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/jfZg")
public class JfZgController extends BaseController {

	@Autowired
	private JfZgService jfZgService;

	@Autowired
	private JfXxService jfXxService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private SystemService systemService;
	


	@ModelAttribute
	public JfZg get(@RequestParam(required=false) String id) {
		JfZg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jfZgService.get(id);
		}
		if (entity == null){
			entity = new JfZg();
		}
		return entity;
	}
	
	/**
	 * 通过登录名获取所属区域
	 * @param request
	 * @return
	 */
	public  Map<String,String> findJfxxByLoginName(HttpServletRequest request) {
		Map<String,String> userMap = new HashMap<String,String>();
		String loginName ="";
		if(null != request){
			loginName = (String) request.getSession().getAttribute("loginName");
		}
		List<Office> list = officeService.findByLoginName(loginName);
		String jfjj ="";
		if (!list.isEmpty()) {
			String jfjjName = list.get(0).getName();
			if(jfjjName.contains(UserUtils.NETWORK_OPERATIONS_BRANCH)) {
				jfjj = jfjjName;
			}
		}
		User user = systemService.getUserByLoginName(loginName);
		String name = user.getName();//登录姓名
		userMap.put("jfjj", jfjj);
		userMap.put("name", name);
		userMap.put("userMap", "userMap");
		return userMap;

	}
	
	@RequiresPermissions("merchant:jfZg:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfZg jfZg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JfZg> page = jfZgService.findPage(new Page<JfZg>(request, response), jfZg); 
		model.addAttribute("page", page);
		
		Map<String,String> userMap = this.findJfxxByLoginName(request);
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfZgList";
	}

	
	@RequiresPermissions("merchant:jfZg:view")
	@RequestMapping(value = "form")
	public String form(JfZg jfZg, Model model,HttpServletRequest request) {model.addAttribute("jfZg", jfZg);
		Map<String,String> userMap = this.findJfxxByLoginName(request);
		if( StringUtils.isBlank(jfZg.getKzzd4())){
			model.addAttribute("loginName", userMap.get("name"));
		}else{
			model.addAttribute("loginName", jfZg.getKzzd4());
		}
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfZgForm";
	}

	@RequiresPermissions("merchant:jfZg:edit")
	@RequestMapping(value = "save")
	public String save(JfZg jfZg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfZg)){
			return form(jfZg, model,null );
		}
		
		if (jfZg.getCfxczp().startsWith("|")) {
			jfZg.setCfxczp(jfZg.getCfxczp().substring(1, jfZg.getCfxczp().length()));
		}
		
		jfZgService.save(jfZg);
		addMessage(redirectAttributes, "保存整改成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfZg/?repage";
	}
	
	@RequiresPermissions("merchant:jfZg:edit")
	@RequestMapping(value = "delete")
	public String delete(JfZg jfZg, RedirectAttributes redirectAttributes) {
		jfZgService.delete(jfZg);
		addMessage(redirectAttributes, "删除整改成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfZg/?repage";
	}

}