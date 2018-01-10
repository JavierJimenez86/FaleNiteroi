package br.uff.faleniteroi.entity;

import java.io.Serializable;
import java.util.Date;

public class Opinion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String comment;
	private int type; // 0 = critica; 1 = sugestão
	private String service;
	private Date date;
	private String protocol;
	private String response;
	private boolean finished = false;

	public Opinion(String comment, int type, String service, Date date,
			String protocol, String response, boolean finished) {
		super();
		this.comment = comment;
		this.type = type;
		this.service = service;
		this.date = date;
		this.protocol = protocol;
		this.response = response;
		this.finished = finished;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
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

	// Number of seconds since the beginning of epoch
	public long getDateEpoch() {
		return date.getTime() / 1000; // em segundos
	}

	public void setDateEpoch(long seconds) {
		date = new Date(seconds * 1000);
	}

}
