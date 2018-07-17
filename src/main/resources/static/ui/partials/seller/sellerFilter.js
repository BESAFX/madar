app.controller('sellerFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageSeller.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageSeller.page = $scope.pageSeller.currentPage - 1;
            $uibModalInstance.close($scope.paramSeller);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);