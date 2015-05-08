package search;

public class ArticleDetail {
	private String title = "", articleAbstract = "", email = "", keywords = "", 
			filePath = "";
	private int articleID = 0;
	public ArticleDetail(int articleID, String title, String articleAbstract, String email, 
			String keywords, String filePath) {
		this.articleID = articleID;
		this.title = title;
		this.articleAbstract = articleAbstract;
		this.email = email;
		this.keywords = keywords;
		this.filePath = filePath;
	}
	public String getArticleAbstract() {
		return articleAbstract;
	}
	public String getEmail() {
		return email;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getKeywords() {
		return keywords;
	}
	public String getTitle() {
		return title;
	}
	public int getArticleID() {
		return articleID;
	}

}
