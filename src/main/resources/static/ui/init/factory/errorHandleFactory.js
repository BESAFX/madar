app.factory('errorInterceptor', ['$q', '$injector', function ($q, $injector) {
    return {
        request: function (config) {
            return config || $q.when(config);
        },
        requestError: function (request) {
            return $q.reject(request);
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            if (response && response.status === 404) {
            }
            if (response && response.status >= 500) {
                var modal = $injector.get("$uibModal");
                modal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: '/ui/partials/modal/confirmModal.html',
                    controller: 'confirmModalCtrl',
                    backdrop: 'static',
                    keyboard: false,
                    resolve: {
                        title: function () {
                            return "رسالة من الخادم";
                        },
                        icon: function () {
                            return 'error';
                        },
                        message: function () {
                            return response.data.message;
                        }
                    }
                });
            }
            return $q.reject(response);
        }
    };
}]);