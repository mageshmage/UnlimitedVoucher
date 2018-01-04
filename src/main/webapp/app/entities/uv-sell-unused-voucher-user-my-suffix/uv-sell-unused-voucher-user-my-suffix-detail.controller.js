(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvSellUnusedVoucherUserMySuffixDetailController', UvSellUnusedVoucherUserMySuffixDetailController);

    UvSellUnusedVoucherUserMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UvSellUnusedVoucherUser'];

    function UvSellUnusedVoucherUserMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, UvSellUnusedVoucherUser) {
        var vm = this;

        vm.uvSellUnusedVoucherUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unlimitedVoucherApp:uvSellUnusedVoucherUserUpdate', function(event, result) {
            vm.uvSellUnusedVoucherUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
