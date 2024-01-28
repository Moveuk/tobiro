# 토비로 Tobiro - 토요일을 비롯한 일상의 로맨틱한 여행
## 👨‍👨‍👧 팀원 소개
| [이동욱](https://github.com/Moveuk) | [김찬모](https://github.com/chanmo231121) | [박규희](https://github.com/qordpsem) |
|---------------------------------|----------------------------------------|------------------------------------|
|![image](https://github.com/Moveuk/tobiro/assets/84966961/7959271c-1a22-4460-9ec2-85fd169c0497)|![image](https://github.com/Moveuk/tobiro/assets/84966961/c6b987c9-8c0f-494f-8a5b-0ba0d1377df0)|![image](https://github.com/Moveuk/tobiro/assets/84966961/61e5e367-863e-4ed5-b8a7-56a274f0d27d)|

## 📝 프로젝트 소개
여행 숙박 예약 서비스
토요일을 비롯한 로맨틱한 여행

여행을 좋아하는 세 명이 모인 이유로 시작되었으며
내일배움캠프가 끝나면 여행을 가고 싶어 시작하게 되었습니다.😀

## 🏁 개발 일정

![image](https://github.com/Moveuk/tobiro/assets/84966961/471a71d5-de4c-40f5-be22-efa76a00402a)

1월 26일 ~ 27일 부족한 점 보완

## KPT 회고
- 1월 29일 월요일 작성 예정

<details>
<summary>KPT 형식</summary>
<div markdown="1">

### Keep

### Problem

### Try
</div>
</details>

## ⚙️ Backend 개발환경

- 언어 : Kotlin  1.9.20 - Java version 17
- 프레임워크 : SpringBoot  3.2.0
- 빌드 툴 : gradle-8.5
- 데이터베이스 : Supabase
- 배포 서버 서비스 : Amazon EC2 - Ubuntu 22
- 형상 관리 : git
- IDE : IntelliJ IDEA 2023.3 (Ultimate Edition) 

## 🤝 협업 툴

- zep / slack : 온라인 미팅 장소
- 노션 : 기능 명세 / 개발 일정 정리
- 디자인 툴 : Figma
- ERD : Quick DBD 웹서비스
- 테스트 툴 : Postman


## 와이어프레임
### 전체
![image](https://github.com/Moveuk/tobiro/assets/84966961/5dbc8f6d-cb62-47a2-b2a0-efebacd35b9d)
### 유저
![image](https://github.com/Moveuk/tobiro/assets/84966961/2da4d035-71cb-402e-b0e5-aad86ef09d86)
### 서비스
![image](https://github.com/Moveuk/tobiro/assets/84966961/e20948de-1963-49f4-8f61-30f3c6fca2fd)
### 백오피스
![image](https://github.com/Moveuk/tobiro/assets/84966961/1cc41b6c-6b91-4ff9-a63e-8901846a2202)

## ERD
![image](https://github.com/Moveuk/tobiro/assets/84966961/b7793122-de8f-4941-a08d-fe513279f39b)

## 📚 Package 구조
![image](https://github.com/Moveuk/tobiro/assets/84966961/1e4ee93e-40f3-440f-bafe-0130f2faba3c)

## 구현 기능
![image](https://github.com/Moveuk/tobiro/assets/84966961/042f1092-8017-415a-a9a3-3b757e152d4b)

## 주요 기능 리뷰

### 1. 최근 비밀번호 검증 로직

- 회원이 비밀번호를 바꾸려할 때 최근 3회 안에 사용한 비밀번호는 못사용하도록 하는 로직
  - ![image](https://github.com/Moveuk/tobiro/assets/84966961/aac46d48-d68d-4cbe-a4cc-21ec8b369d19)

### 2. 숙박업소 검색 기능 - QueryDSL 도입

- 숙박업소를 검색할 때 옵션으로 두어야하는 설정들이 많아 동적 쿼리 구현이 필수적이었고, JPA와 JQPL 만으로는 개발 효율이 나오지 않는다고 판단하여 QueryDSL 라이브러리 도임
  - ![image](https://github.com/Moveuk/tobiro/assets/84966961/7233e7b8-c792-4aaa-aa0f-ab8e8ba3eee5)
  - ![image](https://github.com/Moveuk/tobiro/assets/84966961/f718c8aa-3016-4b7b-a27b-b6777972bd0d)

### 3. 배포 

![image](https://github.com/Moveuk/tobiro/assets/84966961/715ffda8-0cb8-4e1b-9421-2d3fbbbb9305)

### 4. 반복되는 코드 리팩토링 

![image](https://github.com/Moveuk/tobiro/assets/84966961/bb67958c-1d00-4c55-ad30-e3b751847e43)
