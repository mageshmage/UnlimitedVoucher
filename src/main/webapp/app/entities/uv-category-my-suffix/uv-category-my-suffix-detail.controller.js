(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .controller('UvCategoryMySuffixDetailController', UvCategoryMySuffixDetailController);

    UvCategoryMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UvCategory'];

    function UvCategoryMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, UvCategory) {
        var vm = this;

        vm.uvCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unlimitedVoucherApp:uvCategoryUpdate', function(event, result) {
            vm.uvCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
