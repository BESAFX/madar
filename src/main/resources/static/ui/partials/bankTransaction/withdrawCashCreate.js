app.controller('withdrawCashCreateCtrl', ['SellerService', 'BankTransactionService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function (SellerService, BankTransactionService, $scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.buffer = {};

        $scope.findSellerBalance = function () {
            SellerService.findSellerBalance($rootScope.selectedCompany.seller.id).then(function (value) {
                return $rootScope.selectedCompany.seller = value;
            });
        };

        $scope.submit = function () {
            BankTransactionService.createWithdrawCash($scope.buffer.amount, $scope.buffer.note).then(function (data) {
                $uibModalInstance.close(data);
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            $scope.findSellerBalance();
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);