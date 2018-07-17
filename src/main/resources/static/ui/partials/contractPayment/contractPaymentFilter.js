app.controller('contractPaymentFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageContractPayment.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageContractPayment.page = $scope.pageContractPayment.currentPage - 1;
            $uibModalInstance.close($scope.paramContractPayment);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);