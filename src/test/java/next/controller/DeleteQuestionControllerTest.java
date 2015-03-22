package next.controller;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
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
		// 아 이렇게 하니까 실존하는 DAO (deleteQuestionController에 의해 생성된 DAO가 동작해서 실제 데이터를 건들이게 되는 것 같다)
		// 질문 1. Mock을 만든다는게 진짜로 내가 이거 메소드 실행하면 이거 나온다고 가정해줘 라는 건가?
		// 질문 2. 성호&부용 코드를 보면 dao를 넘긴다. 솔직히 dao를 넘길꺼라고는 생각지 못했다.
		// 교수님께서 단위 테스트 혹은 TDD로 개발해가면 좀더 객체지향적으로 코드를 작성하기 쉽고
		// 또 테스트가 어려운 코드는 좋지 않을 코드라고 했기에 dao가 넘어가게 변하는거에 대해서 처음봐서 낮설기는 하지만
		// 뭔가 크게 동의를 못하겠다는 생각은 없다만
		// 만약 해당 class내에서 dao가 많이 쓰일텐데 성호&부용코드가 테스트를 편하게 하기 위해서 dao를 주입하게 되던데
		// 이렇게 되면 어떤 클래스 내에서는 멤버 변수로 DAO를 못묶어 두고 다 메소드 안에 지역변수로 뺴주어야 해서 오히려 코드의 복잡도를 올려버리는거 아닌가? 
		System.out.println(deleteQuestionController.delete(questionId, "javajigi"));
	}

}
