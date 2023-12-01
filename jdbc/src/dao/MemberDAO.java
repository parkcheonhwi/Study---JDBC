package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vo.MemberVO;

public class MemberDAO {
   public static Long session;
   
//   1. ����
   Connection connection;
//   2. ���� ����
   PreparedStatement preparedStatement;
//   3. ���
   ResultSet resultSet;
   
//   ���̵� �ߺ��˻�
//   ȭ�鿡�� ����ڰ� �Է��� ���̵� ���޹޴´�.
   public boolean checkId(String memberId) {
      String query = "SELECT ID FROM TBL_MEMBER WHERE MEMBER_ID = ?";
//      �ߺ� �� false, �ߺ� ���� �� true
      boolean check = false;
      try {
//         ���� ��ü �ޱ�
         connection = DBConnecter.getConnection();
//         �ۼ��� ���� �����ϱ�
         preparedStatement = connection.prepareStatement(query);
//         ������ ���� �ϼ��ϱ�
         preparedStatement.setString(1, memberId);
//         ��� ���
         resultSet = preparedStatement.executeQuery();
         
//         �� ��������
         resultSet.next();
//         �� ��������
         resultSet.getLong(1);
         
      } catch (SQLException e) {
         System.out.println("checkId(String) SQL�� ����");
//         getLong(1)���� null�� �߻��ϸ� ��� ������ ���̵��̱� ������ check�� true�� ����ش�. 
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
   
   
//   ȸ������
//   executeUpdate()�� �����ϱ�!
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
         
//         INSERT, DELETE, UPDATE �� ������ ���� executeUpdate()�� ����Ѵ�.
//         SELECT�� executeQuery()�� ����Ѵ�.
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
   
   
//   �α���
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
//         �α��� ���� �� �α��ε� ȸ���� ��ȣ�� session(static �ʵ�)�� ����ش�.
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
   
//   �α׾ƿ�
   public void logout() {
      session = null;
   }
   
//   ����������
   public MemberVO findById() {
      String query = "SELECT ID, MEMBER_ID, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_ADDRESS, RECOMMANDER_ID "
            + "FROM TBL_MEMBER WHERE ID = ?";
      MemberVO memberVO = new MemberVO();
      
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
//         �α��� �� ���������� ���񽺸� �̿��� �� �ֱ� ������
//         session�� �ִ� ȸ�� ������ ������������ �ʿ��� ������ ��ȸ�Ѵ�.
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
   
//   ���� ����
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
   
//   ��й�ȣ ����
//   1. ��й�ȣ ã��
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
   
//   2. ���ο� ��й�ȣ ����
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
   
//   ȸ��Ż��
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
   
//   ��õ��
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
   	
//   ���� ��õ�� ���
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
   	
//   ���� ��õ�� ���
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







