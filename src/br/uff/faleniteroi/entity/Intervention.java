package br.uff.faleniteroi.entity;

import java.util.Date;

public class Intervention {
	
	private String desription;
	private String address;
	private Date date;
	private double latitude = -22.8975554;
	private double longitude = -43.0995201;
	
	public Intervention() {
	}

	public Intervention(String desription, String address, Date date,
			double latitude, double longitude) {
		super();
		this.desription = desription;
		this.address = address;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public boolean comparePosition(double latitude, double longitude)
	{
		latitude = Math.round((latitude * 1000000.0)) / 1000000.0;
		longitude = Math.round((longitude * 1000000.0)) / 1000000.0;

		if (this.latitude == latitude && this.longitude == longitude) {
			return true;
		}
		
		return false;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
