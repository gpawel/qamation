rem echo off
call set_root.bat
@ECHO %ROOT%
set TARGET=%ROOT%\Jmeter\lib\ext
set ARCH=%ROOT%\Tests\REQUIRED_FILES\for_jmeter_lib_ext
call del /Q .\build\*.*
call copy /y .\data-provider\target\*.jar build
call copy /y .\org.qamation.jmeter.data.provider.controller.excel-utils\target\*.jar build
call copy /y .\jmeter-config-data-provider\target\*jar build
call copy /y .\jmeter-java-sampler-abstracts\target\*.jar build
call copy /y .\jmeter-java-sampler-org.qamation.jmeter.data.provider.controller.excel\target\*.jar build
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

