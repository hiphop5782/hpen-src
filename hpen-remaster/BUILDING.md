# Windows 실행 파일과 설치 프로그램 제작

Windows 11 x64, Node.js 22.12 이상과 npm이 필요합니다. Node.js 24 LTS 권장. PowerShell에서 저장소의 `hpen-remaster` 폴더로 이동합니다.

## 전체 제작

```powershell
.\build-windows.cmd
```

스크립트는 잠금 파일 기준 의존성 설치, Electron 실행 파일 설치, 타입 검사, 테스트 후 두 배포 파일을 만듭니다. 최초 제작 때 Electron과 NSIS 등 빌드 도구를 인터넷에서 다운로드합니다.

## 개별 제작

```powershell
npm ci
npm run setup
npm run build:portable
npm run build:installer
```

`npm run pack`은 설치·압축하지 않은 실행 폴더만 만듭니다. `npm run build:win`은 포터블과 설치 프로그램을 함께 만듭니다.

| 결과 | 위치 |
|---|---|
| 설치 없이 실행하는 EXE | `release/hPen-Portable-0.1.0-x64.exe` |
| 설치 프로그램 | `release/hPen-Setup-0.1.0-x64.exe` |
| 실행 폴더 | `release/win-unpacked/hPen.exe` |

버전은 `package.json`의 version에 따라 바뀝니다. 설치는 사용자 단위이고 설치 위치 선택, 바탕 화면/시작 메뉴 바로가기를 지원합니다. 완료 후 자동 실행하지 않습니다. 삭제할 때 사용자 설정·캡처 파일은 자동 삭제하지 않습니다.

## 소스와 배포 파일

소스 저장소에는 `node_modules`, `dist`, `release`, 캡처 결과, 테스트 프로필을 커밋하지 않습니다. electron-builder의 허용 목록에는 `dist`와 앱 메타데이터만 들어 있어 개인 화면이나 테스트 결과가 배포 파일에 섞이지 않습니다.

현재 코드는 서명 인증서를 포함하지 않습니다. 서명되지 않은 EXE는 Windows에서 게시자를 확인할 수 없다는 안내가 나올 수 있습니다. 정식 서명은 별도의 인증서 또는 서명 서비스 설정이 필요합니다.
