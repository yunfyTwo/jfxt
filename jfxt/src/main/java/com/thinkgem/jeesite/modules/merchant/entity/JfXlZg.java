
package com.thinkgem.jeesite.modules.merchant.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;


public class JfXlZg extends DataEntity<JfXlZg> {

	private static final long serialVersionUID = 2313214561L;
	private JfXx zgjf;		// 整改机房
	private String id;		// 编号
	private String zgdh;		// 整改单号
	private Date zgrq;		// 日期
	private String zgrqMs;		// 日期描述
	

	private String zgwt;		// 问题情况
	private String cfxczp;		// 整改现场照片
	private String kzzd1;		// 扩展字段1
	private String kzzd2;	   // 扩展字段2
	private String kzzd3;		// 扩展字段3
	private String kzzd4;		// 当前登录用户
	private Date startDate;		// 开始日期
	private Date overDate;		// 结束日期
	private String isSafetyHazard;		// 是否存在安全隐患
	private String briefDescription;		// 隐患简要说明
	private String reason;		// 隐患原因
	
	private String zgfs;		// 处理方式
	private String xjgl;		// 需新建杆路（km）
	private String ghdx;		// 更换吊线（km）
	private String ghdg;		// 更换电杆（根）
	private String dgfz;		// 扶正（偏杆、倒杆）（根）
	private String xjlx;		// 新建拉线（条）
	private String ghlx;		// 更换拉线（条）
	private String ghgl;		// 需更换光缆（km）
	private String iszg;		// 是否已整改
	private String ygfy;		// 预估费用（万元）
	private String remarks;		// 备注
	
	private String userId;      //当前登录用户Id
	private String jfName;      //整改机房名称
	
	private List<String> xctps;
	
	private String limit;//app分页

	public String getLimit () {
		return limit;
	}

	public void setLimit (String limit) {
		this.limit = limit;
	}
	
	public List<String> getXctps() {
		return xctps;
	}

	public void setXctps(List<String> xctps) {
		this.xctps = xctps;
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

	@Length(min=0, max=4000, message="整改现场照片长度必须介于 0 和 4000 之间")
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

	public JfXx getZgjf() {
		return zgjf;
	}

	public void setZgjf(JfXx zgjf) {
		this.zgjf = zgjf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZgwt() {
		return zgwt;
	}

	public void setZgwt(String zgwt) {
		this.zgwt = zgwt;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public String getIsSafetyHazard() {
		return isSafetyHazard;
	}

	public void setIsSafetyHazard(String isSafetyHazard) {
		this.isSafetyHazard = isSafetyHazard;
	}

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

	public String getZgfs() {
		return zgfs;
	}

	public void setZgfs(String zgfs) {
		this.zgfs = zgfs;
	}

	public String getXjgl() {
		return xjgl;
	}

	public void setXjgl(String xjgl) {
		this.xjgl = xjgl;
	}

	public String getGhdx() {
		return ghdx;
	}

	public void setGhdx(String ghdx) {
		this.ghdx = ghdx;
	}

	public String getGhdg() {
		return ghdg;
	}

	public void setGhdg(String ghdg) {
		this.ghdg = ghdg;
	}

	public String getDgfz() {
		return dgfz;
	}

	public void setDgfz(String dgfz) {
		this.dgfz = dgfz;
	}

	public String getXjlx() {
		return xjlx;
	}

	public void setXjlx(String xjlx) {
		this.xjlx = xjlx;
	}

	public String getGhlx() {
		return ghlx;
	}

	public void setGhlx(String ghlx) {
		this.ghlx = ghlx;
	}

	public String getGhgl() {
		return ghgl;
	}

	public void setGhgl(String ghgl) {
		this.ghgl = ghgl;
	}

	public String getIszg() {
		return iszg;
	}

	public void setIszg(String iszg) {
		this.iszg = iszg;
	}

	public String getYgfy() {
		return ygfy;
	}

	public void setYgfy(String ygfy) {
		this.ygfy = ygfy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getJfName() {
		return jfName;
	}

	public void setJfName(String jfName) {
		this.jfName = jfName;
	}
	public String getZgrqMs() {
		return zgrqMs;
	}

	public void setZgrqMs(String zgrqMs) {
		this.zgrqMs = zgrqMs;
	}
	
}