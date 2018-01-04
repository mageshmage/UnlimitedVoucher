(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherMySuffixDeleteController',UvSellUnusedVoucherMySuffixDeleteController);

    UvSellUnusedVoucherMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'UvSellUnusedVoucher'];

    function UvSellUnusedVoucherMySuffixDeleteController($uibModalInstance, entity, UvSellUnusedVoucher) {
        var vm = this;

        vm.uvSellUnusedVoucher = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UvSellUnusedVoucher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
