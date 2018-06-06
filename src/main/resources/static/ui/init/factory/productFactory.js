app.factory("ProductService",
    ['$http', '$log', function ($http, $log) {
        return {
            findOne: function (id) {
                return $http.get("/api/product/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            findParents: function () {
                return $http.get("/api/product/findParents").then(function (response) {
                    return response.data;
                });
            },
            findChilds: function (id) {
                return $http.get("/api/product/findChilds/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (product) {
                return $http.post("/api/product/create", product).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/product/delete/" + id);
            },
            update: function (product) {
                return $http.put("/api/product/update", product).then(function (response) {
                    return response.data;
                });
            },
            filter: function (search) {
                return $http.get("/api/product/filter?" + search).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);