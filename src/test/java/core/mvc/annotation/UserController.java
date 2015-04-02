package core.mvc.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserDao userDao;
	
	@Inject
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		logger.debug("오호 이 메시지가 뜨면 Reflection으로 의존성 주입에 성공을 한거야");
	}
	
	@RequestMapping("/users/updateForm.next")
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");
		User user = userDao.findById(userId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping(value="/users/update.next", method=RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// update user
		return null;
	}
}
