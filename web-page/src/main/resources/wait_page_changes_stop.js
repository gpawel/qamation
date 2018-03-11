var callback = arguments[arguments.length - 1];
var timeOut = arguments[0];
var pauseTime = arguments[1];
var element = document.documentElement;//arguments[2];
console.log("timeout: ",timeOut," pause: ",pauseTime);
var changes=0;
var drawStarted = false;
var startingTime = Date.now();

var screenReadyObserver = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
    changes++;
  });
});
//var target = document.querySelector('.grid-ui');
var target = element;
var config = { attributes: true, subtree: true, childList: true, characterData: true, attributeOldValue: true, characterDataOldValue: true}
screenReadyObserver.observe(target, config);

var intervalObserverHandler = function() {
  if (drawStarted) {
        console.log("drawing started");
        if (changes == 0) {
          console.log("drawing; mutations: ",0," - exiting");
          stopAndExit(true);
        }
        else {
          console.log("drawing; mutations: ",changes);
          changes = 0;
        }
  }
  else {
        console.log("drawing not started");
        if (changes > 0) {
           drawStarted = true;
           console.log("mutions found: ",changes);
           changes=0;
        } else
           if ((Date.now() - startingTime) > timeOut) {
            console.log("time out");
            stopAndExit(false);
           }
  }
}

var stopAndExit = function(message) {
  clearInterval(screenReadyTimer);
  screenReadyObserver.disconnect();
  console.log("stop and exit!");
  callback(message);
}

var screenReadyTimer = setInterval(intervalObserverHandler, pauseTime);