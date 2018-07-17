app.controller('confirmModalCtrl', [
    '$uibModalInstance',
    '$scope',
    '$timeout',
    'title',
    'icon',
    'message',
    function ($uibModalInstance,
              $scope,
              $timeout,
              title,
              icon,
              message) {

        $scope.modalTitle = title;
        $scope.modalIcon = icon;
        $scope.modalMessage = message;

        $scope.submit = function () {
            $uibModalInstance.close(true);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);