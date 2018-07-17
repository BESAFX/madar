app.controller('bankTransactionFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageBankTransaction.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageBankTransaction.page = $scope.pageBankTransaction.currentPage - 1;
            $uibModalInstance.close($scope.paramBankTransaction);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);