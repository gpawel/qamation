var ready_callback = arguments[arguments.length - 1];
var awaitTimeout = arguments[0];
var pauseTime = arguments[1];
var elementSelector = arguments[2];
console.log("timeout: ",awaitTimeout," pause: ",pauseTime," element: ",elementSelector);
var mutationsPerInterval = []

var drawStarted = false;
var startingTime = Date.now();

var screenReadyObserver = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
    mutationsPerInterval.push(mutation);
  });
});
//var target = document.querySelector('.grid-ui');
var target = document.documentElement;
var config = { attributes: true, subtree: true, childList: true, characterData: true, attributeOldValue: false, characterDataOldValue: false}
screenReadyObserver.observe(target, config);

var intervalObserverHandler = function() {
  if (!drawStarted) {
    console.log("drawing not started");
    if (mutationsPerInterval.length > 0) {
      drawStarted = true;
      console.log("mutions found: ",mutationsPerInterval.length);
      mutationsPerInterval = [];
    } else if ((Date.now() - startingTime) > awaitTimeout) {
      console.log("time out");
      stopAndExit(false);
    }
  } else {
    console.log("drawing started");
    if (mutationsPerInterval.length == 0) {
      console.log("drawing; mutations: ",mutationsPerInterval.length,"exiting");
      stopAndExit(true);
    } else {
      console.log("drawing; mutations: ",mutationsPerInterval.length);
      mutationsPerInterval = [];
    }
  }
}

var stopAndExit = function(message) {
  clearInterval(screenReadyTimer);
  screenReadyObserver.disconnect();
  console.log("stop and exit!");
  ready_callback(message);
}

var screenReadyTimer = setInterval(intervalObserverHandler, pauseTime);