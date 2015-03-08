package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.DaoFactory;
import next.dao.QuestionDao;
import next.model.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteQuestionController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(DeleteQuestionController.class);
	
	private AnswerDao answerDao =  DaoFactory.getAnswerDao();
	private QuestionDao questionDao =  DaoFactory.getQuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		String memberName = ServletRequestUtils.getRequiredStringParameter(request, "memberName");
		
		ModelAndView mav = jsonView();
		
		if(!delete(questionId, memberName)) {
			mav.addObject("errorMessage", "질문 삭제를 하기위한 권한이 부족합니다");
		}
		
		return mav;
	}

	boolean delete(Long questionId, String memberName) {
		Question question = questionDao.findById(questionId);
		if(!question.isDeteteAvailable(memberName, question, answerDao))
			return false;

		questionDao.delete(questionId);
		logger.debug("Question Deleted - questionId : {}", questionId);
		return true;
	}
}
