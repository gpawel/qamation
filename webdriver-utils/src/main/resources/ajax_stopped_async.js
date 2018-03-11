console.log("ajax.stop(); ");
var callback = arguments[arguments.length-1];
$(document).ajaxStop(callback(true));
