package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자

	// 메소드 g/s

	// 메소드 일반
	public void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// guestbookList 가져오기
	public List<GuestbookVo> getList() {

		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();

		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " select  no ";
			query += "    	   ,name ";
			query += " 		   ,password ";
			query += "  	   ,content	";
			query += " 		   ,to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc ";// String query문 안에는 끝날 때 ; 표기하지 말 것. 주의!

			// 쿼리
			pstmt = conn.prepareStatement(query);

			// 바인딩 x

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);
				guestbookList.add(guestbookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return guestbookList;
	}// getGuestbookList

	public int guestInsert(GuestbookVo guestbookVo) {

		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열 만들기
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval,?,?, ?,sysdate) ";

			// 쿼리로 만들기
			pstmt = conn.prepareStatement(query);

			// ? 안에 값 받아 넣기
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getContent());

			// 쿼리문 실행
			count = pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	//비밀번호 일치 여부를 delete.jsp에서 확인하는 삭제 메소드
	public int guestDelete(int no) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열 만들기
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";

			// 쿼리로 만들기
			pstmt = conn.prepareStatement(query);

			// ? 안에 값 받아 넣기
			pstmt.setInt(1, no);

			// 쿼리문 실행
			count = pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// 쿼리문의 조건절로 비밀번호가 일치하는 경우 삭제
	public int delete(int no, String password) {

		int count = 0;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from guestbook ";
			query += " where no= ? ";
			query += " and password= ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			pstmt.setString(2, password);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	// 게시글 1개 가져오기
	public GuestbookVo getGuest(int no) {
		GuestbookVo gbVo = null;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no); // ?(물음표) 중 1번째, 순서중요

			rs = pstmt.executeQuery();

			// 4.결과처리
			rs.next();
			int gbno = rs.getInt("no");
			String name = rs.getString("name");
			String pw = rs.getString("password");
			String content = rs.getString("content");
			String regDate = rs.getString("reg_date");

			gbVo = new GuestbookVo(gbno, name, pw, content, regDate);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return gbVo;

	}

	public void getPass(int no) {
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열 만들기
			String query = "";
			query += " select password ";
			query += " from guestbook ";
			query += " where no = ? ";

			// 쿼리로 만들기
			pstmt = conn.prepareStatement(query);
			// ? 안에 값 받아 넣기
			pstmt.setInt(1, no);

			// 쿼리문 실행
			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

	}

}
