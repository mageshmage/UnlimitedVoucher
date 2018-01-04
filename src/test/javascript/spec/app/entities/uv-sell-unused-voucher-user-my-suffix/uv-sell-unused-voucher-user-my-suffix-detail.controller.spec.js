'use strict';

describe('Controller Tests', function() {

    describe('UvSellUnusedVoucherUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUvSellUnusedVoucherUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUvSellUnusedVoucherUser = jasmine.createSpy('MockUvSellUnusedVoucherUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UvSellUnusedVoucherUser': MockUvSellUnusedVoucherUser
            };
            createController = function() {
                $injector.get('$controller')("UvSellUnusedVoucherUserMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unlimitedVoucherApp:uvSellUnusedVoucherUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
