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
//      postVO.setPostTitle("�׽�Ʈ ����2");
//      postVO.setPostContent("�׽�Ʈ ����2");
//      
//      
//      MemberDAO memberDAO = new MemberDAO();
//      
//      MemberVO memberVO = new MemberVO();
//      
//      �α���
//      memberVO.setMemberId("hds1234");
//      memberVO.setMemberPassword("1234");
//      
//      if(memberDAO.login(memberVO)) {
//         System.out.println("�α��� ����");
//         postDAO.insert(postVO);
//         
//      }else {
//         System.out.println("�α��� ����");
//      }
//      
//      
////      ����������
//      System.out.println(memberDAO.findById());
//      
//      memberDAO.logout();
//      System.out.println(memberDAO.session);
      
//      ȸ������
//      memberVO.setMemberId("hds1234");
//      memberVO.setMemberPassword("1234");
//      memberVO.setMemberName("�ѵ���");
//      memberVO.setMemberAddress("��⵵ �����ֽ�");
//      
//      if(memberDAO.checkId(memberVO.getMemberId())) {
//         System.out.println("��� ������ ���̵�");
//         
//         memberDAO.join(memberVO);
//         System.out.println("ȸ������ ����");
//         
//      }else {
//         System.out.println("�ߺ��� ���̵�");
//      }
   }
}














