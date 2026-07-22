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

<br>

## 🧱 5. 클래스 설계 컨벤션 (Entity / DTO / Service / Builder)

### 🟢 Entity
* Lombok은 `@Getter`, `@NoArgsConstructor(access = AccessLevel.PROTECTED)`, `@AllArgsConstructor`, `@Builder` 조합만 사용합니다. **`@Setter`는 절대 사용하지 않습니다.**
* 상태 변경은 setter 대신 의도가 드러나는 **명명된 메서드**로 노출합니다. *(예: `User.updatePushToken()`, `Notice.isExpired()`)*
* `createdAt` 등 생성 시점에 채워야 하는 값과 기본값(default)은 `@PrePersist` 훅에서 세팅합니다.
* 연관관계는 기본적으로 `fetch = FetchType.LAZY`를 사용합니다.
* 양방향 연관관계의 연결 메서드(예: `setNoticeSummary`)에서는 무한루프 방지를 위해 반대편 setter를 `protected`로 감싸 내부에서만 호출되게 합니다.

### 🟡 DTO
* 패키지 위치: `domain/{도메인명}/dto/request`, `domain/{도메인명}/dto/response`로 분리합니다.
* 네이밍은 `{동작/대상}Request` / `{동작/대상}Response` 접미사를 붙입니다. *(예: `LoginRequest`, `NoticeDetailResponse`)*
* 요청 DTO는 Bean Validation 어노테이션(`@NotBlank`, `@NotNull` 등)으로 필수값을 검증합니다.
* 응답 DTO는 Entity를 그대로 반환하지 않고, `from(Entity entity)` 정적 팩토리 메서드로 변환해서 반환합니다.
* 리스트/페이지 응답은 직접 구현하지 않고 공통 `ListResponse`/`PageResponse`/`ScrapPageResponse`를 재사용합니다.

### 🔵 Service
* 클래스명은 `{도메인명}Service` 형태의 `PascalCase`를 사용합니다. *(예: `NoticeService`, `UserKeywordService`)*
* 클래스 레벨에 `@Transactional(readOnly = true)`를 기본으로 걸고, 쓰기 로직이 있는 메서드에만 `@Transactional`을 별도로 붙입니다.
* 도메인 예외는 `throw new ProjectException(errorCode)`로 던지고, 서비스 계층에서 `try-catch`로 삼키지 않습니다.

### 🟣 Builder
* Entity의 `@Builder`는 컨트롤러/서비스에서 직접 호출하지 않고, 생성 책임을 갖는 정적 팩토리 메서드(`of()`, `create()` 등) 안에서만 사용하는 것을 권장합니다.
* Builder로 생성 시 필수값 검증(널 체크 등)이 필요한 필드는 정적 팩토리 메서드 내부에서 검증 후 `build()`를 호출합니다.

<br>

## ⚠️ 6. 예외 처리 컨벤션 (Exception Handling)

`GeneralExceptionHandler`(`@RestControllerAdvice`)는 아래 예외들을 **반드시** 개별 핸들러로 매핑합니다. 목록에 없는 예외만 최종적으로 `Exception.class` 핸들러(500)로 떨어져야 하며, 4xx 성격의 예외가 500으로 응답되면 안 됩니다.

| 예외 클래스 | 발생 상황 | 매핑 상태코드 |
| :--- | :--- | :--- |
| `MethodArgumentNotValidException` | `@Valid` 바디 검증 실패 | 400 |
| `BindException` | 쿼리 파라미터(`@ModelAttribute`) 검증 실패 | 400 |
| `MethodArgumentTypeMismatchException` | 파라미터 타입 불일치 | 400 |
| `HttpMessageNotReadableException` | 요청 JSON 파싱 실패 | 400 |
| `MissingServletRequestParameterException` | 필수 쿼리 파라미터 누락 | 400 |
| `HttpRequestMethodNotSupportedException` | 지원하지 않는 HTTP 메소드 호출 | 405 |
| `NoResourceFoundException` / `NoHandlerFoundException` | 존재하지 않는 엔드포인트 호출 | 404 |
| `DataIntegrityViolationException` | DB 유니크/제약조건 위반 | 409 |
| `ProjectException` | 도메인에서 명시적으로 던진 예외 | 각 도메인 `BaseErrorCode`의 status |
| `Exception` (최종 catch-all) | 그 외 예기치 못한 서버 오류 | 500 |

### 세부 규칙
1. **강제 캐스팅 금지:** Validation 에러 수집 시 `ex.getBindingResult().getAllErrors()` + `(FieldError)` 캐스팅을 사용하지 않습니다. `ObjectError`가 섞이면 `ClassCastException`이 발생하므로 `ex.getBindingResult().getFieldErrors()`를 사용합니다.
2. **내부 정보 노출 금지:** 500 응답 바디에 `ex.getMessage()` 등 예외 원문 메시지를 그대로 내려주지 않습니다. 클라이언트에는 `GeneralErrorCode.INTERNAL_SERVER_ERROR`의 고정 메시지만 반환합니다.
3. **로깅 필수:** `GeneralExceptionHandler`에는 `@Slf4j`를 붙이고, 최소 500(서버 오류) 케이스에서는 `log.error(..., ex)`로 스택트레이스를 남깁니다. 4xx(클라이언트 귀책) 케이스는 `log.warn` 또는 생략하여 로그 레벨을 구분합니다.
