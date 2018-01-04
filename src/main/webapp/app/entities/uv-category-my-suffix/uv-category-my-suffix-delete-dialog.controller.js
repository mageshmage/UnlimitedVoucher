(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvCategoryMySuffixDeleteController',UvCategoryMySuffixDeleteController);

    UvCategoryMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'UvCategory'];

    function UvCategoryMySuffixDeleteController($uibModalInstance, entity, UvCategory) {
        var vm = this;

        vm.uvCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UvCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
