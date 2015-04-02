package core.mvc.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class ReflectionUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtilTest.class);

	@Ignore @Test
	public void 힌트1_Bean_어노테이션이_붙은_클래스를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Bean.class);
		for (Class<?> each : annotated) {
			logger.debug(each.getName());
		}
	}
	
	@Ignore @Test
	public void 힌트1_Controller_어노테이션이_붙은_클래스를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		for (Class<?> each : annotated) {
			logger.debug(each.getName());
		}
	}
	
	@Ignore @Test
	public void 힌트2_힌트1에서_찾은_클래스에서_Inject_어노테이션이_붙은_메소드를_몽땅_찾는다() {
	        Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotatedWithBean = reflections.getTypesAnnotatedWith(Bean.class);
		Set<Class<?>> annotatedWithController = reflections.getTypesAnnotatedWith(Controller.class);
		findMethodWithInjectAnnotation(annotatedWithBean);
		findMethodWithInjectAnnotation(annotatedWithController);
	}

	private void findMethodWithInjectAnnotation(Set<Class<?>> classWIthAnnotated) {
		for (Class<?> each : classWIthAnnotated) {
			for (Method metd : each.getDeclaredMethods()) {
				if(!metd.isAnnotationPresent(Inject.class)) continue;
				logger.debug(metd.getName());
			}
		}
	}
	
	@Test
	public void 힌트3_앞에서생성한_모든클래스_인스턴스를_생성_후_Map에저장__Inject어노테이션있는_Method의_인자에해당하는_인스턴스를_Map에서_찾은후_의존성주입() throws Exception {
		Reflections reflections = new Reflections("core", "next");
		Set<Class<?>> annotatedWithBean = reflections.getTypesAnnotatedWith(Bean.class);
		Set<Class<?>> annotatedWithController = reflections.getTypesAnnotatedWith(Controller.class);
		Map<Class<?>, Object> instanceMap = Maps.newHashMap();
		
		createInstance(annotatedWithBean, instanceMap);
		createInstance(annotatedWithController, instanceMap);
		findMethodWithInjectAnnotation(annotatedWithBean, instanceMap);
		findMethodWithInjectAnnotation(annotatedWithController,instanceMap);
	}

	private void findMethodWithInjectAnnotation(Set<Class<?>> classWithAnnotated, Map<Class<?>, Object> instanceMap) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Class<?> each : classWithAnnotated) {
			for (Method method : each.getDeclaredMethods()) {
				if(!method.isAnnotationPresent(Inject.class)) continue;

				Parameter[] params = method.getParameters();
						
				for (Parameter parameter : params) {
					logger.debug("파라미터    " + parameter.getType());
					logger.debug("Class name        " + method.getDeclaringClass());
					method.invoke(instanceMap.get(each.getDeclaringClass()), instanceMap.get(parameter.getType()));
				}
			}
		}
	}
	
	private void createInstance(Set<Class<?>> classWithAnnotated, Map<Class<?>, Object> instanceMap)
			throws InstantiationException, IllegalAccessException {
		for (Class<?> clazz : classWithAnnotated) {
			instanceMap.put(clazz.getDeclaringClass(), clazz.newInstance());
		}
	}
}
