app.controller('contractUpdateCtrl', ['ContractService', 'ModalProvider', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractService, ModalProvider, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.buffer = {};

        $scope.contract = contract;

        $scope.submit = function () {
            ContractService.update($scope.contract).then(function (data) {
                ContractService.findOne(data.id).then(function (value) {
                    $uibModalInstance.close(value);
                });
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);