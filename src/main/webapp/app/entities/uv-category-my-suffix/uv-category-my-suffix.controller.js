(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvCategoryMySuffixController', UvCategoryMySuffixController);

    UvCategoryMySuffixController.$inject = ['UvCategory'];

    function UvCategoryMySuffixController(UvCategory) {

        var vm = this;

        vm.uvCategories = [];

        loadAll();

        function loadAll() {
            UvCategory.query(function(result) {
                vm.uvCategories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
