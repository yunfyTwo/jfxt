package com.thinkgem.jeesite.modules.merchant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.merchant.dao.JfXlZgDao;
import com.thinkgem.jeesite.modules.merchant.entity.JfXlZg;

@Service
@Transactional(readOnly = true)
public class JfXlZgService extends CrudService<JfXlZgDao, JfXlZg> {
	
	public JfXlZg get(String id) {
		return super.get(id);
	}
	
	public List<JfXlZg> findList(JfXlZg jfXlZg) {
		jfXlZg.getSqlMap().put("dsf",dataScopeFilter(jfXlZg.getCurrentUser(), "o", "u"));
		List<JfXlZg> JfXlZgList=super.findList(jfXlZg);
		if(JfXlZgList!=null && JfXlZgList.size()>0){
			for(int i=0;i<JfXlZgList.size();i++){
				JfXlZg xx=JfXlZgList.get(i);
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
		return JfXlZgList;
	}
	
	public Page<JfXlZg> findPage(Page<JfXlZg> page, JfXlZg jfXlZg) {
		jfXlZg.getSqlMap().put("dsf",dataScopeFilter(jfXlZg.getCurrentUser(), "o", "u"));
		Page<JfXlZg> pageList = super.findPage(page, jfXlZg);
		if(pageList!=null && pageList.getList()!=null && pageList.getList().size()>0){
			for(int i=0;i<pageList.getList().size();i++){
				JfXlZg xx=pageList.getList().get(i);
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
	public void save(JfXlZg jfXlZg) {
		super.save(jfXlZg);
	}
	
	@Transactional(readOnly = false)
	public void delete(JfXlZg jfXlZg) {
		super.delete(jfXlZg);
	}

}
