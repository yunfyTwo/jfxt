/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 巡检过程Entity
 * @author wangdandan
 * @version 2019-04-11
 */
public class JfXjgc extends DataEntity<JfXjgc> {
	
	private static final long serialVersionUID = 1L;
	private JfXx xjjf;		// 巡检机房
	private Date xjsj;		// 巡检时间
	private String xjsjMs;		// 巡检时间
	private User xjry;		// 巡检人员
	private String xczp;		// 现场图片
	private String zgpd;		// 整改判断
	private String xjsftg;		// 巡检是否通过
	private String xjdf;		// 巡检打分
	private String kzzd1;		// 扩展字段1
	private String kzzd2;		// 扩展字段2
	private String kzzd3;		// 扩展字段3
	private String kzzd4;		// 扩展字段4
	private String userId;      //当前登录用户Id

	private List<String> xctps;//多图片存储

	private Date startDate;		// 开始日期
	private Date overDate;		// 结束日期

	private String limit;//app分页
	
	public String getXjsjMs() {
		return xjsjMs;
	}

	public void setXjsjMs(String xjsjMs) {
		this.xjsjMs = xjsjMs;
	}

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

	public JfXjgc() {
		super();
	}

	public JfXjgc(String id){
		super(id);
	}

	public JfXx getXjjf() {
		return xjjf;
	}

	public void setXjjf(JfXx xjjf) {
		this.xjjf = xjjf;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getXjsj() {
		return xjsj;
	}

	public void setXjsj(Date xjsj) {
		this.xjsj = xjsj;
	}

	public User getXjry() {
		return xjry;
	}

	public void setXjry(User xjry) {
		this.xjry = xjry;
	}
	
	@Length(min=0, max=4000, message="现场图片长度必须介于 0 和 4000 之间")
	public String getXczp() {
		return xczp;
	}
	
	public void setXczp(String xczp) {
		this.xczp = xczp.replace("/jfxt/userfiles/", "/userfiles/");
	}
	
	@Length(min=0, max=1000, message="整改判断长度必须介于 0 和 1000 之间")
	public String getZgpd() {
		return zgpd;
	}

	public void setZgpd(String zgpd) {
		this.zgpd = zgpd;
	}
	
	@Length(min=0, max=1, message="巡检是否通过长度必须介于 0 和 1 之间")
	public String getXjsftg() {
		return xjsftg;
	}

	public void setXjsftg(String xjsftg) {
		this.xjsftg = xjsftg;
	}
	
	@Length(min=0, max=64, message="巡检打分长度必须介于 0 和 64 之间")
	public String getXjdf() {
		return xjdf;
	}

	public void setXjdf(String xjdf) {
		this.xjdf = xjdf;
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