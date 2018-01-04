(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvBrandMySuffixDetailController', UvBrandMySuffixDetailController);

    UvBrandMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UvBrand'];

    function UvBrandMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, UvBrand) {
        var vm = this;

        vm.uvBrand = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unlimitedVoucherApp:uvBrandUpdate', function(event, result) {
            vm.uvBrand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
