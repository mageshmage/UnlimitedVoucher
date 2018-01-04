(function() {
    'use strict';
    angular
        .module('unlimitedVoucherApp')
        .factory('UvSellUnusedVoucherUser', UvSellUnusedVoucherUser);

    UvSellUnusedVoucherUser.$inject = ['$resource'];

    function UvSellUnusedVoucherUser ($resource) {
        var resourceUrl =  'api/uv-sell-unused-voucher-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
