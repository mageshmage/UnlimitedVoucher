'use strict';

describe('Controller Tests', function() {

    describe('UvSellUnusedVoucher Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUvSellUnusedVoucher, MockUvBrand, MockUvCategory, MockUvSellUnusedVoucherUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUvSellUnusedVoucher = jasmine.createSpy('MockUvSellUnusedVoucher');
            MockUvBrand = jasmine.createSpy('MockUvBrand');
            MockUvCategory = jasmine.createSpy('MockUvCategory');
            MockUvSellUnusedVoucherUser = jasmine.createSpy('MockUvSellUnusedVoucherUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UvSellUnusedVoucher': MockUvSellUnusedVoucher,
                'UvBrand': MockUvBrand,
                'UvCategory': MockUvCategory,
                'UvSellUnusedVoucherUser': MockUvSellUnusedVoucherUser
            };
            createController = function() {
                $injector.get('$controller')("UvSellUnusedVoucherMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unlimitedVoucherApp:uvSellUnusedVoucherUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
