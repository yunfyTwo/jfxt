/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.merchant.entity.JfZg;

/**
 * 整改DAO接口
 * @author wangdandan
 * @version 2019-04-11
 */
@MyBatisDao
public interface JfZgDao extends CrudDao<JfZg> {

	void saveImport(JfZg jfZg);

	
}