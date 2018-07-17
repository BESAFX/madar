app.controller('contractPaymentCreateCtrl', ['ContractPremiumService', 'ContractPaymentService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractPremiumService, ContractPaymentService, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.buffer = {};

        $scope.contract = contract;

        $scope.contractPayment = {};

        $scope.contractPayment.contract = contract;

        $scope.submit = function () {
            ContractPaymentService.create($scope.contractPayment).then(function (data) {
                ContractPaymentService.findOne(data.id).then(function (value) {
                    $uibModalInstance.close(value);
                });
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            ContractPremiumService.findByContract($scope.contract.id).then(function (value) {
                $scope.contractPremiums = value;
            });
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);