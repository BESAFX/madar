app.controller('productPurchaseFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageProductPurchase.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageProductPurchase.page = $scope.pageProductPurchase.currentPage - 1;
            $uibModalInstance.close($scope.paramProductPurchase);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 700);

    }]);