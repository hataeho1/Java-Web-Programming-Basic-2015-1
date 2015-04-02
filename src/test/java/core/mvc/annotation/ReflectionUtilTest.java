package core.mvc.annotation;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtilTest.class);

	@Test
	public void 힌트1_Bean_어노테이션이_붙은_클래스를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Bean.class);
		for (Class<?> each : annotated) {
			logger.debug(each.getName());
		}
	}
	
	@Test
	public void 힌트1_Controller_어노테이션이_붙은_클래스를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		for (Class<?> each : annotated) {
			logger.debug(each.getName());
		}
	}
	
	@Test
	public void 힌트2_힌트1에서_찾은_클래스에서_Inject_어노테이션이_붙은_메소드를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotatedWithBean = reflections.getTypesAnnotatedWith(Bean.class);
		Set<Class<?>> annotatedWithController = reflections.getTypesAnnotatedWith(Controller.class);
		findMethodWithInjectAnnotation(annotatedWithBean);
		findMethodWithInjectAnnotation(annotatedWithController);
	}

	private void findMethodWithInjectAnnotation(Set<Class<?>> annotatedWithBean) {
		for (Class<?> each : annotatedWithBean) {
			for (Method metd : each.getDeclaredMethods()) {
				if(!metd.isAnnotationPresent(Inject.class)) continue;
				logger.debug(metd.getName());
			}
		}
	}
}
