app.controller('contractPremiumFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageContractPremium.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageContractPremium.page = $scope.pageContractPremium.currentPage - 1;
            $uibModalInstance.close($scope.paramContractPremium);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);