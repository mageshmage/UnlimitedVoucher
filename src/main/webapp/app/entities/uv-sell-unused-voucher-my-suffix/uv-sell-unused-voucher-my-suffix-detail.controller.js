(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherMySuffixDetailController', UvSellUnusedVoucherMySuffixDetailController);

    UvSellUnusedVoucherMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UvSellUnusedVoucher', 'UvBrand', 'UvCategory', 'UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, UvSellUnusedVoucher, UvBrand, UvCategory, UvSellUnusedVoucherUser) {
        var vm = this;

        vm.uvSellUnusedVoucher = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unlimitedVoucherApp:uvSellUnusedVoucherUpdate', function(event, result) {
            vm.uvSellUnusedVoucher = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
