app.controller('customerSendMessageCtrl', ['CustomerService', '$scope', '$rootScope', '$timeout', '$uibModalInstance', 'customers',
    function (CustomerService, $scope, $rootScope, $timeout, $uibModalInstance, customers) {

        $scope.customers = customers;

        $scope.buffer = {};

        $scope.buffer.message =
            "نفيدكم علما بأن اجمالي الدين عليكم يساوي " +
            " #remain# " +
            "ريال سعودي"
        ;

        $scope.submit = function () {
            var ids = [];
            angular.forEach($scope.customers, function (customer) {
                ids.push(customer.id);
            });
            CustomerService.sendMessage($scope.buffer.message, ids).then(function () {
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