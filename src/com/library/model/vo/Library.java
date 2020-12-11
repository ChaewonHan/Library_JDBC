package com.library.model.vo;

import java.sql.Date;

public class Library {
	private int lease_no;
	private int book_no;
	private String user_id;
	private Date lease_date;
	private Date return_date;
	
	public Library() {}
	public Library(int lease_no, int book_no, String user_id, Date lease_date, Date return_date) {
		this.lease_no = lease_no;
		this.book_no = book_no;
		this.user_id = user_id;
		this.lease_date = lease_date;
		this.return_date = return_date;
	}
	public int getLease_no() {
		return lease_no;
	}
	public void setLease_no(int lease_no) {
		this.lease_no = lease_no;
	}
	public int getBook_no() {
		return book_no;
	}
	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}
	public Date getLease_date() {
		return lease_date;
	}
	public void setLease_date(Date lease_date) {
		this.lease_date = lease_date;
	}
	public Date getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String uesr_id) {
		this.user_id = uesr_id;
	}

}
