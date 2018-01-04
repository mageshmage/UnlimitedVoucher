(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvFreeVoucherMySuffixDeleteController',UvFreeVoucherMySuffixDeleteController);

    UvFreeVoucherMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'UvFreeVoucher'];

    function UvFreeVoucherMySuffixDeleteController($uibModalInstance, entity, UvFreeVoucher) {
        var vm = this;

        vm.uvFreeVoucher = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UvFreeVoucher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
