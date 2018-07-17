app.controller('contractPremiumSendMessageCtrl', ['ContractPremiumService', '$scope', '$rootScope', '$timeout', '$uibModalInstance', 'contractPremiums',
    function (ContractPremiumService, $scope, $rootScope, $timeout, $uibModalInstance, contractPremiums) {

        $scope.contractPremiums = contractPremiums;

        $scope.buffer = {};

        $scope.buffer.message =
            "فضلاً قم بسداد مبلغ" +
            " #amount# " +
            "ريال سعودي" +
            " " +
            "نظير القسط المستحق عليكم بتاريخ" +
            " #dueDate#"
        ;

        $scope.submit = function () {
            var ids = [];
            angular.forEach($scope.contractPremiums, function (contractPremium) {
                ids.push(contractPremium.id);
            });
            ContractPremiumService.sendMessage($scope.buffer.message, ids).then(function () {
                $uibModalInstance.dismiss('cancel');
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);