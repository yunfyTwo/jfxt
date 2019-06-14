/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.service;

import java.util.ArrayList;
import java.util.List;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.merchant.entity.JfCf;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.merchant.entity.JfXjgc;
import com.thinkgem.jeesite.modules.merchant.dao.JfXjgcDao;

/**
 * 巡检过程Service
 * @author wangdandan
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class JfXjgcService extends CrudService<JfXjgcDao, JfXjgc> {

	public JfXjgc get(String id) {
		return super.get(id);
	}
	
	public List<JfXjgc> findList(JfXjgc jfXjgc) {
		jfXjgc.getSqlMap().put("dsf",dataScopeFilter(jfXjgc.getCurrentUser(), "o", "u"));
		List<JfXjgc> jfXjgcList=super.findList(jfXjgc);
		if(jfXjgcList!=null && jfXjgcList.size()>0){
			for(int i=0;i<jfXjgcList.size();i++){
				JfXjgc xx=jfXjgcList.get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getXczp())){
					String[] imgs=xx.getXczp().split("\\|");
					if(imgs!=null && imgs.length>0){
						for(int j=0;j<imgs.length;j++){
							String sj=imgs[j];
							if(StringUtils.isNotBlank(sj)){
								list.add(sj);
							}
						}
						xx.setXctps(list);
					}
				}
			}
		}
		return jfXjgcList;
	}
	
	public Page<JfXjgc> findPage(Page<JfXjgc> page, JfXjgc jfXjgc) {
		jfXjgc.getSqlMap().put("dsf",dataScopeFilter(jfXjgc.getCurrentUser(), "o", "u"));
		Page<JfXjgc> pageList = super.findPage(page, jfXjgc);
		if(pageList!=null && pageList.getList()!=null && pageList.getList().size()>0){
			for(int i=0;i<pageList.getList().size();i++){
				JfXjgc xx=pageList.getList().get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getXczp())){
					String[] imgs=xx.getXczp().split("\\|");
					if(imgs!=null && imgs.length>0){
						for(int j=0;j<imgs.length;j++){
							String sj=imgs[j];
							if(StringUtils.isNotBlank(sj)){
							StringBuffer img=new StringBuffer(sj);
							img.insert(0,"/jfxt");
								list.add(img.toString());
							}
						}
						xx.setXctps(list);
					}
				}
			}
		}
		return pageList;
	}
	
	@Transactional(readOnly = false)
	public void save(JfXjgc jfXjgc) {
		super.save(jfXjgc);
	}
	
	@Transactional(readOnly = false)
	public void delete(JfXjgc jfXjgc) {
		super.delete(jfXjgc);
	}
	
}