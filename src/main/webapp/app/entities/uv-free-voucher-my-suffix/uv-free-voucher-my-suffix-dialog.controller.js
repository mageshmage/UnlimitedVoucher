(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvFreeVoucherMySuffixDialogController', UvFreeVoucherMySuffixDialogController);

    UvFreeVoucherMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UvFreeVoucher', 'UvBrand', 'UvCategory'];

    function UvFreeVoucherMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UvFreeVoucher, UvBrand, UvCategory) {
        var vm = this;

        vm.uvFreeVoucher = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.uvbrands = UvBrand.query();
        vm.uvcategories = UvCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uvFreeVoucher.id !== null) {
                UvFreeVoucher.update(vm.uvFreeVoucher, onSaveSuccess, onSaveError);
            } else {
                UvFreeVoucher.save(vm.uvFreeVoucher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unlimitedVoucherApp:uvFreeVoucherUpdate', result);
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
