package com.library.view;

import java.util.Scanner;

import com.library.controller.BookController;
import com.library.controller.MemberController;
import com.library.controller.RentController;

public class LibraryMenu {
	Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		while(true) {
		System.out.println("---------- 메인메뉴 ----------");
		System.out.println("1. 책 관리");
		System.out.println("2. 회원 관리");
		System.out.println("3. 대여 관리");
		System.out.println("4. 프로그램 종료");
		System.out.print("메뉴를 입력해주세요 : ");
		int num = sc.nextInt();
		
		switch(num) {
			case 1:
				BookMenu();
				break;
			case 2:
				MemberMenu();
				break;
			case 3:
				RentMenu();
				break;
			case 4:
				System.out.println("프로그램이 종료됩니다.");
				System.exit(0);
			default:
				System.out.println("잘못된 선택 입니다.(1~3)");
			} // switch
		
		}// while
	}// mainMenu
	public void BookMenu() {
		int num = 0;
		while(num!=5) {
			BookController bc = new BookController();
			System.out.println("---------- 책 메뉴 ----------");
			System.out.println("1. 전체 책 조회");
			System.out.println("2. 책 코드로 조회");
			System.out.println("3. 책 추가하기");
			System.out.println("4. 책 삭제하기");
			System.out.println("5. 메인 메뉴로 이동");
			System.out.print("메뉴를 입력해주세요 : ");
			num = sc.nextInt();
			
			switch(num) {
			case 1:
				bc.allListBook();
				break;
			case 2:
				bc.searchBookCode();
				break;
			case 3:
				bc.insertBook();
				break;
			case 4:
				bc.delectBook();
				break;
			case 5:
				System.out.println("메인 메뉴로 이동합니다.");
				break;
			default:
				System.out.println("잘못된 선택 입니다.(1~5)");
			} // switch
		}
	}
	public void MemberMenu() {
		MemberController mc = new MemberController();
		int num = 0;
		while(num!=7) {
			System.out.println("---------- 회원 메뉴 ----------");
			System.out.println("1. 회원 전체 조회");
			System.out.println("2. 회원 이름으로 조회");
			System.out.println("3. 회원 아이디로 조회");
			System.out.println("4. 회원 가입");
			System.out.println("5. 회원 정보 수정");
			System.out.println("6. 회원 탈퇴");
			System.out.println("7. 메인 메뉴로 이동");
			System.out.print("메뉴를 입력해주세요 : ");
			num = sc.nextInt();
			
			switch(num) {
			case 1:
				mc.allListMember();
				break;
			case 2:
				mc.searchName();
				break;
			case 3:
				mc.searchId();
				break;
			case 4:
				mc.insertMember();
				break;
			case 5:
				mc.updateMember();
				break;
			case 6:
				mc.deleteMember();
				break;
			case 7:
				System.out.println("메인 메뉴로 이동합니다.");
				break;
			default:
				System.out.println("잘못된 선택 입니다.(1~7)");
			} // switch		
		}
	}
	
	public void RentMenu() {
		RentController rc = new RentController();
		int num = 0;
		while(num!=5) {
			System.out.println("---------- 대여 메뉴 ----------");
			System.out.println("1. 대여 관리 전체 조회");
			System.out.println("2. 회원 아이디로 대여 조회");
			System.out.println("3. 책 이름으로 대여 조회");
			System.out.println("4. 대여정보 추가");
			System.out.println("5. 메인 메뉴로 이동");
			System.out.print("메뉴를 입력해주세요 : ");
			num = sc.nextInt();
			switch(num) {
			case 1:
				rc.allListRent();
				break;
			case 2:
				rc.searchIdRent();
				break;
			case 3:
				rc.searchBookRent();
				break;
			case 4:
				rc.updateRent();
				break;
			case 5:
				System.out.println("메인 메뉴로 이동합니다.");
				break;
			default:
				System.out.println("잘못된 선택 입니다.(1~5)");
			} // switch
		}
	}
}
