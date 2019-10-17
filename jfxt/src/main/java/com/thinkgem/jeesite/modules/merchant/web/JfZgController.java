/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.service.JfXxService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
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
	 * 导出整改单数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("merchant:jfZg:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(JfZg jfZg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网元整改单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JfZg> page = jfZgService.findPage(new Page<JfZg>(request, response, -1), jfZg);
    		new ExportExcel("网元整改单", JfZg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant/jfZg/list?repage";
    }
	 /**
	 * 导入网元整改单数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("merchant:jfZg:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/merchant/jfZg/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<JfZg> list = ei.getDataList(JfZg.class);
			for (JfZg jfZg : list){                
				try{
					//jfZgService.saveImport(jfZg);
					jfZgService.save(jfZg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>网元整改单 "+jfZg.getJfName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");  //告诉你出错原因
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>网元信息 "+jfZg.getJfName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入网元整改单失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant/jfZg/list?repage";
    }
	
	/**
	 * 下载导入网元整改单模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("merchant:jfZg:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网元整改单导入模板.xlsx";
    		List<JfZg> list = Lists.newArrayList(); 
    		JfZg jfZg = new JfZg();
    		    jfZg.setJfName("11");
    		list.add(jfZg);
    		new ExportExcel("网元整改单数据", JfZg.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant:jfZg/list?repage";

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
		Map<String,String> userMap= new HashMap<String,String>();
		if(request!=null){
			userMap = this.findJfxxByLoginName(request);
		}
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		long count =page.getCount();
		model.addAttribute("count", count);
		return "modules/merchant/jfZgList";
	}

	
	@RequiresPermissions("merchant:jfZg:view")
	@RequestMapping(value = "form")
	public String form(JfZg jfZg, Model model,HttpServletRequest request) {model.addAttribute("jfZg", jfZg);
		Map<String,String> userMap= new HashMap<String,String>();
		if(request!=null){
			userMap = this.findJfxxByLoginName(request);
		}
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