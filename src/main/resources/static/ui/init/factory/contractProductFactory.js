app.factory("ContractProductService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/contractProduct/findAll").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/contractProduct/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            findByContract: function (id) {
                return $http.get("/api/contractProduct/findByContract/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (contractProduct) {
                return $http.post("/api/contractProduct/create", contractProduct).then(function (response) {
                    return response.data;
                });
            },
            createBatch: function (contractProducts) {
                return $http.post("/api/contractProduct/createBatch", contractProducts).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/contractProduct/delete/" + id);
            }
        };
    }]);