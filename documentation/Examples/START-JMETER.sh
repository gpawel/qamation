ROOT="/home/pavel/workspace/qamation/documentation/Examples"
cd $ROOT
JMETER_HOME="/home/pavel/workspace/envs/jmeter_src"
SELENIUM_PATH="/home/pavel/workspace/Selenium"
SELENIUM_HOME="-DSELENIUM_HOME=$SELENIUM_PATH"	
USER_DIR="-DROOT=$ROOT -Duser.dir=$ROOT"
SEARCH_PATH="-Dsearch_paths=$ROOT/LIB_EXT;$ROOT/LIB;$SELENIUM_PATH/Server;"
TIME_OUT_CONFIG="-S $ROOT/TIME_OUT.properties"
BEAN_SHELLS="-Dbeanshell.sampler.init=$ROOT/BEAN_SHELLS/BeanShellSampler.bshrc -Dbeanshell.listener.init=$ROOT/BEAN_SHELLS/BeanShellListeners.bshrc"
#echo $USER_DIR
#echo $SEARCH_PATH
#echo $SEARCH_CLASSPATH
# user.dir=/home/pavel/workspace/qamation/documentation/Examples
# search_paths=/home/pavel/workspace/qamation/documentation/Examples/LIB_EXT
# user.classpath=/home/pavel/workspace/qamation/documentation/Examples/LIB

/bin/bash $JMETER_HOME/bin/jmeter.sh $TIME_OUT_CONFIG $USER_DIR $SEARCH_PATH $SELENIUM_HOME $BEAN_SHELLS
