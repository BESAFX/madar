app.controller('contractFilterCtrl', ['$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function ($scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.addSortBy = function () {
            var sortBy = {};
            $scope.pageContract.sorts.push(sortBy);
        };

        $scope.submit = function () {
            $scope.pageContract.page = $scope.pageContract.currentPage - 1;
            $uibModalInstance.close($scope.paramContract);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);