/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
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
	
	/**
	 * 导出网元列表数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("merchant:jfXx:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(JfXx jfXx, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网元列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JfXx> page = jfXxService.findPage(new Page<JfXx>(request, response, -1), jfXx);
    		new ExportExcel("网元列表", JfXx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant/jfXx/list?repage";
    }
	 /**
		 * 导入网元信息数据
		 * @param file
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("merchant:jfXx:edit")
	    @RequestMapping(value = "import", method=RequestMethod.POST)
	    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
			if(Global.isDemoMode()){
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/merchant/jfXx/?repage";
			}
			try {
				int successNum = 0;
				int failureNum = 0;
				StringBuilder failureMsg = new StringBuilder();
				ImportExcel ei = new ImportExcel(file, 1, 0);
				List<JfXx> list = ei.getDataList(JfXx.class);
				for (JfXx jfXx : list){                
					try{
						jfXxService.save(jfXx);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>网元信息 "+jfXx.getName()+" 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");  //告诉你出错原因
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>网元信息 "+jfXx.getName()+" 导入失败："+ex.getMessage());
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条信息，导入信息如下：");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条信息"+failureMsg);
			} catch (Exception e) {
				addMessage(redirectAttributes, "导入网元信息失败！失败信息："+e.getMessage());
			}
			return "redirect:" + adminPath + "/merchant/jfXx/list?repage";
	    }
		/**
		 * 下载导入网元信息数据模板
		 * @param response
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("merchant:jfXx:view")
	    @RequestMapping(value = "import/template")
	    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
			try {
	            String fileName = "网元信息导入模板.xlsx";
	    		List<JfXx> list = Lists.newArrayList(); 
	    		JfXx jfXx = new JfXx();
	    		    jfXx.setName("XX机房或线路");
	    		    jfXx.setJfwz("线路");
	                jfXx.setJfbh("1111111");
	                jfXx.setJfjj("旺苍网络运营分局");
	                jfXx.setRemarks("请删除这条数据");
	    		list.add(jfXx);
	    		new ExportExcel("网元信息数据", JfXx.class, 2).setDataList(list).write(response, fileName).dispose();
	    		return null;
			} catch (Exception e) {
				addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
			}
			return "redirect:" + adminPath + "/merchant:jfXx/list?repage";

		}
	/**
	 * 通过登录名获取所属区域
	 * @param request
	 * @return
	 */
	public  String findJfxxByLoginName(HttpServletRequest request) {
		String loginName = String.valueOf(request.getSession().getAttribute("loginName"));
		List<Office> list = officeService.findByLoginName(loginName);
		String jfjj ="";
		if (!list.isEmpty()) {
			String jfjjName = list.get(0).getName();
			if(jfjjName.contains(UserUtils.NETWORK_OPERATIONS_BRANCH)) {
				jfjj = jfjjName;
			}
		}
		return jfjj;

	}
	
	@RequiresPermissions("merchant:jfXx:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfXx jfXx, HttpServletRequest request, HttpServletResponse response, Model model) {
		String jfjj ="";
		if(request!=null ){
			jfjj = this.findJfxxByLoginName(request);//管理员此处为空
		}
		String initJfxx = jfXx.getJfjj();//查询条件
		if(StringUtils.isNotEmpty(initJfxx)&& StringUtils.isEmpty(jfjj)){
			jfXx.setJfjj(initJfxx);
		}else{
			jfXx.setJfjj(jfjj);
		}
		Page<JfXx> page = jfXxService.findPage(new Page<JfXx>(request, response), jfXx); 
		model.addAttribute("page", page);
		long count =page.getCount();
		model.addAttribute("count", count);
		return "modules/merchant/jfXxList";
	}

	@RequiresPermissions("merchant:jfXx:view")
	@RequestMapping(value = "form")
	public String form(JfXx jfXx, Model model,HttpServletRequest request) {
		String jfjj = "";
		if(request!=null ){
			jfjj = this.findJfxxByLoginName(request);//管理员此处为空
		}
		jfXx.setJfjj(jfjj);
		model.addAttribute("jfXx", jfXx);
		//机房所属区域
		List<JfXx> jfjjList=jfXxService.findJfjjList(jfXx);
		if(!StringUtils.isEmpty(jfjj)){
			List<JfXx> jd  =  new ArrayList<JfXx>();
			jd.add(jfjjList.get(0));
			model.addAttribute("jfjjList", jd);
		}else{
			model.addAttribute("jfjjList", jfjjList);
		}
		return "modules/merchant/jfXxForm";
	}

	@RequiresPermissions("merchant:jfXx:edit")
	@RequestMapping(value = "save")
	public String save(JfXx jfXx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfXx)){
			return form(jfXx, model,null);
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