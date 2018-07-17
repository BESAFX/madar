app.factory("AttachTypeService", ['$http', '$log',
    function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/attachType/findAll").then(function (response) {
                    return response.data;
                })
            }
        };
    }]);