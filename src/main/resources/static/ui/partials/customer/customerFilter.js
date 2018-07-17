app.controller('customerFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageCustomer.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageCustomer.page = $scope.pageCustomer.currentPage - 1;
            $uibModalInstance.close($scope.paramCustomer);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);