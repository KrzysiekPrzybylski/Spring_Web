call runcrud.bat
if "%ERRORLEVEL%"==0 goto page
echo.
echo There are some errors with the script
goto fail

:page
start "iexplorer.exe" http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo There were problems with running browser.
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished