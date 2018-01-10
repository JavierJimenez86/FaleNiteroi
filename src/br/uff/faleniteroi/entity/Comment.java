package br.uff.faleniteroi.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	private String comment;
	private String userName;
	private int requestId;
	private Date date;

	public Comment(String comment, String userName, int requestId, Date date) {
		super();
		this.comment = comment;
		this.userName = userName;
		this.requestId = requestId;
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Number of seconds since the beginning of epoch
	public long getDateEpoch() {
		return date.getTime() / 1000; // em segundos
	}

	public void setDateEpoch(long seconds) {
		date = new Date(seconds * 1000);
	}

}
