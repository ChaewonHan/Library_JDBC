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

public class BookController {
	Scanner sc = new Scanner(System.in);
	
	public void allListBook() {
		System.out.println("---------- 전체 책 조회 ----------");
		ArrayList<Book> list = new ArrayList<Book>();
		
		Connection conn = null;
		String query  = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM BOOK";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			// 컬럼의 자료형이랑 개수를 을 모를 때에는 어떻게 가져오지?
			while(rset.next()) {
				Book bk = new Book(rset.getInt(1),rset.getString(2),rset.getString(3),
						rset.getInt(4), rset.getString(5), rset.getString(6));
				list.add(bk);
			}
			for(Book bk : list) {
				System.out.println(bk.toString());
			}
			System.out.println();
		} catch (SQLException e1) {
			System.out.println("오류 발생 : 관리자에게 문의하세요.");
		}
		finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch(SQLException e2){
				System.out.println("오류 발생 : 관리자에게 문의하세요.");
			}
		}
		
	}
	
	public void searchBookCode() {
		System.out.println("---------- 책 코드로 조회----------");
		System.out.print("책 코드를 입력해주세요 : ");
		String code = sc.nextLine();

		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "SELECT * FROM BOOK WHERE BOOK_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, code);
			rset = pstmt.executeQuery();
			
			
			if(!rset.next()) {
				System.out.println("책 정보가 없습니다.");
				return;				
			}
			Book bk = new Book(rset.getInt(1),rset.getString(2),rset.getString(3),
			rset.getInt(4), rset.getString(5), rset.getString(6));

			System.out.println(bk.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("오류발생 : 관리자에게 문의하세요");
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e2) {
				System.out.println("오류발생 : 관리자에게 문의하세요");
			}
		}
	}

	public void insertBook() {
		Book bk = new Book();
		System.out.println("---------- 책 추가하기 ----------");
		System.out.print("추가할 책 번호: ");
		bk.setBookNo(sc.nextInt());
		sc.nextLine();
		System.out.print("추가할 책 이름 : ");
		bk.setBookName(sc.nextLine());
		System.out.print("추가할 책 작가 이름 : ");
		bk.setBookWriter(sc.nextLine());
		System.out.print("추가할 책 가격 : ");
		bk.setBookPrice(sc.nextInt());
		sc.nextLine();
		System.out.print("추가할 책 출판사 : ");
		bk.setPublisher(sc.nextLine());
		System.out.print("추가할 책 장르 : ");
		bk.setGenre(sc.nextLine());
		
		Connection conn = null;
		String query = null;
		Statement stmt = null;
		int result = 0;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "INSERT INTO BOOK VALUES('"+bk.getBookNo()+"','"+bk.getBookName()+"','"+bk.getBookWriter()
			+"','"+bk.getBookPrice()+"','"+bk.getPublisher()+"','"+bk.getGenre()+"')";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			if(result > 0) {
				System.out.println("책 등록 완료");
			}else {
				System.out.println("책 등록 실패");
			}				
		} catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요~!");
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("오류발생 : 관리자에게 문의하세요~!");
				e.printStackTrace();
			}
		}
		
	}

	public void delectBook() {
		Book bk = new Book();
		System.out.println("---------- 책 삭제하기 ----------");
		System.out.print("삭제할 책 이름: ");
		String name = sc.next();
		
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","library","1234");
			query = "DELETE FROM BOOK WHERE BOOK_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("책 정보 삭제 완료");
			}else {
				System.out.println("책 정보 삭제 실패");
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
		}
		
	}
}
