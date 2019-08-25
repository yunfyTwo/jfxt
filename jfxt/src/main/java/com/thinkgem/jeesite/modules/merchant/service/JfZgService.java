/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.merchant.entity.JfCf;
import com.thinkgem.jeesite.modules.merchant.entity.JfXjgc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.merchant.entity.JfZg;
import com.thinkgem.jeesite.modules.merchant.dao.JfZgDao;

/**
 * 整改Service
 * @author wangdandan
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class JfZgService extends CrudService<JfZgDao, JfZg> {

	public JfZg get(String id) {
		return super.get(id);
	}
	
	public List<JfZg> findList(JfZg jfZg) {
		jfZg.getSqlMap().put("dsf",dataScopeFilter(jfZg.getCurrentUser(), "o", "u"));
		List<JfZg> jfZgList=super.findList(jfZg);
		if(jfZgList!=null && jfZgList.size()>0){
			for(int i=0;i<jfZgList.size();i++){
				JfZg xx=jfZgList.get(i);
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
		return jfZgList;
	}
	
	public Page<JfZg> findPage(Page<JfZg> page, JfZg jfZg) {
		jfZg.getSqlMap().put("dsf",dataScopeFilter(jfZg.getCurrentUser(), "o", "u"));
		Page<JfZg> pageList = super.findPage(page, jfZg);
		if(pageList!=null && pageList.getList()!=null && pageList.getList().size()>0){
			for(int i=0;i<pageList.getList().size();i++){
				JfZg xx=pageList.getList().get(i);
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
	public void save(JfZg jfZg) {
		super.save(jfZg);
	}
	
	@Transactional(readOnly = false)
	public void delete(JfZg jfZg) {
		super.delete(jfZg);
	}
	
	/*
	 * 巡检过程
	 */
	public List<JfZg> findJfxjgcList(JfXjgc jfxjgc) {
		// TODO Auto-generated method stub
		return null;
	}
	
}