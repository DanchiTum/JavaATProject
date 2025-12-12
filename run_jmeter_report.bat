@echo off
REM JMeter Load Test with HTML Report Generation
REM Default: 30 minutes (1800 seconds), can override with -Jduration=3600 for 60 min

SET JMETER_HOME=C:\apache-jmeter-5.6.3
SET TEST_PLAN=%~dp0src\test\resources\jmeter\opencart_load_test.jmx
SET REPORT_DIR=%~dp0jmeter-report
SET RESULTS_FILE=%~dp0jmeter-results.jtl

IF NOT EXIST "%JMETER_HOME%\bin\jmeter.bat" (
    echo JMeter not found at %JMETER_HOME%
    exit /b 1
)

REM Clean previous results
IF EXIST "%RESULTS_FILE%" del "%RESULTS_FILE%"
IF EXIST "%REPORT_DIR%" rmdir /s /q "%REPORT_DIR%"

echo ============================================
echo OpenCart JMeter Load Test
echo Duration: 30 minutes (default) or use -Jduration=3600 for 60 min
echo ============================================
echo.

REM Run JMeter test with HTML report generation
REM Parameters:
REM   -Jduration=1800   : Test duration in seconds (30 min default)
REM   -Jthreads_browse=10 : Browse thread count
REM   -Jthreads_search=15 : Search thread count
REM   -Jthreads_cart=5    : Cart thread count
"%JMETER_HOME%\bin\jmeter" -n -t "%TEST_PLAN%" -l "%RESULTS_FILE%" -e -o "%REPORT_DIR%" %*

echo.
echo ============================================
echo Test completed!
echo HTML Report: %REPORT_DIR%\index.html
echo Results CSV: %RESULTS_FILE%
echo ============================================
echo.
echo To run with 60 minute duration:
echo   run_jmeter_report.bat -Jduration=3600

pause
