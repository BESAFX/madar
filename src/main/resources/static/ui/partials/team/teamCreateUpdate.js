app.controller('teamCreateUpdateCtrl', ['TeamService', '$scope', '$rootScope', '$timeout', '$log', '$uibModalInstance', 'title', 'action', 'team',
    function (TeamService, $scope, $rootScope, $timeout, $log, $uibModalInstance, title, action, team) {

        $scope.title = title;

        $scope.action = action;

        $scope.roles = [];

        //////////////////////////Company////////////////////////////////////////
        $scope.roles.push({
            name: 'تعديل بيانات الشركة',
            value: 'ROLE_COMPANY_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'عملية إيداع',
            value: 'ROLE_DEPOSIT_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'عملية سحب',
            value: 'ROLE_WITHDRAW_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'عملية تحويل',
            value: 'ROLE_TRANSFER_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'عملية تسجيل مصروفات',
            value: 'ROLE_WITHDRAW_CASH_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'إرسال الرسائل',
            value: 'ROLE_SMS_SEND',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Customer//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء حسابات العملاء',
            value: 'ROLE_CUSTOMER_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل حسابات العملاء',
            value: 'ROLE_CUSTOMER_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف حسابات العملاء',
            value: 'ROLE_CUSTOMER_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Customer Note//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء ملاحظات العملاء',
            value: 'ROLE_CUSTOMER_NOTE_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل ملاحظات العملاء',
            value: 'ROLE_CUSTOMER_NOTE_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف ملاحظات العملاء',
            value: 'ROLE_CUSTOMER_NOTE_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Seller//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء حسابات المستثمرين',
            value: 'ROLE_SELLER_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل حسابات المستثمرين',
            value: 'ROLE_SELLER_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف حسابات المستثمرين',
            value: 'ROLE_SELLER_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Product//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء التصنيفات',
            value: 'ROLE_PRODUCT_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل التصنيفات',
            value: 'ROLE_PRODUCT_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف التصنيفات',
            value: 'ROLE_PRODUCT_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'شراء سلعة جديدة',
            value: 'ROLE_PRODUCT_PURCHASE_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Contract//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء العقود',
            value: 'ROLE_CONTRACT_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل العقود',
            value: 'ROLE_CONTRACT_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف العقود',
            value: 'ROLE_CONTRACT_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'اضافة سلع للعقود',
            value: 'ROLE_CONTRACT_PRODUCT_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف سلع من العقود',
            value: 'ROLE_CONTRACT_PRODUCT_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'اضافة قسط للعقود',
            value: 'ROLE_CONTRACT_PREMIUM_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف قسط من العقود',
            value: 'ROLE_CONTRACT_PREMIUM_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تسديد دفعة مالية',
            value: 'ROLE_CONTRACT_PAYMENT_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف دفعة مالية',
            value: 'ROLE_CONTRACT_PAYMENT_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Person//////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء حسابات المستخدمين',
            value: 'ROLE_PERSON_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل حسابات المستخدمين',
            value: 'ROLE_PERSON_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف حسابات المستخدمين',
            value: 'ROLE_PERSON_DELETE',
            selected: false,
            category: 'الإدارة'
        });
        //////////////////////////Team////////////////////////////////////////////
        $scope.roles.push({
            name: 'إنشاء الصلاحيات',
            value: 'ROLE_TEAM_CREATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'تعديل الصلاحيات',
            value: 'ROLE_TEAM_UPDATE',
            selected: false,
            category: 'الإدارة'
        });
        $scope.roles.push({
            name: 'حذف الصلاحيات',
            value: 'ROLE_TEAM_DELETE',
            selected: false,
            category: 'الإدارة'
        });


        if (team) {
            $scope.team = team;
            if (typeof team.authorities === 'string') {
                $scope.team.authorities = team.authorities.split(',');
            }
            //
            angular.forEach($scope.team.authorities, function (auth) {
                angular.forEach($scope.roles, function (role) {
                    if (role.value === auth) {
                        return role.selected = true;
                    }
                })
            });
        } else {
            $scope.team = {};
        }

        $scope.submit = function () {
            $scope.team.authorities = [];
            angular.forEach($scope.roles, function (role) {
                if (role.selected) {
                    $scope.team.authorities.push(role.value);
                }
            });
            $scope.team.authorities = $scope.team.authorities.join();
            switch ($scope.action) {
                case 'create' :
                    TeamService.create($scope.team).then(function (data) {
                        $uibModalInstance.close(data);
                    });
                    break;
                case 'update' :
                    TeamService.update($scope.team).then(function (data) {
                        $scope.team = data;
                        $scope.team.authorities = team.authorities.split(',');
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