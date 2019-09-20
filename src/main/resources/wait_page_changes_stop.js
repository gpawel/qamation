var callback = arguments[arguments.length - 1];
var notStartedtimeOut = arguments[0];
var watchTimeOut = arguments[1];
var pauseTime = arguments[2];
var element = document.documentElement;//arguments[2];
console.log("Waiting for page changes to stop. timeout if drawing not starting: ",notStartedtimeOut," watch drawing timeout",watchTimeOut," pause: ",pauseTime);
var changes=0;
var totalChanges=0;
var drawStarted = false;
var startingTime = Date.now();
var drawingStartMoment= -1;


var screenReadyObserver = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
    changes++;
  });
});


var config = { attributes: true, subtree: true, childList: true, characterData: true, attributeOldValue: true, characterDataOldValue: true};
screenReadyObserver.observe(element, config);

var intervalObserverHandler = function() {
  if (drawStarted) {
        if (changes == 0) {
          //console.log("drawing; mutations: ",0," - exiting");
          stopAndExit(totalChanges);
        }
        else {
          //console.log("drawing; mutations: ",changes);
          totalChanges = totalChanges + changes;
          changes = 0;
          checkTimeOut(watchTimeOut);
        }
  }
  else {
        if (changes > 0) {
           drawingStartMoment = Date.now() - startingTime;
           drawStarted = true;
           totalChanges = totalChanges + changes;
           changes=0;
           //console.log("drawing started");
        }
        else
           //console.log("drawing not started");
           checkTimeOut(notStartedtimeOut);
  }
}

var checkTimeOut = function(duration) {
 if ((Date.now() - startingTime) > duration) {
      stopAndExit(totalChanges);
 }
}



var stopAndExit = function(message) {
  clearInterval(screenReadyTimer);
  screenReadyObserver.disconnect();
  console.log("Ready. Mttns: ",totalChanges," Draw star in ",drawingStartMoment,"Total time: ",Date.now()-startingTime);
  callback(message);
  }

var screenReadyTimer = setInterval(intervalObserverHandler, pauseTime);
