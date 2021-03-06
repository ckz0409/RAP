package com.taobao.rigel.rap.organization.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.taobao.rigel.rap.common.ActionBase;
import com.taobao.rigel.rap.organization.bo.ProductionLine;
import com.taobao.rigel.rap.organization.service.OrganizationMgr;

public class ProductlineAction extends ActionBase {

	private static final long serialVersionUID = -5059179317694489758L;
	private OrganizationMgr organizationMgr;
	private int corpId;
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCorpId() {
		return corpId;
	}

	

	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}

	public OrganizationMgr getOrganizationMgr() {
		return organizationMgr;
	}

	public void setOrganizationMgr(OrganizationMgr organizationMgr) {
		this.organizationMgr = organizationMgr;
	}

	public String all() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		List<ProductionLine> lineModels = organizationMgr
				.getProductionLineList(corpId);
		for (ProductionLine lineModel : lineModels) {
			Map<String, Object> line = new HashMap<String, Object>();
			line.put("id", lineModel.getId());
			line.put("name", lineModel.getName());
			line.put("count", lineModel.getProjectNum());
			items.add(line);
		}
		result.put("items", items);

		Gson gson = new Gson();
		setJson(gson.toJson(result));
		return SUCCESS;
	}

	public String create() {
		ProductionLine line = new ProductionLine();
		Gson gson = new Gson();
		line.setName(name);
		line.setUserId((int) getCurUserId());
		line.setCorporationId(corpId);
		int id = organizationMgr.addProductionLine(line);
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", id);
		p.put("name", name);
		setJson("{\"items\":[" + gson.toJson(p) + "]}");
		return SUCCESS;
	}

	public String delete() {
		setJson(organizationMgr.removeProductionLine(id).toString());
		return SUCCESS;
	}

	public String update() {
		ProductionLine line = new ProductionLine();
		line.setId(id);
		line.setName(name);
		organizationMgr.updateProductionLine(line);
		setJson("{\"isOk\":\"true\"}");
		return SUCCESS;
	}
}
