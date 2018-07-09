app.factory("ContractService",
    ['$http', '$log', function ($http, $log) {
        return {
            findOne: function (id) {
                return $http.get("/api/contract/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            findByThisMonth: function () {
                return $http.get("/api/contract/findByThisMonth").then(function (response) {
                    return response.data;
                });
            },
            findMyContracts: function () {
                return $http.get("/api/contract/findMyContracts").then(function (response) {
                    return response.data;
                });
            },
            findBySeller: function (sellerId) {
                return $http.get("/api/contract/findBySeller/" + sellerId).then(function (response) {
                    return response.data;
                });
            },
            create: function (contract) {
                return $http.post("/api/contract/create", contract).then(function (response) {
                    return response.data;
                });
            },
            createOld: function (wrapperUtil) {
                return $http.post("/api/contract/createOld", wrapperUtil).then(function (response) {
                    return response.data;
                });
            },
            update: function (contract) {
                return $http.put("/api/contract/update", contract).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/contract/delete/" + id);
            },
            filter: function (search) {
                return $http.get("/api/contract/filter?" + search).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);