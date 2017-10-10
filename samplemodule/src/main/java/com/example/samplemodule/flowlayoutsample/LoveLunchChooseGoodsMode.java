package com.example.samplemodule.flowlayoutsample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoveLunchChooseGoodsMode {


	private String id;
	private String name;
	private String price;
	private String images;
	private String shopNum;
	private String shopId;
	private String pno;
	private int num=1;
	private List<Property> property;
	private Map<String ,Standard> map=null;

	public Map<String, Standard> getMap() {
		return map;
	}

	public void setMap(Map<String, Standard> map) {
		this.map = map;
	}

	public List<Property> getProperty() {
		return property;
	}

	public void setProperty(List<Property> property) {
		this.property = property;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private boolean isCheck;

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getShopNum() {
		return shopNum;
	}

	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public static List<LoveLunchChooseGoodsMode> newsInstance(JSONObject obj) {
		JSONObject optJSONObject = obj.optJSONObject("content");
		JSONArray jsonArray = optJSONObject.optJSONArray("data");
		List<LoveLunchChooseGoodsMode> list=new ArrayList<LoveLunchChooseGoodsMode>();
		for (int i = 0; i < jsonArray.length(); i++) {
			LoveLunchChooseGoodsMode mode = new LoveLunchChooseGoodsMode();
			mode.parse(jsonArray.optJSONObject(i));
			list.add(mode);
		}
		return list;
	}

	public void parse(JSONObject obj) {
		// TODO Auto-generated method stub
		id = obj.optString("id");
		name = obj.optString("name");
		images = obj.optString("images");
		price = obj.optString("price");
		pno = obj.optString("pno");
		shopNum = obj.optString("shopNum");
		shopId = obj.optString("shopId");
		property= Property.getPropertys(obj);

	}

	public static class Property {
		private String id;
		private String name;
		private List<Standard> standard;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Standard> getStandard() {
			return standard;
		}

		public void setStandard(List<Standard> standard) {
			this.standard = standard;
		}

		public static List<Property> getPropertys(JSONObject json) {
			JSONArray array = json.optJSONArray("property");
			List<Property> list = new ArrayList<>(0);
			if (array == null) return list;
			for (int i = 0; i < array.length(); i++) {
				Property mode = new Property();
				mode.parse(array.optJSONObject(i));
				list.add(mode);
			}
			return list;
		}


		public void parse(JSONObject obj) {
			id = obj.optString("id");
			name = obj.optString("name");
			standard = Standard.getStandards(obj);
		}
	}

	public static class Standard implements Serializable{
		private String price;
		private String standard;
		private String pid;

		public static List<Standard> getStandards(JSONObject json) {
			JSONArray array = json.optJSONArray("standard");
			List<Standard> list = new ArrayList<>(0);
			if (array == null) return list;
			for (int i = 0; i < array.length(); i++) {
				Standard mode = new Standard();
				mode.parse(array.optJSONObject(i));
				list.add(mode);
			}
			return list;
		}


		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getStandard() {
			return standard;
		}

		public void setStandard(String standard) {
			this.standard = standard;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public void parse(JSONObject obj) {
			price = obj.optString("price");
			standard = obj.optString("standard");
			pid = obj.optString("pid");
		}
	}


}
