rem call mvn javadoc:javadoc
rem echo off
set SOURCE=target\site\apidocs\*
set TARGET=build\API
call copy /y .\org.qamation.jmeter.data.provider.controller.excel-data-privider\%SOURCE% %TARGET%
call copy /y .\keyboard\%SOURCE% %TARGET%
call copy /y .\navigator\%SOURCE% %TARGET%
call copy /y .\utils\%SOURCE% %TARGET%
call copy /y .\webdriver-utils\%SOURCE% %TARGET%
call copy /y .\web-page\target\%SOURCE% %TARGET%
call copy /y .\wmq-wrapper\%SOURCE% %TARGET%
call copy /y .\jmeter-java-sampler-abstracts\%SOURCE% %TARGET%
call copy /y .\jmeter-java-sampler-org.qamation.jmeter.data.provider.controller.excel\%SOURCE% %TARGET%
call copy /y .\jmeter-java-sampler-keyboard\%SOURCE% %TARGET%
call copy /y .\jmeter-java-sampler-web-extension\%SOURCE% %TARGET%
call copy /y .\jmeter-java-sampler-wmq\target\%SOURCE% %TARGET%

