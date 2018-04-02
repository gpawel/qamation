var callback = arguments[arguments.length-1];
var timeOut = arguments[0];
var interval = arguments[1];
console.log("timeOut: ",timeOut," interval: ",interval);
var totalScriptsLoaded = document.scripts.length;
var scriptsSofar = document.scripts.length;
var startTS = Date.now();
var loadStarted = false;

var checkLoadedScripts = function() {
    totalScriptsLoaded = document.scripts.length;
    var diff = totalScriptsLoaded - scriptsSofar;
    if (loadStarted) {
        if (diff == 0) exit(true);
        scriptsSofar = totalScriptsLoaded;
    }
    else {
        if ((totalScriptsLoaded - scriptsSofar) > 0) {
            loadStarted = true;
            scriptsSofar = totalScriptsLoaded;
        }
        else {
            if (Date.now() - startTS > timeOut) {
                exit(false)
            }
        }
    }

}

function exit(message) {
    console.log("total scripts loaded: ",totalScriptsLoaded," load time: ",Date.now()-startTS, " message: ",message);
    clearInterval(repeat);
    callback(message)
}

var repeat = setInterval(checkLoadedScripts,interval)