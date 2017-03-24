rem echo off
set TARGET_ROOT=D:\QA_ENV_1
set TARGET_JMETER_FOLDER=Jmeter
set TARGET=%TARGET_ROOT%\%TARGET_JMETER_FOLDER%\lib\ext  
REM set ARCH=C:\TEST_ENV\SMOKE_TESTS\REQUIRED_FILES\for_jmeter_lib_ext
call copy /y .\excel-data-privider\target\*.jar build
call copy /y .\keyboard\target\*.jar build
call copy /y .\navigator\target\*.jar build
call copy /y .\utils\target\*.jar build
call copy /y .\webdriver-utils\target\*.jar build
call copy /y .\web-page\target\*.jar build
call copy /y .\wmq-wrapper\target\*.jar build
call copy /y .\jmeter-java-sampler-abstracts\target\*.jar build
call copy /y .\jmeter-java-sampler-excel\target\*.jar build
call copy /y .\jmeter-java-sampler-keyboard\target\*.jar build
call copy /y .\jmeter-java-sampler-web-extension\target\*.jar build
call copy /y .\jmeter-java-sampler-wmq\target\*.jar build
call copy /y .\jmeter-config-excel\target\*.jar build
call copy build\*.jar %TARGET%
REM call copy build\*.jar %ARCH%
rem copy /y extentions*.jar c:\TEST_ENV\apache-jmeter-3.1\lib\ext
rem copy /y extentions*.jar c:\TEST_ENV\SMOKE_TESTS\REQUIRED_FILES\for_jmeter_lib_ext
rem cd ..
