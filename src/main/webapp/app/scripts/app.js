function _createCookie(name, value, days) {
    var date, expires;
    if (days) {
        date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = '; expires=' + date.toGMTString();
    } else {
        expires = '';
    }
    document.cookie = name + '=' + value + expires + '; path=/';
}

(function (document) {
    'use strict';

    // Grab a reference to our auto-binding template
    // and give it some initial binding values
    // Learn more about auto-binding templates at http://goo.gl/Dx1u2g
    var app = document.querySelector('#app');

    app.displayInstalledToast = function () {
        document.querySelector('#caching-complete').show();
    };

    // Listen for template bound event to know when bindings
    // have resolved and content has been stamped to the page
    app.addEventListener('dom-change', function () {
        console.log('Our app is ready to rock!');
    });

    // See https://github.com/Polymer/polymer/issues/1381
    window.addEventListener('WebComponentsReady', function () {
        // imports are loaded and elements have been registered
    });

    window.addEventListener('authentication', function (event) {
        var authenticationData = event.detail;
        console.log('Authentication received', authenticationData);
        app.authenticationData = authenticationData;
        _createCookie('AUTH_TOKEN', authenticationData.token, 3650);

        // Redirect to home if you authenticated from the account validation page.
        if (app.route == 'accountValidation') {
            page('/');
        }
    });

    window.addEventListener('signout', function () {
        console.log('Signed out', app.authenticationData);
        app.authenticationData = {token: null};
    });

    window.addEventListener('piece-move', function () {
        console.log('A piece has moved in an open game, refreshing open games list');
        document.querySelector('#games-loader').refresh();
    });

    window.addEventListener('game-created', function () {
        console.log('Game created, refreshing open games list');
        document.querySelector('#games-loader').refresh();
    });

    window.addEventListener('game-ended', function () {
        console.log('Game closed, refreshing open games list');
        document.querySelector('#games-loader').refresh();
    });

    window.addEventListener('keydown', function (evt) {
        if (evt.target.nodeName === 'BODY') {
            if (evt.code === 'ArrowLeft') {
                document.querySelector('chess-game').previous();
            }

            if (evt.code === 'ArrowRight') {
                document.querySelector('chess-game').next();
            }
        }
    });

    app.openGames = [];

})(document);
