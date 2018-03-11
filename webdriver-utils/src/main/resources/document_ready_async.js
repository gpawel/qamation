var callback = arguments[arguments.length-1];
var isDocReady = ((document.readyState === "complete") ? true:false);
console.log("document ready async; ",isDocReady);
callback(isDocReady);
