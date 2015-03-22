package next.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import org.junit.Test;
import org.mockito.Mockito;

public class DeleteQuestionControllerTest {
	DeleteQuestionController deleteQuestionController = new DeleteQuestionController();
	QuestionDao questionDao = Mockito.mock(QuestionDao.class);
	AnswerDao answerDao = Mockito.mock(AnswerDao.class);

	@Test
	public void deleteWhenAnswerSizeZero_답변이없으면_삭제가능() {
		long questionId = 10;
		Mockito.when(questionDao.findById(questionId)).thenReturn(new Question("javajigi", "javascript 학습하기 좋은 라이브러리를 추천한다면...", "이번 slipp에서 진행한 ... 그런 라이브러리면 딱 좋겠다."));

		// TODO sysout 제거
		// 질문) Mock을 만든다는게 진짜로 내가 이거 메소드 실행하면 이거 나온다고 가정해줘 라는 건가? -> 맞다
		assertTrue(deleteQuestionController.delete(questionId, "javajigi", answerDao, questionDao));
	}

	@Test
	public void 답변이_존재하더라도_질문의_글쓴이와_같은경우_삭제할_수_있다() throws Exception {
		int COUNT_OF_COMMENT = 1;
		Question question = new Question(1, "javajigi", "제목", "내용", new Date(), COUNT_OF_COMMENT);
		Mockito.when(questionDao.findById(1)).thenReturn(question);

		// JAVA 7부터 지원되는 <> (다이아몬드 연산자)
		// JDK 6 이전에는 Generic 사용시 앞 뒤 모두 데이터 타입을 지정해주어야 했으나
		// JDK 7 부터는 컴파일러가 타입유추를 통해 자동으로 캐스팅 코드를 추가하기 때문에
		// 생성자 영역의 타입 파라미터들은 <>로 대체 가능
		List<Answer> answerList = new ArrayList<>();
		answerList.add(new Answer("javajigi", "댓글내용", 1));
		Mockito.when(answerDao.findAllByQuestionId(1)).thenReturn(answerList);

		assertTrue(deleteQuestionController.delete(1L, "javajigi", answerDao, questionDao));
	}
	
	@Test
	public void 글쓴이가_아니면_삭제_불가() throws Exception {
		Mockito.when(questionDao.findById(1L)).thenReturn(new Question("javajigi", "제목", "내용"));
		assertFalse(deleteQuestionController.delete(1L, "FakeJavajigi", answerDao, questionDao));
	}
	
	@Test
	public void 질문의_글쓴이와_다른_사람이_답변을_추가한_경우_삭제할_수_없다() throws Exception {
		int COUNT_OF_COMMENT = 1;
		Question question = new Question(1, "javajigi", "제목", "내용", new Date(), COUNT_OF_COMMENT);
		Mockito.when(questionDao.findById(1)).thenReturn(question);
		
		List<Answer> answerList = new ArrayList<>();
		answerList.add(new Answer("javajigi", "댓글내용", 1));
		Mockito.when(answerDao.findAllByQuestionId(1)).thenReturn(answerList);
		
		assertFalse(deleteQuestionController.delete(1L, "FakeJavajigi", answerDao, questionDao));
	}
}
