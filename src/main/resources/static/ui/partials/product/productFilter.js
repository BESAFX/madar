app.controller('productFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'ProductService',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance, ProductService) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageProduct.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageProduct.page = $scope.pageProduct.currentPage - 1;
            $uibModalInstance.close($scope.paramProduct);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            ProductService.findParents().then(function (value) {
                $scope.parents = value;
            });
            window.componentHandler.upgradeAllRegistered();
        }, 700);

    }]);