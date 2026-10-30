# hPen 재제작 사전 조사 보고서

작성일: 2026-10-30. 최우선 목표: Windows 11에서 입력 충돌 없이 정상 작동.

## 1. 결론

재제작 가능성이 높다. 핵심 기능은 화면 캡처, 캡처 이미지 위 필기, 화이트보드, 확대 표시로, Windows 11에서 구현 가능한 기능들이다. 다만 기존 충돌의 실제 원인은 아직 재현하지 않았으므로 해결을 보장할 단계는 아니다.

권장 방향은 **C# + 지원 중인 .NET LTS + WPF로 Windows 11용 앱을 먼저 검증**하는 것이다. WPF는 Windows 전용이므로 향후 타 OS UI를 그대로 재사용할 수는 없다. OS 공통 도형·문서·명령 모델과 Windows 연동을 분리해 확장 여지를 남긴다. 처음부터 동일 UI로 다중 OS 배포가 필수라면 Qt 6 + C++를 대안으로 검증한다.

언어 변경보다 중요한 변경은 다음과 같다.

- 전역 입력 가로채기·차단 대신 필요한 실행 단축키만 OS에 등록한다.
- 그리기 명령은 필기 창에 포커스가 있을 때만 처리한다.
- 한글은 자체 자판 변환기가 아닌 Windows IME와 표준 텍스트 편집 컨트롤로 입력한다.
- 화면 캡처·필기·텍스트 편집·확대의 상태 전환과 자원 해제를 명확히 한다.

## 2. 조사 범위와 한계

- 저장소: https://github.com/hiphop5782/hpen-src
- 분석 커밋: `1a28f2a438b4566ed4e34bf1be571c724cc845f8`
- 해당 커밋 날짜: 2022-02-14, 메시지: 캡쳐프레임, 홀드프레임 완성.
- `hpen-deprecated`의 기존 기능과 `hpen-new`의 진행 중인 재작성 코드를 구분해 정적 분석했다.
- README만으로 기능을 확정하지 않고 실제 구현을 함께 확인했다. 두 디렉터리의 기능 완성도는 동일하지 않다. 예를 들어 새 코드의 `ui/function/NoteFrame.java`는 빈 클래스이며 별도 `NoteFullScreenFrame` 구현이 존재한다.
- 실행 파일 구동, Windows 11 충돌 재현, 성능 측정, 의존성 취약점 전수 점검은 하지 않았다.
- 기존 `hUtility.jar` 내부 훅 구현과 jkeymaster 내부 구현은 이번에 역분석하지 않았다. 따라서 키 이벤트 전달 순서·키 차단 범위의 내부 동작은 확인되지 않았다.
- 실제 사용 중인 배포 파일이 어느 소스/라이브러리 버전과 일치하는지도 아직 확정되지 않았다.

## 3. 기존 기능

| 기능 | 확인 내용 | 재제작 판단 |
|---|---|---|
| 영역 캡처 | Alt+1, 영역 선택, 파일 또는 클립보드 저장 | 구현 가능, DPI별 좌표 검증 필요 |
| 화면 필기 | Alt+2, 현재 화면을 캡처해 고정한 뒤 그 위에 필기 | 구현 가능, 첫 버전의 중심 기능 |
| 도형 | 사각형, 원, 선, 방향별 화살표, X, 번개, 곡선, 아이콘 | 공통 도형 모델로 구현 가능 |
| 텍스트 | 글꼴·크기, 한영 전환, 자체 한글 조합 | OS IME 기반으로 교체 권장 |
| 편집 | 지우기, undo/redo, 마지막 화면 복원, 이미지 저장 | 명령 기록 방식 권장 |
| 메모리 | 숫자패드 기반 10개 슬롯 저장/복원 | 구현 가능, 숫자패드 없는 환경 대안 필요 |
| 화이트보드 | Alt+3, 배경을 비운 필기 화면 | 필기 엔진 재사용 가능 |
| 전체 화면 돋보기 | Alt+4, 외부 MagnificationManager 호출 | Windows 전용 연동으로 별도 검증 |
| 라이브 줌 | Alt+5, 커서 주변 실시간 확대, 1~10배, Esc 종료 | 구현 가능, 캡처 스트림과 자기 화면 재캡처 방지 필요 |
| 설정·상주 | 트레이, 색상·두께·글꼴·경로·확대 설정 | 구현 가능 |

Alt+6은 README에 나오지만 기존 GlobalkeyManager의 해당 등록 코드는 주석 처리되어 있다. 실행 가능한 기능으로 간주하면 안 된다. README의 실시간 캡처도 미지원으로 표시되어 있다.

기존 화면 필기는 배경 앱을 계속 조작하면서 그리는 라이브 오버레이와 다르다. **캡처해 정지시킨 화면 위 필기**를 먼저 복원하면 입력·포커스 구조를 단순하게 유지할 수 있다.

## 4. 발견한 문제와 충돌 후보

### 4.1 Windows 키·오른쪽 Alt 차단과 한영 전환

`hpen-deprecated/src/com/hpen/draw/ui/DrawingFrame.java:679`에서 좌우 Windows 키, MENU, 오른쪽 Alt를 차단 목록에 넣는다. 오른쪽 Alt를 놓으면 자체 `changeKorean()`을 호출한다.

확인된 사실은 차단을 요청하는 코드가 있다는 것이다. 실제 Windows 11 충돌 원인이라는 결론은 아직 가설이다. 다만 오른쪽 Alt·한영키·AltGr의 의미가 자판과 입력기에 따라 달라지므로 가장 먼저 제거·비교해야 할 의존성이다. 입력기, 다른 단축키 도구, 포커스 전환과의 상호작용을 확인해야 한다.

### 4.2 예외 발생 시 차단 해제가 누락될 수 있는 순서

같은 파일의 `start():78`은 키 차단 설정 뒤 `prepare()`, 이벤트 연결, 창 표시를 수행한다. `exit():96`은 설정 저장과 이벤트 해제 등의 작업 뒤 키 차단을 해제한다. 이 경로에는 전체 정리를 보장하는 `finally`가 없다.

앞선 작업이 예외로 중단되면 앱이 살아 있는 동안 차단 설정이 남을 가능성이 있다. 이는 정적 분석상 위험이며 실제 발생 여부는 재현이 필요하다. 프로세스 종료 후에도 OS에 훅이 영구 잔류한다고 의미하지는 않는다.

### 4.3 자체 한글 조합

`hpen-deprecated/src/com/hpen/draw/shapes/Text.java:30,55`에서 `English2KoreanParser`로 변환·분해·재조합한다. OS IME 조합 상태와 앱 내부 한영 상태가 달라질 여지가 있다. 다국어·붙여넣기·조합 중 편집도 별도 구현 부담이 된다.

표준 편집 컨트롤을 캔버스 위에 임시 표시하고 편집 확정 시 텍스트 객체로 저장하는 방식이 적합하다. 텍스트 편집 중에는 Q/W/E 등 도형 단축키를 중지해야 한다.

### 4.4 새 코드에도 전역 훅과 수명 관리 문제가 있다

`hpen-new/.../ui/MultiOptionFrame.java:160`과 `ui/function/CaptureFrame.java:109`는 `GlobalKeyboardHook`을 생성한다. 일부 경로는 `GlobalHookMode.FINAL`을 사용한다. 이름만으로 모든 키 차단 여부를 단정하지 않으며, 사용 라이브러리 3.8의 실제 네이티브 구현 확인이 필요하다.

`MultiOptionFrame.java:250`, `CaptureFrame.java:164`는 닫을 때마다 `newSingleThreadExecutor()`를 만들고 해당 executor를 종료하지 않는다. 반복 사용 시 스레드 누수 위험이 있다. 훅 콜백에서 직접 UI를 변경하는 부분도 있어 UI 스레드로 전달하도록 정리해야 한다.

CaptureFrame은 `open()`마다 WindowListener를 추가하지만 `close()`에서 제거하지 않는다. 동일 인스턴스를 반복 열면 리스너가 누적될 수 있다. 비동기 훅 종료 중 재개방되는 경우의 순서도 검증해야 한다.

### 4.5 전역 단축키 충돌 처리

기존 `GlobalkeyManager.java:14` 이후는 Alt+1~5를 jkeymaster Provider에 등록한다. 이 파일에는 등록 성공 상태를 사용자에게 표시하거나 대체 조합을 제시하는 처리가 보이지 않는다. `ShortcutOption`도 설정 키 정의 위주다.

등록형 단축키도 다른 앱과 같은 조합을 사용하면 충돌할 수 있다. Windows RegisterHotKey의 성공 여부를 확인하고, 실패한 조합·대체 설정·트레이 실행 경로를 제공해야 한다. Alt+숫자를 다른 앱에서도 사용할 수 있다는 사실을 안내하고 사용자 변경을 지원한다.

### 4.6 이미지 복사와 라이브 줌 비용

`ScreenData`는 이력에 `ImageAndPath.clone()`을 저장한다. `ImageAndPath.java:86`은 이미지와 배경 이미지를 복사하며 도형 리스트는 얕게 복사한다. 고해상도에서 메모리 사용이 커지고 도형 객체 공유로 과거 상태가 달라지는지도 검토해야 한다.

4K RGBA 한 장은 단순 계산으로 약 31.6 MiB다. 이미지와 배경을 매번 복제하면 이력 하나당 그 배수까지 증가할 수 있다. 실제 힙 사용량은 측정이 필요하다. 도형 추가·삭제 명령 기록과 공유 배경으로 전환하는 것이 적합하다.

`ZoomFrame`은 paint 과정에서 Robot을 새로 만들고 화면을 캡처한다. 프레임별 객체 생성, UI 스레드 지연, 화면 경계, 자기 창 재캡처를 검증해야 한다. 스트림 기반 캡처와 재사용 버퍼를 권장한다.

### 4.7 오래된 실행·빌드 의존성

기존 lib에는 JNA 4.5.2, jkeymaster 1.2 등이 포함되어 있고 새 pom은 Java 8과 system-hook 3.8 등을 사용한다. 오래되었다는 사실만으로 Windows 11 비호환이나 특정 보안 취약점을 단정할 수는 없다. 그러나 현재 런타임·아키텍처와의 지원 여부, 재현 가능한 빌드, 배포 파일 일치를 다시 확인해야 한다.

## 5. Windows 11 우선 구현안

### 권장 기술

**C# + 현재 지원 중인 .NET LTS + WPF**, OS 연동은 작은 Windows 전용 모듈로 분리한다. WPF의 2D 도형·텍스트·마우스·펜 입력과 Windows API 연동이 이 앱의 첫 목표에 적합하다는 설계 판단이다. 프레임워크 선택 자체가 충돌 해결의 증거는 아니다.

| 영역 | 권장 방식 |
|---|---|
| 전역 실행 | RegisterHotKey + WM_HOTKEY, MOD_NOREPEAT, 실패 표시, 사용자 변경 |
| 필기 단축키 | 활성 필기 창 내부 명령 처리, 텍스트 편집 시 비활성화 |
| 한글 | WPF 표준 TextBox 등 IME 지원 편집기, 조합/확정 이벤트 존중 |
| 필기 화면 | 선택 모니터 캡처 후 일반 데스크톱 창으로 표시 |
| 캡처 | 정지 이미지 캡처 경로와 Windows.Graphics.Capture 기반 연속 캡처를 요구에 맞게 비교 검증 |
| 확대 | 고정 화면 확대부터 검증하고 라이브 줌·OS 돋보기는 후속 구현 |
| DPI | 모니터별 배율과 물리 픽셀/논리 좌표 변환을 한 계층에서 처리 |
| 이력 | 도형/명령 기반 undo·redo, 제한된 스냅샷 |
| 수명 | 단일 실행, 창·단축키·캡처 세션의 명시적 해제, UI 스레드로 명령 전달 |

Windows.Graphics.Capture도 지원 여부·권한·캡처 제외 대상 등 제약이 있으므로 모든 화면을 항상 캡처한다고 보장하지 않는다. 보안 데스크톱·보호 콘텐츠·독점 전체화면은 일반 강의용 데스크톱 지원과 구분한다. 기본 실행은 일반 사용자 권한으로 설계한다.

전역 저수준 훅은 초기 버전에서 사용하지 않는 것을 목표로 한다. 특정 후속 요구 때문에 필요해질 경우 범위를 제한하고 별도 검증한다. Microsoft 문서상 저수준 키보드 훅은 처리 지연 시 조용히 제거될 수 있으므로 무거운 작업을 콜백에서 수행해서는 안 된다.

### 기술 대안

| 선택 | 장점 | 비용·제약 | 현재 판단 |
|---|---|---|---|
| C# / WPF | Windows 기능과 직접 통합, 표준 입력·도형 UI | WPF UI는 다른 OS로 이전 불가 | Windows 11 우선 권장 |
| Qt 6 / C++ | 공통 UI·도형·IME·DPI 기반 | OS 캡처·단축키 어댑터, C++ 관리, 배포 라이선스 검토 | 다중 OS가 필수일 때 유력 |
| Java 유지 | 일부 기존 로직 재사용 | 입력 구조와 네이티브 경계는 재설계 필요 | 작은 비교 실험에는 유용 |
| Electron | 웹 UI, 전역 단축키·캡처 API 제공 | 메모리·실시간 확대·포커스 실측 필요 | 가능한 대안이나 현재 1순위 아님 |

## 6. OS 범용성 조사

공통화할 수 있는 부분은 도형 모델, 편집 명령, 설정 스키마, 문서 저장 형식이다. 화면 접근 권한·전역 단축키·확대·트레이·최상위 창 처리는 OS별 구현이 필요하다.

| 기능 | Windows 11 | macOS | Linux X11 / Wayland |
|---|---|---|---|
| 화이트보드·정지 화면 필기 | 가능 | 가능 | 가능 |
| 화면 캡처 | Windows 캡처 API | ScreenCaptureKit 등, 화면 기록 권한 필요 | X11 경로 또는 Screenshot/ScreenCast portal |
| 전역 단축키 | RegisterHotKey | OS별 단축키 연동, 방식별 권한 검토 | X11 등록 / GlobalShortcuts portal 지원 확인 |
| 라이브 줌 | 연속 캡처와 확대 렌더링 | 캡처 권한과 스트림 처리 | Wayland에서는 portal·PipeWire 및 backend 지원 확인 |
| 전체 OS 화면 확대 | Windows 전용 경로 | 동일한 제어 방식 보장 불가 | 데스크톱별 차이 큼 |
| OS 키 강제 차단 | 가능 범위가 있으나 최소화 권장 | 보안·권한 제약 | Wayland 공통 기능으로 보장하기 어려움 |

Wayland의 GlobalShortcuts portal은 사용자에게 단축키 설정 UI를 제시할 수 있고 ScreenCast는 세션을 통해 스트림에 접근한다. 따라서 Windows처럼 앱이 임의 위치·입력·화면을 항상 제어한다고 가정하면 안 된다. 포털 인터페이스 존재와 사용자의 데스크톱 backend 지원은 별개다.

현실적인 범용성 목표는 **정지 화면 필기·화이트보드·저장 기능은 공통 제공하고, 단축키·캡처·라이브 확대는 OS별로 지원 수준을 명시하는 것**이다. Windows 11 첫 구현에서는 이 확장을 위해 인터페이스와 저장 형식만 준비한다.

## 7. 권장 검증 순서와 통과 기준

1. 기존 문제 재현: 사용 중인 exe/JRE/라이브러리, Windows 빌드, 키보드 배열, IME, 함께 쓰는 입력 도구를 기록한다. 원문 키 입력이나 화면 내용을 상시 기록하지 말고 명령·상태 전환·등록 실패·예외 중심으로 진단한다.
2. 작은 Windows 11 시제품: 트레이, 단축키 하나, 선택 모니터 캡처, 필기 창, 표준 한글 입력, Esc 종료만 구현한다. 전역 저수준 훅 없이 작동하는지 확인한다.
3. 입력 비교: 한영키/오른쪽 Alt/좌우 Ctrl·Shift·Alt, 키를 누른 채 모드 전환, 빠른 재실행, Alt+Tab, 포커스 상실을 검증한다. IME 조합 중 도형 명령이 발동하지 않아야 한다.
4. 복원 검증: 열기/닫기 100회, 저장 취소, 예외 유도, 절전 복귀에서 일반 앱 입력이 정상이어야 한다. 스레드·핸들이 계속 늘지 않아야 한다. 단축키가 이미 점유되어도 트레이로 기능 실행이 가능해야 한다.
5. 화면 검증: 100/125/150/200% 배율, 배율이 다른 듀얼 모니터, 음수 좌표, 세로 모니터, 연결 해제에서 캡처와 필기 좌표가 맞아야 한다.
6. 기능 확장: 도형·undo/redo·저장·슬롯 → 고정 화면 확대 → 라이브 줌 → OS 돋보기 순으로 추가한다. 라이브 줌 추가 때 CPU/GPU·메모리·응답 지연을 측정하고 목표를 정한다.
7. 배포 확인: 개발 환경이 없는 Windows 11 일반 사용자 계정에서 설치·실행·업데이트·종료를 확인한다.

이 보고서는 구현 착수 전 조사 결과다. Windows 11에서의 안정성 확인은 위 시제품과 재현 검증을 통과한 뒤 판단해야 한다.

## 8. 공식 자료

- [RegisterHotKey: 등록 실패와 MOD_NOREPEAT](https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-registerhotkey)
- [LowLevelKeyboardProc: 제한 시간과 콜백 설계](https://learn.microsoft.com/en-us/windows/win32/winmsg/lowlevelkeyboardproc)
- [WPF 개요: 그래픽·컨트롤, Windows 전용](https://learn.microsoft.com/en-us/dotnet/desktop/wpf/overview/)
- [WPF 입력 구조](https://learn.microsoft.com/en-us/dotnet/desktop/wpf/advanced/input-overview)
- [Windows 화면 캡처](https://learn.microsoft.com/en-us/windows/apps/develop/media-authoring-processing/screen-capture)
- [Apple ScreenCaptureKit](https://developer.apple.com/documentation/screencapturekit)
- [macOS 화면 캡처 예제](https://developer.apple.com/documentation/screencapturekit/capturing-screen-content-in-macos)
- [GlobalShortcuts portal](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.GlobalShortcuts.html)
- [ScreenCast portal](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.ScreenCast.html)
- [Qt High DPI](https://doc.qt.io/qt-6/highdpi.html)
- [Qt 입력기 이벤트](https://doc.qt.io/qt-6/qinputmethodevent.html)
- [Qt 라이선스](https://doc.qt.io/qt-6/licensing.html)
- [Electron 전역 단축키](https://www.electronjs.org/docs/latest/api/global-shortcut)
- [Electron 화면 캡처](https://www.electronjs.org/docs/latest/api/desktop-capturer)
