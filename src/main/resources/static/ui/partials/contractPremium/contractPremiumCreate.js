app.controller('contractPremiumCreateCtrl', ['ContractPremiumService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractPremiumService, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.contract = contract;

        $scope.contractPremium = {};

        $scope.contractPremium.contract = contract;

        $scope.submit = function () {
            ContractPremiumService.create($scope.contractPremium).then(function (data) {
                $uibModalInstance.close(data);
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);