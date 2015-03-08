package next.model;

import java.util.Date;
import java.util.List;

import next.dao.AnswerDao;

public class Question {
	private long questionId;
	
	private String writer;
	
	private String title;
	
	private String contents;
	
	private Date createdDate;
	
	private int countOfComment;
	
	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}	
	
	public Question(long questionId, String writer, String title, String contents,
			Date createdDate, int countOfComment) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfComment = countOfComment;
	}

	public long getQuestionId() {
		return questionId;
	}
	
	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public int getCountOfComment() {
		return countOfComment;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer
				+ ", title=" + title + ", contents=" + contents
				+ ", createdDate=" + createdDate + ", countOfComment="
				+ countOfComment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
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
		Question other = (Question) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}

	public boolean hasNoAnswer() {
		if(countOfComment == 0) return true;
		return false;
	}
	
	public boolean hasQuestionOwnership(String member){
		if(!member.equals(writer)) return false;
		return true;
	}

	public boolean hasAnotherAnswerWriter(AnswerDao answerDao) {
		List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
		for(int i = 0; i < answerList.size(); i++) {
			if(!answerList.get(i).getWriter().equals(writer)) return true;
		}
		return false;
	}
	
	public boolean isDeteteAvailable(String memberName, Question question, AnswerDao answerDao) {
		// TODO 메소드를 순서에 따라 호출하지 않으면 오류가 발생할 수 있다는건 코드를 잘못작성한것 같다는 생각이 든다.
		// 그런데 질문을 삭제하는 기능을 구현하는데에 있어 메소드 호출 순서에 영향을 받지 않는 구조를 생각하려니까
		// 좋은 생각이 떠오르지 않았다. 다른코드를 구경하던 중 이러한 고민에 대한 해결책을 발견했다. -> http://git.io/pvW4
		// 함수의 return값으로 다시 함수를 호출하는 구조인데 괜찮은것 같다는 생각이 든다.
		// 메소드 호출 순서에 영향을 받는다는 문제점을 직접적으로 해결하지는 못했지만
		// 특정 기능을 수행하기 위한 부가적인 메소드는 private로 감추고
		// public으로는 1개의 메소드만 노출시켜서 메소드 사용자는 메소드 호출 순서에 따른 영향을 신경쓰지 않아도 되는 좋은 방법인것 같다  
		
		if(question.hasNoAnswer()) return true;
		if(question.hasNoAnswer() && !question.hasAnotherAnswerWriter(answerDao)) return false;
		if(question.hasAnotherAnswerWriter(answerDao)) return false;
		if(!question.hasQuestionOwnership(memberName)) return false;
		return true;
	}
}
