(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherUserMySuffixDialogController', UvSellUnusedVoucherUserMySuffixDialogController);

    UvSellUnusedVoucherUserMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherUserMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UvSellUnusedVoucherUser) {
        var vm = this;

        vm.uvSellUnusedVoucherUser = entity;
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
            if (vm.uvSellUnusedVoucherUser.id !== null) {
                UvSellUnusedVoucherUser.update(vm.uvSellUnusedVoucherUser, onSaveSuccess, onSaveError);
            } else {
                UvSellUnusedVoucherUser.save(vm.uvSellUnusedVoucherUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unlimitedVoucherApp:uvSellUnusedVoucherUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
