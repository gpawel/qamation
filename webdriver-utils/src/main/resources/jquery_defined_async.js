var callback = arguments[arguments.length-1];
var isJQueryDefined = (window.jQuery == undefined) ? false:true;
console.log("jquery defined async; ",isJQueryDefined);
callback(isJQueryDefined);