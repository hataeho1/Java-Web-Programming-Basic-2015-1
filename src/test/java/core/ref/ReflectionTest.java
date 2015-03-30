package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import next.model.Question;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
	
	@Test
	public void showClass() {
		logger.debug("###show classes");
		Class<Question> clazz = Question.class;
		logger.debug(clazz.getName());
	}
	
	@Test
	public void getAllFileds() throws Exception {
		logger.debug("###show All Fileds");
		Class<Question> clazz = Question.class;
		for (Field filed : clazz.getDeclaredFields()) {
			logger.debug(filed.getName());
		}
	}
	
	@Test
	public void getAllConstructer() throws Exception {
		logger.debug("###show All Constructer");
		Class<Question> clazz = Question.class;
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			logger.debug(constructor.getName());
		}
	}
	
	@Test
	public void getAllMethod() throws Exception {
		logger.debug("###show All Method");
		Class<Question> clazz = Question.class;
		for (Method methods : clazz.getDeclaredMethods()) {
			logger.debug(methods.getName());
		}
	}
}
