var callback = arguments[arguments.length-1];
var isJQueryActive = ($.active == 0) ? true:false;
console.log("jquery_stopped_async; ",isJQueryActive);
callback(isJQueryActive);