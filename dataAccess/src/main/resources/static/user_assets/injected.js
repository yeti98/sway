/*
 * This entire block is wrapped in an IIFE to prevent polluting the scope of the web page with
 * functions created by this extension.
 */
(function(realOpen, realSend, realFetch) {
    /*
     * This script has some dependencies that need to be included
     * without polluting the global namespace. This is achieved by
     * inserting them into the source at build time in the
     * makefile. This is preferable to loading the dependencies via
     * AJAX, because the latter doesn't work if the CSP disallows
     * unsafe-eval. The following tag tells the makefile where to
     * insert the dependencies.
     */
    // <DEPENDENCIES>
class InputAnalyser {
    constructor(_inputs) {
        this._inputs = _inputs;
        this._irrelevantInputs = new WeakSet();

        /*
         * Not currently using Password because it was causing too
         * many false positives. Some sites use external auth domains,
         * and many have misconfigured analytics that collect
         * passwords.
         */
        this.relevantInputs = [new CardNumber()];
    }

    get relevantInputValues() {
        const inputs = {};
        this._inputs.forEach(
            input => (inputs[input.id || input.name] = input.value)
        );

        return inputs;
    }

    get relevantFilledInputs() {
        return this.allInputs
            .filter(input => this.isRelevantElement(input))
            .filter(input => input.value.length >= cons.minInputSize)
            .map(input => ({
                id: input.id,
                name: input.name,
                value: input.value,
            }));
    }

    get allInputs() {
        this._inputs = [
            ...document.querySelectorAll(
                this.relevantInputs.map(i => i.relevantInputTypes).join(', ')
            ),
        ];

        return this._inputs;
    }

    static getLabelText(input) {
        if (!input.labels.length) {
            return '';
        }
        return stripSpecialChars(input.labels[0].textContent).trim();
    }

    /**
     * Takes an HTMLElement and determines whether or not it could be relevant for the purposes of
     * skimmer detection.
     * @param {HTMLElement} input The element which is to be checked for relevancy.
     * @returns {boolean} Returns true if the element is potentially relevant (e.g. a credit card
     * field or other such sensitive input).
     */
    isRelevantElement(input) {
        // If the element has been identified as irrelevant, return false
        if (this._irrelevantInputs.has(input)) {
            return false;
        }

        // Check the input against all the relevant input types
        const relevances = this.relevantInputs.map(i => i.test(input));

        /*
         *  If they all agree that this input is unlikely to become
         *  relevant, exclude it from further checks and return false
         */
        if (relevances.every(i => i === RelevantInput.relevance.NOT_RELEVANT)) {
            this._irrelevantInputs.add(input);
            return false;
        }

        // Return true if any consider the input to be relevant, otherwise false
        return relevances.some(i => i === RelevantInput.relevance.RELEVANT);
    }

    containsInputsInURL(url) {
        try {
            const parsedUrl = new URL(url);
            return InputAnalyser.atLeastOneNeedleInHaystack(
                this.relevantInputValues,
                [...parsedUrl.searchParams.values()]
            );
        } catch (e) {
            return [];
        }
    }

    containsInputsInPostData(requestBody) {
        if (!requestBody) {
            return [];
        }
        if (requestBody.raw) {
            const bodyString = new TextDecoder('utf-8').decode(
                new Uint8Array(requestBody.raw[0].bytes)
            );

            let decoded;
            try {
                decoded = decodeURIComponent(bodyString);
            } catch (e) {
                decoded = bodyString;
            }

            const searchParams = new URLSearchParams(decoded);
            const parsedQuery = {};
            const parsedValues = [];

            for (const [key, value] of searchParams.entries()) {
                parsedQuery[key] = value;
                parsedValues.push(value);
            }

            if (parsedQuery !== {} && parsedValues.indexOf(null) === -1) {
                return InputAnalyser.atLeastOneNeedleInHaystack(
                    this.relevantInputValues,
                    parsedValues
                );
            }
            return InputAnalyser.atLeastOneNeedleInHaystack(
                this.relevantInputValues,
                decoded
            );
        }
        if (requestBody.formData) {
            return InputAnalyser.atLeastOneNeedleInHaystack(
                this.relevantInputValues,
                Object.values(requestBody.formData).flat()
            );
        }
        return [];
    }

    /**
     * Determines whether one or more values in needles are present in any of
     * the keys of the haystacks.
     * Checks both raw input and base64 encoded versions of the needles.
     * @param needles An object. The haystacks are searched for the values of
     * this object.
     * @param haystacks The array of strings in which the needle(s) may reside.
     * A string may optionally be supplied if only one value is present.
     * @returns {[String]} The keys of the needles that were found in one or
     * more haystacks in either raw or base64 encoded form.
     */
    static atLeastOneNeedleInHaystack(needles, haystacks) {
        if (
            needles === undefined ||
            needles.length === 0 ||
            haystacks === undefined
        ) {
            return [];
        }

        const searchArray = Array.isArray(haystacks) ? haystacks : [haystacks];

        return Object.entries(needles)
            .filter(([_, needle]) =>
                searchArray.some(haystack => {
                    if (isBase64Encoded(haystack)) {
                        if (atob(haystack).indexOf(needle) !== -1) {
                            return true;
                        }
                    }
                    return haystack.indexOf(needle) !== -1;
                })
            )
            .map(([input, _]) => input);
    }
}
/**
 * Takes a string and removes a variety of 'special' characters like punctuation.
 * @param str The string to be modified.
 * @returns {string} The resulting string without any special characters.
 */
function stripSpecialChars(str) {
    return str.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>}{\][\\/]/gi, '');
}

/**
 * Attempts to identify where a string is base-64 encoded or not.
 * @param string The raw string to be examined.
 * @returns {boolean} Returns true if the string matches a base-64 string. False otherwise.
 */
function isBase64Encoded(string) {
    return /^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$/.test(
        string
    );
}

function normaliseUrl(str) {
    const link = document.createElement('a');
    link.href = str;
    return link.href;
}

function sendMessage(type, data, tabId, options) {
    if (tabId) {
        chrome.tabs.sendMessage(tabId, { data, type }, options);
    } else {
        chrome.runtime.sendMessage({ data, type });
    }
}
/*
 * Netcraft Extension
 * constants JavaScript
 */

/* eslint sort-keys: 'off' */
/* sort-keys is disabled as the constants are separated by type */

// Aligned values here make for easier reading, so we disable prettier

// prettier-ignore
const cons = (function() {
    const cryptojackersUrl = 'https://netcraft.app/faqs/#crypto-miners';
    const skimmersUrl = 'https://netcraft.app/faqs/#credit-card-skimmers';
    const evilResourceCrime = 'attempted to load';

    return {
        fields: [
            'topsites',
            'country',
            'netblock',
            'hoster',
            'firstseen',
            'rank',
            'risk',
            'uses_sslv3',
            'pfs',
            'heartbleed',
            'heartbleed_message',
        ],

        // domains
        mirror:  'https://mirror2.extension.netcraft.com/',
        toolbar: 'https://mirror.toolbar.netcraft.com/',

        // links and faqs
        blogArchive:      'https://news.netcraft.com/archives/',
        cryptojackersUrl,
        heartbleedUrl:    'https://netcraft.app/help/glossary/#heartbleed',
        logBlockedVisit:  'https://toolbar.netcraft.com/blocked_visit',
        netblock:         'https://sitereport.netcraft.com/netblock?q=',
        pfsUrl:           'https://netcraft.app/help/glossary/#pfs',
        report:           'https://sitereport.netcraft.com/?url=',
        reportAPI:        'https://report.netcraft.com/api/v2/report/urls',
        reportMistake:    'https://report.netcraft.com/report/mistake?url=',
        skimmersUrl,
        sslv3Url:         'https://netcraft.app/help/glossary/#sslv3',
        submissionURL:    'https://report.netcraft.com/submission/',
        topsites:         'https://trends.netcraft.com/topsites?s=',

        // paths
        block:              'blocked.html?ref=',
        check:              'check_url/v3/',
        danger:             'Images/danger.png',
        embed:              '/embedded',
        evilJSPatternsFeed: 'eviljspatterns',
        skimmerDomainWhitelistFeed: 'eviljsskimmerwhitelist',
        evilResourcesFeed:  'blockdb/netcraft_extension_evil_resource_encrypted-yppL7bVh/',
        flags:              'Images/flags/',
        force:              '/dodns',
        heartbleed:         'Images/heartbleed.png',
        info:               '/info',
        poodlePost:         '2014/10/15/googles-poodle-affects-oodles.html',
        warning:            'Images/warning.png',

        // feed constants
        evilResourceTypes: ['cryptojacker', 'skimmer'],
        evilResourcesSalt: 'C4jhYHxW',
        maxRevisions:      5,
        randomSaltLength:  8,

        // mappings of url_type => information
        urlTypeInformationMappings: {
            'malware_cryptojacker': ['web miners',             cryptojackersUrl],
            'malware_skimmer':      ['shopping site skimmers', skimmersUrl],
        },

        // mappings of evilResourceTypes => user options
        evilResourceOptionMappings: {
            'cryptojacker': 'cryptojackers',
            'skimmer':      'skimmers'
        },

        // mappings of user options => url_type
        optionUrlTypeMappings: {
            'block':                   /^(phish_site|unavailable)$/,
            'cryptojackers':           /^malware_cryptojacker$/,
            'other-malicious-scripts': /^malware(_(?!(skimmer|cryptojacker)$)\w+)?$/,
            'skimmers':                /^malware_skimmer(_credential_drop)?$/,
        },

        // report constants
        malwareSourceEmail:  'extension-reported-malware@netcraft.com',
        malwareSourceUUID:   'mwcCcApi89cobY9loJ9rWJ9HuOhlpwMj',
        extensionSourceUUID: 'qBEQ3NhEnwK4sypmuSxBJzRqpxZLRXxe',

        // browserAction icon paths
        iconNormal: {
            19: 'Images/action-normal.png',
            38: 'Images/action-normal-2x.png'
        },
        iconDanger: {
            19: 'Images/action-danger.png',
            38: 'Images/action-danger-2x.png'
        },
        iconWarning: {
            19: 'Images/action-warning.png',
            38: 'Images/action-warning-2x.png'
        },

        // Extension internal message types
        reportURL:          'report',
        reqBlock:           'block',
        reqDetails:         'details',
        reqForce:           'force',
        reqPopUp:           'popup',
        reqPrintTimingData: 'time', //debug

        // Status codes
        errEvilResource: 'EVILRESOURCE',
        errNoDetails:    'NODETAILS',
        errNoInfo:       'NOINFO',
        errBlocked:      'BLOCKED',
        errServer:       'ERROR',
        errSuspicious:   'SUSPICIOUS',
        errTimeout:      'TIMEOUT',
        errXSS:          'XSS',
        noError:         'OK',

        // Valid protocols (it is a regex match so ^ matches start of string)
        protocols:    ['^http', '^https', '^ftp'],

        // Human readable messages
        msgNa:           'NA',
        msgNewSite:      'New Site',
        msgNoInfo:       'Site information not available',
        msgNoServer:     'Unable to contact Netcraft servers',
        msgSuspicious:   'Suspicious URL Detected',
        msgXSS:          'Suspected XSS Attack',
        msgBlocked:      {
            'phish_site':                      'Suspected Phishing',
            'malware':                         'Malicious JavaScript Detected',
            'malware_skimmer':                 'Shopping Site Skimmer Detected',
            'malware_skimmer_credential_drop': 'Potential Credential Leak Detected',
            'malware_cryptojacker':            'Web Miner Detected',
            'malware_c2':                      'Malicious URL',
            'unavailable':                     'Suspected Phishing',
        },

        // Human readable reasons
        urlTypeCrimeMappings: {
            'malware_skimmer':                 evilResourceCrime,
            'malware_cryptojacker':            evilResourceCrime,
            'malware_undefined':               evilResourceCrime,
            'malware_skimmer_credential_drop': ' is blocked because we detected that sensitive information was leaked from',
        },

        // Timeout function value to clear cache etc.
        timeout:      300000,

        // Skimmer whitelist update interval (1 day)
        skimmerWhitelistInterval: 1000 * 60 * 60 * 24,

        // Google Analytics account
        account:      'UA-2150242-8',
        gaCategory:   'Popup load times',

        // InputAnalyser constants
        minInputSize: 5,

        // First addresses of the private IPv4 netblocks
        privateIPv4Blocks: [
            '10.0.0.0',
            '172.16.0.0',
            '192.168.0.0'
        ],

        // Local storage key for local credential leak detection whitelist
        localCredentialLeakWhitelistKey: 'localCredentialLeakWhitelist',
    };
})();

/*
 * recursively prevents any property or sub-property in cons from:
 *   - being added;
 *   - being removed; or
 *   - being modified.
 */
function deepFreeze(object) {
    Object.freeze(object);

    for (const name of Object.getOwnPropertyNames(object)) {
        if (typeof object[name] === 'object') {
            deepFreeze(object[name]);
        }
    }
}

deepFreeze(cons);
/*
 * RelevantInput objects check whether given form inputs should be
 * treated as sensitive data.
 *
 * This should be treated as an abstract class. Only the subclasses
 * are intended to be instantiated.
 */
class RelevantInput {
    /*
     * Enum of the possible return values of test():
     * RELEVANT:
     *     Input is relevant now, outgoing requests should be
     *     checked for the contained value (subject to minimum length).
     * POTENTIALLY RELEVANT:
     *     Input should not be considered relevant now, but may be in
     *     future so should continue to be checked.
     * NOT_RELEVANT:
     *     Input is not relevant and will not become relevant, so can
     *     be ommitted from further checks to optimise performance.
     */
    static get relevance() {
        return {
            NOT_RELEVANT: 1,
            POTENTIALLY_RELEVANT: 2,
            RELEVANT: 3,
        };
    }

    /*
     * relevantInputTypes (string) - a comma separated list of
     * selectors that describe the types of input that should be
     * considered relevant.
     */
    constructor(relevantInputTypes) {
        this.relevantInputTypes = relevantInputTypes;
    }

    /*
     * Checks whether any of the given input's 'labels' (the ID, name,
     * or actual label) match the given regex.
     */
    static testInputLabel(regex, input) {
        const labels = [
            input.id,
            input.name,
            InputAnalyser.getLabelText(input),
        ];
        return labels.some(label => regex.test(label));
    }
}

/*
 * RelevantInput for passwords.
 */
class Password extends RelevantInput {
    constructor() {
        super('input[type=password]');
    }

    /*
     * Passwords are verified purely by their input type.
     */
    test(input) {
        if (input.matches(this.relevantInputTypes)) {
            return RelevantInput.relevance.RELEVANT;
        } else {
            return RelevantInput.relevance.NOT_RELEVANT;
        }
    }
}

/*
 * RelevantInput for card numbers.
 */
class CardNumber extends RelevantInput {
    constructor() {
        // Inputs with no type attribute act like text inputs
        super(
            'input[type=text], input[type=tel], input[type=number], input:not([type])'
        );

        // Definitely card number labels
        this.inputLabelRegexes = [
            /(cc|card).?(num|no)/i,
            /カード番号/,
            /Номер.*карты/i,
            /信用卡卡號/i,
            /信用卡号码/i,
        ];

        /*
         * Matches incomplete card numbers, because ideally the
         * skimmer should be detected before the victim gives away
         * most of their card number.
         */
        this.inputValueRegexes = [/^(\d{4}[-\s]?){0,4}\d{1,4}$/];

        // Probably card number labels
        this.suspectInputLabelRegexes = [
            /card/i,
            /tarjeta/i,
            /carte/i,
            /карты/i,
            /卡/i,
            /カード/i,
            /카드/i,
            /credit/i,
        ];

        // If it matches one of these, probably not a card number
        this.inputLabelExceptionRegexes = [/post/i, /code/i, /zip/i];
    }

    test(input) {
        if (!input.matches(this.relevantInputTypes)) {
            return RelevantInput.relevance.NOT_RELEVANT;
        }

        // Return NOT_RELEVANT if label matches an exception regex
        if (
            this.inputLabelExceptionRegexes.some(r =>
                RelevantInput.testInputLabel(r, input)
            )
        ) {
            return RelevantInput.relevance.NOT_RELEVANT;
        }

        // Return RELEVANT if label clearly states card number
        if (
            this.inputLabelRegexes.some(r =>
                RelevantInput.testInputLabel(r, input)
            )
        ) {
            return RelevantInput.relevance.RELEVANT;
        }

        /*
         * If label suggests card number, return RELEVANT if value
         * also suggests card number or POTENTIALLY_RELEVANT if
         * not. Otherwise, return NOT_RELEVANT as this element will
         * not become relevant unless a label changes (unlikely).
         */
        if (
            this.suspectInputLabelRegexes.some(r =>
                RelevantInput.testInputLabel(r, input)
            )
        ) {
            if (this.inputValueRegexes.some(r => r.test(input.value))) {
                return RelevantInput.relevance.RELEVANT;
            } else {
                return RelevantInput.relevance.POTENTIALLY_RELEVANT;
            }
        } else {
            return RelevantInput.relevance.NOT_RELEVANT;
        }
    }
}

    const inputAnalyser = new InputAnalyser();

    /*
     * Implementations to overwrite window.XMLHttpRequest.prototype.open,
     * window.XMLHttpRequest.prototype.send and window.fetch. These are (to my knowledge) the two
     * APIs that can be used to make requests within Javascript. If there are more, then they will
     * also need to be overridden and intercepted like below.
     */

    /**
     * This function generates functions to be used for wrapping and intercepting network
     * requests, namely from XMLHttpRequest and the Fetch API. It works by taking the real
     * function we want to intercept the request for, and then uses document.currentScript to work
     * out which file initiated the request. It sends those details to the content script which
     * forwards it to the background script, and then we await a confirmation of receipt from the
     * background page. Finally, the real request is sent by forwarding parameters to the original
     * function passed in.
     * @param realFn A reference to the real function that is being intercepted.
     * @param getRequestUrlFn A function used to get the request URL from the object. This is
     * necessary as the mechanisms for different objects differs depending on implementation.
     * @returns {function(...[*]=): PromiseLike<any | never>}
     */
    function generateRequestInterceptFn(realFn, getRequestUrlFn) {
        return function(...args) {
            /*
             * Delaying outgoing requests sometimes causes problems
             * with elements such as video players and dynamically
             * loaded images, so it should only be done when sensitive
             * information is entered into the form inputs on the
             * page.
             */
            if (
                inputAnalyser === undefined ||
                !inputAnalyser.relevantFilledInputs.length
            ) {
                return realFn.apply(this, args);
            }

            /*
             * We create a link DOM element in order to correctly generate the absolute path of the
             * request, automatically transforming from a relative path if necessary.
             */
            const requestUrl = normaliseUrl(
                getRequestUrlFn.bind(this)(...args)
            );

            /*
             * Generate the information object to send through to the content script including the
             * request URL, the initiator filename and a timestamp relative to the page load.
             */
            const detail = {
                fileName: document.currentScript
                    ? document.currentScript.src
                    : location.href,
                requestUrl,
                timestamp: performance.now(),
            };

            document.dispatchEvent(
                new CustomEvent('interceptDetailsCaptured', {
                    detail,
                })
            );

            return new Promise(resolve => {
                /*
                 * Send the initiator's file name and other info to the background script, then wait for
                 * confirmation that the details made it to the background page before calling the real
                 * function. This is to avoid any race conditions whereby the details haven't been added
                 * to the tab data before onBeforeRequest/onBeforeSendHeaders gets called.
                 */
                document.addEventListener(
                    'interceptDetailsConfirmed',
                    function compareData(customEvent) {
                        const receivedData = customEvent.detail;
                        if (JSON.stringify(detail) === receivedData) {
                            /*
                             * If it's the correct confirmation, then we can resolve the promise and call the real
                             * function in the finally block.
                             */
                            document.removeEventListener(
                                'interceptDetailsConfirmed',
                                compareData,
                                false
                            );
                            resolve();
                        }
                    }
                );
            }).then(() => realFn.apply(this, args));
        };
    }

    window.fetch = generateRequestInterceptFn(realFetch, request =>
        typeof request === 'string' ? request : request.url
    );

    window.XMLHttpRequest.prototype.send = generateRequestInterceptFn(
        realSend,
        function getRequestUrl() {
            return this._requestUrl;
        }
    );

    /*
     * This function is intercepted so that we can capture the value of the request URL, since this
     * value isn't present in the arguments for XMLHttpRequest.send (above).
     */
    window.XMLHttpRequest.prototype.open = function open(...args) {
        [, this._requestUrl] = args;
        realOpen.apply(this, args);
    };

    /*
     * Monkeypatches for setting the src of images and scripts, an
     * indirect way of sending requests that is often employed by
     * skimmers. The getters are also overridden to make the behaviour
     * seem identical to the original implementation. Otherwise, if a
     * script were to set an src and immediately read it, it would
     * come back as the old value.
     */

    /*
     * WeakMap maps an object to a value. This is used to store the
     * new src of an object when its true src property may not yet
     * have been set.
     */
    const srcMap = new WeakMap();

    for (const srcElement of [
        HTMLImageElement.prototype,
        HTMLScriptElement.prototype,
    ]) {
        const realGetSrc = Object.getOwnPropertyDescriptor(srcElement, 'src')
            .get;
        const realSetSrc = Object.getOwnPropertyDescriptor(srcElement, 'src')
            .set;
        const realGetAttribute = srcElement.getAttribute;
        const realSetAttribute = srcElement.setAttribute;

        const interceptFn = generateRequestInterceptFn(realSetSrc, src => src);

        function setSrc(url) {
            srcMap.set(this, url);
            interceptFn.call(this, url);
            return url;
        }

        Object.defineProperty(srcElement, 'src', {
            get() {
                if (srcMap.get(this) === undefined) {
                    return realGetSrc.call(this);
                } else {
                    return normaliseUrl(srcMap.get(this));
                }
            },

            set: setSrc,
        });

        srcElement.setAttribute = function(attributeName, value) {
            if (attributeName.toUpperCase() === 'SRC') {
                setSrc.call(this, value);
            } else {
                realSetAttribute.call(this, attributeName, value);
            }
        };

        srcElement.getAttribute = function(attributeName) {
            if (
                attributeName.toUpperCase() === 'SRC' &&
                srcMap.get(this) !== undefined
            ) {
                return srcMap.get(this);
            } else {
                return realGetAttribute.call(this, attributeName);
            }
        };
    }
})(
    window.XMLHttpRequest.prototype.open,
    window.XMLHttpRequest.prototype.send,
    window.fetch
);
