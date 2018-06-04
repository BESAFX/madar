app.service('ModalProvider', ['$uibModal', '$log', function ($uibModal, $log) {

    /**************************************************************
     *                                                            *
     * Customer Model                                             *
     *                                                            *
     *************************************************************/
    this.openCustomerCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/customer/customerCreateUpdate.html',
            controller: 'customerCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'عميل / كفيل جديد';
                },
                action: function () {
                    return 'create';
                },
                customer: function () {
                    return {};
                }
            }
        });
    };

    this.openCustomerUpdateModel = function (customer) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/customer/customerCreateUpdate.html',
            controller: 'customerCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات عميل / كفيل';
                },
                action: function () {
                    return 'update';
                },
                customer: function () {
                    return customer;
                }
            }
        });
    };

    this.openCustomerSendMessageModel = function (customers) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/customer/customerSendMessage.html',
            controller: "customerSendMessageCtrl",
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                customers: function () {
                    return customers;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Seller Model                                               *
     *                                                            *
     *************************************************************/
    this.openSellerCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/seller/sellerCreateUpdate.html',
            controller: 'sellerCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'مستثمر جديد';
                },
                action: function () {
                    return 'create';
                },
                seller: function () {
                    return {};
                }
            }
        });
    };

    this.openSellerUpdateModel = function (seller) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/seller/sellerCreateUpdate.html',
            controller: 'sellerCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات مستثمر';
                },
                action: function () {
                    return 'update';
                },
                seller: function () {
                    return seller;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Product Model                                              *
     *                                                            *
     *************************************************************/
    this.openParentCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/product/parentCreateUpdate.html',
            controller: 'parentCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تصنيف رئيسي جديد';
                },
                action: function () {
                    return 'create';
                },
                product: function () {
                    return {};
                }
            }
        });
    };

    this.openParentUpdateModel = function (product) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/product/parentCreateUpdate.html',
            controller: 'parentCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات التصنيف الرئيسي';
                },
                action: function () {
                    return 'update';
                },
                product: function () {
                    return product;
                }
            }
        });
    };

    this.openProductCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/product/productCreateUpdate.html',
            controller: 'productCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تصنيف فرعي جديد';
                },
                action: function () {
                    return 'create';
                },
                product: function () {
                    return {};
                }
            }
        });
    };

    this.openProductUpdateModel = function (product) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/product/productCreateUpdate.html',
            controller: 'productCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات التصنيف الفرعي';
                },
                action: function () {
                    return 'update';
                },
                product: function () {
                    return product;
                }
            }
        });
    };

    this.openProductPurchaseCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/product/productPurchaseCreate.html',
            controller: 'productPurchaseCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg'
        });
    };

    /**************************************************************
     *                                                            *
     * Contract Model                                             *
     *                                                            *
     *************************************************************/
    this.openContractCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contract/contractCreate.html',
            controller: 'contractCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg'
        });
    };

    this.openContractUpdateModel = function (contract) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contract/contractUpdate.html',
            controller: 'contractUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg',
            resolve: {
                contract: ['ContractService', function (ContractService) {
                    return ContractService.findOne(contract.id).then(function (data) {
                        return contract = data;
                    });
                }]
            }
        });
    };

    this.openContractOldCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contract/contractOldCreate.html',
            controller: 'contractOldCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            windowClass: 'xlg'
        });
    };

    this.openContractDetailsModel = function (contract) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contract/contractDetails.html',
            controller: 'contractDetailsCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                contract: function () {
                    return contract;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * ContractProduct Model                                      *
     *                                                            *
     *************************************************************/
    this.openContractProductCreateModel = function (contract) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contractProduct/contractProductCreate.html',
            controller: 'contractProductCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                contract: ['ProductPurchaseService', function (ProductPurchaseService) {
                    return ProductPurchaseService.findBySellerAndRemainFull(contract.seller.id).then(function (data) {
                        contract.seller.productPurchases = [];
                        angular.forEach(data, function (productPurchase) {
                            productPurchase.requiredQuantity = 0;
                            productPurchase.unitSellPrice = 0;
                            contract.seller.productPurchases.push(productPurchase);
                        });
                        return contract;
                    });
                }]
            }
        });
    };

    /**************************************************************
     *                                                            *
     * ContractPremium Model                                      *
     *                                                            *
     *************************************************************/
    this.openContractPremiumCreateModel = function (contract) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contractPremium/contractPremiumCreate.html',
            controller: 'contractPremiumCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                contract: ['ContractService', function (ContractService) {
                    return ContractService.findOne(contract.id).then(function (data) {
                        return data;
                    });
                }]
            }
        });
    };

    this.openContractPremiumSendMessageModel = function (contractPremiums) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contractPremium/contractPremiumSendMessage.html',
            controller: "contractPremiumSendMessageCtrl",
            backdrop: 'static',
            keyboard: false,
            size: 'lg',
            resolve: {
                contractPremiums: function () {
                    return contractPremiums;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * ContractPayment Model                                      *
     *                                                            *
     *************************************************************/
    this.openContractPaymentCreateModel = function (contract) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/contractPayment/contractPaymentCreate.html',
            controller: 'contractPaymentCreateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                contract: function () {
                    return contract;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * DepositCreate Model                                        *
     *                                                            *
     *************************************************************/
    this.openDepositCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/bankTransaction/depositCreate.html',
            controller: 'depositCreateCtrl',
            backdrop: 'static',
            keyboard: false
        });
    };

    /**************************************************************
     *                                                            *
     * WithdrawCreate Model                                       *
     *                                                            *
     *************************************************************/
    this.openWithdrawCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/bankTransaction/withdrawCreate.html',
            controller: 'withdrawCreateCtrl',
            backdrop: 'static',
            keyboard: false
        });
    };

    /**************************************************************
     *                                                            *
     * TransferCreate Model                                       *
     *                                                            *
     *************************************************************/
    this.openTransferCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/bankTransaction/transferCreate.html',
            controller: 'transferCreateCtrl',
            backdrop: 'static',
            keyboard: false
        });
    };

    /**************************************************************
     *                                                            *
     * WithdrawCashCreate Model                                   *
     *                                                            *
     *************************************************************/
    this.openWithdrawCashCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/bankTransaction/withdrawCashCreate.html',
            controller: 'withdrawCashCreateCtrl',
            backdrop: 'static',
            keyboard: false
        });
    };

    /**************************************************************
     *                                                            *
     * Team Model                                                 *
     *                                                            *
     *************************************************************/
    this.openTeamCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/team/teamCreateUpdate.html',
            controller: 'teamCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'مجموعة جديدة';
                },
                action: function () {
                    return 'create';
                },
                team: function () {
                    return undefined;
                }
            }
        });
    };

    this.openTeamUpdateModel = function (team) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/team/teamCreateUpdate.html',
            controller: 'teamCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات مجموعة';
                },
                action: function () {
                    return 'update';
                },
                team: function () {
                    return team;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Person Model                                               *
     *                                                            *
     *************************************************************/
    this.openPersonCreateModel = function () {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/person/personCreateUpdate.html',
            controller: 'personCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'مستخدم جديد';
                },
                action: function () {
                    return 'create';
                },
                person: function () {
                    return {};
                }
            }
        });
    };

    this.openPersonUpdateModel = function (person) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/person/personCreateUpdate.html',
            controller: 'personCreateUpdateCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return 'تعديل بيانات مستخدم';
                },
                action: function () {
                    return 'update';
                },
                person: function () {
                    return person;
                }
            }
        });
    };

    /**************************************************************
     *                                                            *
     * Confirm Model                                              *
     *                                                            *
     *************************************************************/
    this.openConfirmModel = function (title, icon, message) {
        return $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '/ui/partials/modal/confirmModal.html',
            controller: 'confirmModalCtrl',
            backdrop: 'static',
            keyboard: false,
            resolve: {
                title: function () {
                    return title;
                },
                icon: function () {
                    return icon;
                },
                message: function () {
                    return message;
                }
            }
        });
    };

}]);

app.service('NotificationProvider', ['$http', function ($http) {

    this.notifyOne = function (code, title, message, type, receiver) {
        $http.post("/notifyOne?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
            + '&'
            + 'receiver=' + receiver);
    };
    this.notifyAll = function (code, title, message, type) {
        $http.post("/notifyAll?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
        );
    };
    this.notifyOthers = function (code, title, message, type) {
        $http.post("/notifyOthers?"
            + 'code=' + code
            + '&'
            + 'title=' + title
            + '&'
            + 'message=' + message
            + '&'
            + 'type=' + type
        );
    };

}]);

