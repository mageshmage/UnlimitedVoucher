(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvBrandMySuffixController', UvBrandMySuffixController);

    UvBrandMySuffixController.$inject = ['UvBrand'];

    function UvBrandMySuffixController(UvBrand) {

        var vm = this;

        vm.uvBrands = [];

        loadAll();

        function loadAll() {
            UvBrand.query(function(result) {
                vm.uvBrands = result;
                vm.searchQuery = null;
            });
        }
    }
})();
