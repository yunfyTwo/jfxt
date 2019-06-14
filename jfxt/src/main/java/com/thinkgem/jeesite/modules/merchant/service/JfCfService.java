/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.merchant.entity.JfCf;
import com.thinkgem.jeesite.modules.merchant.dao.JfCfDao;

/**
 * 处罚Service
 * @author wangdandan
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class JfCfService extends CrudService<JfCfDao, JfCf> {

	public JfCf get(String id) {
		return super.get(id);
	}
	
	public List<JfCf> findList(JfCf jfCf) {
		jfCf.getSqlMap().put("dsf",dataScopeFilter(jfCf.getCurrentUser(), "o", "u"));
		List<JfCf> jfCfList=super.findList(jfCf);
		if(jfCfList!=null && jfCfList.size()>0){
			for(int i=0;i<jfCfList.size();i++){
				JfCf xx=jfCfList.get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getCfxczp())){
					String[] imgs=xx.getCfxczp().split("\\|");
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

		return jfCfList;
	}
	
	public Page<JfCf> findPage(Page<JfCf> page, JfCf jfCf) {
		jfCf.getSqlMap().put("dsf",dataScopeFilter(jfCf.getCurrentUser(), "o", "u"));
		Page<JfCf> pageList = super.findPage(page, jfCf);
		if(pageList!=null && pageList.getList()!=null && pageList.getList().size()>0){
			for(int i=0;i<pageList.getList().size();i++){
				JfCf xx=pageList.getList().get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getCfxczp())){
					String[] imgs=xx.getCfxczp().split("\\|");
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
	public void save(JfCf jfCf) {
		super.save(jfCf);
	}
	
	@Transactional(readOnly = false)
	public void delete(JfCf jfCf) {
		super.delete(jfCf);
	}
	
}