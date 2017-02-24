var ready_callback = arguments[arguments.length - 1];
var awaitTimeout = arguments[0];

var mutationsPerInterval = []

var drawStarted = false;
var startingTime = Date.now();

var screenReadyObserver = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
    mutationsPerInterval.push(mutation);
  });
});
var target = document.querySelector('.grid-ui'); 
var config = { attributes: true, subtree: true, childList: false, characterData: true, attributeOldValue: true }
screenReadyObserver.observe(target, config);

var intervalObserverHandler = function() {
  if (!drawStarted) {
    if (mutationsPerInterval.length > 0) {
      drawStarted = true;
      mutationsPerInterval = [];
    } else if ((Date.now() - startingTime) > awaitTimeout) {
      stopAndExit('timed out: ' + (Date.now() - startingTime));
    }
  } else {
    if (mutationsPerInterval.length == 0) {
      stopAndExit('ready: ' + (Date.now() - startingTime));
    } else {
      mutationsPerInterval = [];
    }
  }
}

var stopAndExit = function(message) {
  clearInterval(screenReadyTimer);
  screenReadyObserver.disconnect();
  ready_callback(message);
}

var screenReadyTimer = setInterval(intervalObserverHandler, 50);