package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.PostDTO;
import vo.PostVO;

public class PostDAO {
   Connection connection;
   PreparedStatement preparedStatement;
   ResultSet resultSet;
   
   
//   게시글 등록
   public void insert(PostVO postVO) {
      String query = "INSERT INTO TBL_POST VALUES(SEQ_POST.NEXTVAL, ?, ?, ?)";
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, postVO.getPostTitle());
         preparedStatement.setString(2, postVO.getPostContent());
         preparedStatement.setLong(3, MemberDAO.session);
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
            throw new RuntimeException(e);
         }
      }
   }
   
//   게시글 전체 목록
   public List<PostDTO> selectAll(){
      String query = "SELECT P.ID, POST_TITLE, POST_CONTENT, M.MEMBER_ID, M.MEMBER_NAME "
            + "FROM TBL_MEMBER M JOIN TBL_POST P "
            + "ON M.ID = P.MEMBER_ID";

      ArrayList<PostDTO> posts = new ArrayList<PostDTO>();
      PostDTO postDTO = new PostDTO();
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         resultSet = preparedStatement.executeQuery();
         while(resultSet.next()) {
            postDTO.setId(resultSet.getLong(1));
            postDTO.setPostTitle(resultSet.getString(2));
            postDTO.setPostContent(resultSet.getString(3));
            postDTO.setMemberIdentification(resultSet.getString(4));
            postDTO.setMemberName(resultSet.getString(5));
            
            posts.add(postDTO);
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
            throw new RuntimeException(e);
         }
      }
      
      return posts;
      
   }
   
//   게시글 조회(회원의 이름까지 조회)
   public PostDTO findById(Long id) {
      String query = "SELECT P.ID, POST_TITLE, POST_CONTENT, M.MEMBER_ID, M.MEMBER_NAME "
            + "FROM TBL_MEMBER M JOIN TBL_POST P "
            + "ON M.ID = P.MEMBER_ID AND P.ID = ?";
      PostDTO postDTO = new PostDTO();
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setLong(1, id);
         resultSet = preparedStatement.executeQuery();
         
         resultSet.next();
            
         postDTO.setId(resultSet.getLong(1));
         postDTO.setPostTitle(resultSet.getString(2));
         postDTO.setPostContent(resultSet.getString(3));
         postDTO.setMemberIdentification(resultSet.getString(4));
         postDTO.setMemberName(resultSet.getString(5));
         
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
            throw new RuntimeException(e);
         }
      }
      
      return postDTO;
      
   }
   
//   게시글 수정(제목과 내용만 수정 가능)
   public void update(PostVO postVO) {
      String query = "UPDATE TBL_POST SET POST_TITLE = ?, POST_CONTENT = ? WHERE ID = ?";
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, postVO.getPostTitle());
         preparedStatement.setString(2, postVO.getPostContent());
         preparedStatement.setLong(3, postVO.getId());
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
            throw new RuntimeException(e);
         }
      }
   }
   
   
//   게시글 삭제
   public void delete(Long id) {
      String query = "DELETE FROM TBL_POST WHERE ID = ?";
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
            throw new RuntimeException(e);
         }
      }
   }
   
//   게시글 삭제
   public void deleteByMemberId() {
      String query = "DELETE FROM TBL_POST WHERE MEMBER_ID = ?";
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         preparedStatement.setLong(1, MemberDAO.session);
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
            throw new RuntimeException(e);
         }
      }
   }
   
//   게시글 목록 시 댓글 수까지 화면에 출력
   public List<PostDTO> getListWithReplyCount() {
      String query = "SELECT P.ID, P.POST_TITLE, P.POST_CONTENT, M.MEMBER_ID, M.MEMBER_NAME, NVL(REPLY_COUNT, 0) REPLY_COUNT FROM TBL_POST P "
            + "LEFT OUTER JOIN "
            + "("
            + "   SELECT POST_ID, COUNT(ID) REPLY_COUNT FROM TBL_REPLY "
            + "   GROUP BY POST_ID"
            + ") R "
            + "ON P.ID = R.POST_ID "
            + "JOIN TBL_MEMBER M "
            + "ON P.MEMBER_ID = M.ID";
      
//      PostVO는 POST 테이블의 컬럼 개수와 동일해야 한다.
//      join 진행 후 여러 컬럼들을 담아줄 때에는 VO를 수정하지 않고 DTO를 새롭게 만들어서 사용한다.
      ArrayList<PostDTO> posts = new ArrayList<PostDTO>();
      
      try {
         connection = DBConnecter.getConnection();
         preparedStatement = connection.prepareStatement(query);
         resultSet = preparedStatement.executeQuery();
         
         
         while(resultSet.next()) {
        	PostDTO postDTO = new PostDTO();
            postDTO.setId(resultSet.getLong(1));
            postDTO.setPostTitle(resultSet.getString(2));
            postDTO.setPostContent(resultSet.getString(3));
            postDTO.setMemberIdentification(resultSet.getString(4));
            postDTO.setMemberName(resultSet.getString(5));
            postDTO.setReplyCount(resultSet.getInt(6));
            
            posts.add(postDTO);
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
            throw new RuntimeException(e);
         }
      }
      
      return posts;
   }
}

















