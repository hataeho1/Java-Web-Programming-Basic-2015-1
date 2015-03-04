##### 2. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* Servlet Container인 톰캣은 WebServerLauncher에 의해 실행되자 마자 아래와 같은 순서를 거쳐 웹 어플리케이션 초기화 과정을 진행한다.

	1. 배포서술자(web.xml)을 읽는다
	2. Servlet Context 객체를 만든다
	3. web.xml에 작성되어 있던 초기화 파라미터들을 객체에 설정한다
	4. 서블릿 컨테이너는 컨텍스트가 초기화 또는 종료 될때 이벤트를 발생시키는데 이때 listner로 등록된 객체들에게 이벤트가 발생했음을 알려준다.
	이 이벤트가 발생했음을 통보 받고싶은 객체는 listner로 미리 등록을 해 두어야 하는데 그 등록은 @WebListener 어노테이션을 이용해서 할 수 있다.<br>
	(현재 next.support.context.ContextLoaderListener에 @WebListener 어노테이션이 작성되어 있다)
	5. 서블릿 컨테이너는 컨텍스트가 초기화되는 시점, 즉 3번 과정이 완료되는 시점에 이벤트를 발생시키고 그 시점에 listener로 등록되어 있는 listener의 contextInitialized() 메소드를 실행시킨다. -> 이 과정에서 DB 초기화를 진행한다.
	
	6. 두가지 경우로 나뉨<br>
	
		만약 load-on-startup이 0 이 아닌 양수로 설정되어 있다면<br>
			-> 모든 서블릿 클래스들을 찾고 로드하여 init() 메소드를 실행시킨다.
		<br><br>
		만약 load-on-startup이 0으로 설정되어 있거나 아예 설정한 적이 없는 경우<br>
			-> 최초로 해당 서블릿에 대한 요청이 들어왔을떄 init() 메소드가 실행된다.

		(7번 과정을 진핸하기 전에는 어떠한 경우에도 각 서블릿에 대해 init()메소드가 실행된 적이 있어야 합니다)<br>

	7. 서블릿 컨테이너(톰캣)에 클라이언트가 요청을 보내면 서블릿 컨테이너는 init()메소드에 의해 초기화된 서블릿의 service() 메소드를 실행시킵니다.

	8. service() 메소드는 각 요청에 대해 새로운 스레드를 만들고 clinet의 request 방식(Get, Post 등..)을 확인하여 doXXX() 메소드를 실행시킵니다.

##### 3. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
(이미 DispatcherServlet의 init()메소드가 호출되어 초기화 되어 있는 상태라고 가정하자)

1. core.mvc 패키지 내의 DispatcherServlet 클래스에서 service 메소드가 클라이언트가 요청한 자원의 주소를 받는다
	
	```
	String requestUri = req.getRequestURI();
	```

2. 이후 RequestMapping 클래스의 findController 메소드를 사용해 클라이언트가 요청한 주소에 대한 응답을 처리해줄 (Controller 인터페이스와 AbstractController 상속받아 구현된)컨트롤러 객체를 찾아서 controller 변수에 담아둔다.
	
	```
	Controller controller = rm.findController(urlExceptParameter(req.getRequestURI()));

	urlExceptParameter() 메소드는 파라미터를 제거해주는 역할을 한다.
	```

3. controller 변수에 묶여있는 컨트롤러 객체가 가지고 있는 execute 메소드를 실행한다.
그 결과 mav라는 변수에는 요청에 대한 model과 view가 담긴 객체가 담기게 된다.

	```
	mav = controller.execute(req, resp);
	```

4. 이후 View 인터페이스를 구현한 클래스들의 render 메소드들이 실행되면 미리 생성되었던 view 객체의 필드에 생성자에 의해 미리 초기화 되어있던 viewName으로 forward하게 됨으로써 질문 목록이 보이게 된다

	```
	view.render(mav.getModel(), req, resp);
	```

##### 10. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 





---
####해설<br>
#####1번. ContextLoaderListener 관련 문제

[ Servlet Context 란? ]

하나의 웹 어플리케이션 내에는 하나의 Servlet Context가 존재한다.<br>
(단, 앱이 하나의 JVM에서 돌아갈 경우에 한정)

이 Servlet Context는 전체 웹 애플리케이션에서 정보 공유가 가능, 즉 접근이 가능하다는 특징이 있다.
이 Servlet Context는 컨테이너가 컨텍스트 초기화 파라미터를 읽어들여 파라미터를 인자로 하여 인스턴스화 시킴으로써 생성된다.<br>
(초기화 파라미터 = WEB-INF/web.xml내 <context-param></context-param>)

[ Servlet Context Listener ]<br>
서블릿 컨테이너는 리스너로 등록된 객체들의 contextInitialized() 메소드를 ServletContextEvent를 인자로 사용하여 호출합니다.

그러기 위해서는 서블릿 컨테이너는 어떤 리스너가 등록되어 있는지 알고 있어야 하는데, 서블릿 컨테이너에게 개발자가 어떤 리스너가 있는지 알려주는 방법은 web.xml 파일에 <listener> 항목을 추가해 주는 것이다.<br>
(서블릿 컨테이너는 시작시 항상 web.xml파일을 읽기 때문)<br>
(여기서 리스너는 ServletContextListener 인터페이스를 구현한 next.support.context.ContextLoaderListener)

그래서 과제로 제공받은 web.xml 을 살펴보면 아래코드가 주석으로 남아있음을 확인할 수 있다.<br>
```
<listener>
	<listener-class>next.support.init.ContextLoaderListender</listener-class>
	(클래스명의 패키지명이 조금 다르긴 하지만.. 문제를 내기 위해 수정하시던 중 놓치신 것으로 추정된다)
</listener>
```

[결론]<br>
1번문제를 풀기위해서는 어떻게든 next.support.context.ContextLoaderListener 클래스의 contextInitialized() 메소드가 실행되도록 만들어야 한다.

[방법 1]<br>
그래서 1번 문제를 해결하기 위해서는 단순히 ```<listener> ...(생략) ... </listener>``` 의 주석을 해제해 주어도 되겠지만

[방법 2]<br>
Annotation을 이용한 더 쉬운 리스너 등록방법이 있어서 아래 방법을 사용하기로 했다.

```
@WebListener 어노테이션을 ServletContextListener 인터페이스를 구현한
next.support.context.ContextLoaderListener에 붙여준다 
```


##### 2번. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

위 답안 참고

##### 3번. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

위 답안 참고

##### 4번. Servlet init() 메소드 호출 시점 관련 문제
최초 접근하는 시점에 DispatcherServlet의 init() 메서드가 호출되어 RequestMapping이 초기화된다.<br>
이와 같이 서 비스할 경우 서버 시작 후 동시 접속자가 많을 경우 성능상 이슈가 발생할 수 있다. -> Init() 메소드가 실행되는 동안 대기열 발생<br>
Tomcat 서버를 시작하는 시점에 DispatcherServlet의 init() 메서드가 호출되어 초기화 가능하도록 설정한다.<br>

Servlet의 @WebServlet 어노테이션에 loadOnStartup=1 속성을 넣어주면 됨<br>
0 또는 설정값이 없을 때 = 최초요청이 들어왔을때 서블릿 초기화( init()메소드 호출 )<br>
1 이상 = 0보다 큰값중에서 가장 낮은 수가 가장먼저 초기화 진행<br>

만약 어노테이션 기반이 아니라 web.xml에 설정해주고 싶다면 아래와 같이 한다

```
예시 코드
<servlet>
    <servlet-name>Servlet</servlet-name>
    <display-name>Apache Servlet</display-name>
    <servlet-class>com.http.FrameworkServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet> 
```

##### 5번. 질문하기 기능 추가 문제
1. 우선 form 태그의 action속성에서 어느 uri를 가리키고 있는지 확인했음
2. RequestMapping class로 가서 mapping Map에 주소 "/save.next" 와 컨트롤러 SaveController() 추가해줌

##### 6번. 필터 관련 문제
Filter 인터페이스 구현체 CharacterEncodingFilter에 @WebFilter(urlPatterns="/*") annotation 적용

##### 7번. 스클립틀릿 JSTL 및 EL로 변환 관련 문제

해설 생략

##### 8번. AJAX 답변하기 기능 관련 문제
1. RequestMapping class로 가서 mapping Map에 주소 "/api/addanswer.next" 와 컨트롤러 AnswerController() 추가해줌
2. AnswerController() 작성
3. DAO 메소드 하나 추가
