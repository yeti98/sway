// Utility function
function Util () {};

/* 
	class manipulation functions
*/
Util.hasClass = function(el, className) {
	if (el.classList) return el.classList.contains(className);
	else return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'));
};

Util.addClass = function(el, className) {
	var classList = className.split(' ');
 	if (el.classList) el.classList.add(classList[0]);
 	else if (!Util.hasClass(el, classList[0])) el.className += " " + classList[0];
 	if (classList.length > 1) Util.addClass(el, classList.slice(1).join(' '));
};

Util.removeClass = function(el, className) {
	var classList = className.split(' ');
	if (el.classList) el.classList.remove(classList[0]);	
	else if(Util.hasClass(el, classList[0])) {
		var reg = new RegExp('(\\s|^)' + classList[0] + '(\\s|$)');
		el.className=el.className.replace(reg, ' ');
	}
	if (classList.length > 1) Util.removeClass(el, classList.slice(1).join(' '));
};

Util.toggleClass = function(el, className, bool) {
	if(bool) Util.addClass(el, className);
	else Util.removeClass(el, className);
};

Util.setAttributes = function(el, attrs) {
  for(var key in attrs) {
    el.setAttribute(key, attrs[key]);
  }
};

/* 
  DOM manipulation
*/
Util.getChildrenByClassName = function(el, className) {
  var children = el.children,
    childrenByClass = [];
  for (var i = 0; i < el.children.length; i++) {
    if (Util.hasClass(el.children[i], className)) childrenByClass.push(el.children[i]);
  }
  return childrenByClass;
};

Util.is = function(elem, selector) {
  if(selector.nodeType){
    return elem === selector;
  }

  var qa = (typeof(selector) === 'string' ? document.querySelectorAll(selector) : selector),
    length = qa.length,
    returnArr = [];

  while(length--){
    if(qa[length] === elem){
      return true;
    }
  }

  return false;
};

/* 
	Animate height of an element
*/
Util.setHeight = function(start, to, element, duration, cb) {
	var change = to - start,
	    currentTime = null;

  var animateHeight = function(timestamp){  
    if (!currentTime) currentTime = timestamp;         
    var progress = timestamp - currentTime;
    var val = parseInt((progress/duration)*change + start);
    element.style.height = val+"px";
    if(progress < duration) {
        window.requestAnimationFrame(animateHeight);
    } else {
    	cb();
    }
  };
  
  //set the height of the element before starting animation -> fix bug on Safari
  element.style.height = start+"px";
  window.requestAnimationFrame(animateHeight);
};

/* 
	Smooth Scroll
*/

Util.scrollTo = function(final, duration, cb, scrollEl) {
  var element = scrollEl || window;
  var start = element.scrollTop || document.documentElement.scrollTop,
    currentTime = null;

  if(!scrollEl) start = window.scrollY || document.documentElement.scrollTop;
      
  var animateScroll = function(timestamp){
  	if (!currentTime) currentTime = timestamp;        
    var progress = timestamp - currentTime;
    if(progress > duration) progress = duration;
    var val = Math.easeInOutQuad(progress, start, final-start, duration);
    element.scrollTo(0, val);
    if(progress < duration) {
        window.requestAnimationFrame(animateScroll);
    } else {
      cb && cb();
    }
  };

  window.requestAnimationFrame(animateScroll);
};

/* 
  Focus utility classes
*/

//Move focus to an element
Util.moveFocus = function (element) {
  if( !element ) element = document.getElementsByTagName("body")[0];
  element.focus();
  if (document.activeElement !== element) {
    element.setAttribute('tabindex','-1');
    element.focus();
  }
};

/* 
  Misc
*/

Util.getIndexInArray = function(array, el) {
  return Array.prototype.indexOf.call(array, el);
};

Util.cssSupports = function(property, value) {
  if('CSS' in window) {
    return CSS.supports(property, value);
  } else {
    var jsProperty = property.replace(/-([a-z])/g, function (g) { return g[1].toUpperCase();});
    return jsProperty in document.body.style;
  }
};

// merge a set of user options into plugin defaults
// https://gomakethings.com/vanilla-javascript-version-of-jquery-extend/
Util.extend = function() {
  // Variables
  var extended = {};
  var deep = false;
  var i = 0;
  var length = arguments.length;

  // Check if a deep merge
  if ( Object.prototype.toString.call( arguments[0] ) === '[object Boolean]' ) {
    deep = arguments[0];
    i++;
  }

  // Merge the object into the extended object
  var merge = function (obj) {
    for ( var prop in obj ) {
      if ( Object.prototype.hasOwnProperty.call( obj, prop ) ) {
        // If deep merge and property is an object, merge properties
        if ( deep && Object.prototype.toString.call(obj[prop]) === '[object Object]' ) {
          extended[prop] = extend( true, extended[prop], obj[prop] );
        } else {
          extended[prop] = obj[prop];
        }
      }
    }
  };

  // Loop through each object and conduct a merge
  for ( ; i < length; i++ ) {
    var obj = arguments[i];
    merge(obj);
  }

  return extended;
};

// Check if Reduced Motion is enabled
Util.osHasReducedMotion = function() {
  if(!window.matchMedia) return false;
  var matchMediaObj = window.matchMedia('(prefers-reduced-motion: reduce)');
  if(matchMediaObj) return matchMediaObj.matches;
  return false; // return false if not supported
}; 

/* 
	Polyfills
*/
//Closest() method
if (!Element.prototype.matches) {
	Element.prototype.matches = Element.prototype.msMatchesSelector || Element.prototype.webkitMatchesSelector;
}

if (!Element.prototype.closest) {
	Element.prototype.closest = function(s) {
		var el = this;
		if (!document.documentElement.contains(el)) return null;
		do {
			if (el.matches(s)) return el;
			el = el.parentElement || el.parentNode;
		} while (el !== null && el.nodeType === 1); 
		return null;
	};
}

//Custom Event() constructor
if ( typeof window.CustomEvent !== "function" ) {

  function CustomEvent ( event, params ) {
    params = params || { bubbles: false, cancelable: false, detail: undefined };
    var evt = document.createEvent( 'CustomEvent' );
    evt.initCustomEvent( event, params.bubbles, params.cancelable, params.detail );
    return evt;
   }

  CustomEvent.prototype = window.Event.prototype;

  window.CustomEvent = CustomEvent;
}

/* 
	Animation curves
*/
Math.easeInOutQuad = function (t, b, c, d) {
	t /= d/2;
	if (t < 1) return c/2*t*t + b;
	t--;
	return -c/2 * (t*(t-2) - 1) + b;
};

Math.easeInQuart = function (t, b, c, d) {
	t /= d;
	return c*t*t*t*t + b;
};

Math.easeOutQuart = function (t, b, c, d) { 
  t /= d;
	t--;
	return -c * (t*t*t*t - 1) + b;
};

Math.easeInOutQuart = function (t, b, c, d) {
	t /= d/2;
	if (t < 1) return c/2*t*t*t*t + b;
	t -= 2;
	return -c/2 * (t*t*t*t - 2) + b;
};

Math.easeOutElastic = function (t, b, c, d) {
  var s=1.70158;var p=d*0.7;var a=c;
  if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
  if (a < Math.abs(c)) { a=c; var s=p/4; }
  else var s = p/(2*Math.PI) * Math.asin (c/a);
  return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
};


/* JS Utility Classes */
(function() {
  // make focus ring visible only for keyboard navigation (i.e., tab key) 
  var focusTab = document.getElementsByClassName('js-tab-focus');
  function detectClick() {
    if(focusTab.length > 0) {
      resetFocusTabs(false);
      window.addEventListener('keydown', detectTab);
    }
    window.removeEventListener('mousedown', detectClick);
  };

  function detectTab(event) {
    if(event.keyCode !== 9) return;
    resetFocusTabs(true);
    window.removeEventListener('keydown', detectTab);
    window.addEventListener('mousedown', detectClick);
  };

  function resetFocusTabs(bool) {
    var outlineStyle = bool ? '' : 'none';
    for(var i = 0; i < focusTab.length; i++) {
      focusTab[i].style.setProperty('outline', outlineStyle);
    }
  };
  window.addEventListener('mousedown', detectClick);
}());
// File#: _1_accordion
// Usage: codyhouse.co/license
(function () {
  var Accordion = function (element) {
    this.element = element;
    this.items = Util.getChildrenByClassName(this.element, 'js-accordion__item');
    this.version = this.element.getAttribute('data-version') ? '-' + this.element.getAttribute('data-version') : '';
    this.showClass = 'accordion' + this.version + '__item--is-open';
    this.animateHeight = (this.element.getAttribute('data-animation') == 'on');
    this.multiItems = !(this.element.getAttribute('data-multi-items') == 'off');
    this.initAccordion();
  };

  Accordion.prototype.initAccordion = function () {
    //set initial aria attributes
    for (var i = 0; i < this.items.length; i++) {
      var button = this.items[i].getElementsByTagName('button')[0],
        content = this.items[i].getElementsByClassName('js-accordion__panel')[0],
        isOpen = Util.hasClass(this.items[i], this.showClass) ? 'true' : 'false';
      Util.setAttributes(button, {
        'aria-expanded': isOpen,
        'aria-controls': 'accordion-content-' + i,
        'id': 'accordion-header-' + i
      });
      Util.addClass(button, 'js-accordion__trigger');
      Util.setAttributes(content, {'aria-labelledby': 'accordion-header-' + i, 'id': 'accordion-content-' + i});
    }

    //listen for Accordion events
    this.initAccordionEvents();
  };

  Accordion.prototype.initAccordionEvents = function () {
    var self = this;

    this.element.addEventListener('click', function (event) {
      var trigger = event.target.closest('.js-accordion__trigger');
      //check index to make sure the click didn't happen inside a children accordion
      if (trigger && Util.getIndexInArray(self.items, trigger.parentElement) >= 0) self.triggerAccordion(trigger);
    });
  };

  Accordion.prototype.triggerAccordion = function (trigger) {
    var self = this;
    var bool = (trigger.getAttribute('aria-expanded') === 'true');

    this.animateAccordion(trigger, bool);
  };

  Accordion.prototype.animateAccordion = function (trigger, bool) {
    var self = this;
    var item = trigger.closest('.js-accordion__item'),
      content = item.getElementsByClassName('js-accordion__panel')[0],
      ariaValue = bool ? 'false' : 'true';

    if (!bool) Util.addClass(item, this.showClass);
    trigger.setAttribute('aria-expanded', ariaValue);
    self.resetContentVisibility(item, content, bool);

    if (!this.multiItems && !bool) this.closeSiblings(item);
  };

  Accordion.prototype.resetContentVisibility = function (item, content, bool) {
    Util.toggleClass(item, this.showClass, !bool);
    content.removeAttribute("style");
    if (bool && !this.multiItems) { // accordion item has been closed -> check if there's one open to move inside viewport
      this.moveContent();
    }
  };

  Accordion.prototype.closeSiblings = function (item) {
    //if only one accordion can be open -> search if there's another one open
    var index = Util.getIndexInArray(this.items, item);
    for (var i = 0; i < this.items.length; i++) {
      if (Util.hasClass(this.items[i], this.showClass) && i != index) {
        this.animateAccordion(this.items[i].getElementsByClassName('js-accordion__trigger')[0], true);
        return false;
      }
    }
  };

  Accordion.prototype.moveContent = function () { // make sure title of the accordion just opened is inside the viewport
    var openAccordion = this.element.getElementsByClassName(this.showClass);
    if (openAccordion.length == 0) return;
    var boundingRect = openAccordion[0].getBoundingClientRect();
    if (boundingRect.top < 0 || boundingRect.top > window.innerHeight) {
      var windowScrollTop = window.scrollY || document.documentElement.scrollTop;
      window.scrollTo(0, boundingRect.top + windowScrollTop);
    }
  };

  window.Accordion = Accordion;

  //initialize the Accordion objects
  var accordions = document.getElementsByClassName('js-accordion');
  if (accordions.length > 0) {
    for (var i = 0; i < accordions.length; i++) {
      (function (i) {
        new Accordion(accordions[i]);
      })(i);
    }
  }
}());

// File#: _1_anim-menu-btn
// Usage: codyhouse.co/license
(function() {
  var menuBtns = document.getElementsByClassName('js-anim-menu-btn');
  if( menuBtns.length > 0 ) {
    for(var i = 0; i < menuBtns.length; i++) {(function(i){
      initMenuBtn(menuBtns[i]);
    })(i);}

    function initMenuBtn(btn) {
      btn.addEventListener('click', function(event){
        event.preventDefault();
        var status = !Util.hasClass(btn, 'anim-menu-btn--state-b');
        Util.toggleClass(btn, 'anim-menu-btn--state-b', status);
        // emit custom event
        var event = new CustomEvent('anim-menu-btn-clicked', {detail: status});
        btn.dispatchEvent(event);
      });
    };
  }
}());

// File#: _1_choice-buttons
// Usage: codyhouse.co/license
(function () {
  var ChoiceButton = function (element) {
    this.element = element;
    this.btns = this.element.getElementsByClassName('js-choice-btn');
    this.inputs = getChoiceInput(this);
    this.isRadio = this.inputs[0].type.toString() == 'radio';
    resetCheckedStatus(this); // set initial classes
    initChoiceButtonEvent(this); // add listeners
  };

  function getChoiceInput(element) { // store input elements in an object property
    var inputs = [];
    for (var i = 0; i < element.btns.length; i++) {
      inputs.push(element.btns[i].getElementsByTagName('input')[0]);
    }
    return inputs;
  }

  function initChoiceButtonEvent(choiceBtn) {
    choiceBtn.element.addEventListener('click', function (event) { // update status on click
      if (Util.getIndexInArray(choiceBtn.inputs, event.target) > -1) return; // triggered by change in input element -> will be detected by the 'change' event

      var selectedBtn = event.target.closest('.js-choice-btn');
      if (!selectedBtn) return;
      var index = Util.getIndexInArray(choiceBtn.btns, selectedBtn);
      if (choiceBtn.isRadio && choiceBtn.inputs[index].checked) { // radio input already checked
        choiceBtn.inputs[index].focus(); // move focus to input element
        return;
      }

      choiceBtn.inputs[index].checked = !choiceBtn.inputs[index].checked;
      choiceBtn.inputs[index].dispatchEvent(new CustomEvent('change')); // trigger change event
      choiceBtn.inputs[index].focus(); // move focus to input element
    });

    for (var i = 0; i < choiceBtn.btns.length; i++) {
      (function (i) { // change + focus events
        choiceBtn.inputs[i].addEventListener('change', function (event) {
          choiceBtn.isRadio ? resetCheckedStatus(choiceBtn) : resetSingleStatus(choiceBtn, i);
        });

        choiceBtn.inputs[i].addEventListener('focus', function (event) {
          resetFocusStatus(choiceBtn, i, true);
        });

        choiceBtn.inputs[i].addEventListener('blur', function (event) {
          resetFocusStatus(choiceBtn, i, false);
        });
      })(i);
    }
  }

  function resetCheckedStatus(choiceBtn) {
    for (var i = 0; i < choiceBtn.btns.length; i++) {
      resetSingleStatus(choiceBtn, i);
    }
  }

  function resetSingleStatus(choiceBtn, index) { // toggle .choice-btn--checked class
    Util.toggleClass(choiceBtn.btns[index], 'choice-btn--checked', choiceBtn.inputs[index].checked);
  }

  function resetFocusStatus(choiceBtn, index, bool) { // toggle .choice-btn--focus class
    Util.toggleClass(choiceBtn.btns[index], 'choice-btn--focus', bool);
  }

  //initialize the ChoiceButtons objects
  var choiceButton = document.getElementsByClassName('js-choice-btns');
  if (choiceButton.length > 0) {
    for (var i = 0; i < choiceButton.length; i++) {
      (function (i) {
        new ChoiceButton(choiceButton[i]);
      })(i);
    }
  }

}());

// File#: _1_menu
// Usage: codyhouse.co/license
(function() {
  var Menu = function(element) {
    this.element = element;
    this.elementId = this.element.getAttribute('id');
    this.menuItems = this.element.getElementsByClassName('js-menu__content');
    this.trigger = document.querySelectorAll('[aria-controls="'+this.elementId+'"]');
    this.selectedTrigger = false;
    this.menuIsOpen = false;
    this.initMenu();
    this.initMenuEvents();
  };

  Menu.prototype.initMenu = function() {
    // init aria-labels
    for(var i = 0; i < this.trigger.length; i++) {
      Util.setAttributes(this.trigger[i], {'aria-expanded': 'false', 'aria-haspopup': 'true'});
    }
    // init tabindex
    for(var i = 0; i < this.menuItems.length; i++) {
      this.menuItems[i].setAttribute('tabindex', '0');
    }
  };

  Menu.prototype.initMenuEvents = function() {
    var self = this;
    for(var i = 0; i < this.trigger.length; i++) {(function(i){
      self.trigger[i].addEventListener('click', function(event){
        event.preventDefault();
        // if the menu had been previously opened by another trigger element -> close it first and reopen in the right position
        if(Util.hasClass(self.element, 'menu--is-visible') && self.selectedTrigger !=  self.trigger[i]) {
          self.toggleMenu(false, false); // close menu
        }
        // toggle menu
        self.selectedTrigger = self.trigger[i];
        self.toggleMenu(!Util.hasClass(self.element, 'menu--is-visible'), true);
      });
    })(i);}

    // keyboard events
    this.element.addEventListener('keydown', function(event) {
      // use up/down arrow to navigate list of menu items
      if( !Util.hasClass(event.target, 'js-menu__content') ) return;
      if( (event.keyCode && event.keyCode == 40) || (event.key && event.key.toLowerCase() == 'arrowdown') ) {
        self.navigateItems(event, 'next');
      } else if( (event.keyCode && event.keyCode == 38) || (event.key && event.key.toLowerCase() == 'arrowup') ) {
        self.navigateItems(event, 'prev');
      }
    });
  };

  Menu.prototype.toggleMenu = function(bool, moveFocus) {
    var self = this;
    // toggle menu visibility
    Util.toggleClass(this.element, 'menu--is-visible', bool);
    this.menuIsOpen = bool;
    if(bool) {
      this.selectedTrigger.setAttribute('aria-expanded', 'true');
      Util.moveFocus(this.menuItems[0]);
      this.element.addEventListener("transitionend", function(event) {Util.moveFocus(self.menuItems[0]);}, {once: true});
      // position the menu element
      this.positionMenu();
      // add class to menu trigger
      Util.addClass(this.selectedTrigger, 'menu-control--active');
    } else if(this.selectedTrigger) {
      this.selectedTrigger.setAttribute('aria-expanded', 'false');
      if(moveFocus) Util.moveFocus(this.selectedTrigger);
      // remove class from menu trigger
      Util.removeClass(this.selectedTrigger, 'menu-control--active');
      this.selectedTrigger = false;
    }
  };

  Menu.prototype.positionMenu = function(event, direction) {
    var selectedTriggerPosition = this.selectedTrigger.getBoundingClientRect(),
      menuOnTop = (window.innerHeight - selectedTriggerPosition.bottom) < selectedTriggerPosition.top;
    // menuOnTop = window.innerHeight < selectedTriggerPosition.bottom + this.element.offsetHeight;

    var left = selectedTriggerPosition.left,
      right = (window.innerWidth - selectedTriggerPosition.right),
      isRight = (window.innerWidth < selectedTriggerPosition.left + this.element.offsetWidth);

    var horizontal = isRight ? 'right: '+right+'px;' : 'left: '+left+'px;',
      vertical = menuOnTop
        ? 'bottom: '+(window.innerHeight - selectedTriggerPosition.top)+'px;'
        : 'top: '+selectedTriggerPosition.bottom+'px;';
    // check right position is correct -> otherwise set left to 0
    if( isRight && (right + this.element.offsetWidth) > window.innerWidth) horizontal = 'left: '+ parseInt((window.innerWidth - this.element.offsetWidth)/2)+'px;';
    var maxHeight = menuOnTop ? selectedTriggerPosition.top - 20 : window.innerHeight - selectedTriggerPosition.bottom - 20;
    this.element.setAttribute('style', horizontal + vertical +'max-height:'+Math.floor(maxHeight)+'px;');
  };

  Menu.prototype.navigateItems = function(event, direction) {
    event.preventDefault();
    var index = Util.getIndexInArray(this.menuItems, event.target),
      nextIndex = direction == 'next' ? index + 1 : index - 1;
    if(nextIndex < 0) nextIndex = this.menuItems.length - 1;
    if(nextIndex > this.menuItems.length - 1) nextIndex = 0;
    Util.moveFocus(this.menuItems[nextIndex]);
  };

  Menu.prototype.checkMenuFocus = function() {
    var menuParent = document.activeElement.closest('.js-menu');
    if (!menuParent || !this.element.contains(menuParent)) this.toggleMenu(false, false);
  };

  Menu.prototype.checkMenuClick = function(target) {
    if( !this.element.contains(target) && !target.closest('[aria-controls="'+this.elementId+'"]')) this.toggleMenu(false);
  };

  window.Menu = Menu;

  //initialize the Menu objects
  var menus = document.getElementsByClassName('js-menu');
  if( menus.length > 0 ) {
    var menusArray = [];
    var scrollingContainers = [];
    for( var i = 0; i < menus.length; i++) {
      (function(i){
        menusArray.push(new Menu(menus[i]));
        var scrollableElement = menus[i].getAttribute('data-scrollable-element');
        if(scrollableElement && !scrollingContainers.includes(scrollableElement)) scrollingContainers.push(scrollableElement);
      })(i);
    }

    // listen for key events
    window.addEventListener('keyup', function(event){
      if( event.keyCode && event.keyCode == 9 || event.key && event.key.toLowerCase() == 'tab' ) {
        //close menu if focus is outside menu element
        menusArray.forEach(function(element){
          element.checkMenuFocus();
        });
      } else if( event.keyCode && event.keyCode == 27 || event.key && event.key.toLowerCase() == 'escape' ) {
        // close menu on 'Esc'
        menusArray.forEach(function(element){
          element.toggleMenu(false, false);
        });
      }
    });
    // close menu when clicking outside it
    window.addEventListener('click', function(event){
      menusArray.forEach(function(element){
        element.checkMenuClick(event.target);
      });
    });
    // on resize -> close all menu elements
    window.addEventListener('resize', function(event){
      menusArray.forEach(function(element){
        element.toggleMenu(false, false);
      });
    });
    // on scroll -> close all menu elements
    window.addEventListener('scroll', function(event){
      menusArray.forEach(function(element){
        if(element.menuIsOpen) element.toggleMenu(false, false);
      });
    });
    // take into account additinal scrollable containers
    for(var j = 0; j < scrollingContainers.length; j++) {
      var scrollingContainer = document.querySelector(scrollingContainers[j]);
      if(scrollingContainer) {
        scrollingContainer.addEventListener('scroll', function(event){
          menusArray.forEach(function(element){
            if(element.menuIsOpen) element.toggleMenu(false, false);
          });
        });
      }
    }
  }
}());

// File#: _1_notice
// Usage: codyhouse.co/license
(function() {
  function initNoticeEvents(notice) {
    notice.addEventListener('click', function(event){
      if(event.target.closest('.js-notice__hide-control')) {
        event.preventDefault();
        Util.addClass(notice, 'notice--hide');
      }
    });
  };

  var noticeElements = document.getElementsByClassName('js-notice');
  if(noticeElements.length > 0) {
    for(var i=0; i < noticeElements.length; i++) {(function(i){
      initNoticeEvents(noticeElements[i]);
    })(i);}
  }
}());

// File#: _1_responsive-sidebar
// Usage: codyhouse.co/license
(function() {
  var Sidebar = function(element) {
    this.element = element;
    this.triggers = document.querySelectorAll('[aria-controls="'+this.element.getAttribute('id')+'"]');
    this.firstFocusable = null;
    this.lastFocusable = null;
    this.selectedTrigger = null;
    this.showClass = "sidebar--is-visible";
    this.staticClass = "sidebar--static";
    this.customStaticClass = "";
    this.readyClass = "sidebar--loaded";
    this.layout = false; // this will be static or mobile
    getCustomStaticClass(this); // custom classes for static version
    initSidebar(this);
  };

  function getCustomStaticClass(element) {
    var customClasses = element.element.getAttribute('data-static-class');
    if(customClasses) element.customStaticClass = ' '+customClasses;
  };

  function initSidebar(sidebar) {
    initSidebarResize(sidebar); // handle changes in layout -> mobile to static and viceversa

    if ( sidebar.triggers ) { // open sidebar when clicking on trigger buttons - mobile layout only
      for(var i = 0; i < sidebar.triggers.length; i++) {
        sidebar.triggers[i].addEventListener('click', function(event) {
          event.preventDefault();
          if(Util.hasClass(sidebar.element, sidebar.showClass)) {
            sidebar.selectedTrigger = event.target;
            closeSidebar(sidebar);
            return;
          }
          sidebar.selectedTrigger = event.target;
          showSidebar(sidebar);
          initSidebarEvents(sidebar);
        });
      }
    }
  };

  function showSidebar(sidebar) { // mobile layout only
    Util.addClass(sidebar.element, sidebar.showClass);
    getFocusableElements(sidebar);
    Util.moveFocus(sidebar.element);
  };

  function closeSidebar(sidebar) { // mobile layout only
    Util.removeClass(sidebar.element, sidebar.showClass);
    sidebar.firstFocusable = null;
    sidebar.lastFocusable = null;
    if(sidebar.selectedTrigger) sidebar.selectedTrigger.focus();
    sidebar.element.removeAttribute('tabindex');
    //remove listeners
    cancelSidebarEvents(sidebar);
  };

  function initSidebarEvents(sidebar) { // mobile layout only
    //add event listeners
    sidebar.element.addEventListener('keydown', handleEvent.bind(sidebar));
    sidebar.element.addEventListener('click', handleEvent.bind(sidebar));
  };

  function cancelSidebarEvents(sidebar) { // mobile layout only
    //remove event listeners
    sidebar.element.removeEventListener('keydown', handleEvent.bind(sidebar));
    sidebar.element.removeEventListener('click', handleEvent.bind(sidebar));
  };

  function handleEvent(event) { // mobile layout only
    switch(event.type) {
      case 'click': {
        initClick(this, event);
      }
      case 'keydown': {
        initKeyDown(this, event);
      }
    }
  };

  function initKeyDown(sidebar, event) { // mobile layout only
    if( event.keyCode && event.keyCode == 27 || event.key && event.key == 'Escape' ) {
      //close sidebar window on esc
      closeSidebar(sidebar);
    } else if( event.keyCode && event.keyCode == 9 || event.key && event.key == 'Tab' ) {
      //trap focus inside sidebar
      trapFocus(sidebar, event);
    }
  };

  function initClick(sidebar, event) { // mobile layout only
    //close sidebar when clicking on close button or sidebar bg layer
    if( !event.target.closest('.js-sidebar__close-btn') && !Util.hasClass(event.target, 'js-sidebar') ) return;
    event.preventDefault();
    closeSidebar(sidebar);
  };

  function trapFocus(sidebar, event) { // mobile layout only
    if( sidebar.firstFocusable == document.activeElement && event.shiftKey) {
      //on Shift+Tab -> focus last focusable element when focus moves out of sidebar
      event.preventDefault();
      sidebar.lastFocusable.focus();
    }
    if( sidebar.lastFocusable == document.activeElement && !event.shiftKey) {
      //on Tab -> focus first focusable element when focus moves out of sidebar
      event.preventDefault();
      sidebar.firstFocusable.focus();
    }
  };

  function initSidebarResize(sidebar) {
    // custom event emitted when window is resized - detect only if the sidebar--static@{breakpoint} class was added
    var beforeContent = getComputedStyle(sidebar.element, ':before').getPropertyValue('content');
    if(beforeContent && beforeContent !='' && beforeContent !='none') {
      checkSidebarLayour(sidebar);

      sidebar.element.addEventListener('update-sidebar', function(event){
        checkSidebarLayour(sidebar);
      });
    }
    Util.addClass(sidebar.element, sidebar.readyClass);
  };

  function checkSidebarLayour(sidebar) {
    var layout = getComputedStyle(sidebar.element, ':before').getPropertyValue('content').replace(/\'|"/g, '');
    if(layout == sidebar.layout) return;
    sidebar.layout = layout;
    if(layout != 'static') Util.addClass(sidebar.element, 'is-hidden');
    Util.toggleClass(sidebar.element, sidebar.staticClass + sidebar.customStaticClass, layout == 'static');
    if(layout != 'static') setTimeout(function(){Util.removeClass(sidebar.element, 'is-hidden')});
    // reset element role
    (layout == 'static') ? sidebar.element.removeAttribute('role', 'alertdialog') :  sidebar.element.setAttribute('role', 'alertdialog');
    // reset mobile behaviour
    if(layout == 'static' && Util.hasClass(sidebar.element, sidebar.showClass)) closeSidebar(sidebar);
  };

  function getFocusableElements(sidebar) {
    //get all focusable elements inside the drawer
    var allFocusable = sidebar.element.querySelectorAll('[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, [tabindex]:not([tabindex="-1"]), [contenteditable], audio[controls], video[controls], summary');
    getFirstVisible(sidebar, allFocusable);
    getLastVisible(sidebar, allFocusable);
  };

  function getFirstVisible(sidebar, elements) {
    //get first visible focusable element inside the sidebar
    for(var i = 0; i < elements.length; i++) {
      if( elements[i].offsetWidth || elements[i].offsetHeight || elements[i].getClientRects().length ) {
        sidebar.firstFocusable = elements[i];
        return true;
      }
    }
  };

  function getLastVisible(sidebar, elements) {
    //get last visible focusable element inside the sidebar
    for(var i = elements.length - 1; i >= 0; i--) {
      if( elements[i].offsetWidth || elements[i].offsetHeight || elements[i].getClientRects().length ) {
        sidebar.lastFocusable = elements[i];
        return true;
      }
    }
  };

  //initialize the Sidebar objects
  var sidebar = document.getElementsByClassName('js-sidebar');
  if( sidebar.length > 0 ) {
    for( var i = 0; i < sidebar.length; i++) {
      (function(i){new Sidebar(sidebar[i]);})(i);
    }
    // switch from mobile to static layout
    var customEvent = new CustomEvent('update-sidebar');
    window.addEventListener('resize', function(event){
      (!window.requestAnimationFrame) ? setTimeout(function(){resetLayout();}, 250) : window.requestAnimationFrame(resetLayout);
    });

    function resetLayout() {
      for( var i = 0; i < sidebar.length; i++) {
        (function(i){sidebar[i].dispatchEvent(customEvent)})(i);
      };
    };
  }
}());

// File#: _1_swipe-content
(function() {
  var SwipeContent = function(element) {
    this.element = element;
    this.delta = [false, false];
    this.dragging = false;
    this.intervalId = false;
    initSwipeContent(this);
  };

  function initSwipeContent(content) {
    content.element.addEventListener('mousedown', handleEvent.bind(content));
    content.element.addEventListener('touchstart', handleEvent.bind(content));
  };

  function initDragging(content) {
    //add event listeners
    content.element.addEventListener('mousemove', handleEvent.bind(content));
    content.element.addEventListener('touchmove', handleEvent.bind(content));
    content.element.addEventListener('mouseup', handleEvent.bind(content));
    content.element.addEventListener('mouseleave', handleEvent.bind(content));
    content.element.addEventListener('touchend', handleEvent.bind(content));
  };

  function cancelDragging(content) {
    //remove event listeners
    if(content.intervalId) {
      (!window.requestAnimationFrame) ? clearInterval(content.intervalId) : window.cancelAnimationFrame(content.intervalId);
      content.intervalId = false;
    }
    content.element.removeEventListener('mousemove', handleEvent.bind(content));
    content.element.removeEventListener('touchmove', handleEvent.bind(content));
    content.element.removeEventListener('mouseup', handleEvent.bind(content));
    content.element.removeEventListener('mouseleave', handleEvent.bind(content));
    content.element.removeEventListener('touchend', handleEvent.bind(content));
  };

  function handleEvent(event) {
    switch(event.type) {
      case 'mousedown':
      case 'touchstart':
        startDrag(this, event);
        break;
      case 'mousemove':
      case 'touchmove':
        drag(this, event);
        break;
      case 'mouseup':
      case 'mouseleave':
      case 'touchend':
        endDrag(this, event);
        break;
    }
  };

  function startDrag(content, event) {
    content.dragging = true;
    // listen to drag movements
    initDragging(content);
    content.delta = [parseInt(unify(event).clientX), parseInt(unify(event).clientY)];
    // emit drag start event
    emitSwipeEvents(content, 'dragStart', content.delta, event.target);
  };

  function endDrag(content, event) {
    cancelDragging(content);
    // credits: https://css-tricks.com/simple-swipe-with-vanilla-javascript/
    var dx = parseInt(unify(event).clientX),
      dy = parseInt(unify(event).clientY);

    // check if there was a left/right swipe
    if(content.delta && (content.delta[0] || content.delta[0] === 0)) {
      var s = getSign(dx - content.delta[0]);

      if(Math.abs(dx - content.delta[0]) > 30) {
        (s < 0) ? emitSwipeEvents(content, 'swipeLeft', [dx, dy]) : emitSwipeEvents(content, 'swipeRight', [dx, dy]);
      }

      content.delta[0] = false;
    }
    // check if there was a top/bottom swipe
    if(content.delta && (content.delta[1] || content.delta[1] === 0)) {
      var y = getSign(dy - content.delta[1]);

      if(Math.abs(dy - content.delta[1]) > 30) {
        (y < 0) ? emitSwipeEvents(content, 'swipeUp', [dx, dy]) : emitSwipeEvents(content, 'swipeDown', [dx, dy]);
      }

      content.delta[1] = false;
    }
    // emit drag end event
    emitSwipeEvents(content, 'dragEnd', [dx, dy]);
    content.dragging = false;
  };

  function drag(content, event) {
    if(!content.dragging) return;
    // emit dragging event with coordinates
    (!window.requestAnimationFrame)
      ? content.intervalId = setTimeout(function(){emitDrag.bind(content, event);}, 250)
      : content.intervalId = window.requestAnimationFrame(emitDrag.bind(content, event));
  };

  function emitDrag(event) {
    emitSwipeEvents(this, 'dragging', [parseInt(unify(event).clientX), parseInt(unify(event).clientY)]);
  };

  function unify(event) {
    // unify mouse and touch events
    return event.changedTouches ? event.changedTouches[0] : event;
  };

  function emitSwipeEvents(content, eventName, detail, el) {
    var trigger = false;
    if(el) trigger = el;
    // emit event with coordinates
    var event = new CustomEvent(eventName, {detail: {x: detail[0], y: detail[1], origin: trigger}});
    content.element.dispatchEvent(event);
  };

  function getSign(x) {
    if(!Math.sign) {
      return ((x > 0) - (x < 0)) || +x;
    } else {
      return Math.sign(x);
    }
  };

  window.SwipeContent = SwipeContent;

  //initialize the SwipeContent objects
  var swipe = document.getElementsByClassName('js-swipe-content');
  if( swipe.length > 0 ) {
    for( var i = 0; i < swipe.length; i++) {
      (function(i){new SwipeContent(swipe[i]);})(i);
    }
  }
}());

// File#: _2_flexi-header
// Usage: codyhouse.co/license
(function () {
  var flexHeader = document.getElementsByClassName('js-f-header');
  if (flexHeader.length > 0) {
    var menuTrigger = flexHeader[0].getElementsByClassName('js-anim-menu-btn')[0],
      firstFocusableElement = getMenuFirstFocusable();

    // we'll use these to store the node that needs to receive focus when the mobile menu is closed
    var focusMenu = false;

    menuTrigger.addEventListener('anim-menu-btn-clicked', function (event) {
      toggleMenuNavigation(event.detail);
    });

    // listen for key events
    window.addEventListener('keyup', function (event) {
      // listen for esc key
      if ((event.keyCode && event.keyCode == 27) || (event.key && event.key.toLowerCase() == 'escape')) {
        // close navigation on mobile if open
        if (menuTrigger.getAttribute('aria-expanded') == 'true' && isVisible(menuTrigger)) {
          focusMenu = menuTrigger; // move focus to menu trigger when menu is close
          menuTrigger.click();
        }
      }
      // listen for tab key
      if ((event.keyCode && event.keyCode == 9) || (event.key && event.key.toLowerCase() == 'tab')) {
        // close navigation on mobile if open when nav loses focus
        if (menuTrigger.getAttribute('aria-expanded') == 'true' && isVisible(menuTrigger) && !document.activeElement.closest('.js-f-header')) menuTrigger.click();
      }
    });

    // listen for resize
    var resizingId = false;
    window.addEventListener('resize', function () {
      clearTimeout(resizingId);
      resizingId = setTimeout(doneResizing, 500);
    });

    function getMenuFirstFocusable() {
      var focusableEle = flexHeader[0].getElementsByClassName('f-header__nav')[0].querySelectorAll('[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, [tabindex]:not([tabindex="-1"]), [contenteditable], audio[controls], video[controls], summary'),
        firstFocusable = false;
      for (var i = 0; i < focusableEle.length; i++) {
        if (focusableEle[i].offsetWidth || focusableEle[i].offsetHeight || focusableEle[i].getClientRects().length) {
          firstFocusable = focusableEle[i];
          break;
        }
      }

      return firstFocusable;
    }

    function isVisible(element) {
      return (element.offsetWidth || element.offsetHeight || element.getClientRects().length);
    }

    function doneResizing() {
      if (!isVisible(menuTrigger) && Util.hasClass(flexHeader[0], 'f-header--expanded')) {
        menuTrigger.click();
      }
    }

    function toggleMenuNavigation(bool) { // toggle menu visibility on small devices
      Util.toggleClass(document.getElementsByClassName('f-header__nav')[0], 'f-header__nav--is-visible', bool);
      Util.toggleClass(flexHeader[0], 'f-header--expanded', bool);
      menuTrigger.setAttribute('aria-expanded', bool);
      if (bool) firstFocusableElement.focus(); // move focus to first focusable element
      else if (focusMenu) {
        focusMenu.focus();
        focusMenu = false;
      }
    }
  }
}());

// File#: _2_menu-bar
// Usage: codyhouse.co/license
(function() {
  var MenuBar = function(element) {
    this.element = element;
    this.items = Util.getChildrenByClassName(this.element, 'menu-bar__item');
    this.mobHideItems = this.element.getElementsByClassName('menu-bar__item--hide');
    this.moreItemsTrigger = this.element.getElementsByClassName('js-menu-bar__trigger');
    initMenuBar(this);
  };

  function initMenuBar(menu) {
    setMenuTabIndex(menu); // set correct tabindexes for menu item
    initMenuBarMarkup(menu); // create additional markup
    checkMenuLayout(menu); // set menu layout
    Util.addClass(menu.element, 'menu-bar--loaded'); // reveal menu

    // custom event emitted when window is resized
    menu.element.addEventListener('update-menu-bar', function(event){
      checkMenuLayout(menu);
      if(menu.menuInstance) menu.menuInstance.toggleMenu(false, false); // close dropdown
    });

    // keyboard events
    // open dropdown when pressing Enter on trigger element
    if(menu.moreItemsTrigger.length > 0) {
      menu.moreItemsTrigger[0].addEventListener('keydown', function(event) {
        if( (event.keyCode && event.keyCode == 13) || (event.key && event.key.toLowerCase() == 'enter') ) {
          if(!menu.menuInstance) return;
          menu.menuInstance.selectedTrigger = menu.moreItemsTrigger[0];
          menu.menuInstance.toggleMenu(!Util.hasClass(menu.subMenu, 'menu--is-visible'), true);
        }
      });

      // close dropdown on esc
      menu.subMenu.addEventListener('keydown', function(event) {
        if((event.keyCode && event.keyCode == 27) || (event.key && event.key.toLowerCase() == 'escape')) { // close submenu on esc
          if(menu.menuInstance) menu.menuInstance.toggleMenu(false, true);
        }
      });
    }

    // navigate menu items using left/right arrows
    menu.element.addEventListener('keydown', function(event) {
      if( (event.keyCode && event.keyCode == 39) || (event.key && event.key.toLowerCase() == 'arrowright') ) {
        navigateItems(menu.items, event, 'next');
      } else if( (event.keyCode && event.keyCode == 37) || (event.key && event.key.toLowerCase() == 'arrowleft') ) {
        navigateItems(menu.items, event, 'prev');
      }
    });
  };

  function setMenuTabIndex(menu) { // set tabindexes for the menu items to allow keyboard navigation
    var nextItem = false;
    for(var i = 0; i < menu.items.length; i++ ) {
      if(i == 0 || nextItem) menu.items[i].setAttribute('tabindex', '0');
      else menu.items[i].setAttribute('tabindex', '-1');
      if(i == 0 && menu.moreItemsTrigger.length > 0) nextItem = true;
      else nextItem = false;
    }
  };

  function initMenuBarMarkup(menu) {
    if(menu.mobHideItems.length == 0 ) { // no items to hide on mobile - remove trigger
      if(menu.moreItemsTrigger.length > 0) menu.element.removeChild(menu.moreItemsTrigger[0]);
      return;
    }

    if(menu.moreItemsTrigger.length == 0) return;

    // create the markup for the Menu element
    var content = '';
    menu.menuControlId = 'submenu-bar-'+Date.now();
    for(var i = 0; i < menu.mobHideItems.length; i++) {
      var item = menu.mobHideItems[i].cloneNode(true),
        svg = item.getElementsByTagName('svg')[0],
        label = item.getElementsByClassName('menu-bar__label')[0];

      svg.setAttribute('class', 'icon menu__icon');
      content = content + '<li role="menuitem"><span class="menu__content js-menu__content">'+svg.outerHTML+'<span>'+label.innerHTML+'</span></span></li>';
    }

    Util.setAttributes(menu.moreItemsTrigger[0], {'role': 'button', 'aria-expanded': 'false', 'aria-controls': menu.menuControlId, 'aria-haspopup': 'true'});

    var subMenu = document.createElement('menu'),
      customClass = menu.element.getAttribute('data-menu-class');
    Util.setAttributes(subMenu, {'id': menu.menuControlId, 'class': 'menu js-menu '+customClass});
    subMenu.innerHTML = content;
    document.body.appendChild(subMenu);

    menu.subMenu = subMenu;
    menu.subItems = subMenu.getElementsByTagName('li');

    menu.menuInstance = new Menu(menu.subMenu); // this will handle the dropdown behaviour
  };

  function checkMenuLayout(menu) { // switch from compressed to expanded layout and viceversa
    var layout = getComputedStyle(menu.element, ':before').getPropertyValue('content').replace(/\'|"/g, '');
    Util.toggleClass(menu.element, 'menu-bar--collapsed', layout == 'collapsed');
  };

  function navigateItems(list, event, direction, prevIndex) { // keyboard navigation among menu items
    event.preventDefault();
    var index = (typeof prevIndex !== 'undefined') ? prevIndex : Util.getIndexInArray(list, event.target),
      nextIndex = direction == 'next' ? index + 1 : index - 1;
    if(nextIndex < 0) nextIndex = list.length - 1;
    if(nextIndex > list.length - 1) nextIndex = 0;
    // check if element is visible before moving focus
    (list[nextIndex].offsetParent === null) ? navigateItems(list, event, direction, nextIndex) : Util.moveFocus(list[nextIndex]);
  };

  function checkMenuClick(menu, target) { // close dropdown when clicking outside the menu element
    if(menu.menuInstance && !menu.moreItemsTrigger[0].contains(target) && !menu.subMenu.contains(target)) menu.menuInstance.toggleMenu(false, false);
  };

  // init MenuBars objects
  var menuBars = document.getElementsByClassName('js-menu-bar');
  if( menuBars.length > 0 ) {
    var j = 0,
      menuBarArray = [];
    for( var i = 0; i < menuBars.length; i++) {
      var beforeContent = getComputedStyle(menuBars[i], ':before').getPropertyValue('content');
      if(beforeContent && beforeContent !='' && beforeContent !='none') {
        (function(i){menuBarArray.push(new MenuBar(menuBars[i]));})(i);
        j = j + 1;
      }
    }

    if(j > 0) {
      var resizingId = false,
        customEvent = new CustomEvent('update-menu-bar');
      // update Menu Bar layout on resize
      window.addEventListener('resize', function(event){
        clearTimeout(resizingId);
        resizingId = setTimeout(doneResizing, 150);
      });

      // close menu when clicking outside it
      window.addEventListener('click', function(event){
        menuBarArray.forEach(function(element){
          checkMenuClick(element, event.target);
        });
      });

      function doneResizing() {
        for( var i = 0; i < menuBars.length; i++) {
          (function(i){menuBars[i].dispatchEvent(customEvent)})(i);
        };
      };
    }
  }
}());

// File#: _1_back-to-top
// Usage: codyhouse.co/license
(function() {
  var backTop = document.getElementsByClassName('js-back-to-top')[0];
  if( backTop ) {
    var dataElement = backTop.getAttribute('data-element');
    var scrollElement = dataElement ? document.querySelector(dataElement) : window;
    var scrollDuration = parseInt(backTop.getAttribute('data-duration')) || 300, //scroll to top duration
      scrollOffset = parseInt(backTop.getAttribute('data-offset')) || 0, //show back-to-top if scrolling > scrollOffset
      scrolling = false;

    //detect click on back-to-top link
    backTop.addEventListener('click', function(event) {
      event.preventDefault();
      if(!window.requestAnimationFrame) {
        scrollElement.scrollTo(0, 0);
      } else {
        dataElement ? Util.scrollTo(0, scrollDuration, false, scrollElement) : Util.scrollTo(0, scrollDuration);
      }
      //move the focus to the #top-element - don't break keyboard navigation
      Util.moveFocus(document.getElementById(backTop.getAttribute('href').replace('#', '')));
    });

    //listen to the window scroll and update back-to-top visibility
    checkBackToTop();
    if (scrollOffset > 0) {
      scrollElement.addEventListener("scroll", function(event) {
        if( !scrolling ) {
          scrolling = true;
          (!window.requestAnimationFrame) ? setTimeout(function(){checkBackToTop();}, 250) : window.requestAnimationFrame(checkBackToTop);
        }
      });
    }

    function checkBackToTop() {
      var windowTop = scrollElement.scrollTop || document.documentElement.scrollTop;
      if(!dataElement) windowTop = window.scrollY || document.documentElement.scrollTop;
      Util.toggleClass(backTop, 'back-to-top--is-visible', windowTop >= scrollOffset);
      scrolling = false;
    }
  }
}());

// File#: _2_slideshow
// Usage: codyhouse.co/license
(function() {
  var Slideshow = function(opts) {
    this.options = slideshowAssignOptions(Slideshow.defaults , opts);
    this.element = this.options.element;
    this.items = this.element.getElementsByClassName('js-slideshow__item');
    this.controls = this.element.getElementsByClassName('js-slideshow__control');
    this.selectedSlide = 0;
    this.autoplayId = false;
    this.autoplayPaused = false;
    this.navigation = false;
    this.navCurrentLabel = false;
    this.ariaLive = false;
    this.moveFocus = false;
    this.animating = false;
    this.supportAnimation = Util.cssSupports('transition');
    this.animationOff = (!Util.hasClass(this.element, 'slideshow--transition-fade') && !Util.hasClass(this.element, 'slideshow--transition-slide') && !Util.hasClass(this.element, 'slideshow--transition-prx'));
    this.animationType = Util.hasClass(this.element, 'slideshow--transition-prx') ? 'prx' : 'slide';
    this.animatingClass = 'slideshow--is-animating';
    initSlideshow(this);
    initSlideshowEvents(this);
    initAnimationEndEvents(this);
  };

  Slideshow.prototype.showNext = function() {
    showNewItem(this, this.selectedSlide + 1, 'next');
  };

  Slideshow.prototype.showPrev = function() {
    showNewItem(this, this.selectedSlide - 1, 'prev');
  };

  Slideshow.prototype.showItem = function(index) {
    showNewItem(this, index, false);
  };

  Slideshow.prototype.startAutoplay = function() {
    var self = this;
    if(this.options.autoplay && !this.autoplayId && !this.autoplayPaused) {
      self.autoplayId = setInterval(function(){
        self.showNext();
      }, self.options.autoplayInterval);
    }
  };

  Slideshow.prototype.pauseAutoplay = function() {
    var self = this;
    if(this.options.autoplay) {
      clearInterval(self.autoplayId);
      self.autoplayId = false;
    }
  };

  function slideshowAssignOptions(defaults, opts) {
    // initialize the object options
    var mergeOpts = {};
    mergeOpts.element = (typeof opts.element !== "undefined") ? opts.element : defaults.element;
    mergeOpts.navigation = (typeof opts.navigation !== "undefined") ? opts.navigation : defaults.navigation;
    mergeOpts.autoplay = (typeof opts.autoplay !== "undefined") ? opts.autoplay : defaults.autoplay;
    mergeOpts.autoplayInterval = (typeof opts.autoplayInterval !== "undefined") ? opts.autoplayInterval : defaults.autoplayInterval;
    mergeOpts.swipe = (typeof opts.swipe !== "undefined") ? opts.swipe : defaults.swipe;
    return mergeOpts;
  };

  function initSlideshow(slideshow) { // basic slideshow settings
    // if no slide has been selected -> select the first one
    if(slideshow.element.getElementsByClassName('slideshow__item--selected').length < 1) Util.addClass(slideshow.items[0], 'slideshow__item--selected');
    slideshow.selectedSlide = Util.getIndexInArray(slideshow.items, slideshow.element.getElementsByClassName('slideshow__item--selected')[0]);
    // create an element that will be used to announce the new visible slide to SR
    var srLiveArea = document.createElement('div');
    Util.setAttributes(srLiveArea, {'class': 'sr-only js-slideshow__aria-live', 'aria-live': 'polite', 'aria-atomic': 'true'});
    slideshow.element.appendChild(srLiveArea);
    slideshow.ariaLive = srLiveArea;
  };

  function initSlideshowEvents(slideshow) {
    // if slideshow navigation is on -> create navigation HTML and add event listeners
    if(slideshow.options.navigation) {
      // check if navigation has already been included
      if(slideshow.element.getElementsByClassName('js-slideshow__navigation').length == 0) {
        var navigation = document.createElement('ol'),
          navChildren = '';

        var navClasses = 'slideshow__navigation js-slideshow__navigation';
        if(slideshow.items.length <= 1) {
          navClasses = navClasses + ' is-hidden';
        }

        navigation.setAttribute('class', navClasses);
        for(var i = 0; i < slideshow.items.length; i++) {
          var className = (i == slideshow.selectedSlide) ? 'class="slideshow__nav-item slideshow__nav-item--selected js-slideshow__nav-item"' :  'class="slideshow__nav-item js-slideshow__nav-item"',
            navCurrentLabel = (i == slideshow.selectedSlide) ? '<span class="sr-only js-slideshow__nav-current-label">Current Item</span>' : '';
          navChildren = navChildren + '<li '+className+'><button class="reset"><span class="sr-only">'+ (i+1) + '</span>'+navCurrentLabel+'</button></li>';
        }
        navigation.innerHTML = navChildren;
        slideshow.element.appendChild(navigation);
      }

      slideshow.navCurrentLabel = slideshow.element.getElementsByClassName('js-slideshow__nav-current-label')[0];
      slideshow.navigation = slideshow.element.getElementsByClassName('js-slideshow__nav-item');

      var dotsNavigation = slideshow.element.getElementsByClassName('js-slideshow__navigation')[0];

      dotsNavigation.addEventListener('click', function(event){
        navigateSlide(slideshow, event, true);
      });
      dotsNavigation.addEventListener('keyup', function(event){
        navigateSlide(slideshow, event, (event.key.toLowerCase() == 'enter'));
      });
    }
    // slideshow arrow controls
    if(slideshow.controls.length > 0) {
      // hide controls if one item available
      if(slideshow.items.length <= 1) {
        Util.addClass(slideshow.controls[0], 'is-hidden');
        Util.addClass(slideshow.controls[1], 'is-hidden');
      }
      slideshow.controls[0].addEventListener('click', function(event){
        event.preventDefault();
        slideshow.showPrev();
        updateAriaLive(slideshow);
      });
      slideshow.controls[1].addEventListener('click', function(event){
        event.preventDefault();
        slideshow.showNext();
        updateAriaLive(slideshow);
      });
    }
    // swipe events
    if(slideshow.options.swipe) {
      //init swipe
      new SwipeContent(slideshow.element);
      slideshow.element.addEventListener('swipeLeft', function(event){
        slideshow.showNext();
      });
      slideshow.element.addEventListener('swipeRight', function(event){
        slideshow.showPrev();
      });
    }
    // autoplay
    if(slideshow.options.autoplay) {
      slideshow.startAutoplay();
      // pause autoplay if user is interacting with the slideshow
      slideshow.element.addEventListener('mouseenter', function(event){
        slideshow.pauseAutoplay();
        slideshow.autoplayPaused = true;
      });
      slideshow.element.addEventListener('focusin', function(event){
        slideshow.pauseAutoplay();
        slideshow.autoplayPaused = true;
      });
      slideshow.element.addEventListener('mouseleave', function(event){
        slideshow.autoplayPaused = false;
        slideshow.startAutoplay();
      });
      slideshow.element.addEventListener('focusout', function(event){
        slideshow.autoplayPaused = false;
        slideshow.startAutoplay();
      });
    }
    // detect if external buttons control the slideshow
    var slideshowId = slideshow.element.getAttribute('id');
    if(slideshowId) {
      var externalControls = document.querySelectorAll('[data-controls="'+slideshowId+'"]');
      for(var i = 0; i < externalControls.length; i++) {
        (function(i){externalControlSlide(slideshow, externalControls[i]);})(i);
      }
    }
    // custom event to trigger selection of a new slide element
    slideshow.element.addEventListener('selectNewItem', function(event){
      // check if slide is already selected
      if(event.detail) {
        if(event.detail - 1 == slideshow.selectedSlide) return;
        showNewItem(slideshow, event.detail - 1, false);
      }
    });
  };

  function navigateSlide(slideshow, event, keyNav) {
    // user has interacted with the slideshow navigation -> update visible slide
    var target = ( Util.hasClass(event.target, 'js-slideshow__nav-item') ) ? event.target : event.target.closest('.js-slideshow__nav-item');
    if(keyNav && target && !Util.hasClass(target, 'slideshow__nav-item--selected')) {
      slideshow.showItem(Util.getIndexInArray(slideshow.navigation, target));
      slideshow.moveFocus = true;
      updateAriaLive(slideshow);
    }
  };

  function initAnimationEndEvents(slideshow) {
    // remove animation classes at the end of a slide transition
    for( var i = 0; i < slideshow.items.length; i++) {
      (function(i){
        slideshow.items[i].addEventListener('animationend', function(){resetAnimationEnd(slideshow, slideshow.items[i]);});
        slideshow.items[i].addEventListener('transitionend', function(){resetAnimationEnd(slideshow, slideshow.items[i]);});
      })(i);
    }
  };

  function resetAnimationEnd(slideshow, item) {
    setTimeout(function(){ // add a delay between the end of animation and slideshow reset - improve animation performance
      if(Util.hasClass(item,'slideshow__item--selected')) {
        if(slideshow.moveFocus) Util.moveFocus(item);
        emitSlideshowEvent(slideshow, 'newItemVisible', slideshow.selectedSlide);
        slideshow.moveFocus = false;
      }
      Util.removeClass(item, 'slideshow__item--'+slideshow.animationType+'-out-left slideshow__item--'+slideshow.animationType+'-out-right slideshow__item--'+slideshow.animationType+'-in-left slideshow__item--'+slideshow.animationType+'-in-right');
      item.removeAttribute('aria-hidden');
      slideshow.animating = false;
      Util.removeClass(slideshow.element, slideshow.animatingClass);
    }, 100);
  };

  function showNewItem(slideshow, index, bool) {
    if(slideshow.items.length <= 1) return;
    if(slideshow.animating && slideshow.supportAnimation) return;
    slideshow.animating = true;
    Util.addClass(slideshow.element, slideshow.animatingClass);
    if(index < 0) index = slideshow.items.length - 1;
    else if(index >= slideshow.items.length) index = 0;
    // skip slideshow item if it is hidden
    if(bool && Util.hasClass(slideshow.items[index], 'is-hidden')) {
      slideshow.animating = false;
      index = bool == 'next' ? index + 1 : index - 1;
      showNewItem(slideshow, index, bool);
      return;
    }
    // index of new slide is equal to index of slide selected item
    if(index == slideshow.selectedSlide) {
      slideshow.animating = false;
      return;
    }
    var exitItemClass = getExitItemClass(slideshow, bool, slideshow.selectedSlide, index);
    var enterItemClass = getEnterItemClass(slideshow, bool, slideshow.selectedSlide, index);
    // transition between slides
    if(!slideshow.animationOff) Util.addClass(slideshow.items[slideshow.selectedSlide], exitItemClass);
    Util.removeClass(slideshow.items[slideshow.selectedSlide], 'slideshow__item--selected');
    slideshow.items[slideshow.selectedSlide].setAttribute('aria-hidden', 'true'); //hide to sr element that is exiting the viewport
    if(slideshow.animationOff) {
      Util.addClass(slideshow.items[index], 'slideshow__item--selected');
    } else {
      Util.addClass(slideshow.items[index], enterItemClass+' slideshow__item--selected');
    }
    // reset slider navigation appearance
    resetSlideshowNav(slideshow, index, slideshow.selectedSlide);
    slideshow.selectedSlide = index;
    // reset autoplay
    slideshow.pauseAutoplay();
    slideshow.startAutoplay();
    // reset controls/navigation color themes
    resetSlideshowTheme(slideshow, index);
    // emit event
    emitSlideshowEvent(slideshow, 'newItemSelected', slideshow.selectedSlide);
    if(slideshow.animationOff) {
      slideshow.animating = false;
      Util.removeClass(slideshow.element, slideshow.animatingClass);
    }
  };

  function getExitItemClass(slideshow, bool, oldIndex, newIndex) {
    var className = '';
    if(bool) {
      className = (bool == 'next') ? 'slideshow__item--'+slideshow.animationType+'-out-right' : 'slideshow__item--'+slideshow.animationType+'-out-left';
    } else {
      className = (newIndex < oldIndex) ? 'slideshow__item--'+slideshow.animationType+'-out-left' : 'slideshow__item--'+slideshow.animationType+'-out-right';
    }
    return className;
  };

  function getEnterItemClass(slideshow, bool, oldIndex, newIndex) {
    var className = '';
    if(bool) {
      className = (bool == 'next') ? 'slideshow__item--'+slideshow.animationType+'-in-right' : 'slideshow__item--'+slideshow.animationType+'-in-left';
    } else {
      className = (newIndex < oldIndex) ? 'slideshow__item--'+slideshow.animationType+'-in-left' : 'slideshow__item--'+slideshow.animationType+'-in-right';
    }
    return className;
  };

  function resetSlideshowNav(slideshow, newIndex, oldIndex) {
    if(slideshow.navigation) {
      Util.removeClass(slideshow.navigation[oldIndex], 'slideshow__nav-item--selected');
      Util.addClass(slideshow.navigation[newIndex], 'slideshow__nav-item--selected');
      slideshow.navCurrentLabel.parentElement.removeChild(slideshow.navCurrentLabel);
      slideshow.navigation[newIndex].getElementsByTagName('button')[0].appendChild(slideshow.navCurrentLabel);
    }
  };

  function resetSlideshowTheme(slideshow, newIndex) {
    var dataTheme = slideshow.items[newIndex].getAttribute('data-theme');
    if(dataTheme) {
      if(slideshow.navigation) slideshow.navigation[0].parentElement.setAttribute('data-theme', dataTheme);
      if(slideshow.controls[0]) slideshow.controls[0].parentElement.setAttribute('data-theme', dataTheme);
    } else {
      if(slideshow.navigation) slideshow.navigation[0].parentElement.removeAttribute('data-theme');
      if(slideshow.controls[0]) slideshow.controls[0].parentElement.removeAttribute('data-theme');
    }
  };

  function emitSlideshowEvent(slideshow, eventName, detail) {
    var event = new CustomEvent(eventName, {detail: detail});
    slideshow.element.dispatchEvent(event);
  };

  function updateAriaLive(slideshow) {
    slideshow.ariaLive.innerHTML = 'Item '+(slideshow.selectedSlide + 1)+' of '+slideshow.items.length;
  };

  function externalControlSlide(slideshow, button) { // control slideshow using external element
    button.addEventListener('click', function(event){
      var index = button.getAttribute('data-index');
      if(!index || index == slideshow.selectedSlide + 1) return;
      event.preventDefault();
      showNewItem(slideshow, index - 1, false);
    });
  };

  Slideshow.defaults = {
    element : '',
    navigation : true,
    autoplay : false,
    autoplayInterval: 5000,
    swipe: false
  };

  window.Slideshow = Slideshow;

  //initialize the Slideshow objects
  var slideshows = document.getElementsByClassName('js-slideshow');
  if( slideshows.length > 0 ) {
    for( var i = 0; i < slideshows.length; i++) {
      (function(i){
        var navigation = (slideshows[i].getAttribute('data-navigation') && slideshows[i].getAttribute('data-navigation') == 'off') ? false : true,
          autoplay = (slideshows[i].getAttribute('data-autoplay') && slideshows[i].getAttribute('data-autoplay') == 'on') ? true : false,
          autoplayInterval = (slideshows[i].getAttribute('data-autoplay-interval')) ? slideshows[i].getAttribute('data-autoplay-interval') : 5000,
          swipe = (slideshows[i].getAttribute('data-swipe') && slideshows[i].getAttribute('data-swipe') == 'on') ? true : false;
        new Slideshow({element: slideshows[i], navigation: navigation, autoplay : autoplay, autoplayInterval : autoplayInterval, swipe : swipe});
      })(i);
    }
  }
}());
