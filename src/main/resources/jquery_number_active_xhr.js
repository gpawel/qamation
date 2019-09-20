var callback = arguments[arguments.length - 1];
var numberOfRunningXHR = $.active;
console.log("Number of active connections; ", numberOfRunningXHR);
callback(numberOfRunningXHR);