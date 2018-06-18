app.controller("menuCtrl", [
    'CompanyService',
    'CustomerService',
    'SellerService',
    'ProductService',
    'ProductPurchaseService',
    'ContractService',
    'ContractPremiumService',
    'ContractPaymentService',
    'BankService',
    'BankTransactionService',
    'AttachTypeService',
    'PersonService',
    'SmsService',
    'TeamService',
    'ModalProvider',
    '$scope',
    '$rootScope',
    '$state',
    '$uibModal',
    '$timeout',
    function (CompanyService,
              CustomerService,
              SellerService,
              ProductService,
              ProductPurchaseService,
              ContractService,
              ContractPremiumService,
              ContractPaymentService,
              BankService,
              BankTransactionService,
              AttachTypeService,
              PersonService,
              SmsService,
              TeamService,
              ModalProvider,
              $scope,
              $rootScope,
              $state,
              $uibModal,
              $timeout) {

        $scope.$watch('toggleState', function (newValue, oldValue) {
            switch (newValue) {
                case 'menu': {
                    $scope.pageTitle = 'القائمة';
                    break;
                }
                case 'company': {
                    $scope.pageTitle = 'المؤسسة';
                    break;
                }
                case 'customer': {
                    $scope.pageTitle = 'العملاء';
                    break;
                }
                case 'seller': {
                    $scope.pageTitle = 'المستثمرين';
                    break;
                }
                case 'product': {
                    $scope.pageTitle = 'السلع';
                    break;
                }
                case 'productPurchase': {
                    $scope.pageTitle = 'المخزون';
                    break;
                }
                case 'contract': {
                    $scope.pageTitle = 'العقود';
                    break;
                }
                case 'contractPremium': {
                    $scope.pageTitle = 'الاقساط';
                    break;
                }
                case 'contractPayment': {
                    $scope.pageTitle = 'الدفعات';
                    break;
                }
                case 'bankTransaction': {
                    $scope.pageTitle = 'المعاملات المالية';
                    break;
                }
                case 'team': {
                    $scope.pageTitle = 'الصلاحيات';
                    break;
                }
                case 'person': {
                    $scope.pageTitle = 'المستخدمين';
                    break;
                }
                case 'profile': {
                    $scope.pageTitle = 'الملف الشخصي';
                    break;
                }
                case 'help': {
                    $scope.pageTitle = 'المساعدة';
                    break;
                }
                case 'about': {
                    $scope.pageTitle = 'عن البرنامج';
                    break;
                }
                case 'report': {
                    $scope.pageTitle = 'التقارير';
                    break;
                }
            }
            $timeout(function () {
                window.componentHandler.upgradeAllRegistered();
            }, 500);
        }, true);
        $scope.toggleState = 'menu';
        $scope.openStateMenu = function () {
            $scope.toggleState = 'menu';
            $rootScope.refreshGUI();
        };
        $scope.openStateCompany = function () {
            $scope.toggleState = 'company';
            $rootScope.refreshGUI();
        };
        $scope.openStateCustomer = function () {
            $scope.toggleState = 'customer';
            $scope.searchCustomers({});
            $rootScope.refreshGUI();
        };
        $scope.openStateSeller = function () {
            $scope.toggleState = 'seller';
            $scope.searchSellers({});
            $rootScope.refreshGUI();
        };
        $scope.openStateProduct = function () {
            $scope.toggleState = 'product';
            $scope.searchProducts({});
            $rootScope.refreshGUI();
        };
        $scope.openStateProductPurchase = function () {
            $scope.toggleState = 'productPurchase';
            $scope.searchProductPurchases({});
            $rootScope.refreshGUI();
        };
        $scope.openStateContract = function () {
            $scope.toggleState = 'contract';
            $scope.searchContracts({});
            $rootScope.refreshGUI();
        };
        $scope.openStateContractPremium = function () {
            $scope.toggleState = 'contractPremium';
            $scope.searchContractPremiums({});
            $rootScope.refreshGUI();
        };
        $scope.openStateContractPayment = function () {
            $scope.toggleState = 'contractPayment';
            $scope.searchContractPayments({});
            $rootScope.refreshGUI();
        };
        $scope.openStateBankTransaction = function () {
            $scope.toggleState = 'bankTransaction';
            $scope.searchBankTransactions({});
            $rootScope.refreshGUI();
        };
        $scope.openStateTeam = function () {
            $scope.toggleState = 'team';
            $scope.fetchTeamTableData();
            $rootScope.refreshGUI();
        };
        $scope.openStatePerson = function () {
            $scope.toggleState = 'person';
            $scope.fetchPersonTableData();
            $rootScope.refreshGUI();
        };
        $scope.openStateProfile = function () {
            $scope.toggleState = 'profile';
            $rootScope.refreshGUI();
        };
        $scope.openStateHelp = function () {
            $scope.toggleState = 'help';
            $rootScope.refreshGUI();
        };
        $scope.openStateAbout = function () {
            $scope.toggleState = 'about';
            $rootScope.refreshGUI();
        };
        $scope.openStateReport = function () {
            $scope.toggleState = 'report';
            $scope.toggleReport = 'mainReportFrame';
            $rootScope.refreshGUI();
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Company                                                                                                    *
         *                                                                                                            *
         **************************************************************************************************************/
        $rootScope.selectedCompany = {};
        $rootScope.sms = {};
        $scope.submitCompany = function () {
            CompanyService.update($rootScope.selectedCompany).then(function (data) {
                $rootScope.selectedCompany = data;
            });
        };
        $scope.browseCompanyLogo = function () {
            document.getElementById('uploader-company').click();
        };
        $scope.uploadCompanyLogo = function (files) {
            CompanyService.uploadCompanyLogo(files[0]).then(function (data) {
                $rootScope.selectedCompany.logo = data;
                CompanyService.update($rootScope.selectedCompany).then(function (data) {
                    $rootScope.selectedCompany = data;
                });
            });
        };
        $scope.findMyContracts = function () {
            ContractService.findMyContracts().then(function (value) {
                $scope.myContracts = value;
            });
        };
        $scope.findMyBanks = function () {
            BankService.findMyBanks().then(function (value) {
                $scope.myBanks = value;
            });
        };
        $scope.findMyBankTransactions = function () {
            BankTransactionService.findMyBankTransactions().then(function (value) {
                $scope.myBankTransactions = value;
            });
        };
        $scope.findMyProductPurchases = function () {
            ProductPurchaseService.findMyProductPurchases().then(function (value) {
                $scope.myProductPurchases = value;
            });
        };
        $rootScope.findSmsCredit = function () {
            SmsService.getCredit().then(function (value) {
                $rootScope.sms.credit = value.GetCreditPostResult.Credit;
                $rootScope.sms.description = value.GetCreditPostResult.Description;
                return $rootScope.sms;
            });
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Customer                                                                                                   *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.customers = [];
        $scope.paramCustomer = {};
        $scope.customers.checkAll = false;

        $scope.pageCustomer = {};
        $scope.pageCustomer.sorts = [];
        $scope.pageCustomer.page = 0;
        $scope.pageCustomer.totalPages = 0;
        $scope.pageCustomer.currentPage = $scope.pageCustomer.page + 1;
        $scope.pageCustomer.currentPageString = ($scope.pageCustomer.page + 1) + ' / ' + $scope.pageCustomer.totalPages;
        $scope.pageCustomer.size = 25;
        $scope.pageCustomer.first = true;
        $scope.pageCustomer.last = true;

        $scope.openCustomersFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/customer/customerFilter.html',
                controller: 'customerFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramCustomer) {
                $scope.paramCustomer = paramCustomer;
                $scope.searchCustomers(paramCustomer);
            }, function () {
            });
        };
        $scope.searchCustomers = function (paramCustomer) {
            var search = [];
            search.push('size=');
            search.push($scope.pageCustomer.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageCustomer.page);
            search.push('&');
            angular.forEach($scope.pageCustomer.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if (paramCustomer.codeFrom) {
                search.push('codeFrom=');
                search.push(paramCustomer.codeFrom);
                search.push('&');
            }
            if (paramCustomer.codeTo) {
                search.push('codeTo=');
                search.push(paramCustomer.codeTo);
                search.push('&');
            }
            if (paramCustomer.registerDateTo) {
                search.push('registerDateTo=');
                search.push(paramCustomer.registerDateTo.getTime());
                search.push('&');
            }
            if (paramCustomer.registerDateFrom) {
                search.push('registerDateFrom=');
                search.push(paramCustomer.registerDateFrom.getTime());
                search.push('&');
            }
            if (paramCustomer.name) {
                search.push('name=');
                search.push(paramCustomer.name);
                search.push('&');
            }
            if (paramCustomer.mobile) {
                search.push('mobile=');
                search.push(paramCustomer.mobile);
                search.push('&');
            }
            if (paramCustomer.phone) {
                search.push('phone=');
                search.push(paramCustomer.phone);
                search.push('&');
            }
            if (paramCustomer.identityNumber) {
                search.push('identityNumber=');
                search.push(paramCustomer.identityNumber);
                search.push('&');
            }
            if (paramCustomer.qualification) {
                search.push('qualification=');
                search.push(paramCustomer.qualification);
                search.push('&');
            }
            if (paramCustomer.enabled) {
                search.push('enabled=');
                search.push(paramCustomer.enabled);
                search.push('&');
            }

            CustomerService.filter(search.join("")).then(function (data) {
                $scope.customers = data.content;

                $scope.pageCustomer.currentPage = $scope.pageCustomer.page + 1;
                $scope.pageCustomer.first = data.first;
                $scope.pageCustomer.last = data.last;
                $scope.pageCustomer.number = data.number;
                $scope.pageCustomer.numberOfElements = data.numberOfElements;
                $scope.pageCustomer.size = data.size;
                $scope.pageCustomer.totalElements = data.totalElements;
                $scope.pageCustomer.totalPages = data.totalPages;
                $scope.pageCustomer.currentPageString = ($scope.pageCustomer.page + 1) + ' / ' + $scope.pageCustomer.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextCustomersPage = function () {
            $scope.pageCustomer.page++;
            $scope.searchCustomers($scope.paramCustomer);
        };
        $scope.selectPrevCustomersPage = function () {
            $scope.pageCustomer.page--;
            $scope.searchCustomers($scope.paramCustomer);
        };
        $scope.checkAllCustomers = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('#checkAllCustomers input').is(":checked")) {
                    element.MaterialCheckbox.check();
                }
                else {
                    element.MaterialCheckbox.uncheck();
                }
            }
            angular.forEach($scope.customers, function (customer) {
                customer.isSelected = $scope.customers.checkAll;
            });
        };
        $scope.checkCustomer = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('.check input:checked').length == $('.check input').length) {
                    document.querySelector('#checkAllCustomers').MaterialCheckbox.check();
                } else {
                    document.querySelector('#checkAllCustomers').MaterialCheckbox.uncheck();
                }
            }
        };
        $scope.newCustomer = function () {
            ModalProvider.openCustomerCreateModel().result.then(function (data) {
                $scope.customers.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.deleteCustomer = function (customer) {
            ModalProvider.openConfirmModel("العملاء والكفلاء", "delete", "هل تود حذف العميل فعلاً؟").result.then(function (value) {
                if (value) {
                    CustomerService.remove(customer.id).then(function () {
                        var index = $scope.customers.indexOf(customer);
                        $scope.customers.splice(index, 1);
                    });
                }
            });
        };
        $scope.rowMenuCustomer = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CUSTOMER_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newCustomer();
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/edit.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تعديل...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CUSTOMER_UPDATE']);
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openCustomerUpdateModel($itemScope.customer);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CUSTOMER_DELETE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.deleteCustomer($itemScope.customer);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/send.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>إرسال رسالة...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_SMS_SEND']);
                },
                click: function ($itemScope, $event, value) {
                    var customers = [];
                    angular.forEach($scope.customers, function (customer) {
                        if (customer.isSelected) {
                            customers.push(customer);
                        }
                    });
                    if (customers.length === 0) {
                        ModalProvider.openConfirmModel('إرسال رسائل الأقساط', 'send', 'فضلا قم باختيار قسط واحد على الأقل');
                        return;
                    }
                    ModalProvider.openCustomerSendMessageModel(customers);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * Seller                                                                                                     *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.sellers = [];
        $scope.paramSeller = {};
        $scope.sellers.checkAll = false;

        $scope.pageSeller = {};
        $scope.pageSeller.sorts = [];
        $scope.pageSeller.page = 0;
        $scope.pageSeller.totalPages = 0;
        $scope.pageSeller.currentPage = $scope.pageSeller.page + 1;
        $scope.pageSeller.currentPageString = ($scope.pageSeller.page + 1) + ' / ' + $scope.pageSeller.totalPages;
        $scope.pageSeller.size = 25;
        $scope.pageSeller.first = true;
        $scope.pageSeller.last = true;

        $scope.openSellersFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/seller/sellerFilter.html',
                controller: 'sellerFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramSeller) {
                $scope.paramSeller = paramSeller;
                $scope.searchSellers(paramSeller);
            }, function () {
            });
        };
        $scope.searchSellers = function (paramSeller) {
            var search = [];
            search.push('size=');
            search.push($scope.pageSeller.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageSeller.page);
            search.push('&');
            angular.forEach($scope.pageSeller.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if (paramSeller.codeFrom) {
                search.push('codeFrom=');
                search.push(paramSeller.codeFrom);
                search.push('&');
            }
            if (paramSeller.codeTo) {
                search.push('codeTo=');
                search.push(paramSeller.codeTo);
                search.push('&');
            }
            if (paramSeller.registerDateTo) {
                search.push('registerDateTo=');
                search.push(paramSeller.registerDateTo.getTime());
                search.push('&');
            }
            if (paramSeller.registerDateFrom) {
                search.push('registerDateFrom=');
                search.push(paramSeller.registerDateFrom.getTime());
                search.push('&');
            }
            if (paramSeller.name) {
                search.push('name=');
                search.push(paramSeller.name);
                search.push('&');
            }
            if (paramSeller.mobile) {
                search.push('mobile=');
                search.push(paramSeller.mobile);
                search.push('&');
            }
            if (paramSeller.phone) {
                search.push('phone=');
                search.push(paramSeller.phone);
                search.push('&');
            }
            if (paramSeller.identityNumber) {
                search.push('identityNumber=');
                search.push(paramSeller.identityNumber);
                search.push('&');
            }
            if (paramSeller.qualification) {
                search.push('qualification=');
                search.push(paramSeller.qualification);
                search.push('&');
            }
            if (paramSeller.enabled) {
                search.push('enabled=');
                search.push(paramSeller.enabled);
                search.push('&');
            }

            SellerService.filter(search.join("")).then(function (data) {
                $scope.sellers = data.content;

                $scope.pageSeller.currentPage = $scope.pageSeller.page + 1;
                $scope.pageSeller.first = data.first;
                $scope.pageSeller.last = data.last;
                $scope.pageSeller.number = data.number;
                $scope.pageSeller.numberOfElements = data.numberOfElements;
                $scope.pageSeller.size = data.size;
                $scope.pageSeller.totalElements = data.totalElements;
                $scope.pageSeller.totalPages = data.totalPages;
                $scope.pageSeller.currentPageString = ($scope.pageSeller.page + 1) + ' / ' + $scope.pageSeller.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextSellersPage = function () {
            $scope.pageSeller.page++;
            $scope.searchSellers($scope.paramSeller);
        };
        $scope.selectPrevSellersPage = function () {
            $scope.pageSeller.page--;
            $scope.searchSellers($scope.paramSeller);
        };
        $scope.checkAllSellers = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('#checkAllSellers input').is(":checked")) {
                    element.MaterialCheckbox.check();
                }
                else {
                    element.MaterialCheckbox.uncheck();
                }
            }
            angular.forEach($scope.sellers, function (seller) {
                seller.isSelected = $scope.sellers.checkAll;
            });
        };
        $scope.checkSeller = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('.check input:checked').length == $('.check input').length) {
                    document.querySelector('#checkAllSellers').MaterialCheckbox.check();
                } else {
                    document.querySelector('#checkAllSellers').MaterialCheckbox.uncheck();
                }
            }
        };
        $scope.newSeller = function () {
            ModalProvider.openSellerCreateModel().result.then(function (data) {
                $scope.sellers.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.deleteSeller = function (seller) {
            ModalProvider.openConfirmModel("المستثمرين", "delete", "هل تود حذف المستثمر فعلاً؟").result.then(function (value) {
                if (value) {
                    SellerService.remove(seller.id).then(function () {
                        var index = $scope.sellers.indexOf(seller);
                        $scope.sellers.splice(index, 1);
                    });
                }
            });
        };
        $scope.rowMenuSeller = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CUSTOMER_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newSeller();
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/edit.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تعديل...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_SELLER_UPDATE']);
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openSellerUpdateModel($itemScope.seller);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_SELLER_DELETE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.deleteSeller($itemScope.seller);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * Product                                                                                                    *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.products = [];
        $scope.paramProduct = {};
        $scope.products.checkAll = false;
        $scope.parents = [];

        $scope.pageProduct = {};
        $scope.pageProduct.sorts = [];
        $scope.pageProduct.page = 0;
        $scope.pageProduct.totalPages = 0;
        $scope.pageProduct.currentPage = $scope.pageProduct.page + 1;
        $scope.pageProduct.currentPageString = ($scope.pageProduct.page + 1) + ' / ' + $scope.pageProduct.totalPages;
        $scope.pageProduct.size = 25;
        $scope.pageProduct.first = true;
        $scope.pageProduct.last = true;

        $scope.openProductsFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/product/productFilter.html',
                controller: 'productFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramProduct) {
                $scope.paramProduct = paramProduct;
                $scope.searchProducts(paramProduct);
            }, function () {});
        };
        $scope.searchProducts = function (paramProduct) {
            var search = [];
            search.push('size=');
            search.push($scope.pageProduct.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageProduct.page);
            search.push('&');
            angular.forEach($scope.pageProduct.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageProduct.sorts.length === 0) {
                search.push('sort=name,asc&');
            }
            //Product Filters
            if (paramProduct.codeFrom) {
                search.push('codeFrom=');
                search.push(paramProduct.codeFrom);
                search.push('&');
            }
            if (paramProduct.codeTo) {
                search.push('codeTo=');
                search.push(paramProduct.codeTo);
                search.push('&');
            }
            if (paramProduct.registerDateTo) {
                search.push('registerDateTo=');
                search.push(paramProduct.registerDateTo.getTime());
                search.push('&');
            }
            if (paramProduct.registerDateFrom) {
                search.push('registerDateFrom=');
                search.push(paramProduct.registerDateFrom.getTime());
                search.push('&');
            }
            if (paramProduct.name) {
                search.push('name=');
                search.push(paramProduct.name);
                search.push('&');
            }
            if (paramProduct.parent) {
                search.push('parentId=');
                search.push(paramProduct.parent.id);
                search.push('&');
            }

            ProductService.filter(search.join("")).then(function (data) {
                $scope.products = data.content;

                $scope.pageProduct.currentPage = $scope.pageProduct.page + 1;
                $scope.pageProduct.first = data.first;
                $scope.pageProduct.last = data.last;
                $scope.pageProduct.number = data.number;
                $scope.pageProduct.numberOfElements = data.numberOfElements;
                $scope.pageProduct.size = data.size;
                $scope.pageProduct.totalElements = data.totalElements;
                $scope.pageProduct.totalPages = data.totalPages;
                $scope.pageProduct.currentPageString = ($scope.pageProduct.page + 1) + ' / ' + $scope.pageProduct.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextProductsPage = function () {
            $scope.pageProduct.page++;
            $scope.searchProducts($scope.paramProduct);
        };
        $scope.selectPrevProductsPage = function () {
            $scope.pageProduct.page--;
            $scope.searchProducts($scope.paramProduct);
        };
        $scope.newProduct = function () {
            ModalProvider.openProductCreateModel().result.then(function (data) {
                $scope.products.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.newParent = function () {
            ModalProvider.openParentCreateModel().result.then(function (data) {
                $scope.parents.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * ProductPurchase                                                                                            *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.productPurchases = [];
        $scope.paramProductPurchase = {};
        $scope.productPurchases.checkAll = false;

        $scope.pageProductPurchase = {};
        $scope.pageProductPurchase.sorts = [];
        $scope.pageProductPurchase.page = 0;
        $scope.pageProductPurchase.totalPages = 0;
        $scope.pageProductPurchase.currentPage = $scope.pageProductPurchase.page + 1;
        $scope.pageProductPurchase.currentPageString = ($scope.pageProductPurchase.page + 1) + ' / ' + $scope.pageProductPurchase.totalPages;
        $scope.pageProductPurchase.size = 25;
        $scope.pageProductPurchase.first = true;
        $scope.pageProductPurchase.last = true;

        $scope.openProductPurchasesFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/productPurchase/productPurchaseFilter.html',
                controller: 'productPurchaseFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramProductPurchase) {
                $scope.paramProductPurchase = paramProductPurchase;
                $scope.searchProductPurchases(paramProductPurchase);
            }, function () {
            });
        };
        $scope.searchProductPurchases = function (paramProductPurchase) {
            var search = [];
            search.push('size=');
            search.push($scope.pageProductPurchase.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageProductPurchase.page);
            search.push('&');
            angular.forEach($scope.pageProductPurchase.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageProductPurchase.sorts.length === 0) {
                search.push('sort=date,desc&');
            }
            //ProductPurchase Filters
            if (paramProductPurchase.codeFrom) {
                search.push('codeFrom=');
                search.push(paramProductPurchase.codeFrom);
                search.push('&');
            }
            if (paramProductPurchase.codeTo) {
                search.push('codeTo=');
                search.push(paramProductPurchase.codeTo);
                search.push('&');
            }
            if (paramProductPurchase.dateTo) {
                search.push('dateTo=');
                search.push(paramProductPurchase.dateTo.getTime());
                search.push('&');
            }
            if (paramProductPurchase.dateFrom) {
                search.push('dateFrom=');
                search.push(paramProductPurchase.dateFrom.getTime());
                search.push('&');
            }
            //Product Filters
            if (paramProductPurchase.productCodeFrom) {
                search.push('productCodeFrom=');
                search.push(paramProductPurchase.productCodeFrom);
                search.push('&');
            }
            if (paramProductPurchase.productCodeTo) {
                search.push('productCodeTo=');
                search.push(paramProductPurchase.productCodeTo);
                search.push('&');
            }
            if (paramProductPurchase.productRegisterDateTo) {
                search.push('productRegisterDateTo=');
                search.push(paramProductPurchase.productRegisterDateTo.getTime());
                search.push('&');
            }
            if (paramProductPurchase.productRegisterDateFrom) {
                search.push('productRegisterDateFrom=');
                search.push(paramProductPurchase.productRegisterDateFrom.getTime());
                search.push('&');
            }
            if (paramProductPurchase.productName) {
                search.push('productName=');
                search.push(paramProductPurchase.productName);
                search.push('&');
            }
            //Seller Filters
            if (paramProductPurchase.sellerName) {
                search.push('sellerName=');
                search.push(paramProductPurchase.sellerName);
                search.push('&');
            }
            if (paramProductPurchase.sellerMobile) {
                search.push('sellerMobile=');
                search.push(paramProductPurchase.sellerMobile);
                search.push('&');
            }
            if (paramProductPurchase.sellerIdentityNumber) {
                search.push('sellerIdentityNumber=');
                search.push(paramProductPurchase.sellerIdentityNumber);
                search.push('&');
            }

            ProductPurchaseService.filter(search.join("")).then(function (data) {
                $scope.productPurchases = data.content;

                $scope.pageProductPurchase.currentPage = $scope.pageProductPurchase.page + 1;
                $scope.pageProductPurchase.first = data.first;
                $scope.pageProductPurchase.last = data.last;
                $scope.pageProductPurchase.number = data.number;
                $scope.pageProductPurchase.numberOfElements = data.numberOfElements;
                $scope.pageProductPurchase.size = data.size;
                $scope.pageProductPurchase.totalElements = data.totalElements;
                $scope.pageProductPurchase.totalPages = data.totalPages;
                $scope.pageProductPurchase.currentPageString = ($scope.pageProductPurchase.page + 1) + ' / ' + $scope.pageProductPurchase.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextProductPurchasesPage = function () {
            $scope.pageProductPurchase.page++;
            $scope.searchProductPurchases($scope.paramProductPurchase);
        };
        $scope.selectPrevProductPurchasesPage = function () {
            $scope.pageProductPurchase.page--;
            $scope.searchProductPurchases($scope.paramProductPurchase);
        };
        $scope.newProductPurchase = function () {
            ModalProvider.openProductPurchaseCreateModel().result.then(function (data) {
                // $scope.productPurchases.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Contract                                                                                                   *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.contracts = [];
        $scope.paramContract = {};
        $scope.contracts.checkAll = false;

        $scope.pageContract = {};
        $scope.pageContract.sorts = [];
        $scope.pageContract.page = 0;
        $scope.pageContract.totalPages = 0;
        $scope.pageContract.currentPage = $scope.pageContract.page + 1;
        $scope.pageContract.currentPageString = ($scope.pageContract.page + 1) + ' / ' + $scope.pageContract.totalPages;
        $scope.pageContract.size = 25;
        $scope.pageContract.first = true;
        $scope.pageContract.last = true;

        $scope.openContractsFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/contract/contractFilter.html',
                controller: 'contractFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramContract) {
                $scope.paramContract = paramContract;
                $scope.searchContracts(paramContract);
            }, function () {
            });
        };
        $scope.searchContracts = function (paramContract) {
            var search = [];
            search.push('size=');
            search.push($scope.pageContract.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageContract.page);
            search.push('&');
            angular.forEach($scope.pageContract.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageContract.sorts.length === 0) {
                search.push('sort=writtenDate,desc&');
            }
            //Contract Filters
            if (paramContract.codeFrom) {
                search.push('codeFrom=');
                search.push(paramContract.codeFrom);
                search.push('&');
            }
            if (paramContract.codeTo) {
                search.push('codeTo=');
                search.push(paramContract.codeTo);
                search.push('&');
            }
            if (paramContract.writtenDateTo) {
                search.push('writtenDateTo=');
                search.push(paramContract.writtenDateTo.getTime());
                search.push('&');
            }
            if (paramContract.writtenDateFrom) {
                search.push('writtenDateFrom=');
                search.push(paramContract.writtenDateFrom.getTime());
                search.push('&');
            }
            //Customer Filters
            if (paramContract.customerCodeFrom) {
                search.push('customerCodeFrom=');
                search.push(paramContract.customerCodeFrom);
                search.push('&');
            }
            if (paramContract.customerCodeTo) {
                search.push('customerCodeTo=');
                search.push(paramContract.customerCodeTo);
                search.push('&');
            }
            if (paramContract.customerRegisterDateTo) {
                search.push('customerRegisterDateTo=');
                search.push(paramContract.customerRegisterDateTo.getTime());
                search.push('&');
            }
            if (paramContract.customerRegisterDateFrom) {
                search.push('customerRegisterDateFrom=');
                search.push(paramContract.customerRegisterDateFrom.getTime());
                search.push('&');
            }
            if (paramContract.customerName) {
                search.push('customerName=');
                search.push(paramContract.customerName.getTime());
                search.push('&');
            }
            if (paramContract.customerMobile) {
                search.push('customerMobile=');
                search.push(paramContract.customerMobile.getTime());
                search.push('&');
            }
            //Seller Filters
            if (paramContract.sellerCodeFrom) {
                search.push('sellerCodeFrom=');
                search.push(paramContract.sellerCodeFrom);
                search.push('&');
            }
            if (paramContract.sellerCodeTo) {
                search.push('sellerCodeTo=');
                search.push(paramContract.sellerCodeTo);
                search.push('&');
            }
            if (paramContract.sellerRegisterDateTo) {
                search.push('sellerRegisterDateTo=');
                search.push(paramContract.sellerRegisterDateTo.getTime());
                search.push('&');
            }
            if (paramContract.sellerRegisterDateFrom) {
                search.push('sellerRegisterDateFrom=');
                search.push(paramContract.sellerRegisterDateFrom.getTime());
                search.push('&');
            }
            if (paramContract.sellerName) {
                search.push('sellerName=');
                search.push(paramContract.sellerName.getTime());
                search.push('&');
            }
            if (paramContract.sellerMobile) {
                search.push('sellerMobile=');
                search.push(paramContract.sellerMobile.getTime());
                search.push('&');
            }
            ContractService.filter(search.join("")).then(function (data) {
                $scope.contracts = data.content;

                $scope.pageContract.currentPage = $scope.pageContract.page + 1;
                $scope.pageContract.first = data.first;
                $scope.pageContract.last = data.last;
                $scope.pageContract.number = data.number;
                $scope.pageContract.numberOfElements = data.numberOfElements;
                $scope.pageContract.size = data.size;
                $scope.pageContract.totalElements = data.totalElements;
                $scope.pageContract.totalPages = data.totalPages;
                $scope.pageContract.currentPageString = ($scope.pageContract.page + 1) + ' / ' + $scope.pageContract.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextContractsPage = function () {
            $scope.pageContract.page++;
            $scope.searchContracts($scope.paramContract);
        };
        $scope.selectPrevContractsPage = function () {
            $scope.pageContract.page--;
            $scope.searchContracts($scope.paramContract);
        };
        $scope.checkAllContracts = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('#checkAllContracts input').is(":checked")) {
                    element.MaterialCheckbox.check();
                }
                else {
                    element.MaterialCheckbox.uncheck();
                }
            }
            angular.forEach($scope.contracts, function (contract) {
                contract.isSelected = $scope.contracts.checkAll;
            });
        };
        $scope.checkContract = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('.check input:checked').length == $('.check input').length) {
                    document.querySelector('#checkAllContracts').MaterialCheckbox.check();
                } else {
                    document.querySelector('#checkAllContracts').MaterialCheckbox.uncheck();
                }
            }
        };
        $scope.newContract = function () {
            ModalProvider.openContractCreateModel().result.then(function (data) {
                $scope.contracts.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.newOldContract = function () {
            ModalProvider.openContractOldCreateModel().result.then(function (data) {
                $scope.contracts.splice(0, 0, data);
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.deleteContract = function (contract) {
            ModalProvider.openConfirmModel("العقود", "delete", "هل تود حذف العقد فعلاً؟").result.then(function (value) {
                if (value) {
                    ContractService.remove(contract.id).then(function () {
                        var index = $scope.contracts.indexOf(contract);
                        $scope.contracts.splice(index, 1);
                    });
                }
            });
        };
        $scope.newContractProduct = function (contract) {
            ModalProvider.openContractProductCreateModel(contract).result.then(function (data) {
                if (!contract.contractProducts) {
                    contract.contractProducts = [];
                }
                return contract.contractProducts.splice(0, 0, data);
            });
        };
        $scope.newContractPremium = function (contract) {
            ModalProvider.openContractPremiumCreateModel(contract).result.then(function (data) {
                if (!contract.contractPremiums) {
                    contract.contractPremiums = [];
                }
                return contract.contractPremiums.splice(0, 0, data);
            });
        };
        $scope.newContractPayment = function (contract) {
            ModalProvider.openContractPaymentCreateModel(contract).result.then(function (data) {
                if (!contract.contractPayments) {
                    contract.contractPayments = [];
                }
                return contract.contractPayments.splice(0, 0, data);
            });
        };
        $scope.rowMenuContract = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newContract();
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_DELETE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.deleteContract($itemScope.contract);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/edit.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تعديل...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_UPDATE']);
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openContractUpdateModel($itemScope.contract);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/product.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>اضافة سلعة...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_PRODUCT_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newContractProduct($itemScope.contract);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/contractPremium.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>اضافة قسط...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_PREMIUM_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newContractPremium($itemScope.contract);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/bankTransaction.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تسديد دفعة مالية...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_PAYMENT_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newContractPayment($itemScope.contract);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/about.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>التفاصيل...</span>' +
                '</div>',
                enabled: function () {
                    return true;
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openContractDetailsModel($itemScope.contract);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * ContractPremium                                                                                            *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.contractPremiums = [];
        $scope.paramContractPremium = {};
        $scope.contractPremiums.checkAll = false;

        $scope.pageContractPremium = {};
        $scope.pageContractPremium.sorts = [];
        $scope.pageContractPremium.page = 0;
        $scope.pageContractPremium.totalPages = 0;
        $scope.pageContractPremium.currentPage = $scope.pageContractPremium.page + 1;
        $scope.pageContractPremium.currentPageString = ($scope.pageContractPremium.page + 1) + ' / ' + $scope.pageContractPremium.totalPages;
        $scope.pageContractPremium.size = 25;
        $scope.pageContractPremium.first = true;
        $scope.pageContractPremium.last = true;

        $scope.openContractPremiumsFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/contractPremium/contractPremiumFilter.html',
                controller: 'contractPremiumFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramContractPremium) {
                $scope.paramContractPremium = paramContractPremium;
                $scope.searchContractPremiums(paramContractPremium);
            }, function () {
            });
        };
        $scope.searchContractPremiums = function (paramContractPremium) {
            var search = [];
            search.push('size=');
            search.push($scope.pageContractPremium.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageContractPremium.page);
            search.push('&');
            angular.forEach($scope.pageContractPremium.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageContractPremium.sorts.length === 0) {
                search.push('sort=dueDate,desc&');
            }
            if (paramContractPremium.dueDateFrom) {
                search.push('dueDateFrom=');
                search.push(paramContractPremium.dueDateFrom.getTime());
                search.push('&');
            }
            if (paramContractPremium.dueDateTo) {
                search.push('dueDateTo=');
                search.push(paramContractPremium.dueDateTo.getTime());
                search.push('&');
            }
            if (paramContractPremium.contractCodeFrom) {
                search.push('contractCodeFrom=');
                search.push(paramContractPremium.contractCodeFrom);
                search.push('&');
            }
            if (paramContractPremium.contractCodeTo) {
                search.push('contractCodeTo=');
                search.push(paramContractPremium.contractCodeTo);
                search.push('&');
            }
            if (paramContractPremium.contractDateFrom) {
                search.push('contractDateFrom=');
                search.push(paramContractPremium.contractDateFrom.getTime());
                search.push('&');
            }
            if (paramContractPremium.contractDateTo) {
                search.push('contractDateTo=');
                search.push(paramContractPremium.contractDateTo.getTime());
                search.push('&');
            }
            if (paramContractPremium.customerName) {
                search.push('customerName=');
                search.push(paramContractPremium.customerName);
                search.push('&');
            }
            if (paramContractPremium.customerMobile) {
                search.push('customerMobile=');
                search.push(paramContractPremium.customerMobile);
                search.push('&');
            }
            if (paramContractPremium.sellerName) {
                search.push('sellerName=');
                search.push(paramContractPremium.sellerName);
                search.push('&');
            }
            if (paramContractPremium.sellerMobile) {
                search.push('sellerMobile=');
                search.push(paramContractPremium.sellerMobile);
                search.push('&');
            }

            search.push('filterCompareType=or');

            ContractPremiumService.filter(search.join("")).then(function (data) {
                $scope.contractPremiums = data.content;

                $scope.pageContractPremium.currentPage = $scope.pageContractPremium.page + 1;
                $scope.pageContractPremium.first = data.first;
                $scope.pageContractPremium.last = data.last;
                $scope.pageContractPremium.number = data.number;
                $scope.pageContractPremium.numberOfElements = data.numberOfElements;
                $scope.pageContractPremium.size = data.size;
                $scope.pageContractPremium.totalElements = data.totalElements;
                $scope.pageContractPremium.totalPages = data.totalPages;
                $scope.pageContractPremium.currentPageString = ($scope.pageContractPremium.page + 1) + ' / ' + $scope.pageContractPremium.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextContractPremiumsPage = function () {
            $scope.pageContractPremium.page++;
            $scope.searchContractPremiums($scope.paramContractPremium);
        };
        $scope.selectPrevContractPremiumsPage = function () {
            $scope.pageContractPremium.page--;
            $scope.searchContractPremiums($scope.paramContractPremium);
        };
        $scope.checkAllContractPremiums = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('#checkAllContractPremiums input').is(":checked")) {
                    element.MaterialCheckbox.check();
                }
                else {
                    element.MaterialCheckbox.uncheck();
                }
            }
            angular.forEach($scope.contractPremiums, function (contractPremium) {
                contractPremium.isSelected = $scope.contractPremiums.checkAll;
            });
        };
        $scope.checkContractPremium = function () {
            var elements = document.querySelectorAll('.check');
            for (var i = 0, n = elements.length; i < n; i++) {
                var element = elements[i];
                if ($('.check input:checked').length == $('.check input').length) {
                    document.querySelector('#checkAllContractPremiums').MaterialCheckbox.check();
                } else {
                    document.querySelector('#checkAllContractPremiums').MaterialCheckbox.uncheck();
                }
            }
        };
        $scope.rowMenuContractPremium = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_PREMIUM_CREATE']);
                },
                click: function ($itemScope, $event, value) {

                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_CONTRACT_PREMIUM_DELETE']);
                },
                click: function ($itemScope, $event, value) {

                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/send.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>إرسال رسالة...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_SMS_SEND']);
                },
                click: function ($itemScope, $event, value) {
                    var contractPremiums = [];
                    angular.forEach($scope.contractPremiums, function (contractPremium) {
                        if (contractPremium.isSelected) {
                            contractPremiums.push(contractPremium);
                        }
                    });
                    if (contractPremiums.length === 0) {
                        ModalProvider.openConfirmModel('إرسال رسائل الأقساط', 'send', 'فضلا قم باختيار قسط واحد على الأقل');
                        return;
                    }
                    ModalProvider.openContractPremiumSendMessageModel(contractPremiums);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * ContractPayment                                                                                            *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.contractPayments = [];
        $scope.paramContractPayment = {};
        $scope.contractPayments.checkAll = false;

        $scope.pageContractPayment = {};
        $scope.pageContractPayment.sorts = [];
        $scope.pageContractPayment.page = 0;
        $scope.pageContractPayment.totalPages = 0;
        $scope.pageContractPayment.currentPage = $scope.pageContractPayment.page + 1;
        $scope.pageContractPayment.currentPageString = ($scope.pageContractPayment.page + 1) + ' / ' + $scope.pageContractPayment.totalPages;
        $scope.pageContractPayment.size = 25;
        $scope.pageContractPayment.first = true;
        $scope.pageContractPayment.last = true;

        $scope.openContractPaymentsFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/contractPayment/contractPaymentFilter.html',
                controller: 'contractPaymentFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramContractPayment) {
                $scope.paramContractPayment = paramContractPayment;
                $scope.searchContractPayments(paramContractPayment);
            }, function () {
            });
        };
        $scope.searchContractPayments = function (paramContractPayment) {
            var search = [];
            search.push('size=');
            search.push($scope.pageContractPayment.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageContractPayment.page);
            search.push('&');
            angular.forEach($scope.pageContractPayment.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageContractPayment.sorts.length === 0) {
                search.push('sort=date,desc&');
            }
            if (paramContractPayment.dateFrom) {
                search.push('dateFrom=');
                search.push(paramContractPayment.dateFrom.getTime());
                search.push('&');
            }
            if (paramContractPayment.dateTo) {
                search.push('dateTo=');
                search.push(paramContractPayment.dateTo.getTime());
                search.push('&');
            }
            if (paramContractPayment.contractCodeFrom) {
                search.push('contractCodeFrom=');
                search.push(paramContractPayment.contractCodeFrom);
                search.push('&');
            }
            if (paramContractPayment.contractCodeTo) {
                search.push('contractCodeTo=');
                search.push(paramContractPayment.contractCodeTo);
                search.push('&');
            }
            if (paramContractPayment.contractDateFrom) {
                search.push('contractDateFrom=');
                search.push(paramContractPayment.contractDateFrom.getTime());
                search.push('&');
            }
            if (paramContractPayment.contractDateTo) {
                search.push('contractDateTo=');
                search.push(paramContractPayment.contractDateTo.getTime());
                search.push('&');
            }
            if (paramContractPayment.customerName) {
                search.push('customerName=');
                search.push(paramContractPayment.customerName);
                search.push('&');
            }
            if (paramContractPayment.customerMobile) {
                search.push('customerMobile=');
                search.push(paramContractPayment.customerMobile);
                search.push('&');
            }
            if (paramContractPayment.sellerName) {
                search.push('sellerName=');
                search.push(paramContractPayment.sellerName);
                search.push('&');
            }
            if (paramContractPayment.sellerMobile) {
                search.push('sellerMobile=');
                search.push(paramContractPayment.sellerMobile);
                search.push('&');
            }

            search.push('filterCompareType=or');

            ContractPaymentService.filter(search.join("")).then(function (data) {
                $scope.contractPayments = data.content;

                $scope.pageContractPayment.currentPage = $scope.pageContractPayment.page + 1;
                $scope.pageContractPayment.first = data.first;
                $scope.pageContractPayment.last = data.last;
                $scope.pageContractPayment.number = data.number;
                $scope.pageContractPayment.numberOfElements = data.numberOfElements;
                $scope.pageContractPayment.size = data.size;
                $scope.pageContractPayment.totalElements = data.totalElements;
                $scope.pageContractPayment.totalPages = data.totalPages;
                $scope.pageContractPayment.currentPageString = ($scope.pageContractPayment.page + 1) + ' / ' + $scope.pageContractPayment.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextContractPaymentsPage = function () {
            $scope.pageContractPayment.page++;
            $scope.searchContractPayments($scope.paramContractPayment);
        };
        $scope.selectPrevContractPaymentsPage = function () {
            $scope.pageContractPayment.page--;
            $scope.searchContractPayments($scope.paramContractPayment);
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * BankTransaction                                                                                            *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.bankTransactions = [];
        $scope.paramBankTransaction = {};
        $scope.paramBankTransaction.transactionTypeCodes = [];

        $scope.pageBankTransaction = {};
        $scope.pageBankTransaction.sorts = [];
        $scope.pageBankTransaction.page = 0;
        $scope.pageBankTransaction.totalPages = 0;
        $scope.pageBankTransaction.currentPage = $scope.pageBankTransaction.page + 1;
        $scope.pageBankTransaction.currentPageString = ($scope.pageBankTransaction.page + 1) + ' / ' + $scope.pageBankTransaction.totalPages;
        $scope.pageBankTransaction.size = 25;
        $scope.pageBankTransaction.first = true;
        $scope.pageBankTransaction.last = true;

        $scope.openBankTransactionsFilter = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: '/ui/partials/bankTransaction/bankTransactionFilter.html',
                controller: 'bankTransactionFilterCtrl',
                scope: $scope,
                backdrop: 'static',
                keyboard: false
            });

            modalInstance.result.then(function (paramBankTransaction) {
                $scope.paramBankTransaction = paramBankTransaction;
                $scope.searchBankTransactions(paramBankTransaction);
            }, function () {
            });
        };
        $scope.searchBankTransactions = function (paramBankTransaction) {
            var search = [];
            search.push('size=');
            search.push($scope.pageBankTransaction.size);
            search.push('&');
            search.push('page=');
            search.push($scope.pageBankTransaction.page);
            search.push('&');
            angular.forEach($scope.pageBankTransaction.sorts, function (sortBy) {
                search.push('sort=');
                search.push(sortBy.name + ',' + sortBy.direction);
                search.push('&');
            });
            if ($scope.pageBankTransaction.sorts.length === 0) {
                search.push('sort=date,desc&');
            }
            if (paramBankTransaction.codeFrom) {
                search.push('codeFrom=');
                search.push(paramBankTransaction.codeFrom);
                search.push('&');
            }
            if (paramBankTransaction.codeTo) {
                search.push('codeTo=');
                search.push(paramBankTransaction.codeTo);
                search.push('&');
            }
            if (paramBankTransaction.dateTo) {
                console.info(paramBankTransaction.dateTo);
                search.push('dateTo=');
                search.push(paramBankTransaction.dateTo.getTime());
                search.push('&');
            }
            if (paramBankTransaction.dateFrom) {
                search.push('dateFrom=');
                search.push(paramBankTransaction.dateFrom.getTime());
                search.push('&');
            }
            if (paramBankTransaction.sellerName) {
                search.push('sellerName=');
                search.push(paramBankTransaction.sellerName);
                search.push('&');
            }
            if (paramBankTransaction.sellerMobile) {
                search.push('sellerMobile=');
                search.push(paramBankTransaction.sellerMobile);
                search.push('&');
            }
            if (paramBankTransaction.sellerIdentityNumber) {
                search.push('sellerIdentityNumber=');
                search.push(paramBankTransaction.sellerIdentityNumber);
                search.push('&');
            }
            if (paramBankTransaction.transactionTypeCode) {
                search.push('transactionTypeCodes=');
                search.push([paramBankTransaction.transactionTypeCode]);
                search.push('&');
            }

            BankTransactionService.filter(search.join("")).then(function (data) {
                $scope.bankTransactions = data.content;

                $scope.pageBankTransaction.currentPage = $scope.pageBankTransaction.page + 1;
                $scope.pageBankTransaction.first = data.first;
                $scope.pageBankTransaction.last = data.last;
                $scope.pageBankTransaction.number = data.number;
                $scope.pageBankTransaction.numberOfElements = data.numberOfElements;
                $scope.pageBankTransaction.size = data.size;
                $scope.pageBankTransaction.totalElements = data.totalElements;
                $scope.pageBankTransaction.totalPages = data.totalPages;
                $scope.pageBankTransaction.currentPageString = ($scope.pageBankTransaction.page + 1) + ' / ' + $scope.pageBankTransaction.totalPages;

                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.selectNextBankTransactionsPage = function () {
            $scope.pageBankTransaction.page++;
            $scope.searchBankTransactions($scope.paramBankTransaction);
        };
        $scope.selectPrevBankTransactionsPage = function () {
            $scope.pageBankTransaction.page--;
            $scope.searchBankTransactions($scope.paramBankTransaction);
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Person                                                                                                     *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.persons = [];
        $scope.fetchPersonTableData = function () {
            PersonService.findAll().then(function (data) {
                $scope.persons = data;
            });
        };
        $scope.newPerson = function () {
            ModalProvider.openPersonCreateModel().result.then(function (data) {
                return $scope.persons.splice(0, 0, data);
            });
        };
        $scope.deletePerson = function (person) {
            ModalProvider.openConfirmModel("المستخدمين", "delete", "هل تود حذف المستخدم فعلاً؟").result.then(function (value) {
                if (value) {
                    PersonService.remove(person.id).then(function () {
                        var index = $scope.persons.indexOf(person);
                        $scope.persons.splice(index, 1);
                    });
                }
            });
        };
        $scope.rowMenuPerson = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_PERSON_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newPerson();
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/edit.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تعديل...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_PERSON_UPDATE']);
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openPersonUpdateModel($itemScope.person);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_PERSON_DELETE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.deletePerson($itemScope.person);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * Team                                                                                                       *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.fetchTeamTableData = function () {
            TeamService.findAll().then(function (data) {
                $scope.teams = data;
            });
        };
        $scope.newTeam = function () {
            ModalProvider.openTeamCreateModel().result.then(function (data) {
                return $scope.teams.splice(0, 0, data);
            });
        };
        $scope.deleteTeam = function (team) {
            ModalProvider.openConfirmModel("الصلاحيات", "delete", "هل تود حذف المجموعة فعلاً؟").result.then(function (value) {
                if (value) {
                    TeamService.remove(team.id).then(function () {
                        var index = $scope.teams.indexOf(team);
                        $scope.teams.splice(index, 1);
                    });
                }
            });
        };
        $scope.rowMenuTeam = [
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/add.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>جديد...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_TEAM_CREATE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.newTeam();
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/edit.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>تعديل...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_TEAM_UPDATE']);
                },
                click: function ($itemScope, $event, value) {
                    ModalProvider.openTeamUpdateModel($itemScope.team);
                }
            },
            {
                html: '<div class="drop-menu">' +
                '<img src="/ui/img/' + $rootScope.iconSet + '/delete.' + $rootScope.iconSetType + '" width="24" height="24">' +
                '<span>حذف...</span>' +
                '</div>',
                enabled: function () {
                    return $rootScope.contains($rootScope.me.team.authorities, ['ROLE_TEAM_DELETE']);
                },
                click: function ($itemScope, $event, value) {
                    $scope.deleteTeam($itemScope.team);
                }
            }
        ];

        /**************************************************************************************************************
         *                                                                                                            *
         * Profile                                                                                                    *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.submitProfile = function () {
            PersonService.updateProfile($rootScope.me).then(function (data) {
                $rootScope.me = data;
            });
        };
        $scope.browseProfilePhoto = function () {
            document.getElementById('uploader-profile').click();
        };
        $scope.uploadProfilePhoto = function (files) {
            PersonService.uploadContactPhoto(files[0]).then(function (data) {
                $rootScope.me.logo = data;
                PersonService.update($rootScope.me).then(function (data) {
                    $rootScope.me = data;
                });
            });
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Print                                                                                                      *
         *                                                                                                            *
         **************************************************************************************************************/
        $rootScope.printToCart = function (printSectionId, title) {
            var innerContents = document.getElementById(printSectionId).innerHTML;
            var mywindow = window.open(title, '_blank', 'height=400,width=600');
            mywindow.document.write('<html><head><title></title>');
            mywindow.document.write('<link rel="stylesheet" href="/ui/app.css" type="text/css" />');
            mywindow.document.write('<link rel="stylesheet" href="/ui/css/style.css" type="text/css" />');
            mywindow.document.write('</head><body >');
            mywindow.document.write(innerContents);
            mywindow.document.write('</body></html>');
            mywindow.document.close();
            mywindow.focus();
            $timeout(function () {
                mywindow.print();
                mywindow.close();
            }, 1000);
            return true;
        };

        /**************************************************************************************************************
         *                                                                                                            *
         * Report                                                                                                     *
         *                                                                                                            *
         **************************************************************************************************************/
        $scope.toggleReport = 'mainReportFrame';
        //السيولة النقدية
        $scope.openReportCashBalance = function () {
            $scope.toggleReport = 'cashBalance';
            $rootScope.refreshGUI();
        };
        //تقرير المصروفات
        $scope.openReportWithdrawCash = function () {
            $scope.toggleReport = 'withdrawCash';
            $rootScope.refreshGUI();
        };
        //أرصدة المستثمرين
        $scope.openReportSellerBalance = function () {
            $scope.toggleReport = 'sellerBalance';
            $rootScope.refreshGUI();
        };
        //تقرير العقود
        $scope.openReportContracts = function () {
            $scope.toggleReport = 'contracts';
            $rootScope.refreshGUI();
        };
        //حركة العمليات اليومية
        $scope.openReportBankTransactions = function () {
            $scope.toggleReport = 'bankTransactions';
            $rootScope.refreshGUI();
        };
        //كشف حساب مستثمر
        $scope.openReportSellerStatement = function () {
            $scope.toggleReport = 'sellerStatement';
            $rootScope.refreshGUI();
        };
        //كشف حساب عميل
        $scope.openReportCustomerStatement = function () {
            $scope.toggleReport = 'customerStatement';
            $rootScope.refreshGUI();
        };
        //تقرير التحصيل والسداد
        $scope.openReportContractPremiums = function () {
            $scope.toggleReport = 'contractPremiums';
            $rootScope.refreshGUI();
        };
        //تقرير أرباح التحصيل
        $scope.openReportContractPaymentsProfit = function () {
            $scope.toggleReport = 'contractPaymentsProfit';
            $rootScope.refreshGUI();
        };

        $scope.paramWithdrawCash = {};
        $scope.sellerStatement = {};
        $scope.customerStatement = {};
        $scope.contractPaymentProfit = {};
        $scope.fetchAllBanks = function () {
            BankService.findAll().then(function (value) {
                $scope.banks = value;
            });
        };
        $scope.fetchWithdrawCashes = function () {
            var search = [];
            if ($scope.paramWithdrawCash.dateTo) {
                search.push('dateTo=');
                search.push($scope.paramWithdrawCash.dateTo.getTime());
                search.push('&');
            }
            if ($scope.paramWithdrawCash.dateFrom) {
                search.push('dateFrom=');
                search.push($scope.paramWithdrawCash.dateFrom.getTime());
                search.push('&');
            }

            search.push('transactionTypeCodes=');
            search.push(['Withdraw_Cash']);

            BankTransactionService.findByDateBetweenOrTransactionTypeCodeIn(search.join("")).then(function (data) {
                $scope.withdrawCashes = data;
                $timeout(function () {
                    window.componentHandler.upgradeAllRegistered();
                }, 300);
            });
        };
        $scope.fetchAllSellerBalance = function () {
            SellerService.findAllSellerBalance().then(function (value) {
                $scope.sellersBalance = value;
            })
        };
        $scope.fetchAllSellerCombo = function () {
            SellerService.findAllCombo().then(function (value) {
                $scope.sellersCombo = value;
            });
        };
        $scope.fetchAllCustomerCombo = function () {
            CustomerService.findAllCombo().then(function (value) {
                $scope.customersCombo = value;
            });
        };
        $scope.fetchSellerStatementContracts = function () {
            ContractService.findBySeller($scope.sellerStatement.seller.id).then(function (value) {
                $scope.sellerStatement.contracts = value;
            });
        };
        $scope.fetchSellerStatementBanks = function () {
            BankService.findBySeller($scope.sellerStatement.seller.id).then(function (value) {
                $scope.sellerStatement.banks = value;
            });
        };
        $scope.fetchContractPaymentsProfit = function () {
            ContractPaymentService.findByDateBetween(
                $scope.contractPaymentProfit.dateFrom.getTime(),
                $scope.contractPaymentProfit.dateTo.getTime()
            )
                .then(function (value) {
                    $scope.contractPaymentProfit.contractPayments = value;
                });
        };
        $scope.findCustomerDetails = function () {
            CustomerService.findOne($scope.customerStatement.customer.id).then(function (value) {
                return $scope.customerStatement.customer = value;
            });
        };

        $timeout(function () {
            CompanyService.get().then(function (data) {
                $rootScope.selectedCompany = data;
            });
            PersonService.findAllCombo().then(function (data) {
                $scope.personsCombo = data;
            });
            AttachTypeService.findAll().then(function (data) {
                $scope.attachTypes = data;
            });
            window.componentHandler.upgradeAllRegistered();
        }, 800);

    }]);