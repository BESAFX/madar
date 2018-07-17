app.factory("BankService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/bank/findAll").then(function (response) {
                    return response.data;
                });
            },
            findMyBanks: function () {
                return $http.get("/api/bank/findMyBanks").then(function (response) {
                    return response.data;
                });
            },
            findBySeller: function (sellerId) {
                return $http.get("/api/bank/findBySeller/" + sellerId).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);