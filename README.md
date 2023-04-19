# Howon-Vaccine
##### Howon Univ, Application develop (2019)
##### 2022/11/02 호원대 커플링 사업단 앱개발 (2019)
##### Howon Vaccine은 휴대폰에 저장된 백신 접종 정보를 NFC를 통해 간단하게 전송 & 인증 할 수 있도록 도와주는 애플리케이션입니다.
##### NFC기술을 사용해 영업시설이나 공공기관등 유동인구가 많은 곳에서 빠르고 원활하게 백신 접종 여부를 확인할 수 있습니다.
<p align="center">
<img src=https://user-images.githubusercontent.com/118334518/232955426-dc02f0c2-49de-48d8-8a49-067882a69bc2.jpg width="50%" height="50%"> </p>
</br></br>



# <span style="color:yellow">안드로이드 클라이언트</span>
+ #####  안드로이드 클라이언트에서는 Cameara2 API, Google ML kit Face Detection, TTS등의 기술을 사용하였습니다.
+ #####  교수 사용자는 본인의 강의를 새로 등록 or 로그인 한 후 카메라 출결 시작, 날짜별 출결확인이 가능합니다.
+ ##### 학생 사용자는 기본적인 정보와 본인의 사진을 입력한뒤 교수 사용자가 등록한 강의 번호를 받아서 본인의 출석정보를 확인할 수 있습니다.
+ ##### Face Detection을 이용해 카메라에서 얼굴이 인식될때 서버에 사진정보를 Crop해서 전송함
+ ##### 출석이 확인됬을시에 TTS로 출석 여부를 음성으로 알려줍니다.
+ ##### 와이어 프레임(Wire frame) Link: https://miro.com/app/board/uXjVO6Z5cH8=/

![image](https://user-images.githubusercontent.com/118334518/231157362-7d22dabd-f46e-41fe-a339-681de6080729.png)
![image](https://user-images.githubusercontent.com/118334518/231157446-af79839f-f3a6-421a-8482-02d3afa2a07a.png)
![image](https://user-images.githubusercontent.com/118334518/231157566-8da3ac72-4217-40fd-9b00-e16abbf590c9.png)

</br></br></br></br>
# <span style="color:yellow">파이썬 서버</span>
+ ##### 소켓 통신으로 안드로이드와 연결된 파이썬 서버에서는 Open-CV, KNN CLassifier, pymysql등의 기술이 사용되었습니다.
+ ##### 학생 사용자의 정보를 서버에 등록하고 출석시에 KNN Classifier 알고리즘을 사용해 미리 등록된 사진과 얼굴을 비교해 유사율을 측정 (80% 이상시 동일)
+ ##### 학생 사용자는 기본적인 정보와 본인의 사진을 입력한뒤 교수 사용자가 등록한 강의 번호를 받아서 본인의 출석정보를 확인할 수 있습니다.

![image](https://user-images.githubusercontent.com/118334518/231157268-ad188c2a-1d77-4df3-8992-c35a99636b45.png)

</br></br>

## 유튜브 시연영상(클릭 시 시청)
[![Video Label](http://img.youtube.com/vi/MNcq4fuzcE0/0.jpg)](https://youtu.be/MNcq4fuzcE0)

</br></br></br>
##### folder name 'last' is the Android Client folder and 'face_recog' is Python server
##### You need Python 3.8.2 version and pip3 install face-recognition and pymysql to exceed program
##### Ubuntu is recommend for Python server







