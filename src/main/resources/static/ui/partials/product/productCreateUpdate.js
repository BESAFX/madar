app.controller('productCreateUpdateCtrl', ['ProductService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'product',
    function (ProductService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, product) {

        $scope.buffer = {};

        $scope.title = title;

        $scope.action = action;

        $scope.product = product;

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    ProductService.create($scope.product).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
                case 'update' :
                    ProductService.update($scope.product).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            ProductService.findParents().then(function (value) {
                $scope.parents = value;
            });
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);