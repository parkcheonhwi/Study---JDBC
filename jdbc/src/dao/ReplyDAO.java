package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.ReplyVO;

public class ReplyDAO {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
//	´ñ±Û µî·Ï
	public void makeReply(ReplyVO replyVO) {
		String query = "INSERT INTO TBL_REPLY VALUES(SEQ_REPLY.NEXTVAL, ?, ?, ?)";
		
		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, replyVO.getReplyContent());
			preparedStatement.setLong(2, replyVO.getPostId());
			preparedStatement.setLong(3, replyVO.getMemberId());
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
	
//	´ñ±Û ¸ñ·Ï
	public List<ReplyVO> findByPostId(Long id) {
		String query = "SELECT ID, REPLY_CONTENT, POST_ID, MEMBER_ID FROM TBL_REPLY WHERE POST_ID = ?";
		ReplyVO replyVO = new ReplyVO();
		ArrayList<ReplyVO> replies = new ArrayList<ReplyVO>();
		
		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				replyVO.setId(resultSet.getLong(1));
				replyVO.setReplyContent(resultSet.getString(2));
				replyVO.setPostId(resultSet.getLong(3));
				replyVO.setMemberId(resultSet.getLong(4));
				replies.add(replyVO);
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
		
		return replies;

	}
	
	
//	´ñ±Û ¼öÁ¤
	public void updateReply(ReplyVO replyVO) {
		String query = "UPDATE TBL_REPLY SET REPLY_CONTENT = ? WHERE POST_ID = ? AND MEMBER_ID = ?";
		
		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, replyVO.getReplyContent());
			preparedStatement.setLong(2, replyVO.getPostId());
			preparedStatement.setLong(3, replyVO.getMemberId());
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
	
//	´ñ±Û »èÁ¦
	public void delete(Long id) {
		String query = "DELETE FROM TBL_REPLY WHERE ID = ?";
		
		try {
			connection = DBConnecter.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
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
	
	
}
