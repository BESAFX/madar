app.factory("ContractAttachService",
    ['$http', '$log', function ($http, $log) {
        return {
            upload: function (contract, files) {
                return $http.post("/api/contractAttach/upload",
                    {contractId: contract.id, files: files},
                    {
                        transformRequest: function () {
                            var formData = new FormData();
                            formData.append("contractId", contract.id);
                            angular.forEach(files, function (file) {
                                formData.append('files', file);
                            });
                            return formData;
                        },
                        headers: {
                            'Content-Type': undefined
                        }
                    }
                ).then(function (response) {
                    return response.data;
                });

            },
            remove: function (contractAttach) {
                return $http.delete("/api/contractAttach/delete/" + contractAttach.id).then(function (response) {
                    return response.data;
                });
            },
            removeWhatever: function (contractAttach) {
                return $http.delete("/api/contractAttach/deleteWhatever/" + contractAttach.id);
            },
            findByContract: function (contract) {
                return $http.get("/api/contractAttach/findByContract/" + contract.id).then(function (response) {
                    return response.data;
                });
            }
        };
    }]);