app.controller('sellerCreateUpdateCtrl', ['SellerService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'seller',
    function (SellerService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, seller) {

        $scope.buffer = {};

        $scope.seller = seller;

        $scope.seller.openCash = 0;

        $scope.title = title;

        $scope.action = action;

        $scope.submit = function () {
            switch ($scope.action) {
                case 'create' :
                    SellerService.create($scope.seller, $scope.seller.openCash).then(function (data) {
                        SellerService.findOne(data.id).then(function (value) {
                            $uibModalInstance.close(value);
                        });
                    });
                    break;
                case 'update' :
                    SellerService.update($scope.seller).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);