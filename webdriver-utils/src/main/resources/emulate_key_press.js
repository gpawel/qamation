var modifiers = arguments[0];
var charKey = arguments[1];
var intKey = arguments[2];

var evt = document.createEvent('KeyboardEvent');
Object.defineProperty(evt, 'which', {
        get : function() {
          return this.whichVal;
        }
      });
Object.defineProperty(evt, 'keyCode', {
        get : function() {
          return this.keyCodeVal;
        }
      });
Object.defineProperty(evt, 'char', {
        get : function() {
          return this.charVal;
        }
      });
Object.defineProperty(evt, 'isTrusted', {
        get : function() {
          return this.isTrustedVal;
        }
      });
var initMethod = typeof evt.initKeyboardEvent !== 'undefined' ? 'initKeyboardEvent' : 'initKeyEvent';
evt[initMethod]('keydown', true, true, window, charKey, 0, modifiers, false, 'en-US');
evt.whichVal = intKey;
evt.keyCodeVal = intKey;
evt.charVal = charKey;
evt.isTrustedVal=true;
document.dispatchEvent(evt);