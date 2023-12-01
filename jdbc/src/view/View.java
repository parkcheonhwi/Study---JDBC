package view;

import dao.MemberDAO;
import dao.PostDAO;
import vo.MemberVO;
import vo.PostVO;

public class View {
   public static void main(String[] args) {
	   
//      PostVO postVO = new PostVO();
      PostDAO postDAO = new PostDAO();
      System.out.println(postDAO.getListWithReplyCount());
//      
//      postVO.setPostTitle("테스트 제목2");
//      postVO.setPostContent("테스트 내용2");
//      
//      
//      MemberDAO memberDAO = new MemberDAO();
//      
//      MemberVO memberVO = new MemberVO();
//      
//      로그인
//      memberVO.setMemberId("hds1234");
//      memberVO.setMemberPassword("1234");
//      
//      if(memberDAO.login(memberVO)) {
//         System.out.println("로그인 성공");
//         postDAO.insert(postVO);
//         
//      }else {
//         System.out.println("로그인 실패");
//      }
//      
//      
////      마이페이지
//      System.out.println(memberDAO.findById());
//      
//      memberDAO.logout();
//      System.out.println(memberDAO.session);
      
//      회원가입
//      memberVO.setMemberId("hds1234");
//      memberVO.setMemberPassword("1234");
//      memberVO.setMemberName("한동석");
//      memberVO.setMemberAddress("경기도 남양주시");
//      
//      if(memberDAO.checkId(memberVO.getMemberId())) {
//         System.out.println("사용 가능한 아이디");
//         
//         memberDAO.join(memberVO);
//         System.out.println("회원가입 성공");
//         
//      }else {
//         System.out.println("중복된 아이디");
//      }
   }
}














