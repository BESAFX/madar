app.run([
    '$http',
    '$window',
    'PersonService',
    '$rootScope',
    '$log',
    '$css',
    '$stomp',
    'defaultErrorMessageResolver',
    'ModalProvider',
    'Fullscreen',
    '$state',
    '$stateParams',
    '$timeout',
    function ($http,
              $window,
              PersonService,
              $rootScope,
              $log,
              $css,
              $stomp,
              defaultErrorMessageResolver,
              ModalProvider,
              Fullscreen,
              $state,
              $stateParams,
              $timeout) {

        $rootScope.state = $state;
        $rootScope.stateParams = $stateParams;

        defaultErrorMessageResolver.getErrorMessages().then(function (errorMessages) {
            errorMessages['fieldRequired'] = 'هذا الحقل مطلوب';
        });

        $rootScope.contains = function (list, values) {
            return list ? _.intersection(values, list.split(',')).length > 0 : false;
        };

        $rootScope.logout = function () {
            $http.post('/logout');
            $window.location.href = '/logout';
        };

        $rootScope.refreshGUI = function () {
            $timeout(function () {
                window.componentHandler.upgradeAllRegistered();
            }, 800);
        };

        $rootScope.style = 'mdl-style';
        $rootScope.setStyle = function (style) {
            $rootScope.style = style ? style : 'mdl-style';
            $css.removeAll();
            $css.add([
                '/ui/css/' + $rootScope.style + '.css',
                '/ui/css/theme-black.css',
                '/ui/css/style.css'
            ], $rootScope);
            PersonService.setStyle($rootScope.style);
        };

        $rootScope.iconSet = 'icon-set-1';
        $rootScope.iconSetType = 'png';
        $rootScope.setIconSet = function (iconSet, iconSetType) {
            $rootScope.iconSet = iconSet ? iconSet : 'icon-set-1';
            $rootScope.iconSetType = iconSetType ? iconSetType : 'png';
            PersonService.setIconSet($rootScope.iconSet, $rootScope.iconSetType);
        };

        $rootScope.ModalProvider = ModalProvider;

        $rootScope.toggleDrawer = function () {
            $rootScope.drawer = document.querySelector('.mdl-layout');
            $rootScope.drawer.MaterialLayout.toggleDrawer();
        };

        $rootScope.dateType = 'H';

        $rootScope.switchDateType = function () {
            $rootScope.dateType === 'H' ? $rootScope.dateType = 'G' : $rootScope.dateType = 'H';
            PersonService.setDateType($rootScope.dateType);
        };

        PersonService.findActivePerson().then(function (data) {
            $rootScope.me = data;
            $rootScope.options = JSON.parse($rootScope.me.options);
            $rootScope.dateType = $rootScope.options.dateType;
            $rootScope.style = $rootScope.options.style ? $rootScope.options.style : 'mdl-style';
            $rootScope.iconSet = $rootScope.options.iconSet ? $rootScope.options.iconSet : 'icon-set-1';
            $rootScope.iconSetType = $rootScope.options.iconSetType ? $rootScope.options.iconSetType : 'png';
            $css.removeAll();
            $css.add([
                '/ui/css/' + $rootScope.style + '.css',
                '/ui/css/theme-black.css',
                '/ui/css/style.css'
            ], $rootScope);
        });

        $rootScope.goFullscreen = function () {
            if (Fullscreen.isEnabled())
                Fullscreen.cancel();
            else
                Fullscreen.all();
        };

        $rootScope.showNotify = function (title, message, type, icon) {
            noty({
                layout: 'bottomCenter',
                theme: 'relax', // or relax, metroui
                type: type, // success, error, warning, information, notification
                text: '<div class="activity-item text-center"><div class="activity">' + message + '</div></div>',
                dismissQueue: true, // [boolean] If you want to use queue feature set this true
                force: true, // [boolean] adds notification to the beginning of queue when set to true
                maxVisible: 3, // [integer] you can set max visible notification count for dismissQueue true option,
                template: '<div class="noty_message text-center"><span class="noty_text"></span></div>',
                timeout: 1500, // [integer|boolean] delay for closing event in milliseconds. Set false for sticky notifications
                progressBar: true, // [boolean] - displays a progress bar
                animation: {
                    open: 'animated fadeIn',
                    close: 'animated fadeOut',
                    easing: 'swing',
                    speed: 500 // opening & closing animation speed
                },
                closeWith: ['hover'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
                modal: false, // [boolean] if true adds an overlay
                killer: false, // [boolean] if true closes all notifications and shows itself
                callback: {
                    onShow: function () {
                    },
                    afterShow: function () {
                    },
                    onClose: function () {
                    },
                    afterClose: function () {
                    },
                    onCloseClick: function () {
                    },
                },
                buttons: false // [boolean|array] an array of buttons, for creating confirmation dialogs.
            });
        };

        $rootScope.showConfirmNotify = function (title, message, type, icon, callback) {
            noty({
                layout: 'center',
                theme: 'metroui', // or relax
                type: type, // success, error, warning, information, notification
                text: '<div class="activity-item text-right"><span>' + title + '</span> <i class="fa ' + icon + '"></i><div class="activity">' + message + '</div></div>',
                dismissQueue: true, // [boolean] If you want to use queue feature set this true
                force: false, // [boolean] adds notification to the beginning of queue when set to true
                maxVisible: 3, // [integer] you can set max visible notification count for dismissQueue true option,
                template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
                timeout: false, // [integer|boolean] delay for closing event in milliseconds. Set false for sticky notifications
                progressBar: true, // [boolean] - displays a progress bar
                animation: {
                    open: 'animated fadeIn',
                    close: 'animated fadeOut',
                    easing: 'swing',
                    speed: 200 // opening & closing animation speed
                },
                closeWith: ['button'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
                modal: true, // [boolean] if true adds an overlay
                killer: false, // [boolean] if true closes all notifications and shows itself
                callback: {
                    onShow: function () {
                    },
                    afterShow: function () {
                    },
                    onClose: function () {
                    },
                    afterClose: function () {
                    },
                    onCloseClick: function () {
                    },
                },
                buttons: [
                    {
                        addClass: 'btn btn-primary', text: 'نعم', onClick: function ($noty) {
                            $noty.close();
                            callback();
                        }
                    },
                    {
                        addClass: 'btn btn-danger', text: 'إلغاء', onClick: function ($noty) {
                            $noty.close();
                        }
                    }
                ]
            });
        };

        $rootScope.showTechnicalNotify = function (title, message, type, icon) {
            noty({
                layout: 'center',
                theme: 'metroui', // or relax
                type: type, // success, error, warning, information, notification
                text: '<div class="activity-item text-right"><span>' + title + '</span> <i class="fa ' + icon + '"></i><div class="activity">' + message + '</div></div>',
                dismissQueue: true, // [boolean] If you want to use queue feature set this true
                force: false, // [boolean] adds notification to the beginning of queue when set to true
                maxVisible: 3, // [integer] you can set max visible notification count for dismissQueue true option,
                template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
                timeout: false, // [integer|boolean] delay for closing event in milliseconds. Set false for sticky notifications
                progressBar: true, // [boolean] - displays a progress bar
                animation: {
                    open: 'animated fadeIn',
                    close: 'animated fadeOut',
                    easing: 'swing',
                    speed: 100 // opening & closing animation speed
                },
                closeWith: ['button'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
                modal: true, // [boolean] if true adds an overlay
                killer: true, // [boolean] if true closes all notifications and shows itself
                buttons: [
                    {
                        addClass: 'btn btn-primary', text: 'إعادة تحميل الصفحة', onClick: function ($noty) {
                            $noty.close();
                            location.reload(true);
                        }
                    },
                    {
                        addClass: 'btn btn-danger', text: 'إغلاق', onClick: function ($noty) {
                            $noty.close();
                        }
                    }
                ]
            });
        };

        /**************************************************************
         *                                                            *
         * STOMP Connect                                              *
         *                                                            *
         *************************************************************/
        $rootScope.globalMessages = [];
        $stomp.connect('/ws').then(function () {
            $stomp.subscribe('/user/queue/notify', function (payload, headers, res) {
                $rootScope.globalMessage = payload.message;
                $rootScope.globalMessages.push(payload.message);
                $rootScope.$apply();
                $rootScope.showNotify(payload.title, payload.message, payload.type, payload.icon);
            }, {'headers': 'notify'});
        });
        $rootScope.today = new Date();

    }]);