app.controller('parentCreateUpdateCtrl', ['ProductService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'product',
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
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);