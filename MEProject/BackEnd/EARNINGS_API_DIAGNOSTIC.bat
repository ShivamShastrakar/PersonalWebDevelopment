@echo off
REM Diagnostic script to help troubleshoot the 404 issue

echo ============================================
echo MahaExam Backend - Earnings API Diagnostic
echo ============================================
echo.
echo Server Port: 28080 (from application.properties)
echo.
echo Expected Endpoints:
echo   1. GET http://localhost:28080/api/earnings/106
echo      (List all students referred by channel partner 106)
echo.
echo   2. GET http://localhost:28080/api/earnings/summary/106
echo      (Get earnings summary for channel partner 106)
echo.
echo ============================================
echo Next Steps:
echo ============================================
echo.
echo 1. REBUILD THE APPLICATION:
echo    cd /d D:\Project\eduval\MahaExam\BackEnd
echo    mvn clean package -DskipTests
echo.
echo 2. STOP the currently running application (if any)
echo    - Find and kill the Java process in Task Manager
echo    - Or press Ctrl+C in the terminal where it's running
echo.
echo 3. START the application fresh:
echo    cd /d D:\Project\eduval\MahaExam\BackEnd
echo    mvn spring-boot:run
echo.
echo 4. TEST THE ENDPOINTS:
echo    In a new terminal window, run:
echo.
echo    A. Test list students endpoint:
echo       curl -v http://localhost:28080/api/earnings/106
echo.
echo    B. Test earnings summary endpoint:
echo       curl -v http://localhost:28080/api/earnings/summary/106
echo.
echo ============================================
echo IMPORTANT NOTES:
echo ============================================
echo - The application MUST be rebuilt after code changes
echo - Old Java processes may still be running on port 28080
echo - Use Task Manager to confirm the process is stopped
echo - Look for "java.exe" or "javaw.exe" and end task
echo.
echo ============================================
