package core.mvc.annotation;

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
}
