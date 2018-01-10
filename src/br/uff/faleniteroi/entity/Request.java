package br.uff.faleniteroi.entity;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;
	private int requestId;
	private String service;
	private String address;
	private String photo;
	private String description;
	private Date date;
	private double latitude;
	private double longitude;
	private boolean finished = false;
	private String protocol;
	private String response;
	private int like;
	private int unlike;

	public Request() {
	}

	public Request(String service, String address, String photo,
			String description, Date date, double latitude, double longitude) {
		super();
		this.service = service;
		this.address = address;
		this.photo = photo;
		this.description = description;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Request(String service, String address, String photo,
			String description, Date date, double latitude, double longitude, 
			boolean finished, String protocol, String response, int like, int unlike, int requestId) {
		super();
		this.service = service;
		this.address = address;
		this.photo = photo;
		this.description = description;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.finished = finished;
		this.protocol = protocol;
		this.response = response;
		this.like = like;
		this.unlike = unlike;
		this.requestId = requestId;
	}
	
	public boolean comparePosition(double latitude, double longitude)
	{
		//latitude = Math.round((latitude * 1000000.0)) / 1000000.0;
		//longitude = Math.round((longitude * 1000000.0)) / 1000000.0;

		if (this.latitude == latitude && this.longitude == longitude) {
			return true;
		}
		
		return false;
	}

	// Number of seconds since the beginning of epoch
	public long getDateEpoch() {
		return date.getTime() / 1000; // em segundos
	}

	public void setDateEpoch(long seconds) {
		date = new Date(seconds * 1000);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getUnlike() {
		return unlike;
	}

	public void setUnlike(int unlike) {
		this.unlike = unlike;
	}
}
