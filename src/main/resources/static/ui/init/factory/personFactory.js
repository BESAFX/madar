app.factory("PersonService",
    ['$http', '$log', function ($http, $log) {
        return {
            findAll: function () {
                return $http.get("/api/person/findAll").then(function (response) {
                    return response.data;
                });
            },
            findAllCombo: function () {
                return $http.get("/api/person/findAllCombo").then(function (response) {
                    return response.data;
                });
            },
            findOne: function (id) {
                return $http.get("/api/person/findOne/" + id).then(function (response) {
                    return response.data;
                });
            },
            create: function (person) {
                return $http.post("/api/person/create", person).then(function (response) {
                    return response.data;
                });
            },
            remove: function (id) {
                return $http.delete("/api/person/delete/" + id);
            },
            update: function (person) {
                return $http.put("/api/person/update", person).then(function (response) {
                    return response.data;
                });
            },
            updateProfile: function (person) {
                return $http.put("/api/person/updateProfile", person).then(function (response) {
                    return response.data;
                });
            },
            uploadContactPhoto: function (file) {
                var fd = new FormData();
                fd.append('file', file);
                return $http.post("/uploadContactPhoto", fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function (response) {
                    return response.data;
                });
            },
            enable: function (person) {
                return $http.put("/api/person/enable", person).then(function (response) {
                    return response.data;
                });
            },
            disable: function (person) {
                return $http.put("/api/person/disable", person).then(function (response) {
                    return response.data;
                });
            },
            setDateType: function (dateType) {
                return $http.get("/api/person/setDateType/" + dateType).then(function (response) {
                    return response.data;
                });
            },
            setStyle: function (style) {
                return $http.get("/api/person/setStyle/" + style).then(function (response) {
                    return response.data;
                });
            },
            setIconSet: function (iconSet, iconSetType) {
                return $http.get("/api/person/setIconSet/" + iconSet + "/" + iconSetType).then(function (response) {
                    return response.data;
                });
            },
            findAuthorities: function () {
                return $http.get("/api/person/findAuthorities").then(function (response) {
                    return response.data;
                });
            },
            findActivePerson: function () {
                return $http.get("/api/person/findActivePerson").then(function (response) {
                    return response.data;
                });
            }
        };
    }]);