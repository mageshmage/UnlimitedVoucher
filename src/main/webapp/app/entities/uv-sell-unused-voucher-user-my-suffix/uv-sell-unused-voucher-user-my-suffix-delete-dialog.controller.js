(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherUserMySuffixDeleteController',UvSellUnusedVoucherUserMySuffixDeleteController);

    UvSellUnusedVoucherUserMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherUserMySuffixDeleteController($uibModalInstance, entity, UvSellUnusedVoucherUser) {
        var vm = this;

        vm.uvSellUnusedVoucherUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UvSellUnusedVoucherUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
