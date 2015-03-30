package core.ref;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {

	@Test
	public void run() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;
		for (Method method : clazz.getDeclaredMethods()) {
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().getSimpleName().equals("MyTest")) {
					method.invoke(clazz.newInstance());
				}
			}
		}
	}

	@Test
	public void run2() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(MyTest.class)) {
				method.invoke(clazz.newInstance());
			}
		}
	}
}
