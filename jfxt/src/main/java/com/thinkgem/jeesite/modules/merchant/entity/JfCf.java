/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 处罚Entity
 * @author wangdandan
 * @version 2019-04-11
 */
public class JfCf extends DataEntity<JfCf> {
	
	private static final long serialVersionUID = 1L;
	private JfXx cfjf;		// 处罚机房
	private String cftzd;		// 处罚通知单号
	private Date cfrq;		// 日期
	private String cfdx;		// 处罚对象
	private String cftd;		// 处罚梯度
	private String cfqxyyz;		// 处罚权限拥有者
	private String cfyyms;		// 处罚原因描述
	private String cfxczp;		// 处罚现场照片
	private String kzzd1;		// 扩展字段1
	private String kzzd2;		// 扩展字段2
	private String kzzd3;		// 扩展字段3
	private String kzzd4;		// 扩展字段4
	private String userId;      //当前登录用户Id

	private List<String> xctps;

	private Date startDate;		// 开始日期
	private Date overDate;		// 结束日期

	private String limit;//app分页

	public String getLimit () {
		return limit;
	}

	public void setLimit (String limit) {
		this.limit = limit;
	}

	public Date getStartDate () {
		return startDate;
	}

	public void setStartDate (Date startDate) {
		this.startDate = startDate;
	}

	public Date getOverDate () {
		return overDate;
	}

	public void setOverDate (Date overDate) {
		this.overDate = overDate;
	}

	public List<String> getXctps () {
		return xctps;
	}

	public void setXctps (List<String> xctps) {
		this.xctps = xctps;
	}
	public JfCf() {
		super();
	}

	public JfCf(String id){
		super(id);
	}

	public JfXx getCfjf() {
		return cfjf;
	}

	public void setCfjf(JfXx cfjf) {
		this.cfjf = cfjf;
	}
	
	@Length(min=0, max=255, message="处罚通知单号长度必须介于 0 和 255 之间")
	public String getCftzd() {
		return cftzd;
	}

	public void setCftzd(String cftzd) {
		this.cftzd = cftzd;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCfrq() {
		return cfrq;
	}

	public void setCfrq(Date cfrq) {
		this.cfrq = cfrq;
	}
	
	@Length(min=0, max=64, message="处罚对象长度必须介于 0 和 64 之间")
	public String getCfdx() {
		return cfdx;
	}

	public void setCfdx(String cfdx) {
		this.cfdx = cfdx;
	}
	
	@Length(min=0, max=64, message="处罚梯度长度必须介于 0 和 64 之间")
	public String getCftd() {
		return cftd;
	}

	public void setCftd(String cftd) {
		this.cftd = cftd;
	}
	
	@Length(min=0, max=64, message="处罚权限拥有者长度必须介于 0 和 64 之间")
	public String getCfqxyyz() {
		return cfqxyyz;
	}

	public void setCfqxyyz(String cfqxyyz) {
		this.cfqxyyz = cfqxyyz;
	}
	
	@Length(min=0, max=4000, message="处罚原因描述长度必须介于 0 和 4000 之间")
	public String getCfyyms() {
		return cfyyms;
	}

	public void setCfyyms(String cfyyms) {
		this.cfyyms = cfyyms;
	}
	
	@Length(min=0, max=1000, message="处罚现场照片长度必须介于 0 和 1000 之间")
	public String getCfxczp() {
		return cfxczp;
	}
	
	public void setCfxczp(String cfxczp) {
		this.cfxczp = cfxczp.replace("/jfxt/userfiles/", "/userfiles/");
	}
	
	@Length(min=0, max=255, message="扩展字段1长度必须介于 0 和 255 之间")
	public String getKzzd1() {
		return kzzd1;
	}

	public void setKzzd1(String kzzd1) {
		this.kzzd1 = kzzd1;
	}
	
	@Length(min=0, max=255, message="扩展字段2长度必须介于 0 和 255 之间")
	public String getKzzd2() {
		return kzzd2;
	}

	public void setKzzd2(String kzzd2) {
		this.kzzd2 = kzzd2;
	}
	
	@Length(min=0, max=255, message="扩展字段3长度必须介于 0 和 255 之间")
	public String getKzzd3() {
		return kzzd3;
	}

	public void setKzzd3(String kzzd3) {
		this.kzzd3 = kzzd3;
	}
	
	@Length(min=0, max=255, message="扩展字段4长度必须介于 0 和 255 之间")
	public String getKzzd4() {
		return kzzd4;
	}

	public void setKzzd4(String kzzd4) {
		this.kzzd4 = kzzd4;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}