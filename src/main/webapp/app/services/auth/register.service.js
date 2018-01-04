(function () {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
