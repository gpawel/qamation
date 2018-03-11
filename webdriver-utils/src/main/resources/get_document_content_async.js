var callback = arguments[arguments.length - 1];
var content = document.documentElement.innerHTML;
console.log("document content; length: ", content.length);
callback(content);