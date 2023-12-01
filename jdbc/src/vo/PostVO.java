package vo;

public class PostVO {
   private Long id;
   private String postTitle;
   private String postContent;
   private Long memberId;
   
   public PostVO() {;}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPostTitle() {
      return postTitle;
   }

   public void setPostTitle(String postTitle) {
      this.postTitle = postTitle;
   }

   public String getPostContent() {
      return postContent;
   }

   public void setPostContent(String postContent) {
      this.postContent = postContent;
   }

   public Long getMemberId() {
      return memberId;
   }

   public void setMemberId(Long memberId) {
      this.memberId = memberId;
   }

   @Override
   public String toString() {
      return "PostVO [id=" + id + ", postTitle=" + postTitle + ", postContent=" + postContent + ", memberId="
            + memberId + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      PostVO other = (PostVO) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }
}