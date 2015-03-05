package next.dao;

public class DaoFactory {
	private static AnswerDao answerDao = new AnswerDao();
	private static QuestionDao questionDao = new QuestionDao();
	
	private DaoFactory() {}
	
	public static AnswerDao getAnswerDao() {
		return answerDao;
	}
	
	public static QuestionDao getQuestionDao() {
		return questionDao;
	}
}
