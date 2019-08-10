/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.merchant.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 网元信息Entity
 * @author wangdandan
 * @version 2019-04-11
 */
public class JfXx extends DataEntity<JfXx> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 网元名称
	private String jfwz;		// 位置
	private String jfbh;		// 编号
	private String jfjj;		// 所属区域
	private String jfxctp;		// 现场图片
	private String kzzd1;		// 扩展字段1
	private String kzzd2;		// 扩展字段2
	private String kzzd3;		// 扩展字段3
	private String kzzd4;		// 扩展字段4
	private String userId;		// 登陆用户ID

	private String limit;//app分页
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLimit () {
		return limit;
	}

	public void setLimit (String limit) {
		this.limit = limit;
	}

	private List<String> xctps;

	public List<String> getXctps () {
		return xctps;
	}

	public void setXctps (List<String> xctps) {
		this.xctps = xctps;
	}

	public JfXx() {
		super();
	}

	public JfXx(String id){
		super(id);
	}

	@Length(min=0, max=255, message="网元名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="位置长度必须介于 0 和 255 之间")
	public String getJfwz() {
		return jfwz;
	}

	public void setJfwz(String jfwz) {
		this.jfwz = jfwz;
	}
	
	@Length(min=0, max=255, message="编号长度必须介于 0 和 255 之间")
	public String getJfbh() {
		return jfbh;
	}

	public void setJfbh(String jfbh) {
		this.jfbh = jfbh;
	}
	
	@Length(min=0, max=255, message="所属区域长度必须介于 0 和 255 之间")
	public String getJfjj() {
		return jfjj;
	}

	public void setJfjj(String jfjj) {
		this.jfjj = jfjj;
	}
	
	@Length(min=0, max=1000, message="现场图片长度必须介于 0 和 1000 之间")
	public String getJfxctp() {
		return jfxctp;
	}

	public void setJfxctp(String jfxctp) {
		this.jfxctp = jfxctp;
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
	
}