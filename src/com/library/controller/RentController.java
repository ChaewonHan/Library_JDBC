package com.library.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.library.model.vo.Book;
import com.library.model.vo.Library;
import com.library.model.vo.Member;

public class RentController {
	/*
	 * ※ 대여관리에서는 조회시 - 대여번호,회원아이디,회원이름,책이름 이 나와야 함 (북 번호가 아님)
	 * 
	 * ※ 대여정보 추가시 - 대여번호,회원아이디,책이름 을 입력시 DB에서 해당 책 이름에 따른 정보를 가지고 책번호를 가져와서 
	 * DB에 넣어져야함 (책 번호를 직접 프로그램 사용자가 넣어주면 안됨)
	 */
	Scanner sc = new Scanner(System.in);
	
	public void allListRent() {
		System.out.println("---------- 대여 관리 전체 조회 ----------");
		Connection conn = null;
		String query = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT L.LEASE_NO, L.USER_ID, C.USER_NAME, B.BOOK_NAME FROM LIBRARY L \r\n" + 
					"JOIN CUSTOMER C ON (L.USER_ID = C.USER_ID)\r\n" + 
					"JOIN BOOK B ON (L.BOOK_NO = B.BOOK_NO)";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			System.out.println("대여번호	회원아이디		회원이름		책이름");
			while(rset.next()) {
				int leaseNo = rset.getInt(1);
				String userId = rset.getString(2);
				String userName = rset.getString(3);
				String bookName = rset.getString(4);
				System.out.println(leaseNo+" 	"+userId+"		"+userName+" 	"+bookName);
			}
			
		} catch (SQLException e1) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e1.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	public void searchIdRent() {
		System.out.println("---------- 회원 아이디로 대여 조회 ----------");
		System.out.print("회원 아이디: ");
		String id = sc.next();
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset= null;

		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT L.LEASE_NO, L.USER_ID, C.USER_NAME, B.BOOK_NAME FROM LIBRARY L \r\n" + 
					"JOIN CUSTOMER C ON (L.USER_ID = C.USER_ID)\r\n" + 
					"JOIN BOOK B ON (L.BOOK_NO = B.BOOK_NO)\r\n" + 
					"where l.user_id = ?";
			pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			if(!rset.next()) {
				System.out.println("조회된 회원이 없습니다.");
				return;
			}
			rset.beforeFirst();
			while(rset.next()) {
				System.out.println("대여번호	회원아이디		회원이름		책이름");
				System.out.println(rset.getInt(1)+" 	"+rset.getString(2)+"		"+rset.getString(3)+" 	"+rset.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
	}
	public void searchBookRent(){
		System.out.println("---------- 책 이름으로 대여 조회 ----------");
		System.out.print("책 이름: ");
		String book = sc.nextLine();
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset= null;

		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT L.LEASE_NO, L.USER_ID, C.USER_NAME, B.BOOK_NAME FROM LIBRARY L \r\n" + 
					"JOIN CUSTOMER C ON (L.USER_ID = C.USER_ID)\r\n" + 
					"JOIN BOOK B ON (L.BOOK_NO = B.BOOK_NO)\r\n" + 
					"WHERE B.BOOK_NAME = ?";
			pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, book);
			rset = pstmt.executeQuery();
			if(!rset.next()) {
				System.out.println("조회된 책이 없습니다.");
				return;
			}
			rset.beforeFirst();
			System.out.println("대여번호	회원아이디		회원이름		책이름");
			while(rset.next()) {
				System.out.println(rset.getInt(1)+" 	"+rset.getString(2)+"		"+rset.getString(3)+" 	"+rset.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
	}
	public void updateRent() {
		Library lb = new Library();
		System.out.println("---------- 대여정보 추가 ----------");
		System.out.print("책 이름: ");
		lb.setBook_no(sc.nextLine());
		System.out.print("대여번호: ");
		lb.setLease_no(sc.nextInt());
		System.out.print("회원아이디: ");
		lb.setUser_id(sc.next());
		System.out.println(lb.getLease_date());
		
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT BOOK_NO FROM LIBRARY WHERE BOOK_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lb.getBook_no());
			rset = pstmt.executeQuery();
			if(!rset.next()) {
				System.out.println("조회된 책 이름이 없습니다.");
				return;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
