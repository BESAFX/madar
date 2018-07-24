app.controller('contractPaymentCreateCtrl', ['ContractService', 'ContractPremiumService', 'ContractPaymentService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractService, ContractPremiumService, ContractPaymentService, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.buffer = {};

        $scope.contractPayment = {};

        $scope.contractPayment.contract = contract;

        $scope.contracts = [];

        $scope.buffer.searchBy = 'code';

        $scope.searchContracts = function ($select, $event) {

            // no event means first load!
            if (!$event) {
                $scope.pageContract = 0;
                $scope.contracts = [];
            } else {
                $event.stopPropagation();
                $event.preventDefault();
                $scope.pageContract++;
            }

            var search = [];

            search.push('size=');
            search.push(10);
            search.push('&');

            search.push('page=');
            search.push($scope.pageContract);
            search.push('&');

            if($scope.buffer.searchBy === 'code'){
                search.push('codeFrom=');
                search.push(parseInt($select.search) || undefined);
                search.push('&');

                search.push('codeTo=');
                search.push(parseInt($select.search) || undefined);
                search.push('&');
            }

            if($scope.buffer.searchBy === 'customerName') {
                search.push('customerName=');
                search.push($select.search);
                search.push('&');
            }

            if($scope.buffer.searchBy === 'customerMobile') {
                search.push('customerMobile=');
                search.push($select.search);
                search.push('&');
            }

            search.push('filterCompareType=and');

            return ContractService.filter(search.join("")).then(function (data) {
                $scope.buffer.lastContract = data.last;
                return $scope.contracts = $scope.contracts.concat(data.content);
            });

        };

        $scope.refreshContract = function () {
            ContractService.findOne($scope.contractPayment.contract.id).then(function (value) {
                return $scope.contractPayment.contract = value;
            })
        };

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