app.controller('premiumCreateCtrl', ['ContractService', 'ContractPremiumService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance',
    function (ContractService, ContractPremiumService, $scope, $rootScope, $timeout, $log, $uibModalInstance) {

        $scope.buffer = {};

        $scope.contractPremium = {};

        $scope.contracts = [];

        $scope.searchBy = 'code';

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

            if($scope.searchBy === 'code'){
                search.push('codeFrom=');
                search.push(parseInt($select.search) || undefined);
                search.push('&');

                search.push('codeTo=');
                search.push(parseInt($select.search) || undefined);
                search.push('&');
            }

            if($scope.searchBy === 'customerName') {
                search.push('customerName=');
                search.push($select.search);
                search.push('&');
            }

            if($scope.searchBy === 'customerMobile') {
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