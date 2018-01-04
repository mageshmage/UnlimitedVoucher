'use strict';

describe('Controller Tests', function() {

    describe('UvFreeVoucher Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUvFreeVoucher, MockUvBrand, MockUvCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUvFreeVoucher = jasmine.createSpy('MockUvFreeVoucher');
            MockUvBrand = jasmine.createSpy('MockUvBrand');
            MockUvCategory = jasmine.createSpy('MockUvCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UvFreeVoucher': MockUvFreeVoucher,
                'UvBrand': MockUvBrand,
                'UvCategory': MockUvCategory
            };
            createController = function() {
                $injector.get('$controller')("UvFreeVoucherMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unlimitedVoucherApp:uvFreeVoucherUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
