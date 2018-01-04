(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvBrandMySuffixDeleteController',UvBrandMySuffixDeleteController);

    UvBrandMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'UvBrand'];

    function UvBrandMySuffixDeleteController($uibModalInstance, entity, UvBrand) {
        var vm = this;

        vm.uvBrand = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UvBrand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
