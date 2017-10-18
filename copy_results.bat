rem echo off
set TARGET=D:\QAMATION_TEST_ENV\Jmeter\lib\ext
set ARCH=D:\QAMATION_TEST_ENV\Tests\REQUIRED_FILES\for_jmeter_lib_ext
call del /Q .\build\*.*
call copy /y .\data-provider\target\*.jar build
call copy /y .\excel-utils\target\*.jar build
call copy /y .\jmeter-config-data-provider\target\*jar build
call copy /y .\jmeter-java-sampler-abstracts\target\*.jar build
call copy /y .\jmeter-java-sampler-excel\target\*.jar build
call copy /y .\jmeter-java-sampler-keyboard\target\*.jar build
call copy /y .\jmeter-java-sampler-web-extension\target\*.jar build
call copy /y .\jmeter-java-sampler-wmq\target\*.jar build
call copy /y .\keyboard\target\*.jar build
call copy /y .\navigator\target\*.jar build
call copy /y .\utils\target\*.jar build
call copy /y .\webdriver-utils\target\*.jar build
call copy /y .\web-page\target\*.jar build
call copy /y .\wmq-wrapper\target\*.jar build
call copy build\*.jar %TARGET%
call copy build\*.jar %ARCH%

