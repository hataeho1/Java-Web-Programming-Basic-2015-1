package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.DaoFactory;
import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ApiListController extends AbstractController {
	private QuestionDao questionDao =  DaoFactory.getQuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Question> questions = questionDao.findAll();
		
		ModelAndView mav = jsonView();
		mav.addObject("questions", questions);
		return mav;
	}
}
