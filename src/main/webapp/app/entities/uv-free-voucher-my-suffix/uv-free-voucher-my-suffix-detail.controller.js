(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvFreeVoucherMySuffixDetailController', UvFreeVoucherMySuffixDetailController);

    UvFreeVoucherMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UvFreeVoucher', 'UvBrand', 'UvCategory'];

    function UvFreeVoucherMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, UvFreeVoucher, UvBrand, UvCategory) {
        var vm = this;

        vm.uvFreeVoucher = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unlimitedVoucherApp:uvFreeVoucherUpdate', function(event, result) {
            vm.uvFreeVoucher = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
