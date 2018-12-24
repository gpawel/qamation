var callback = arguments[arguments.length - 1];
var timeOut = arguments[0];
var pauseTime = arguments[1];
var element = document.documentElement;//arguments[2];
console.log("Waiting for page changes to stop: timeout: ",timeOut," pause: ",pauseTime);
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
          console.log("drawing; mutations: ",0," - exiting");
          stopAndExit(totalChanges);
        }
        else {
          console.log("drawing; mutations: ",changes);
          totalChanges = totalChanges + changes;
          changes = 0;
          checkTimeOut();
        }
  }
  else {
        if (changes > 0) {
           drawingStartMoment = Date.now() - startingTime;
           drawStarted = true;
           totalChanges = totalChanges + changes;
           changes=0;
           console.log("drawing started");
        }
        else
           console.log("drawing not started");
           checkTimeOut();
  }
}

var checkTimeOut = function() {
 if ((Date.now() - startingTime) > timeOut) {
      stopAndExit(-1);
 }
}

var stopAndExit = function(message) {
  clearInterval(screenReadyTimer);
  screenReadyObserver.disconnect();
  console.log("Ready. Mttns: ",totalChanges," Draw star in ",drawingStartMoment,"Total time: ",Date.now()-startingTime);
  callback(message);
  }

var screenReadyTimer = setInterval(intervalObserverHandler, pauseTime);
