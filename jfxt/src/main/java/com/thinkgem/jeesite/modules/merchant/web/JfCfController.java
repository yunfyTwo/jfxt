/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserveda.
 */
package com.thinkgem.jeesite.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.entity.JfZg;
import com.thinkgem.jeesite.modules.merchant.service.JfXxService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.merchant.entity.JfCf;
import com.thinkgem.jeesite.modules.merchant.service.JfCfService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private OfficeService officeService;
	

	@Autowired
	private SystemService systemService;

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
	/**
	 * 导出处罚单数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("merchant:jfCf:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(JfCf jfCf, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "处罚单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JfCf> page = jfCfService.findPage(new Page<JfCf>(request, response, -1), jfCf);
    		new ExportExcel("处罚单", JfCf.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant/jfCf/list?repage";
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
	
	
	@RequiresPermissions("merchant:jfCf:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfCf jfCf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JfCf> page = jfCfService.findPage(new Page<JfCf>(request, response), jfCf); 
		model.addAttribute("page", page);
		Map<String,String> userMap =new HashMap<String,String>();
		if(request!=null ){
			userMap = this.findJfxxByLoginName(request);//管理员此处为空
		}
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfCfList";
	}

	@RequiresPermissions("merchant:jfCf:view")
	@RequestMapping(value = "form")
	public String form(JfCf jfCf, Model model, HttpServletRequest request) {
		model.addAttribute("jfCf", jfCf);
		Map<String,String> userMap =new HashMap<String,String>();
		if(request!=null ){
			userMap = this.findJfxxByLoginName(request);//管理员此处为空
		}
		if( StringUtils.isBlank(jfCf.getKzzd3())){
			model.addAttribute("loginName", userMap.get("name"));
		}else{
			model.addAttribute("loginName", jfCf.getKzzd3());
		}
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfCfForm";
	}

	@RequiresPermissions("merchant:jfCf:edit")
	@RequestMapping(value = "save")
	public String save(JfCf jfCf, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfCf)){
			return form(jfCf, model,null);
		}
		jfCf.setCreateDate(new Date());
		jfCf.setUpdateDate(new Date());
		
		if (jfCf.getCfxczp().startsWith("|")) {
			jfCf.setCfxczp(jfCf.getCfxczp().substring(1, jfCf.getCfxczp().length()));
		}

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