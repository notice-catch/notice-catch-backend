# 🤝 공지캐치 프로젝트 SpringBoot Convention

우리 프로젝트의 일관성과 효율적인 협업을 위한 기본 규칙입니다. 개발을 진행하며 필요에 따라 언제든 수정될 수 있습니다.
<br><br>


## 🚀 1. Git 커밋 메시지 규칙 (Commit Message)
커밋 메시지는 어떤 작업을 했는지 명확히 알 수 있도록 아래의 **말머리(태그)**를 앞에 붙여서 작성합니다.

> **형식:** `태그/작업 내용 요약` (예: `feat/social-login`)

| 태그 (Tag) | 설명 (Description) |
| :--- | :--- |
| **`feat/`** | 새로운 기능 추가 |
| **`fix/`** | 버그 및 에러 수정 |
| **`docs/`** | 문서 수정 (README, 위키, 노션 등) |
| **`refactor/`** | 기능 변경 없는 코드 구조 개선 및 정리 |
| **`test/`** | 테스트 코드 작성 및 수정 |

<br>

## ⌨️ 2. 코드 및 데이터베이스 이름 짓기 (Naming)

### 🟢 Backend (Spring Boot) & Android
* **클래스 (Class):** 첫 글자를 대문자로 시작하는 `PascalCase`를 사용합니다.
  * *예: `UserController`, `NoticeService`*
* **변수 및 메서드 (Variable & Method):** 첫 글자는 소문자, 연결되는 단어는 대문자로 쓰는 `camelCase`를 사용합니다.
  * *예: `universityId`, `getNotificationList()`*

### 🔵 Database (MySQL)
* **테이블 및 컬럼:** 단어 사이에 언더바(`_`)를 넣는 `snake_case`를 사용합니다.
  * *예: `users`, `social_id`, `expired_at`*
