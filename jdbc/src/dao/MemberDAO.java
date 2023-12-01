package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vo.MemberVO;

public class MemberDAO {
   public static Long session;
   
//   1. 연결
   Connection connection;
//   2. 쿼리 실행
   PreparedStatement preparedStatement;
//   3. 결과
   ResultSet resultSet;
   
//   아이디 중복검사
//   화면에서 사용자가 입력한 아이디를 전달받는다.
   public boolean checkId(String memberId) {
      String query = "SELECT ID FROM TBL_MEMBER WHERE MEMBER_ID = ?";
//      중복 시 false, 중복 없을 시 true
      boolean check = false;
      try {
//         연결 객체 받기
         connection = DBConnecter.getConnection();
//         작성한 쿼리 전달하기
         preparedStatement = connection.prepareStatement(query);
//         전달한 쿼리 완성하기
         preparedStatement.setString(1, memberId);
//         결과 담기
         resultSet = preparedStatement.executeQuery();
         
//         행 가져오기
         resultSet.next();
//         열 가져오기
         resultSet.getLong(1);
         
      } catch (SQLException e) {
         System.out.println("checkId(String) SQL문 오류");
//         getLong(1)에서 null이 발생하면 사용 가능한 아이디이기 때문에 check에 true를 담아준다. 
         check = true;
         e.printStackTrace();
      } finally {
         try {
            if(resultSet != null) {
               resultSet.close();
            }
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
         }
      }
      
      return check;
   }
   
   
//   회원가입
//   executeUpdate()로 실행하기!
   public void join(MemberVO memberVO) {
      String query = "INSERT INTO TBL_MEMBER "
            + "(ID, MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_ADDRESS) "
            + "VALUES(SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?)";
      
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, memberVO.getMemberId());
         preparedStatement.setString(2, memberVO.getMemberPassword());
         preparedStatement.setString(3, memberVO.getMemberName());
         preparedStatement.setString(4, memberVO.getMemberAddress());
         
//         INSERT, DELETE, UPDATE 세 가지는 전부 executeUpdate()를 사용한다.
//         SELECT만 executeQuery()를 사용한다.
         preparedStatement.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
         }
      }
   }
   
   
//   로그인
   public boolean login(MemberVO memberVO) {
      String query = "SELECT ID FROM TBL_MEMBER WHERE MEMBER_ID = ? AND MEMBER_PASSWORD = ?";
      connection = DBConnecter.getConnection();
      boolean check = true;
      try {
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, memberVO.getMemberId());
         preparedStatement.setString(2, memberVO.getMemberPassword());
         
         resultSet = preparedStatement.executeQuery();
         
         resultSet.next();
//         로그인 성공 시 로그인된 회원의 번호를 session(static 필드)에 담아준다.
         session = resultSet.getLong(1);
         
      } catch (SQLException e) {
         e.printStackTrace();
         check = false;
      } finally {
         try {
            if(resultSet != null) {
               resultSet.close();
            }
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
         }
      }
      
      return check;
   }
   
//   로그아웃
   public void logout() {
      session = null;
   }
   
//   마이페이지
   public MemberVO findById() {
      String query = "SELECT ID, MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_ADDRESS, RECOMMANDER_ID "
            + "FROM TBL_MEMBER WHERE ID = ?";
      MemberVO memberVO = new MemberVO();
      
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
//         로그인 후 마이페이지 서비스를 이용할 수 있기 때문에
//         session에 있는 회원 정보로 마이페이지에 필요한 정보를 조회한다.
         preparedStatement.setLong(1, session);
         resultSet = preparedStatement.executeQuery();
         
         resultSet.next();
         memberVO.setId(resultSet.getLong(1));
         memberVO.setMemberId(resultSet.getString(2));
         memberVO.setMemberPassword(resultSet.getString("MEMBER_PASSWORD"));
         memberVO.setMemberName(resultSet.getString(4));
         memberVO.setMemberAddress(resultSet.getString(5));
         memberVO.setRecommenderId(resultSet.getString(6));
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(resultSet != null) {
               resultSet.close();
            }
            if(preparedStatement != null) {
               preparedStatement.close();
            }
            if(connection != null) {
               connection.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
         }
      }
      
      return memberVO;
   }
   
//   정보 수정
   public void update(MemberVO memberVO) {
	   String query = "UPDATE TBL_MEMBER SET MEMBER_ID = ?, MEMBER_PASSWORD = ?, MEMBER_NAME = ?, MEMBER_ADDRESS = ? WHERE ID = ?";
	   try{
		   connection = DBConnecter.getConnection();
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setString(1, memberVO.getMemberId());
		   preparedStatement.setString(2, memberVO.getMemberPassword());
		   preparedStatement.setString(3, memberVO.getMemberName());
		   preparedStatement.setString(4, memberVO.getMemberAddress());
		   preparedStatement.setLong(5, session);
		   
	   } catch(SQLException e) {
		   e.printStackTrace();
	   } finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	          }
	    }
   }
   
//   비밀번호 변경
//   1. 비밀번호 찾기
   public void findPassword(MemberVO memberVO) {
	   String query = "UPDATE TBL_MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_ID = ?";
	   try {
		connection = DBConnecter.getConnection();
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setString(1, memberVO.getMemberPassword());
		   preparedStatement.setString(2, memberVO.getMemberId());
	   } catch (SQLException e) {
		   e.printStackTrace();
	   } finally {
		   try {
			   if(preparedStatement != null) {
				   preparedStatement.close();
			   }
			   if(connection != null) {
				   connection.close();
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
			   throw new RuntimeException();
		   }
	   }
   }
   
//   2. 새로운 비밀번호 변경
   public boolean checkPassword(String memberPassword) {
	   String query = "SELECT ID FROM TBL_MEMBER WHERE MEMBER_ID = (SELECT MEMBER_ID WHERE ID = ?) AND MEMBER_PASSWORD = ?";
	   boolean check = true;
	   connection = DBConnecter.getConnection();
	   try {
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setLong(1, session);
		   preparedStatement.setString(2, memberPassword);
		   
		   resultSet = preparedStatement.executeQuery();
		   resultSet.next();
		   resultSet.getLong(1);
		   
	   } catch (SQLException e) {
		   e.printStackTrace();
		   check = false;
	   } finally {
	         try {
	             if(resultSet != null) {
	                resultSet.close();
	             }
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	          }
	   }
	   
	   return check;
	   
   }
   
   public void changePassword(String memberPassword) {
	   String query = "UPDATE TBL_MEMBER SET MEMBER_PASSWORD = ? WHERE ID = ?";
	   try {
		   connection = DBConnecter.getConnection();
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setString(1, memberPassword);
		   preparedStatement.setLong(2, session);
		   preparedStatement.executeUpdate();
	   } catch (SQLException e) {
		   e.printStackTrace();
	   } finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	         }
	   }
   }
   
//   회원탈퇴
   public void delete() {
	   String query = "DELETE FROM TBL_MEMBER WHERE ID = ?";
	   try {
		connection = DBConnecter.getConnection();
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setLong(1, session);
		   preparedStatement.executeUpdate();
	   } catch (SQLException e) {
		   e.printStackTrace();
	   } finally {
	         try {
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	         }
	   }
   }
   
//   추천수
   	public int getCountOfRecommender(String memberId) {
   		String query = "SELECT COUNT(ID) WHERE RECOMMENDER_ID = ?";
   		int recommenderCount = 0;
   		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, memberId);
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			recommenderCount = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	         try {
	             if(resultSet != null) {
	                resultSet.close();
	             }
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	          }
	    }
   		
   		return recommenderCount;
   	}
   	
//   나를 추천한 사람
   	public ArrayList<MemberVO> getRecommenders(){
   		String query = "SELECT ID, MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_ADDRESS FROM TBL_MEMBER WHERE RECOMMENDER_ID = (SELECT MEMBER_ID FROM TBL_MEMBER WHERE ID = ?)";
   		MemberVO memberVO = new MemberVO();
   		ArrayList<MemberVO> members = new ArrayList<MemberVO>();
   		
   		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, session);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				memberVO.setId(resultSet.getLong(1));
			    memberVO.setMemberId(resultSet.getString(2));
			    memberVO.setMemberPassword(resultSet.getString("MEMBER_PASSWORD"));
			    memberVO.setMemberName(resultSet.getString(4));
			    memberVO.setMemberAddress(resultSet.getString(5));
			    
			    members.add(memberVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	         try {
	             if(resultSet != null) {
	                resultSet.close();
	             }
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	          }
	    }
   		
   		return members;
   	}
   	
//   내가 추천한 사람
   	public MemberVO getMyRecommender() {
   		String query = "SELECT ID, MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_ADDRESS FROM TBL_MEMBER "
   				+ "WHERE MEMBER_ID = (SELECT RECOMMENDER_ID FROM TBL_MEMBER WHERE ID = ?)";
   		MemberVO memberVO = new MemberVO();
   		
   		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, session);
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			memberVO.setId(resultSet.getLong(1));
			memberVO.setMemberId(resultSet.getString(2));
			memberVO.setMemberPassword(resultSet.getString(3));
			memberVO.setMemberName(resultSet.getString(4));
			memberVO.setMemberAddress(resultSet.getString(5));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	         try {
	             if(resultSet != null) {
	                resultSet.close();
	             }
	             if(preparedStatement != null) {
	                preparedStatement.close();
	             }
	             if(connection != null) {
	                connection.close();
	             }
	          } catch (SQLException e) {
	             e.printStackTrace();
	             throw new RuntimeException();
	          }
	    }
   		
   		return memberVO;
	    
   		
   	}
   
}







