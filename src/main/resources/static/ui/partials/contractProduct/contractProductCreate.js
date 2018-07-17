app.controller('contractProductCreateCtrl', ['ContractService', 'ContractProductService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractService, ContractProductService, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.buffer = {};

        $scope.contract = contract;

        $scope.capitalCash = 0;

        $scope.profitPercentage = 0;

        $scope.totalPrice = 0;

        $scope.totalPriceAfterVat = 0;

        //رأس المال
        $scope.findCapitalCash = function () {
            $scope.capitalCash = 0;
            angular.forEach($scope.contract.seller.productPurchases, function (productPurchase) {
                $scope.capitalCash = $scope.capitalCash + (productPurchase.requiredQuantity * productPurchase.unitPurchasePrice);
            });
        };

        //إجمالي الاصناف قبل (القيمة المضافة)
        //إجمالي الاصناف بعد (القيمة المضافة)
        $scope.findTotalPrice = function () {
            $scope.totalPrice = 0;
            $scope.totalPriceAfterVat = 0;
            angular.forEach($scope.contract.seller.productPurchases, function (productPurchase) {
                $scope.totalPrice = $scope.totalPrice + (productPurchase.requiredQuantity * productPurchase.unitSellPrice);
                $scope.totalPriceAfterVat = $scope.totalPriceAfterVat +
                    (productPurchase.requiredQuantity * productPurchase.unitSellPrice) +
                    (productPurchase.requiredQuantity * (productPurchase.unitSellPrice * $rootScope.selectedCompany.vatFactor));
            });
            return $scope.totalPrice;
        };

        //نسبة الربح
        $scope.findProfitPercentage = function () {
            $scope.findCapitalCash();
            $scope.findTotalPrice();
            $scope.profitPercentage = (($scope.totalPrice - $scope.capitalCash) / $scope.capitalCash) * 100;
        };

        $scope.submit = function () {
            //ربط الأصناف بالعقد
            $scope.contract.contractProducts = [];
            angular.forEach($scope.contract.seller.productPurchases, function (productPurchase) {
                if (productPurchase.requiredQuantity > 0) {
                    var contractProduct = {};
                    contractProduct.quantity = productPurchase.requiredQuantity;
                    contractProduct.unitSellPrice = productPurchase.unitSellPrice;
                    contractProduct.unitVat = productPurchase.unitVat;
                    contractProduct.productPurchase = productPurchase;
                    contractProduct.contract = {}; //هامة جداًَ لربط السلعة مع العقد
                    contractProduct.contract.id = $scope.contract.id; //هامة جداًَ لربط السلعة مع العقد
                    $scope.contract.contractProducts.push(contractProduct);
                }
            });
            ContractProductService.createBatch($scope.contract.contractProducts).then(function (data) {
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