# tour-weather

## 로컬 실행 환경변수

`DB_PASSWORD`, `WEATHER_API_KEY`, `TOUR_CLIMATE_API_KEY`를:

```powershell
Copy-Item .env.example .env
# .env를 열어서 실제 값 채우기 (이 파일은 git에 올라가지 않음)
. .\load-env.ps1
```

새 터미널을 열 때마다 `. .\load-env.ps1` 다시 실행

## Docker로 실행

```powershell
docker build -t tripcast .
docker run -p 8080:8080 `
  -e DB_HOST=host.docker.internal `
  -e DB_PASSWORD=<MySQL 비밀번호> `
  -e WEATHER_API_KEY=<기상청 API 키> `
  tripcast
```

컨테이너 안에서 `localhost`는 컨테이너 자기 자신을 가리키므로, 호스트 PC에서 돌고 있는 MySQL에 붙으려면 `DB_HOST=host.docker.internal`(Docker Desktop이 제공하는 호스트 접근용 주소)을 넘겨야 한다. MySQL을 별도 컨테이너로 띄울 경우엔 그 컨테이너 이름을 `DB_HOST`로 넘기면 된다.

이미지에는 API 키/비밀번호를 넣지 않는다 — 항상 `docker run -e`로 실행 시점에 주입한다.

## 문서

- API 전체 목록/테스트: 앱 실행 후 `/swagger-ui.html`
- DB 테이블 명세: [docs/db-table-spec.md](docs/db-table-spec.md)
- 관광기후지수 연동(외부 API, 배치, 환경변수) 가이드: [docs/tour-climate-api.md](docs/tour-climate-api.md)
- DB 스키마 관리(Flyway 마이그레이션, 기존 DB 편입) 가이드: [docs/flyway-migration.md](docs/flyway-migration.md)
- 인증(JWT, 관리자 API) 가이드: [docs/auth.md](docs/auth.md)

## 커밋 컨벤션

형식: `type: 한글 설명`

| type | 설명 |
| --- | --- |
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 (README 등) |
| style | 코드 포맷팅, 세미콜론 누락 등 (로직 변경 없음) |
| refactor | 코드 리팩토링 (기능 변경 없는 코드 개선) |
| test | 테스트 코드 추가/수정 |
| chore | 빌드 설정, 패키지 매니저 등 기타 변경 |
| merge | 브랜치 병합 또는 PR 병합 |


예시: `feat: 날씨 조회 API 추가`

## 브랜치 컨벤션

형식: `type/작업내용` (작업내용은 영문, 하이픈으로 구분)

| 브랜치 | 설명 |
| --- | --- |
| main | 배포 가능한 안정 버전 |
| develop | 다음 배포를 위한 개발 브랜치 |
| feature | 기능 개발 브랜치 |
| fix | 버그 수정 브랜치 |
| hotfix | 배포 후 긴급 수정 브랜치 |
| release | 배포 준비 브랜치 |

예시: `feature/weather-api`
