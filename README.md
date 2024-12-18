# Tongue_Twist
프로젝트 선정 이유: 원 목표인 음성 인식을 통해 책을 읽기에는 API에서 허용하는 1회 녹음 시간이 20초로, 쪼개어 사용하기 어렵다. 또한, 발음 개선을 위한 발음 인식 API의 정확도가 높지 않고, 시중에 유사한 케이스가 있었으며, 정확한 발음-구강구조 모델이 없어서 진행하기 어려웠다. 따라서, 주어진 환경 내에서 이용할 수 있는 잰말 놀이(Tongue Twister)를 프로젝트 목표로 결정하여 방향을 틀었다.

# 개발 환경
JAVA API 28
Android Studio

# 구성
MainActivity.java + activity_main.xml
Game.java + game.xml
TongueTwisterDB.java

# MainActivity
MainActivity 실행 시 TongueTwisterDB 파일을 자동으로 생성한다.
이후 화면에 한국어와 영어를 선택하는 두 TextView를 통해 intent로 넘길 language 정보를 확보했다.
<img src="https://github.com/user-attachments/assets/653db81c-3fd2-4b72-8852-36cca23a2ccc" width="50%" height="50%"/>
![MainActivity](https://github.com/user-attachments/assets/653db81c-3fd2-4b72-8852-36cca23a2ccc) {: width="50%" height="50%"}
그리고 game.java 액티비티를 실행한다.

# Game.java
game.java 실행 시 화면 기본 구성을 설정하고 기본 버튼을 이어준다.
쿼리를 통해 각 언어에 맞는 무작위의 문장이 선택되고 textview에 표시된다.
api 문서 페이지에 나온 코드를 참조하여 음성 녹음과 저장, 가공, 송신을 처리하고
돌아온 결과 응답 메시지에서 음성 인식 결과 json로 받아 출력한다.

# 한국어 선택 결과 & 영어 선택 결과
![Korean](https://github.com/user-attachments/assets/42208006-b217-4c25-a2a6-4345bc4e38fb){: width="50%" height="50%"}
![English](https://github.com/user-attachments/assets/5a4315a3-a0c1-4e73-bb8e-73afc8236064){: width="50%" height="50%"}

# 결과
api 호출을 제외한 부분에서 정상 작동하나, 제일 중요한 기능에서 알 수 없는 이유로 작동하지 않아 개선이 필요하다.

