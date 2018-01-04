(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvBrandMySuffixDialogController', UvBrandMySuffixDialogController);

    UvBrandMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UvBrand'];

    function UvBrandMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UvBrand) {
        var vm = this;

        vm.uvBrand = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uvBrand.id !== null) {
                UvBrand.update(vm.uvBrand, onSaveSuccess, onSaveError);
            } else {
                UvBrand.save(vm.uvBrand, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unlimitedVoucherApp:uvBrandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
