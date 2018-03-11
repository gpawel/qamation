var callback = arguments[arguments.length-1];
console.log("entering wait window load. ",Date.now());
var startTs = Date.now();
function exit (event) {
    if (event != null) {
        if (typeof event === 'undefined') console.log("event is undefined");
        else {
        //console.log("target:", event.target);
        console.log("type: ",event.type);
        console.log("bubbles: ",event.bubbles);
        console.log("cancelable: ",event.cancelable);
        console.log("view: ",event.view);
        console.detail("detail: ",event.detail);
        }
    }
    else {console.log("event is null");}
    var endTs = Date.now();
    console.log("exiting in ",endTs-startTs);
    callback(endTs-startTs);
}
window.addEventListener("load",exit(event),true);
