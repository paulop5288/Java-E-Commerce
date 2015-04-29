package Review;

public class Article {
	
	private String articleID = "";
	private String authorID = "";
	private String title = "";
	private String articleAbstract = "";

	public Article(String articleID, String authorID, String title, String articleAbstract) {
		this.articleID = articleID;
		this.title = title;
		this.authorID = authorID;
		this.articleAbstract = articleAbstract;
		
	}
	
	public String getArticleAbstract() {
		return articleAbstract;
	}
	
	public String getArticleID() {
		return articleID;
	}
	
	public String getAuthorID() {
		return authorID;
	}
	
	public String getTitle() {
		return title;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Article " + articleID + " is written by author" + authorID + ".Named as " + title + "\n";
	}

}
