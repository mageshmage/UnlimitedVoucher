(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvCategoryMySuffixDialogController', UvCategoryMySuffixDialogController);

    UvCategoryMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UvCategory'];

    function UvCategoryMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UvCategory) {
        var vm = this;

        vm.uvCategory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uvCategory.id !== null) {
                UvCategory.update(vm.uvCategory, onSaveSuccess, onSaveError);
            } else {
                UvCategory.save(vm.uvCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unlimitedVoucherApp:uvCategoryUpdate', result);
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
