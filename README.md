# 📖 전국 문화 여과 활동 시설 데이터를 활용한 CampCino 서비스

<div style="text-align: center;">
    <img src="./img/logo.png" style="width: 100%;" alt="main">
</div>

## 🖥️ 프로젝트 소개
전국 문화 여가 활동 시설(캠핑) 데이터 를 가지고 전국 캠핑장을 한 눈에 볼 수 있도록 캠핑장 검색 사이트를 제작한다.
지역 별 위치를 Kakao Map API를 통해 지도로 볼 수 있고, 별도의 리스트를 통해 주소와 후기도 볼 수 있다.
추가적으로 게시판 기능을 추가하여 각 캠핑장에 대한 정보와 의견을 소통할 수 있는 등 다양한 아이디어를 통한 추가 기능을 구현해 본다.
또한 캠핑 상점 사이트를 통해 캠핑 용품을 구매 하는등 다양한 편의 기능을 구현해본다.

## 🕰️ 개발 기간
* 24.11.19일 - 24.11.27일

### 🧑‍🤝‍🧑 맴버구성
- **정상범**, **최은창**

### ⚙️ 개발 환경
**Frontend**
- Framework: `Vue 3.0`
- Markup Language: `HTML`
- Styling: `CSS 3`, `Tailwind CSS`, `Bootstrap 5`
- Scripting: `JavaScript` 
- Icons: `FontAwesome`, `HeroIcons`
- Editor: `VS Code`
- Build Tool: `Vite`
- Package Manager: `npm`

**Backend**
- Framework: `Spring Boot 3.3.5`
- Language: `Java 17`
- Database: `MySQL 8.0.38`
- Database Management Tool: `MySQL Workbench`
- ORM: `MyBatis`
- Build Tool: `Maven`
- API Testing & Documentation: `Swagger UI`
- Authentication: `JWT`
- Dependencies:
    - `Lombok`, `Devtools`, `Spring Security`, `MySQL Connector`

**사용 API**
- `ChatGPT API`
- `KaKao Map API`

## 🎬 Usecase Diagram
### 💎주요 기능 Usecase
<div style="text-align: center;">
    <img src="./img/total_use.png" style="width: 100%;" alt="total_use">
</div>

### 💎회원 기능 Usecase
<div style="text-align: center;">
    <img src="./img/user_use.png" style="width: 100%;" alt="user_use">
</div>

### 💎캠핑 기능 Usecase
<div style="text-align: center;">
    <img src="./img/camp_use.png" style="width: 100%;" alt="camp_use">
</div>

### 💎게시판 기능 Usecase
<div style="text-align: center;">
    <img src="./img/board_use.png" style="width: 100%;" alt="board_use">
</div>

### 💎상점 기능 Usecase
<div style="text-align: center;">
    <img src="./img/store_use.png" style="width: 100%;" alt="store_use">
</div>

## 💿 Database
<div style="text-align: center;">
    <img src="./img/CampCino_ERD.png" style="width: 100%;" alt="CampCino_ERD">
</div> 


## ✨ Class Diagram
* Domain : User
<div style="text-align: center;">
    <img src="./img/User_use.cld.jpg" style="width: 100%;" alt="main">
</div>

* Domain : Board
<div style="text-align: center;">
    <img src="./img/Board_use.cld.jpg" style="width: 100%;" alt="main">
</div>

* Domain : Camp
<div style="text-align: center;">
    <img src="./img/Camp_use.cld.jpg" style="width: 100%;" alt="main">
</div>

* Domain : Store
<div style="text-align: center;">
    <img src="./img/Store_use.cld.jpg" style="width: 100%;" alt="main">
</div>

## 🎨 화면 흐름도

<div style="text-align: center;">
    <img src="./img/displayflow.png" style="width: 100%;" alt="main">
</div>


## 🖼 화면 설계서

<div style="text-align: center;">
    <img src="./img/display.png" style="width: 100%;" alt="main">
</div>




## 📌 주요 기능
### 👤User

- **✔ 회원가입**
<div style="text-align: center;">
    <img src="./img/join.gif" style="width: 100%;" alt="main">
</div>

- **✔ 로그인**
<div style="text-align: center;">
    <img src="./img/login.gif" style="width: 100%;" alt="login">
</div>

- **✔ 로그아웃**
<div style="text-align: center;">
    <img src="./img/logout.gif" style="width: 100%;" alt="main">
</div>

- **✔ 회원정보 수정**
<div style="text-align: center;">
    <img src="./img/userupdate.gif" style="width: 100%;" alt="main">
</div>

----
### 📋Board
- **✔ 게시글 전체 조회**
<div style="text-align: center;">
    <img src="./img/boardlist.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 작성**
<div style="text-align: center;">
    <img src="./img/boardwrite.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 검색**
<div style="text-align: center;">
    <img src="./img/boardsearch.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 카테고리 검색**
<div style="text-align: center;">
    <img src="./img/boardcata.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 수정**
<div style="text-align: center;">
    <img src="./img/boardupdate.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 삭제**
<div style="text-align: center;">
    <img src="./img/boarddelete.gif" style="width: 100%;" alt="main">
</div>


- **✔ 게시글 댓글 조회**
<div style="text-align: center;">
    <img src="./img/sublist.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 댓글 쓰기**
<div style="text-align: center;">
    <img src="./img/subwrite.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 댓글 수정**
<div style="text-align: center;">
    <img src="./img/subupdate.gif" style="width: 100%;" alt="main">
</div>

- **✔ 게시글 댓글 삭제**
<div style="text-align: center;">
    <img src="./img/subdelete.gif" style="width: 100%;" alt="main">
</div>


----
### 🏕Camping
#### ✔캠핑장 검색
- **지역명(전라남도, 경기도, 광주광역시, 울산광역시 등)**
<div style="text-align: center;">
    <img src="./img/camplocal.gif" style="width: 100%;" alt="main">
</div>

- **테마명(일반 야영장, 글램핑, 자동차야영장, 카라반등)**
<div style="text-align: center;">
    <img src="./img/campcata.gif" style="width: 100%;" alt="main">
</div>

- **검색명**
<div style="text-align: center;">
    <img src="./img/campsearch.gif" style="width: 100%;" alt="main">
</div>

#### ✔ 부가기능
- **찜하기**
<div style="text-align: center;">
    <img src="./img/camplike.gif" style="width: 100%;" alt="main">
</div>

#### ✔ 캠핑장 상세 조회
- **찜하기**
<div style="text-align: center;">
    <img src="./img/campdetail.gif" style="width: 100%;" alt="main">
</div>

#### ✔ 캠핑장 리뷰 작성
- **찜하기**
<div style="text-align: center;">
    <img src="./img/campreview.gif" style="width: 100%;" alt="main">
</div>


----
### 🥄Store
- 상점 전체 조회
<div style="text-align: center;">
    <img src="./img/storelist.gif" style="width: 100%;" alt="main">
</div>

- 상점 물품 상세 조회
<div style="text-align: center;">
    <img src="./img/storedetail.gif" style="width: 100%;" alt="main">
</div>

- 상세 물품 리뷰 쓰기
<div style="text-align: center;">
    <img src="./img/productreviewwrite.gif" style="width: 100%;" alt="main">
</div>

- 상세 물품 리뷰 수정
<div style="text-align: center;">
    <img src="./img/productreviewupdate.gif" style="width: 100%;" alt="main">
</div>

- 상세 물품 리뷰 수정
<div style="text-align: center;">
    <img src="./img/productreviewdelete.gif" style="width: 100%;" alt="main">
</div>

----
### 🥄리뷰
- 캠핑장 평점별 조회
<div style="text-align: center;">
    <img src="./img/popular.gif" style="width: 100%;" alt="main">
</div>

- 캠핑장 리뷰수 조회
<div style="text-align: center;">
    <img src="./img/reviewcnt.gif" style="width: 100%;" alt="main">
</div>

- 캠핑장 이름순 조회
<div style="text-align: center;">
    <img src="./img/reviewname.gif" style="width: 100%;" alt="main">
</div>

----
### ⚙AI(인공지능)
- ChatBot
<div style="text-align: center;">
    <img src="./img/chatbot.gif" style="width: 100%;" alt="main">
</div>

----
## ✏ 실행방법
**FrontEnd Project**
```bash
$npm install

$npm run dev
```