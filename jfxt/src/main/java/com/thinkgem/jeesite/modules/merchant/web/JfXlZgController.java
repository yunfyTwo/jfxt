package com.thinkgem.jeesite.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.entity.JfZg;
import com.thinkgem.jeesite.modules.merchant.service.JfXxService;

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
import com.thinkgem.jeesite.modules.merchant.entity.JfXlZg;
import com.thinkgem.jeesite.modules.merchant.service.JfXlZgService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/merchant/jfXlZg")
public class JfXlZgController extends BaseController {

	@Autowired
	private JfXlZgService jfXlZgService;

	@Autowired
	private JfXxService jfXxService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private SystemService systemService;
	


	@ModelAttribute
	public JfXlZg get(@RequestParam(required=false) String id) {
		JfXlZg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jfXlZgService.get(id);
		}
		if (entity == null){
			entity = new JfXlZg();
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
	@RequiresPermissions("merchant:jfXlZg:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(JfXlZg jfXlZg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "线路整改单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JfXlZg> page = jfXlZgService.findPage(new Page<JfXlZg>(request, response, -1), jfXlZg);
    		new ExportExcel("线路整改单", JfXlZg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/merchant/jfXlZg/list?repage";
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
	
	@RequiresPermissions("merchant:jfXlZg:view")
	@RequestMapping(value = {"list", ""})
	public String list(JfXlZg jfXlZg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JfXlZg> page = jfXlZgService.findPage(new Page<JfXlZg>(request, response), jfXlZg); 
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
		return "modules/merchant/jfXlZgList";
	}

	
	@RequiresPermissions("merchant:jfXlZg:view")
	@RequestMapping(value = "form")
	public String form(JfXlZg jfXlZg, Model model,HttpServletRequest request) {model.addAttribute("JfXlZg", jfXlZg);
		Map<String,String> userMap= new HashMap<String,String>();
		if(request!=null){
			userMap = this.findJfxxByLoginName(request);
		}
		if( StringUtils.isBlank(jfXlZg.getKzzd4())){
			model.addAttribute("loginName", userMap.get("name"));
		}else{
			model.addAttribute("loginName", jfXlZg.getKzzd4());
		}
		JfXx jfXx= new JfXx();
		jfXx.setJfjj(userMap.get("jfjj"));
		
		List<JfXx> jfXxList=jfXxService.findList(jfXx);
		model.addAttribute("jfXxList", jfXxList);
		return "modules/merchant/jfXlZgForm";
	}

	@RequiresPermissions("merchant:jfXlZg:edit")
	@RequestMapping(value = "save")
	public String save(JfXlZg jfXlZg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jfXlZg)){
			return form(jfXlZg, model,null );
		}
		
		if (jfXlZg.getCfxczp().startsWith("|")) {
			jfXlZg.setCfxczp(jfXlZg.getCfxczp().substring(1, jfXlZg.getCfxczp().length()));
		}
		
		jfXlZgService.save(jfXlZg);
		addMessage(redirectAttributes, "保存整改成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXlZg/?repage";
	}
	
	@RequiresPermissions("merchant:JfXlZg:edit")
	@RequestMapping(value = "delete")
	public String delete(JfXlZg jfXlZg, RedirectAttributes redirectAttributes) {
		jfXlZgService.delete(jfXlZg);
		addMessage(redirectAttributes, "删除整改成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/jfXlZg/?repage";
	}

}