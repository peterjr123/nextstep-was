# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* index.html에서 css가 반영되지 않는 문제 발생 -> 크롬 개발자 도구를 사용하여 local에서 브라우저로 index.html을 오픈한 결과와
http로 전송한 index.html을 비교하였음
* 이를 통해서 브라우저가 http로 전송되는 content를 정확히 해석하기 위해서 Content-Type을 지정하지 않은 문제 발견하고 이를 해결하였음.
* index와 관련해서 하드코딩된 부분, 오류가 발생할 가능성이 높아보이는 부분을 몇 발견했다. 프로젝트를 진행해 가면서 이를 개선할 방법들을 찾을
수 있을 것이라 기대한다.
### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* 이번 요구사항에서 제일 신경쓴 부분은 "어떻게 다양한 종류의 http 요청에 대한 처리를 쉽게 추가할 수 있을 것인가?"이다.  
처음에는 html/js/css와 같은 파일들에 대한 요청이었지만, 지금은 POST 요청이 들어오며, 이를 요청하는 url또한 다르다. 앞으로 다양한 요청의 종류가
추가될 수 있을 것이라고 판단하여, 요청을 처리하는 방식을 개선하였다.  
개선하기 위해서 웹 프레임워크에서 url, 파라미터 등, 여러 정보를 통해서 알맞은 controller(handler)로 dispatch하는 것을 떠올리고, HttpRequestDispatcher를 구현하였다.
이 dispatcher로 다양한 strategy 중 하나를 공통된 인터페이스 strategy로 반환하므로써, RequestHandler 클래스에서 일관된 방식으로 요청을 처리할 수 있도록
구현하였다.

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 