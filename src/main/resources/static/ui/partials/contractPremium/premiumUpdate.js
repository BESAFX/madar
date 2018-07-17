app.controller('premiumUpdateCtrl', ['ContractPremiumService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contractPremium',
    function (ContractPremiumService, $scope, $rootScope, $timeout, $log, $uibModalInstance, contractPremium) {

        $scope.contractPremium = contractPremium;

        $scope.submit = function () {
            ContractPremiumService.update($scope.contractPremium).then(function (data) {
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