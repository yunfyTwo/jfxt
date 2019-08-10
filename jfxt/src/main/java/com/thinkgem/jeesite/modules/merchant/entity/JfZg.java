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
 * 整改Entity
 * @author wangdandan
 * @version 2019-04-11
 */
public class JfZg extends DataEntity<JfZg> {
	
	private static final long serialVersionUID = 1L;
	private JfXx zgjf;		// 整改机房
	private String zgdh;		// 整改单号
	private Date zgrq;		// 日期
	private String zgyq;		// 整改要求
	private String cfxczp;		// 整改现场照片
	private String kzzd1;		// 扩展字段1
	private String kzzd2;	   // 是否已经整改
	private String kzzd3;		// 整改费用
	private String kzzd4;		// 扩展字段4
	private Date startDate;		// 开始日期
	private Date overDate;		// 结束日期
	private String isSafetyHazard;		// 是否存在安全隐患
	private String briefDescription;		// 隐患简要说明
	private String reason;		// 隐患原因
	private String isODF;		// 是否有ODF架/柜
	private String isCutOver;		// 是否需要网络设备整治割接
	private String isCutOverContent;		// 隐患级别
	private String isOpticalCable;		// 是否需要光缆割接
	private String opticalCableCutting;		// 光缆割接量（条/芯）
	private String opticalCableContent;		// 整治批次
	private String needRemediation;		// 是否需要环境整治
	private String contentDescription;		// 环境整治内容描述（门、窗、墙面等）
	private String userId;      //当前登录用户Id
	

	public String getIsCutOverContent() {
		return isCutOverContent;
	}

	public void setIsCutOverContent(String isCutOverContent) {
		this.isCutOverContent = isCutOverContent;
	}

	private List<String> xctps;
	public String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsODF() {
		return isODF;
	}

	public void setIsODF(String isODF) {
		this.isODF = isODF;
	}

	public String getIsCutOver() {
		return isCutOver;
	}

	public void setIsCutOver(String isCutOver) {
		this.isCutOver = isCutOver;
	}

	public String getIsOpticalCable() {
		return isOpticalCable;
	}

	public void setIsOpticalCable(String isOpticalCable) {
		this.isOpticalCable = isOpticalCable;
	}

	public String getOpticalCableCutting() {
		return opticalCableCutting;
	}

	public void setOpticalCableCutting(String opticalCableCutting) {
		this.opticalCableCutting = opticalCableCutting;
	}

	public String getOpticalCableContent() {
		return opticalCableContent;
	}

	public void setOpticalCableContent(String opticalCableContent) {
		this.opticalCableContent = opticalCableContent;
	}

	public String getNeedRemediation() {
		return needRemediation;
	}

	public void setNeedRemediation(String needRemediation) {
		this.needRemediation = needRemediation;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getIsSafetyHazard() {
		return isSafetyHazard;
	}

	public void setIsSafetyHazard(String isSafetyHazard) {
		this.isSafetyHazard = isSafetyHazard;
	}
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

	public JfZg() {
		super();
	}

	public JfZg(String id){
		super(id);
	}

	public JfXx getZgjf() {
		return zgjf;
	}

	public void setZgjf(JfXx zgjf) {
		this.zgjf = zgjf;
	}
	
	@Length(min=0, max=255, message="整改单号长度必须介于 0 和 255 之间")
	public String getZgdh() {
		return zgdh;
	}

	public void setZgdh(String zgdh) {
		this.zgdh = zgdh;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getZgrq() {
		return zgrq;
	}

	public void setZgrq(Date zgrq) {
		this.zgrq = zgrq;
	}
	
	@Length(min=0, max=1000, message="整改要求长度必须介于 0 和 1000 之间")
	public String getZgyq() {
		return zgyq;
	}

	public void setZgyq(String zgyq) {
		this.zgyq = zgyq;
	}
	@Length(min=0, max=1000, message="整改现场照片长度必须介于 0 和 1000 之间")
	public String getCfxczp() {
		return cfxczp;
	}
	//网页端系统在页面修改的时候上传的图片自带了系统名称，暂时找不到怎么解决，强行替换
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