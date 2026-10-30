@echo off
setlocal
cd /d "%~dp0"
where npm >nul 2>nul
if errorlevel 1 (
  echo Install Node.js 22.12 or later with npm, then run this script again.
  exit /b 1
)
call npm ci
if errorlevel 1 exit /b 1
call npm run setup
if errorlevel 1 exit /b 1
call npm run check
if errorlevel 1 exit /b 1
call npm test
if errorlevel 1 exit /b 1
call npm run build:win
if errorlevel 1 exit /b 1
echo Portable EXE and installer are available in release.
