(function() {
    'use strict';
    angular
        .module('unlimitedVoucherApp')
        .factory('UvBrand', UvBrand);

    UvBrand.$inject = ['$resource'];

    function UvBrand ($resource) {
        var resourceUrl =  'api/uv-brands/:id';

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
