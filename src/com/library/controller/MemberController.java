package com.library.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		int result = 0;

		System.out.println("---------- 회원 가입 ----------");
		System.out.print("아이디 입력: ");
		mb.setUserId(sc.nextLine());
		System.out.print("이름 입력 : ");
		mb.setUserName(sc.nextLine());
		System.out.print("나이 입력 : ");
		mb.setUserAge(sc.nextInt());
		sc.nextLine();
		System.out.print("주소 입력 : ");
		mb.setAddr(sc.nextLine());
		System.out.print("성별 입력(M/F): ");
		mb.setGender(sc.nextLine().charAt(0));
		mb.setDate(new java.sql.Date(new java.util.Date().getTime()));
		
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "INSERT INTO customer VALUES (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getCount());
			pstmt.setString(2, mb.getUserId());
			pstmt.setString(3, mb.getUserName());
			pstmt.setInt(4, mb.getUserAge());
			pstmt.setString(5, mb.getAddr());
			pstmt.setString(6, String.valueOf(mb.getGender()));
			pstmt.setDate(7, mb.getDate());
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("정상적으로 가입되었습니다.");
			}
			else {
				System.out.println("가입 실패");
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e){
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
	}
	
	public void updateMember() {
		System.out.print("수정할 회원 아이디 입력 : ");
		String id = sc.next();
		
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		Member mb = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM CUSTOMER WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			if(!rset.next()) {
				System.out.println("회원 정보가 없습니다.");
				return;
			}
			
			mb = new Member(rset.getInt(1),rset.getString(2),rset.getString(3)
					,rset.getInt(4),rset.getString(5),rset.getString(6).charAt(0),rset.getDate(7));
			
			System.out.println(mb.getUserId()+"님의 회원 정보");
			System.out.println("회원 이름: "+mb.getUserName());
			System.out.println("회원 등록일자: "+mb.getDate());
			System.out.println("---------- 회원 정보 수정 ----------");
			System.out.println("1. 회원 이름 수정");
			System.out.println("2. 회원 주소 수정");
			System.out.println("3. 회원 성별 수정");
			System.out.println("4. 수정 완료");
			System.out.print("수정할 항목 번호 입력 : ");
			int num = sc.nextInt();
	
			switch(num) {
			case 1:
				System.out.print("이름을 입력해주세요 : ");
				mb.setUserName(sc.next());
				break;
			case 2:
				System.out.print("주소를 입력해주세요 : ");
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				mb.setAddr(sc.nextLine());
				break;
			case 3:
				System.out.print("성별을 입력해주세요 : ");
				mb.setGender(sc.next().charAt(0));
				break;
			case 4:	break;
			default:
				System.out.println("다시 입력해주세요(1~5)");
			}
			query = "UPDATE CUSTOMER SET USER_NAME=?, ADDR=?, GENDER=? WHERE USER_ID = ?";
			pstmt.close();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(4, id);
			pstmt.setString(1, mb.getUserName());
			pstmt.setString(2, mb.getAddr());
			pstmt.setString(3, String.valueOf(mb.getGender()));
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("회원 정보 수정 완료");
			}else {
				System.out.println("회원 정보 수정 실패");
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
			System.out.println("오류발생: 관리자에게 문의하세요");
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e2){
				System.out.println("오류발생: 관리자에게 문의하세요");
			}
		}
		
	}
	
	public void deleteMember() {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		System.out.println("---------- 회원 삭제 ----------");
		System.out.println("삭제할 회원 아이디를 입력해주세요: ");
		String id = sc.next();
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "DELETE FROM CUSTOMER WHERE USER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println(id+" 회원이 삭제되었습니다.");
			}
			else {
				System.out.println("회원 삭제 실패");
			}
		} catch (SQLException e) {
			System.out.println("오류발생: 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				System.out.println("오류발생: 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}

	}

	public int getCount() {
		String query = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		int count = 0;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT COUNT(USER_NO) COUNT FROM CUSTOMER";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				count = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
				e.printStackTrace();
			}
		}
		return count+1;
	}
}
