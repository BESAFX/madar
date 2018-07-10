app.factory("HistoryService",
    ['$http', '$log', function ($http, $log) {
        return {
            findDaily: function () {
                return $http.get("/api/history/findDaily").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);