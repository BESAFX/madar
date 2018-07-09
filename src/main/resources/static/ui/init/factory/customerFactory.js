app.factory("CustomerService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAllCombo: function () {
                return $http.get("/api/customer/findAllCombo").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/customer/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            findByThisMonth: function () {
                return $http.get("/api/customer/findByThisMonth").then(function (response) {
                    return response.data;
                });
            },
            create: function (customer) {
                return $http.post("/api/customer/create", customer).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/customer/delete/" + id);
            },
            update: function (customer) {
                return $http.put("/api/customer/update", customer).then(function (response) {
                    return response.data;
                });
            },
            sendMessage: function (message, ids) {
                return $http.post("/api/customer/sendMessage/" + ids, message).then(function (response) {
                    return response.data;
                });
            },
            filter: function (search) {
                return $http.get("/api/customer/filter?" + search).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);