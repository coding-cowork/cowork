package com.william.core.model;

/**
 * 
 * @author William
 *
 */
public class Material {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2476212648741383192L;

	private String _id;

	private String materialName;

	private String materialLocation;

	private String region;

	private String unit;

	private double amount;

	private int dailyAmount;

	private double accumulatedAmount;

	private String comment;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialLocation() {
		return materialLocation;
	}

	public void setMaterialLocation(String materialLocation) {
		this.materialLocation = materialLocation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAccumulatedAmount() {
		return accumulatedAmount;
	}

	public void setAccumulatedAmount(double accumulatedAmount) {
		this.accumulatedAmount = accumulatedAmount;
	}

	public int getDailyAmount() {
		return dailyAmount;
	}

	public void setDailyAmount(int dailyAmount) {
		this.dailyAmount = dailyAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
