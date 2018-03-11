var isDocReady = (document.readyState === "complete") ? true:false;
console.log("document ready sync; ",isDocReady);
return isDocReady;