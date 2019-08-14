/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.merchant.entity.JfXx;
import com.thinkgem.jeesite.modules.merchant.dao.JfXxDao;

/**
 * 网元信息Service
 * @author wangdandan
 * @version 2019-04-11
 */
@Service
@Transactional(readOnly = true)
public class JfXxService extends CrudService<JfXxDao, JfXx> {

	public JfXx get(String id) {
		return super.get(id);
	}
	
	public List<JfXx> findList(JfXx jfXx) {
		List<JfXx> jfXxList=super.findList(jfXx);
		if(jfXxList!=null && jfXxList.size()>0){
			for(int i=0;i<jfXxList.size();i++){
				JfXx xx=jfXxList.get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getJfxctp())){
					String[] imgs=xx.getJfxctp().split("\\|");
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
		return jfXxList;
	}
	
	public Page<JfXx> findPage(Page<JfXx> page, JfXx jfXx) {
		Page<JfXx> pageList = super.findPage(page, jfXx);
		if(pageList!=null && pageList.getList()!=null && pageList.getList().size()>0){
			for(int i=0;i<pageList.getList().size();i++){
				JfXx xx=pageList.getList().get(i);
				List<String> list=new ArrayList<String>();
				if(xx!=null && StringUtils.isNotBlank(xx.getJfxctp())){
					String[] imgs=xx.getJfxctp().split("\\|");
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

		return pageList;
	}
	
	@Transactional(readOnly = false)
	public void save(JfXx jfXx) {
		super.save(jfXx);
	}
	
	@Transactional(readOnly = false)
	public void delete(JfXx jfXx) {
		super.delete(jfXx);
	}
	/**
	 * 查询机房所属区域
	 */
	public List<JfXx> findJfjjList(JfXx jfXx) {
		List<JfXx> JfjjList=super.findJfjjList(jfXx);
		return JfjjList;
	}
	/**
	 * 网元属性
	 * @param jfXx
	 * @return
	 */
	public List<JfXx> findJfwzList(JfXx jfXx) {
		List<JfXx> JfwzList=super.findJfwzList(jfXx);
		return JfwzList;
	}
}