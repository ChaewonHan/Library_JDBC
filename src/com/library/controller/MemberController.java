package com.library.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.library.model.vo.Member;

public class MemberController {
	Scanner sc = new Scanner(System.in);
	public void allListMember() {
		
		ArrayList<Member> list = new ArrayList<Member>();
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		System.out.println("---------- 전체 회원 조회 ----------");
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM CUSTOMER";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member mb = new Member(rset.getInt(1),rset.getString(2),rset.getString(3),
						rset.getInt(4),rset.getString(5),rset.getString(6).charAt(0),rset.getDate(7));
				list.add(mb);
			}
			for(Member mb : list) {
				System.out.println(mb.toString());
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e){
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
		
	}
	
	public void searchName() {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		
		System.out.println("---------- 회원 이름으로 조회 ----------");
		System.out.print("조회할 이름 입력 : ");
		String name = sc.nextLine();
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM CUSTOMER WHERE USER_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			
			if(!rset.next()) {
				System.out.println("조회된 이름이 없습니다.");
				return;
			}
			Member mb = new Member(rset.getInt(1),rset.getString(2),rset.getString(3),
					rset.getInt(4),rset.getString(5),rset.getString(6).charAt(0),rset.getDate(7));
			System.out.println(mb.toString());
			// 중복된 이름은 어떻게 처리하지. arrayList말고 다른 방법 없을까
		} catch (SQLException e1) {
			System.out.println("오류발생 : 관리자에게 문의하세요.");
			e1.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e1){
				System.out.println("오류발생 : 관리자에게 문의하세요.");
			}
		}
		
	}
	
	public void searchId() {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		
		System.out.println("---------- 회원 아이디로 조회 ----------");
		System.out.print("조회할 아이디 입력 : ");
		String id = sc.nextLine();
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM CUSTOMER WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			if(!rset.next()) {
				System.out.println("조회된 아이디가 없습니다.");
				return;
			}
			Member mb = new Member(rset.getInt(1),rset.getString(2),rset.getString(3),
					rset.getInt(4),rset.getString(5),rset.getString(6).charAt(0),rset.getDate(7));
			System.out.println(mb.toString());
			
		} catch (SQLException e1) {
			System.out.println("오류발생 : 관리자에게 문의하세요.");
			e1.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e1){
				System.out.println("오류발생 : 관리자에게 문의하세요.");
			}
		}
		
	}

	public void insertMember() {
		Member mb = new Member();
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT user_no FROM (SELECT user_no FROM customer ORDER BY ROWNUM DESC) WHERE ROWNUM = 1;";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			System.out.println(rset.getInt(1));
//			query = "INSERT INTO CURSTOMER VALUES (?,?,?,?,?,?,?)";
//			pstmt = conn.prepareStatement(query);
//			pstmt.setInt(1,ㅇ)
//			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e){
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
	}
	public void updateMember() {
		
	}
	public void deleteMember() {
		
	}
}
