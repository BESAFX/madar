app.controller('contractDetailsCtrl', ['ContractService', 'ContractAttachService', 'ContractProductService', 'ContractPremiumService', 'ContractPaymentService', 'ModalProvider', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'contract',
    function (ContractService, ContractAttachService, ContractProductService, ContractPremiumService, ContractPaymentService, ModalProvider, $scope, $rootScope, $timeout, $log, $uibModalInstance, contract) {

        $scope.contract = {};

        $scope.contract.contractAttaches = [];

        $scope.contract = contract;

        $scope.files = [];

        $scope.refreshContract = function () {
            ContractService.findOne($scope.contract.id).then(function (data) {
                $scope.contract = data;
            })
        };

        $scope.refreshContractProducts = function () {
            ContractProductService.findByContract($scope.contract.id).then(function (data) {
                $scope.contract.contractProducts = data;
            })
        };

        $scope.refreshContractPremiums = function () {
            ContractPremiumService.findByContract($scope.contract.id).then(function (data) {
                $scope.contract.contractPremiums = data;
            })
        };

        $scope.refreshContractPayments = function () {
            ContractPaymentService.findByContract($scope.contract.id).then(function (data) {
                $scope.contract.contractPayments = data;
            })
        };

        $scope.newContractProduct = function () {
            ModalProvider.openContractProductCreateModel($scope.contract).result.then(function (data) {
                $scope.refreshContractProducts();
            });
        };

        $scope.newContractPremium = function () {
            ModalProvider.openContractPremiumCreateModel($scope.contract).result.then(function (data) {
                $scope.refreshContractPremiums();
            });
        };

        $scope.newContractPayment = function () {
            ModalProvider.openContractPaymentCreateModel($scope.contract).result.then(function (data) {
                $scope.refreshContractPayments();
            });
        };

        $scope.deleteContractProduct = function (contractProduct) {
            ModalProvider.openConfirmModel("العقود", "delete", "هل تود حذف السلعة فعلاً؟").result.then(function (value) {
                if (value) {
                    ContractProductService.remove(contractProduct.id).then(function () {
                        var index = $scope.contract.contractProducts.indexOf(contractProduct);
                        $scope.contract.contractProducts.splice(index, 1);
                    });
                }
            });
        };

        $scope.deleteContractPremium = function (contractPremium) {
            ModalProvider.openConfirmModel("العقود", "delete", "هل تود حذف القسط فعلاً؟").result.then(function (value) {
                if (value) {
                    ContractPremiumService.remove(contractPremium.id).then(function () {
                        var index = $scope.contract.contractPremiums.indexOf(contractPremium);
                        $scope.contract.contractPremiums.splice(index, 1);
                    });
                }
            });
        };

        $scope.deleteContractPayment = function (contractPayment) {
            ModalProvider.openConfirmModel("العقود", "delete", "هل تود حذف الدفعة فعلاً؟").result.then(function (value) {
                if (value) {
                    ContractPaymentService.remove(contractPayment.id).then(function () {
                        var index = $scope.contract.contractPayments.indexOf(contractPayment);
                        $scope.contract.contractPayments.splice(index, 1);
                    });
                }
            });
        };

        //////////////////////////File Manager///////////////////////////////////
        $scope.uploadFiles = function () {
            document.getElementById('uploader').click();
        };

        $scope.uploadAll = function () {
            if($scope.files.length > 0){
                ContractAttachService.upload($scope.contract, $scope.files).then(function (value) {
                    Array.prototype.push.apply($scope.contract.contractAttaches, value);
                });
            }else{
                ModalProvider.openConfirmModel('العقود', 'attach_file', 'فضلاً اختر على الأقل ملف واحد للتحميل');
            }
        };

        $scope.deleteContractAttach = function (contractAttach) {
            ModalProvider.openConfirmModel("العقود", "delete", "هل تود حذف المستند فعلاً؟").result.then(function (value) {
                if (value) {
                    ContractAttachService.remove(contractAttach).then(function (data) {
                        if(data){
                            var index = $scope.contract.contractAttaches.indexOf(contractAttach);
                            $scope.contract.contractAttaches.splice(index, 1);
                        }else{
                            ModalProvider.openConfirmModel("العقود", "delete", "يبدو أن الملف لم يعد موجوداً بوحدات التخزين، هل تود حذفه نهائياً؟").result.then(function (value) {
                                if(value){
                                    ContractAttachService.removeWhatever(contractAttach).then(function () {
                                        var index = $scope.contract.contractAttaches.indexOf(contractAttach);
                                        $scope.contract.contractAttaches.splice(index, 1);
                                    })
                                }
                            });
                        }
                    });
                }
            });
        };
        //////////////////////////File Manager///////////////////////////////////

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $timeout(function () {
            $scope.refreshContract();
            window.componentHandler.upgradeAllRegistered();
        }, 600);

    }]);