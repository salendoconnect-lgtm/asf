@echo off
setlocal EnableExtensions
cd /d "%~dp0"

title End Expansion 1.20.1 - One Click Build

echo.
echo ================================================
echo       END EXPANSION - ONE CLICK BUILD
echo       Minecraft 1.20.1 / Fabric
echo ================================================
echo.

where java >nul 2>nul
if errorlevel 1 goto NOJAVA

for /f "tokens=3" %%V in ('java -version 2^>^&1 ^| findstr /i "version"') do set "JAVA_VER=%%~V"
echo Found Java: %JAVA_VER%
java -version

if not exist ".tools\gradle\bin\gradle.bat" (
    echo.
    echo [1/3] First run: downloading Gradle 8.6...
    echo This is automatic. Please wait.
    if not exist ".tools" mkdir ".tools"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "$u='https://services.gradle.org/distributions/gradle-8.6-bin.zip'; $o='.tools\gradle.zip'; Invoke-WebRequest -UseBasicParsing -Uri $u -OutFile $o" 
    if errorlevel 1 goto DOWNLOADFAIL
    echo Extracting Gradle...
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Force '.tools\gradle.zip' '.tools\gradle_unpack'; Move-Item -Force '.tools\gradle_unpack\gradle-8.6' '.tools\gradle'; Remove-Item -Recurse -Force '.tools\gradle_unpack'; Remove-Item -Force '.tools\gradle.zip'"
    if errorlevel 1 goto EXTRACTFAIL
)

echo.
echo [2/3] Building End Expansion...
echo The first build may take several minutes because Minecraft/Fabric dependencies are downloaded.
echo.
call ".tools\gradle\bin\gradle.bat" --no-daemon clean build
if errorlevel 1 goto BUILDFAIL

if not exist "build\libs\end-expansion-1.0.0.jar" goto JARNOTFOUND

copy /Y "build\libs\end-expansion-1.0.0.jar" ".\end-expansion-1.0.0.jar" >nul

echo.
echo ================================================
echo                 BUILD SUCCESS!
echo ================================================
echo.
echo Your ready-to-install mod is:
echo.
echo   %CD%\end-expansion-1.0.0.jar
echo.
echo Copy that JAR to:
echo   %%APPDATA%%\.minecraft\mods\
echo.
echo You also need Fabric API for Minecraft 1.20.1.
echo ================================================
echo.
pause
exit /b 0

:NOJAVA
echo.
echo [ERROR] Java is not installed or is not in PATH.
echo Minecraft 1.20.1 requires Java 17.
echo Install Java 17, then double-click build.bat again.
pause
exit /b 1

:DOWNLOADFAIL
echo.
echo [ERROR] Could not download Gradle 8.6.
echo Check your internet connection and run build.bat again.
pause
exit /b 1

:EXTRACTFAIL
echo.
echo [ERROR] Could not extract Gradle.
pause
exit /b 1

:BUILDFAIL
echo.
echo ================================================
echo                 BUILD FAILED
echo ================================================
echo.
echo Scroll up to see the first error.
echo If this is a network error, run build.bat again.
pause
exit /b 1

:JARNOTFOUND
echo.
echo [ERROR] Gradle reported success, but the JAR was not found.
pause
exit /b 1
