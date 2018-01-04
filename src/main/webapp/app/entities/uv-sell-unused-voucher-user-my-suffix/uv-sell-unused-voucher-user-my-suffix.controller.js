(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherUserMySuffixController', UvSellUnusedVoucherUserMySuffixController);

    UvSellUnusedVoucherUserMySuffixController.$inject = ['UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherUserMySuffixController(UvSellUnusedVoucherUser) {

        var vm = this;

        vm.uvSellUnusedVoucherUsers = [];

        loadAll();

        function loadAll() {
            UvSellUnusedVoucherUser.query(function(result) {
                vm.uvSellUnusedVoucherUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
