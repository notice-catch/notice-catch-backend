# 🤝 공지캐치 프로젝트 컨벤션

우리 프로젝트의 일관성과 효율적인 협업을 위한 기본 규칙입니다. 개발을 진행하며 필요에 따라 언제든 수정될 수 있습니다.
<br><br>

## 🚀 1. Git 커밋 메시지 규칙 (Commit Message)
커밋 메시지는 어떤 작업을 했는지 명확히 알 수 있도록 아래의 **말머리(태그)**를 앞에 붙여서 작성합니다.

**형식:** `태그: 작업 내용 요약` (예: `feat: 소셜 로그인 구현`)

| 태그 (Tag) | 설명 (Description) |
| :--- | :--- |
| **`feat:`** | 새로운 기능 추가 |
| **`fix:`** | 버그 및 에러 수정 |
| **`docs:`** | 문서 수정 (README, 위키, 노션 등) |
| **`refactor:`** | 기능 변경 없는 코드 구조 개선 및 정리 |
| **`test:`** | 테스트 코드 작성 및 수정 |

<br>

## 🌿 2. Git 브랜치 이름 규칙 (Branch Naming)
브랜치는 `[종류/기능명]` 형태로 작성하며, 단어 구분은 하이픈(`-`)을 사용합니다. 언더바(`_`)는 가시성을 위해 지양합니다.

* **새로운 기능 개발:** `feat/기능명` *(예: `feat/social-login`, `feat/keyword-setting`)*
* **버그 및 오류 수정:** `fix/오류명` *(예: `fix/dday-timer`)*
* **문서 수정:** `docs/수정내용` *(예: `docs/readme-update`)*

<br>

## ⌨️ 3. 코드 및 데이터베이스 이름 짓기 (Naming)

### 🟢 Backend (Spring Boot) & Android
* **클래스 (Class):** 첫 글자를 대문자로 시작하는 `PascalCase`를 사용합니다.
  * *예: `UserController`, `NoticeService`*
* **변수 및 메서드 (Variable & Method):** 첫 글자는 소문자, 연결되는 단어는 대문자로 쓰는 `camelCase`를 사용합니다.
  * *예: `universityId`, `getNotificationList()`*

### 🔵 Database (MySQL)
* **테이블 및 컬럼:** 단어 사이에 언더바(`_`)를 넣는 `snake_case`를 사용합니다.
  * *예: `users`, `social_id`, `expired_at`*

<br>

## 🤝 4. PR 및 코드리뷰 규칙 (PR & Code Review)

### 🌿 PR 작성 및 머지 규칙
* 모든 기능 개발은 `feature/기능명` 브랜치에서 진행하며, 완료 시 `main` 브랜치로 PR(Pull Request)을 보냅니다.
* **PR 작성 시 제공되는 템플릿 양식(상세 설명, 리뷰 요청 사항 등)을 명확히 작성하여 리뷰어에게 공유합니다.**
* 최소 1명 이상의 리뷰어에게 **Approve(승인)**를 받은 후 머지(Merge)할 수 있습니다.

### 💬 코드리뷰 매너 (Pn Rule)
리뷰어는 작성자가 PR에 적어둔 **[리뷰 요청 사항]**을 중심으로 코드를 검토하며, 피드백의 중요도를 앞에 적어 소통의 오해를 줄입니다.
* **P1 (Must):** 반드시 수정해야 하는 필수 사항 (버그 가능성, 컨벤션 위반 등)
* **P2 (Recommend):** 수정을 권장하는 제안 (더 좋은 방식 추천 및 작업자 자율 선택)
* **P3 (Comment):** 가벼운 의견, 질문, 또는 사소한 칭찬
