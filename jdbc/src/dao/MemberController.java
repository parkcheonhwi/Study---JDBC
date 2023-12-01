package dao;

public class MemberController {
	MemberDAO memberDAO = new MemberDAO();
	PostDAO postDAO = new PostDAO();
	
	public void withdraw() {
		try {
			postDAO.deleteByMemberId();
			memberDAO.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
