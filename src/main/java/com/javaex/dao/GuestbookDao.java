package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	//필드
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<GuestbookVo> getList(){
		
		List<GuestbookVo> guestList = sqlSession.selectList("guestbook.selectList");
		System.out.println(guestList);
		
		return guestList;
	}

	public int guestInsert(GuestbookVo guestbookVo) {
		
		int count = sqlSession.insert("guestbook.insert", guestbookVo);
		System.out.println(count + "건 추가되었습니다.");
		
		return count;
	}

	public int delete(int no, String password) {
		
		GuestbookVo guestbookVo = new GuestbookVo(no,password);
		int count = sqlSession.delete("guestbook.delete", guestbookVo);//("namespace.id",parameterType)
		System.out.println(count + "건 삭제되었습니다.");
		
		return count;
	}
}
