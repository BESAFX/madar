app.factory("SmsService",
    ['$http', '$log', function ($http, $log) {
        return {
            getCredit: function () {
                return $http.get("/api/sms/getCredit").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);