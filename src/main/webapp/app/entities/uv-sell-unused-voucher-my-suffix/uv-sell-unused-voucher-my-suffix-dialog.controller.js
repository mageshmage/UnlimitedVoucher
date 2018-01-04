(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherMySuffixDialogController', UvSellUnusedVoucherMySuffixDialogController);

    UvSellUnusedVoucherMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UvSellUnusedVoucher', 'UvBrand', 'UvCategory', 'UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UvSellUnusedVoucher, UvBrand, UvCategory, UvSellUnusedVoucherUser) {
        var vm = this;

        vm.uvSellUnusedVoucher = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.uvbrands = UvBrand.query();
        vm.uvcategories = UvCategory.query();
        vm.uvsellunusedvoucherusers = UvSellUnusedVoucherUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uvSellUnusedVoucher.id !== null) {
                UvSellUnusedVoucher.update(vm.uvSellUnusedVoucher, onSaveSuccess, onSaveError);
            } else {
                UvSellUnusedVoucher.save(vm.uvSellUnusedVoucher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unlimitedVoucherApp:uvSellUnusedVoucherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.lastUpdatedOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
